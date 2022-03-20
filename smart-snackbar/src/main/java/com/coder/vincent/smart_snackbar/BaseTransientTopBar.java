package com.coder.vincent.smart_snackbar;

import static androidx.annotation.RestrictTo.Scope.LIBRARY_GROUP;
import static com.coder.vincent.smart_snackbar.util.AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
import static com.coder.vincent.smart_snackbar.util.AnimationUtils.LINEAR_INTERPOLATOR;
import static com.coder.vincent.smart_snackbar.util.AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
import static com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.coder.vincent.smart_snackbar.util.MaterialResources;
import com.coder.vincent.smart_snackbar.util.ThemeEnforcement;
import com.coder.vincent.smart_snackbar.util.ViewUtils;
import com.coder.vincent.smart_snackbar.view.ContentViewCallback;
import com.coder.vincent.smart_snackbar.view.TopSnackBarContentLayout;
import com.google.android.material.R;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.google.android.material.color.MaterialColors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

abstract class BaseTransientTopBar<B extends BaseTransientTopBar<B>> {

    public static final int ANIMATION_MODE_SLIDE = 0;
    public static final int ANIMATION_MODE_FADE = 1;

    @IntDef({ANIMATION_MODE_SLIDE, ANIMATION_MODE_FADE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationMode {
    }

    public abstract static class BaseCallback<B> {
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_TIMEOUT = 2;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;

        @IntDef({
                DISMISS_EVENT_SWIPE,
                DISMISS_EVENT_ACTION,
                DISMISS_EVENT_TIMEOUT,
                DISMISS_EVENT_MANUAL,
                DISMISS_EVENT_CONSECUTIVE
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface DismissEvent {
        }

        public void onDismissed(B transientBottomBar, @DismissEvent int event) {
        }

        public void onShown(B transientBottomBar) {
        }
    }

    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;

    static final int ANIMATION_DURATION = 250;
    static final int ANIMATION_FADE_DURATION = 180;

    private static final int ANIMATION_FADE_IN_DURATION = 150;
    private static final int ANIMATION_FADE_OUT_DURATION = 75;
    private static final float ANIMATION_SCALE_FROM_VALUE = 0.8f;

    @NonNull
    static final Handler handler;
    static final int MSG_SHOW = 0;
    static final int MSG_DISMISS = 1;

    // On JB/KK versions of the platform sometimes View.setTranslationY does not result in
    // layout / draw pass, and CoordinatorLayout relies on a draw pass to happen to sync vertical
    // positioning of all its child views
    private static final boolean USE_OFFSET_API = VERSION.SDK_INT <= VERSION_CODES.KITKAT;

    private static final int[] SNACKBAR_STYLE_ATTR = new int[]{R.attr.snackbarStyle};

    private static final String TAG = BaseTransientTopBar.class.getSimpleName();

    static {
        handler =
                new Handler(
                        Looper.getMainLooper(),
                        message -> {
                            switch (message.what) {
                                case MSG_SHOW:
                                    ((BaseTransientTopBar) message.obj).showView();
                                    return true;
                                case MSG_DISMISS:
                                    ((BaseTransientTopBar) message.obj).hideView(message.arg1);
                                    return true;
                                default:
                                    return false;
                            }
                        });
    }

    @NonNull
    private final ViewGroup targetParent;
    private final Context context;
    @NonNull
    protected final TopSnackbarBaseLayout view;

    @NonNull
    private final ContentViewCallback contentViewCallback;

    private int duration;
    private boolean gestureInsetBottomIgnored;

    @Nullable
    private Anchor anchor;

    private boolean anchorViewLayoutListenerEnabled = false;

    @RequiresApi(VERSION_CODES.Q)
    private final Runnable bottomMarginGestureInsetRunnable =
            new Runnable() {
                @Override
                public void run() {
                    if (view == null || context == null) {
                        return;
                    }
                    // Calculate current bottom inset, factoring in translationY to account for where the
                    // view will likely be animating to.
                    int currentInsetBottom =
                            getScreenHeight() - getViewAbsoluteBottom() + (int) view.getTranslationY();
                    if (currentInsetBottom >= extraBottomMarginGestureInset) {
                        // No need to add extra offset if view is already outside of bottom gesture area
                        return;
                    }

                    LayoutParams layoutParams = view.getLayoutParams();
                    if (!(layoutParams instanceof MarginLayoutParams)) {
                        Log.w(
                                TAG,
                                "Unable to apply gesture inset because layout params are not MarginLayoutParams");
                        return;
                    }

                    // Move view outside of bottom gesture area
                    MarginLayoutParams marginParams = (MarginLayoutParams) layoutParams;
                    marginParams.bottomMargin += extraBottomMarginGestureInset - currentInsetBottom;
                    view.requestLayout();
                }
            };

    @Nullable
    private Rect originalMargins;
    private int extraBottomMarginWindowInset;
    private int extraLeftMarginWindowInset;
    private int extraRightMarginWindowInset;
    private int extraBottomMarginGestureInset;
    private int extraBottomMarginAnchorView;

    private List<BaseCallback<B>> callbacks;

    private Behavior behavior;

    @Nullable
    private final AccessibilityManager accessibilityManager;


    protected interface OnLayoutChangeListener {
        void onLayoutChange(View view, int left, int top, int right, int bottom);
    }

    protected interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(View v);

        void onViewDetachedFromWindow(View v);
    }

    protected BaseTransientTopBar(
            @NonNull ViewGroup parent,
            @NonNull View content,
            @NonNull ContentViewCallback contentViewCallback) {
        this(parent.getContext(), parent, content, contentViewCallback);
    }

    protected BaseTransientTopBar(
            @NonNull Context context,
            @NonNull ViewGroup parent,
            @NonNull View content,
            @NonNull ContentViewCallback contentViewCallback) {
        if (parent == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        }
        if (content == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        }
        if (contentViewCallback == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        }

        targetParent = parent;
        this.contentViewCallback = contentViewCallback;
        this.context = context;

        ThemeEnforcement.checkAppCompatTheme(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        // Note that for backwards compatibility reasons we inflate a layout that is defined
        // in the extending Snackbar class. This is to prevent breakage of apps that have custom
        // coordinator layout behaviors that depend on that layout.
        view = (TopSnackbarBaseLayout) inflater.inflate(getSnackbarBaseLayoutResId(), targetParent, false);
        if (content instanceof TopSnackBarContentLayout) {
            ((TopSnackBarContentLayout) content)
                    .updateActionTextColorAlphaIfNeeded(view.getActionTextColorAlpha());
            ((TopSnackBarContentLayout) content).setMaxInlineActionWidth(view.getMaxInlineActionWidth());
        }
        view.addView(content);
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof MarginLayoutParams) {
            MarginLayoutParams marginParams = (MarginLayoutParams) layoutParams;
            originalMargins =
                    new Rect(
                            marginParams.leftMargin,
                            marginParams.topMargin,
                            marginParams.rightMargin,
                            marginParams.bottomMargin);
        }

        ViewCompat.setAccessibilityLiveRegion(view, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);
        ViewCompat.setImportantForAccessibility(view, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);

        // Make sure that we fit system windows and have a listener to apply any insets
        ViewCompat.setFitsSystemWindows(view, true);
        ViewCompat.setOnApplyWindowInsetsListener(
                view,
                (v, insets) -> {
                    // Save window insets for additional margins, e.g., to dodge the system navigation bar
                    extraBottomMarginWindowInset = insets.getSystemWindowInsetBottom();
                    extraLeftMarginWindowInset = insets.getSystemWindowInsetLeft();
                    extraRightMarginWindowInset = insets.getSystemWindowInsetRight();
                    updateMargins();
                    return insets;
                });

        // Handle accessibility events
        ViewCompat.setAccessibilityDelegate(
                view,
                new AccessibilityDelegateCompat() {
                    @Override
                    public void onInitializeAccessibilityNodeInfo(
                            View host, @NonNull AccessibilityNodeInfoCompat info) {
                        super.onInitializeAccessibilityNodeInfo(host, info);
                        info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS);
                        info.setDismissable(true);
                    }

                    @Override
                    public boolean performAccessibilityAction(View host, int action, Bundle args) {
                        if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS) {
                            dismiss();
                            return true;
                        }
                        return super.performAccessibilityAction(host, action, args);
                    }
                });

        accessibilityManager =
                (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    private void updateMargins() {
        LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof MarginLayoutParams) || originalMargins == null) {
            Log.w(TAG, "Unable to update margins because layout params are not MarginLayoutParams");
            return;
        }

        int extraBottomMargin =
                getAnchorView() != null ? extraBottomMarginAnchorView : extraBottomMarginWindowInset;
        MarginLayoutParams marginParams = (MarginLayoutParams) layoutParams;
        marginParams.bottomMargin = originalMargins.bottom + extraBottomMargin;
        marginParams.leftMargin = originalMargins.left + extraLeftMarginWindowInset;
        marginParams.rightMargin = originalMargins.right + extraRightMarginWindowInset;
        view.requestLayout();

        if (VERSION.SDK_INT >= VERSION_CODES.Q && shouldUpdateGestureInset()) {
            // Ensure there is only one gesture inset runnable running at a time
            view.removeCallbacks(bottomMarginGestureInsetRunnable);
            view.post(bottomMarginGestureInsetRunnable);
        }
    }

    private boolean shouldUpdateGestureInset() {
        return extraBottomMarginGestureInset > 0 && !gestureInsetBottomIgnored && isSwipeDismissable();
    }

    private boolean isSwipeDismissable() {
        LayoutParams layoutParams = view.getLayoutParams();
        return layoutParams instanceof CoordinatorLayout.LayoutParams
                && ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior()
                instanceof SwipeDismissBehavior;
    }

    @LayoutRes
    protected int getSnackbarBaseLayoutResId() {
        return hasSnackbarStyleAttr() ? com.coder.vincent.smart_snackbar.R.layout.mtrl_layout_top_snackbar : com.coder.vincent.smart_snackbar.R.layout.design_layout_top_snackbar;
    }

    /**
     * {@link TopSnackBar}s should still work with AppCompat themes, which don't specify a {@code
     * snackbarStyle}. This method helps to check if a valid {@code snackbarStyle} is set within the
     * current context, so that we know whether we can use the attribute.
     */
    protected boolean hasSnackbarStyleAttr() {
        TypedArray a = context.obtainStyledAttributes(SNACKBAR_STYLE_ATTR);
        int snackbarStyleResId = a.getResourceId(0, -1);
        a.recycle();
        return snackbarStyleResId != -1;
    }

    @NonNull
    public B setDuration(@Duration int duration) {
        this.duration = duration;
        return (B) this;
    }

    @Duration
    public int getDuration() {
        return duration;
    }

    /**
     * Sets whether this bottom bar should adjust it's position based on the system gesture area on
     * Android Q and above.
     *
     * <p>Note: the bottom bar will only adjust it's position if it is dismissable via swipe (because
     * that would cause a gesture conflict), gesture navigation is enabled, and this {@code
     * gestureInsetBottomIgnored} flag is false.
     */
    @NonNull
    public B setGestureInsetBottomIgnored(boolean gestureInsetBottomIgnored) {
        this.gestureInsetBottomIgnored = gestureInsetBottomIgnored;
        return (B) this;
    }

    public boolean isGestureInsetBottomIgnored() {
        return gestureInsetBottomIgnored;
    }

    @AnimationMode
    public int getAnimationMode() {
        return view.getAnimationMode();
    }

    @NonNull
    public B setAnimationMode(@AnimationMode int animationMode) {
        view.setAnimationMode(animationMode);
        return (B) this;
    }

    @Nullable
    public View getAnchorView() {
        return anchor == null ? null : anchor.getAnchorView();
    }

    @NonNull
    public B setAnchorView(@Nullable View anchorView) {
        if (this.anchor != null) {
            this.anchor.unanchor();
        }
        this.anchor = anchorView == null ? null : Anchor.anchor(this, anchorView);
        return (B) this;
    }

    @NonNull
    public B setAnchorView(@IdRes int anchorViewId) {
        View anchorView = targetParent.findViewById(anchorViewId);
        if (anchorView == null) {
            throw new IllegalArgumentException("Unable to find anchor view with id: " + anchorViewId);
        }
        return setAnchorView(anchorView);
    }

    public boolean isAnchorViewLayoutListenerEnabled() {
        return anchorViewLayoutListenerEnabled;
    }

    public void setAnchorViewLayoutListenerEnabled(boolean anchorViewLayoutListenerEnabled) {
        this.anchorViewLayoutListenerEnabled = anchorViewLayoutListenerEnabled;
    }

    @NonNull
    public B setBehavior(Behavior behavior) {
        this.behavior = behavior;
        return (B) this;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    @NonNull
    public Context getContext() {
        return context;
    }

    @NonNull
    public View getView() {
        return view;
    }

    public void show() {
        TopSnackBarManager.getInstance().show(getDuration(), managerCallback);
    }

    public void dismiss() {
        dispatchDismiss(BaseCallback.DISMISS_EVENT_MANUAL);
    }

    protected void dispatchDismiss(@BaseCallback.DismissEvent int event) {
        TopSnackBarManager.getInstance().dismiss(managerCallback, event);
    }

    @NonNull
    public B addCallback(@Nullable BaseCallback<B> callback) {
        if (callback == null) {
            return (B) this;
        }
        if (callbacks == null) {
            callbacks = new ArrayList<BaseCallback<B>>();
        }
        callbacks.add(callback);
        return (B) this;
    }

    @NonNull
    public B removeCallback(@Nullable BaseCallback<B> callback) {
        if (callback == null) {
            return (B) this;
        }
        if (callbacks == null) {
            // This can happen if this method is called before the first call to addCallback
            return (B) this;
        }
        callbacks.remove(callback);
        return (B) this;
    }

    public boolean isShown() {
        return TopSnackBarManager.getInstance().isCurrent(managerCallback);
    }

    public boolean isShownOrQueued() {
        return TopSnackBarManager.getInstance().isCurrentOrNext(managerCallback);
    }

    @NonNull
    TopSnackBarManager.Callback managerCallback =
            new TopSnackBarManager.Callback() {
                @Override
                public void show() {
                    handler.sendMessage(handler.obtainMessage(MSG_SHOW, BaseTransientTopBar.this));
                }

                @Override
                public void dismiss(int event) {
                    handler.sendMessage(
                            handler.obtainMessage(MSG_DISMISS, event, 0, BaseTransientTopBar.this));
                }
            };

    @NonNull
    protected SwipeDismissBehavior<? extends View> getNewBehavior() {
        return new Behavior();
    }

    final void showView() {
        this.view.setOnAttachStateChangeListener(
                new OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
                            WindowInsets insets = view.getRootWindowInsets();
                            if (insets != null) {
                                extraBottomMarginGestureInset = insets.getMandatorySystemGestureInsets().bottom;
                                updateMargins();
                            }
                        }
                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {
                        if (isShownOrQueued()) {
                            // If we haven't already been dismissed then this event is coming from a
                            // non-user initiated action. Hence we need to make sure that we callback
                            // and keep our state up to date. We need to post the call since
                            // removeView() will call through to onDetachedFromWindow and thus overflow.
                            handler.post(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            onViewHidden(BaseCallback.DISMISS_EVENT_MANUAL);
                                        }
                                    });
                        }
                    }
                });

        if (this.view.getParent() == null) {
            LayoutParams lp = this.view.getLayoutParams();

            if (lp instanceof CoordinatorLayout.LayoutParams) {
                setUpBehavior((CoordinatorLayout.LayoutParams) lp);
            }

            recalculateAndUpdateMargins();

            // Set view to INVISIBLE so it doesn't flash on the screen before the inset adjustment is
            // handled and the enter animation is started
            view.setVisibility(View.INVISIBLE);
            targetParent.addView(this.view);
        }

        if (ViewCompat.isLaidOut(this.view)) {
            showViewImpl();
            return;
        }

        // Otherwise, add one of our layout change listeners and show it in when laid out
        this.view.setOnLayoutChangeListener(
                new OnLayoutChangeListener() {
                    @Override
                    public void onLayoutChange(View view, int left, int top, int right, int bottom) {
                        BaseTransientTopBar.this.view.setOnLayoutChangeListener(null);
                        BaseTransientTopBar.this.showViewImpl();
                    }
                });
    }

    private void showViewImpl() {
        if (shouldAnimate()) {
            // If animations are enabled, animate it in
            animateViewIn();
        } else {
            // Else if animations are disabled, just make view VISIBLE and call back now
            if (view.getParent() != null) {
                view.setVisibility(View.VISIBLE);
            }
            onViewShown();
        }
    }

    private int getViewAbsoluteBottom() {
        int[] absoluteLocation = new int[2];
        view.getLocationOnScreen(absoluteLocation);
        return absoluteLocation[1] + view.getHeight();
    }

    @RequiresApi(VERSION_CODES.JELLY_BEAN_MR1)
    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void setUpBehavior(CoordinatorLayout.LayoutParams lp) {
        // If our LayoutParams are from a CoordinatorLayout, we'll setup our Behavior
        CoordinatorLayout.LayoutParams clp = lp;

        SwipeDismissBehavior<? extends View> behavior =
                this.behavior == null ? getNewBehavior() : this.behavior;

        if (behavior instanceof BaseTransientTopBar.Behavior) {
            ((Behavior) behavior).setBaseTransientBottomBar(this);
        }

        behavior.setListener(
                new SwipeDismissBehavior.OnDismissListener() {
                    @Override
                    public void onDismiss(@NonNull View view) {
                        if (view.getParent() != null) {
                            view.setVisibility(View.GONE);
                        }
                        dispatchDismiss(BaseCallback.DISMISS_EVENT_SWIPE);
                    }

                    @Override
                    public void onDragStateChanged(int state) {
                        switch (state) {
                            case SwipeDismissBehavior.STATE_DRAGGING:
                            case SwipeDismissBehavior.STATE_SETTLING:
                                // If the view is being dragged or settling, pause the timeout
                                TopSnackBarManager.getInstance().pauseTimeout(managerCallback);
                                break;
                            case SwipeDismissBehavior.STATE_IDLE:
                                // If the view has been released and is idle, restore the timeout
                                TopSnackBarManager.getInstance().restoreTimeoutIfPaused(managerCallback);
                                break;
                            default:
                                // Any other state is ignored
                        }
                    }
                });
        clp.setBehavior(behavior);
        // Also set the inset edge so that views can dodge the bar correctly, but only if there is
        // no anchor view.
        if (getAnchorView() == null) {
            clp.insetEdge = Gravity.BOTTOM;
        }
    }

    private void recalculateAndUpdateMargins() {
        extraBottomMarginAnchorView = calculateBottomMarginForAnchorView();
        updateMargins();
    }

    private int calculateBottomMarginForAnchorView() {
        if (getAnchorView() == null) {
            return 0;
        }

        int[] anchorViewLocation = new int[2];
        getAnchorView().getLocationOnScreen(anchorViewLocation);
        int anchorViewAbsoluteYTop = anchorViewLocation[1];

        int[] targetParentLocation = new int[2];
        targetParent.getLocationOnScreen(targetParentLocation);
        int targetParentAbsoluteYBottom = targetParentLocation[1] + targetParent.getHeight();

        return targetParentAbsoluteYBottom - anchorViewAbsoluteYTop;
    }

    void animateViewIn() {
        // Post to make sure animation doesn't start until after all inset handling has completed
        view.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if (view == null) {
                            return;
                        }
                        // Make view VISIBLE now that we are about to start the enter animation
                        if (view.getParent() != null) {
                            view.setVisibility(View.VISIBLE);
                        }
                        if (view.getAnimationMode() == ANIMATION_MODE_FADE) {
                            startFadeInAnimation();
                        } else {
                            startSlideInAnimation();
                        }
                    }
                });
    }

    private void animateViewOut(int event) {
        if (view.getAnimationMode() == ANIMATION_MODE_FADE) {
            startFadeOutAnimation(event);
        } else {
            startSlideOutAnimation(event);
        }
    }

    private void startFadeInAnimation() {
        ValueAnimator alphaAnimator = getAlphaAnimator(0, 1);
        ValueAnimator scaleAnimator = getScaleAnimator(ANIMATION_SCALE_FROM_VALUE, 1);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, scaleAnimator);
        animatorSet.setDuration(ANIMATION_FADE_IN_DURATION);
        animatorSet.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        onViewShown();
                    }
                });
        animatorSet.start();
    }

    private void startFadeOutAnimation(final int event) {
        ValueAnimator animator = getAlphaAnimator(1, 0);
        animator.setDuration(ANIMATION_FADE_OUT_DURATION);
        animator.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        onViewHidden(event);
                    }
                });
        animator.start();
    }

    private ValueAnimator getAlphaAnimator(float... alphaValues) {
        ValueAnimator animator = ValueAnimator.ofFloat(alphaValues);
        animator.setInterpolator(LINEAR_INTERPOLATOR);
        animator.addUpdateListener(
                new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                        view.setAlpha((Float) valueAnimator.getAnimatedValue());
                    }
                });
        return animator;
    }

    private ValueAnimator getScaleAnimator(float... scaleValues) {
        ValueAnimator animator = ValueAnimator.ofFloat(scaleValues);
        animator.setInterpolator(LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        animator.addUpdateListener(
                new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                        float scale = (float) valueAnimator.getAnimatedValue();
                        view.setScaleX(scale);
                        view.setScaleY(scale);
                    }
                });
        return animator;
    }

    private void startSlideInAnimation() {
        final int translationYBottom = getTranslationYBottom();
        if (USE_OFFSET_API) {
            ViewCompat.offsetTopAndBottom(view, translationYBottom);
        } else {
            view.setTranslationY(translationYBottom);
        }

        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(translationYBottom, 0);
        animator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(ANIMATION_DURATION);
        animator.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        contentViewCallback.animateContentIn(
                                ANIMATION_DURATION - ANIMATION_FADE_DURATION, ANIMATION_FADE_DURATION);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        onViewShown();
                    }
                });
        animator.addUpdateListener(
                new AnimatorUpdateListener() {
                    private int previousAnimatedIntValue = translationYBottom;

                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                        int currentAnimatedIntValue = (int) animator.getAnimatedValue();
                        if (USE_OFFSET_API) {
                            // On JB/KK versions of the platform sometimes View.setTranslationY does not
                            // result in layout / draw pass
                            ViewCompat.offsetTopAndBottom(
                                    view, currentAnimatedIntValue - previousAnimatedIntValue);
                        } else {
                            view.setTranslationY(currentAnimatedIntValue);
                        }
                        previousAnimatedIntValue = currentAnimatedIntValue;
                    }
                });
        animator.start();
    }

    private void startSlideOutAnimation(final int event) {
        ValueAnimator animator = new ValueAnimator();
        animator.setIntValues(0, getTranslationYBottom());
        animator.setInterpolator(FAST_OUT_SLOW_IN_INTERPOLATOR);
        animator.setDuration(ANIMATION_DURATION);
        animator.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        contentViewCallback.animateContentOut(0, ANIMATION_FADE_DURATION);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        onViewHidden(event);
                    }
                });
        animator.addUpdateListener(
                new AnimatorUpdateListener() {
                    private int previousAnimatedIntValue = 0;

                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator animator) {
                        int currentAnimatedIntValue = (int) animator.getAnimatedValue();
                        if (USE_OFFSET_API) {
                            // On JB/KK versions of the platform sometimes View.setTranslationY does not
                            // result in layout / draw pass
                            ViewCompat.offsetTopAndBottom(
                                    view, currentAnimatedIntValue - previousAnimatedIntValue);
                        } else {
                            view.setTranslationY(currentAnimatedIntValue);
                        }
                        previousAnimatedIntValue = currentAnimatedIntValue;
                    }
                });
        animator.start();
    }

    private int getTranslationYBottom() {
        int translationY = view.getHeight();
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof MarginLayoutParams) {
            translationY += ((MarginLayoutParams) layoutParams).bottomMargin;
        }
        return -translationY;
    }

    final void hideView(@BaseCallback.DismissEvent int event) {
        if (shouldAnimate() && view.getVisibility() == View.VISIBLE) {
            animateViewOut(event);
        } else {
            // If anims are disabled or the view isn't visible, just call back now
            onViewHidden(event);
        }
    }

    void onViewShown() {
        TopSnackBarManager.getInstance().onShown(managerCallback);
        if (callbacks != null) {
            // Notify the callbacks. Do that from the end of the list so that if a callback
            // removes itself as the result of being called, it won't mess up with our iteration
            int callbackCount = callbacks.size();
            for (int i = callbackCount - 1; i >= 0; i--) {
                callbacks.get(i).onShown((B) this);
            }
        }
    }

    void onViewHidden(int event) {
        // First tell the SnackbarManager that it has been dismissed
        TopSnackBarManager.getInstance().onDismissed(managerCallback);
        if (callbacks != null) {
            // Notify the callbacks. Do that from the end of the list so that if a callback
            // removes itself as the result of being called, it won't mess up with our iteration
            int callbackCount = callbacks.size();
            for (int i = callbackCount - 1; i >= 0; i--) {
                callbacks.get(i).onDismissed((B) this, event);
            }
        }

        // Lastly, hide and remove the view from the parent (if attached)
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
    }

    /**
     * Returns true if we should animate the Snackbar view in/out.
     */
    boolean shouldAnimate() {
        if (accessibilityManager == null) {
            return true;
        }
        int feedbackFlags = AccessibilityServiceInfo.FEEDBACK_SPOKEN;
        List<AccessibilityServiceInfo> serviceList =
                accessibilityManager.getEnabledAccessibilityServiceList(feedbackFlags);
        return serviceList != null && serviceList.isEmpty();
    }

    protected static class TopSnackbarBaseLayout extends FrameLayout {

        private static final OnTouchListener consumeAllTouchListener =
                new OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Prevent touches from passing through this view.
                        return true;
                    }
                };

        private BaseTransientTopBar.OnLayoutChangeListener onLayoutChangeListener;
        private BaseTransientTopBar.OnAttachStateChangeListener onAttachStateChangeListener;
        @AnimationMode
        private int animationMode;
        private final float backgroundOverlayColorAlpha;
        private final float actionTextColorAlpha;
        private final int maxWidth;
        private final int maxInlineActionWidth;
        private ColorStateList backgroundTint;
        private PorterDuff.Mode backgroundTintMode;

        protected TopSnackbarBaseLayout(@NonNull Context context) {
            this(context, null);
        }

        protected TopSnackbarBaseLayout(@NonNull Context context, AttributeSet attrs) {
            super(wrap(context, attrs, 0, 0), attrs);
            // Ensure we are using the correctly themed context rather than the context that was passed
            // in.
            context = getContext();
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SnackbarLayout);
            if (a.hasValue(R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(
                        this, a.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
            }
            animationMode = a.getInt(R.styleable.SnackbarLayout_animationMode, ANIMATION_MODE_SLIDE);
            backgroundOverlayColorAlpha =
                    a.getFloat(R.styleable.SnackbarLayout_backgroundOverlayColorAlpha, 1);
            setBackgroundTintList(
                    MaterialResources.getColorStateList(
                            context, a, R.styleable.SnackbarLayout_backgroundTint));
            setBackgroundTintMode(
                    ViewUtils.parseTintMode(
                            a.getInt(R.styleable.SnackbarLayout_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN));
            actionTextColorAlpha = a.getFloat(R.styleable.SnackbarLayout_actionTextColorAlpha, 1);
            maxWidth = a.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
            maxInlineActionWidth =
                    a.getDimensionPixelSize(R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
            a.recycle();

            setOnTouchListener(consumeAllTouchListener);
            setFocusable(true);

            if (getBackground() == null) {
                ViewCompat.setBackground(this, createThemedBackground());
            }
        }

        @Override
        public void setBackground(@Nullable Drawable drawable) {
            setBackgroundDrawable(drawable);
        }

        @Override
        public void setBackgroundDrawable(@Nullable Drawable drawable) {
            if (drawable != null && backgroundTint != null) {
                drawable = DrawableCompat.wrap(drawable.mutate());
                DrawableCompat.setTintList(drawable, backgroundTint);
                DrawableCompat.setTintMode(drawable, backgroundTintMode);
            }
            super.setBackgroundDrawable(drawable);
        }

        @Override
        public void setBackgroundTintList(@Nullable ColorStateList backgroundTint) {
            this.backgroundTint = backgroundTint;
            if (getBackground() != null) {
                Drawable wrappedBackground = DrawableCompat.wrap(getBackground().mutate());
                DrawableCompat.setTintList(wrappedBackground, backgroundTint);
                DrawableCompat.setTintMode(wrappedBackground, backgroundTintMode);
                if (wrappedBackground != getBackground()) {
                    super.setBackgroundDrawable(wrappedBackground);
                }
            }
        }

        @Override
        public void setBackgroundTintMode(@Nullable PorterDuff.Mode backgroundTintMode) {
            this.backgroundTintMode = backgroundTintMode;
            if (getBackground() != null) {
                Drawable wrappedBackground = DrawableCompat.wrap(getBackground().mutate());
                DrawableCompat.setTintMode(wrappedBackground, backgroundTintMode);
                if (wrappedBackground != getBackground()) {
                    super.setBackgroundDrawable(wrappedBackground);
                }
            }
        }

        @Override
        public void setOnClickListener(@Nullable OnClickListener onClickListener) {
            // Clear touch listener that consumes all touches if there is a custom click listener.
            setOnTouchListener(onClickListener != null ? null : consumeAllTouchListener);
            super.setOnClickListener(onClickListener);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            if (maxWidth > 0 && getMeasuredWidth() > maxWidth) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.EXACTLY);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (onLayoutChangeListener != null) {
                onLayoutChangeListener.onLayoutChange(this, l, t, r, b);
            }
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (onAttachStateChangeListener != null) {
                onAttachStateChangeListener.onViewAttachedToWindow(this);
            }

            ViewCompat.requestApplyInsets(this);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (onAttachStateChangeListener != null) {
                onAttachStateChangeListener.onViewDetachedFromWindow(this);
            }
        }

        void setOnLayoutChangeListener(
                BaseTransientTopBar.OnLayoutChangeListener onLayoutChangeListener) {
            this.onLayoutChangeListener = onLayoutChangeListener;
        }

        void setOnAttachStateChangeListener(
                BaseTransientTopBar.OnAttachStateChangeListener listener) {
            onAttachStateChangeListener = listener;
        }

        @AnimationMode
        int getAnimationMode() {
            return animationMode;
        }

        void setAnimationMode(@AnimationMode int animationMode) {
            this.animationMode = animationMode;
        }

        float getBackgroundOverlayColorAlpha() {
            return backgroundOverlayColorAlpha;
        }

        float getActionTextColorAlpha() {
            return actionTextColorAlpha;
        }

        int getMaxWidth() {
            return maxWidth;
        }

        int getMaxInlineActionWidth() {
            return maxInlineActionWidth;
        }

        @NonNull
        private Drawable createThemedBackground() {
            float cornerRadius =
                    getResources().getDimension(R.dimen.mtrl_snackbar_background_corner_radius);

            GradientDrawable background = new GradientDrawable();
            background.setShape(GradientDrawable.RECTANGLE);
            background.setCornerRadius(cornerRadius);

            int backgroundColor =
                    MaterialColors.layer(
                            this, R.attr.colorSurface, R.attr.colorOnSurface, getBackgroundOverlayColorAlpha());
            background.setColor(backgroundColor);
            if (backgroundTint != null) {
                Drawable wrappedDrawable = DrawableCompat.wrap(background);
                DrawableCompat.setTintList(wrappedDrawable, backgroundTint);
                return wrappedDrawable;
            } else {
                return DrawableCompat.wrap(background);
            }
        }
    }

    public static class Behavior extends SwipeDismissBehavior<View> {
        @NonNull
        private final BehaviorDelegate delegate;

        public Behavior() {
            delegate = new BehaviorDelegate(this);
        }

        private void setBaseTransientBottomBar(
                @NonNull BaseTransientTopBar<?> baseTransientTopBar) {
            delegate.setBaseTransientBottomBar(baseTransientTopBar);
        }

        @Override
        public boolean canSwipeDismissView(View child) {
            return delegate.canSwipeDismissView(child);
        }

        @Override
        public boolean onInterceptTouchEvent(
                @NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent event) {
            delegate.onInterceptTouchEvent(parent, child, event);
            return super.onInterceptTouchEvent(parent, child, event);
        }
    }

    /**
     * @hide
     */
    @RestrictTo(LIBRARY_GROUP)
    // TODO(b/76413401): Delegate can be rolled up into behavior after widget migration is finished.
    public static class BehaviorDelegate {
        private TopSnackBarManager.Callback managerCallback;

        public BehaviorDelegate(@NonNull SwipeDismissBehavior<?> behavior) {
            behavior.setStartAlphaSwipeDistance(0.1f);
            behavior.setEndAlphaSwipeDistance(0.6f);
            behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);
        }

        public void setBaseTransientBottomBar(
                @NonNull BaseTransientTopBar<?> baseTransientTopBar) {
            this.managerCallback = baseTransientTopBar.managerCallback;
        }

        public boolean canSwipeDismissView(View child) {
            return child instanceof BaseTransientTopBar.TopSnackbarBaseLayout;
        }

        public void onInterceptTouchEvent(
                @NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    // We want to make sure that we disable any Snackbar timeouts if the user is
                    // currently touching the Snackbar. We restore the timeout when complete
                    if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                        TopSnackBarManager.getInstance().pauseTimeout(managerCallback);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    TopSnackBarManager.getInstance().restoreTimeoutIfPaused(managerCallback);
                    break;
                default:
                    break;
            }
        }
    }

    @SuppressWarnings("rawtypes") // Generic type of BaseTransientBottomBar doesn't matter here.
    static class Anchor
            implements View.OnAttachStateChangeListener, OnGlobalLayoutListener {
        @NonNull
        private final WeakReference<BaseTransientTopBar> transientBottomBar;

        @NonNull
        private final WeakReference<View> anchorView;

        static Anchor anchor(
                @NonNull BaseTransientTopBar transientBottomBar, @NonNull View anchorView) {
            Anchor anchor = new Anchor(transientBottomBar, anchorView);
            if (ViewCompat.isAttachedToWindow(anchorView)) {
                ViewUtils.addOnGlobalLayoutListener(anchorView, anchor);
            }
            anchorView.addOnAttachStateChangeListener(anchor);
            return anchor;
        }

        private Anchor(
                @NonNull BaseTransientTopBar transientBottomBar, @NonNull View anchorView) {
            this.transientBottomBar = new WeakReference<>(transientBottomBar);
            this.anchorView = new WeakReference<>(anchorView);
        }

        @Override
        public void onViewAttachedToWindow(View anchorView) {
            if (unanchorIfNoTransientBottomBar()) {
                return;
            }
            ViewUtils.addOnGlobalLayoutListener(anchorView, this);
        }

        @Override
        public void onViewDetachedFromWindow(View anchorView) {
            if (unanchorIfNoTransientBottomBar()) {
                return;
            }
            ViewUtils.removeOnGlobalLayoutListener(anchorView, this);
        }

        @Override
        public void onGlobalLayout() {
            if (unanchorIfNoTransientBottomBar()
                    || !transientBottomBar.get().anchorViewLayoutListenerEnabled) {
                return;
            }
            transientBottomBar.get().recalculateAndUpdateMargins();
        }

        @Nullable
        View getAnchorView() {
            return anchorView.get();
        }

        private boolean unanchorIfNoTransientBottomBar() {
            if (transientBottomBar.get() == null) {
                unanchor();
                return true;
            }
            return false;
        }

        void unanchor() {
            if (anchorView.get() != null) {
                anchorView.get().removeOnAttachStateChangeListener(this);
                ViewUtils.removeOnGlobalLayoutListener(anchorView.get(), this);
            }
            anchorView.clear();
            transientBottomBar.clear();
        }
    }
}
