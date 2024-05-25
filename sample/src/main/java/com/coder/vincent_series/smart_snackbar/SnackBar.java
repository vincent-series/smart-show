package com.coder.vincent_series.smart_snackbar;

import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_CONTROLS;
import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_ICONS;
import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_TEXT;
import static com.coder.vincent_series.smart_snackbar.ConstantsKt.FAST_OUT_SLOW_IN_INTERPOLATOR;
import static com.coder.vincent_series.smart_snackbar.ConstantsKt.LINEAR_INTERPOLATOR;
import static com.coder.vincent_series.smart_snackbar.ConstantsKt.LINEAR_OUT_SLOW_IN_INTERPOLATOR;
import static com.coder.vincent_series.smart_snackbar.MaterialThemeOverlay.wrap;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
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
import android.os.Message;
import android.text.TextUtils;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.coder.vincent_series.smart_snackbar.bean.SnackBarStyle;
import com.coder.vincent_series.smart_snackbar.view.ContentViewCallback;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SnackBar<B extends SnackBar<B>> {

    protected SnackBarStyle snackBarStyle = SnackBarStyle.MATERIAL;
    protected static final int[] SNACKBAR_CONTENT_STYLE_ATTRS =
            new int[]{R.attr.snackbarButtonStyle, R.attr.snackbarTextViewStyle};
    protected boolean hasAction;
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
            // empty
        }

        public void onShown(B transientBottomBar) {
            // empty
        }
    }

    @IntRange(from = LENGTH_INDEFINITE)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;

    // Legacy slide animation duration constant.
    static final int DEFAULT_SLIDE_ANIMATION_DURATION = 250;
    // Legacy slide animation content fade duration constant.
    static final int DEFAULT_ANIMATION_FADE_DURATION = 180;
    // Legacy slide animation interpolator constant.
    private static final TimeInterpolator DEFAULT_ANIMATION_SLIDE_INTERPOLATOR =
            FAST_OUT_SLOW_IN_INTERPOLATOR;

    // Fade and scale animation constants.
    private static final int DEFAULT_ANIMATION_FADE_IN_DURATION = 150;
    private static final int DEFAULT_ANIMATION_FADE_OUT_DURATION = 75;
    private static final TimeInterpolator DEFAULT_ANIMATION_FADE_INTERPOLATOR = LINEAR_INTERPOLATOR;
    private static final TimeInterpolator DEFAULT_ANIMATION_SCALE_INTERPOLATOR =
            LINEAR_OUT_SLOW_IN_INTERPOLATOR;
    private static final float ANIMATION_SCALE_FROM_VALUE = 0.8f;

    private final int animationFadeInDuration;
    private final int animationFadeOutDuration;
    private final int animationSlideDuration;
    private final TimeInterpolator animationFadeInterpolator;
    private final TimeInterpolator animationSlideInterpolator;
    private final TimeInterpolator animationScaleInterpolator;

    @NonNull
    static final Handler handler;
    static final int MSG_SHOW = 0;
    static final int MSG_DISMISS = 1;

    // On JB/KK versions of the platform sometimes View.setTranslationY does not result in
    // layout / draw pass, and CoordinatorLayout relies on a draw pass to happen to sync vertical
    // positioning of all its child views
    private static final boolean USE_OFFSET_API =
            (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN)
                    && (VERSION.SDK_INT <= VERSION_CODES.KITKAT);

    private static final int[] SNACKBAR_STYLE_ATTR = new int[]{R.attr.snackbarStyle};

    private static final String TAG = SnackBar.class.getSimpleName();

    static {
        handler =
                new Handler(
                        Looper.getMainLooper(),
                        new Handler.Callback() {
                            @Override
                            public boolean handleMessage(@NonNull Message message) {
                                switch (message.what) {
                                    case MSG_SHOW:
                                        ((SnackBar) message.obj).showView();
                                        return true;
                                    case MSG_DISMISS:
                                        ((SnackBar) message.obj).hideView(message.arg1);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
    }

    @NonNull
    private final ViewGroup targetParent;
    private final Context context;
    @NonNull
    protected final SnackbarBaseLayout view;

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

    private int extraBottomMarginWindowInset;
    private int extraLeftMarginWindowInset;
    private int extraRightMarginWindowInset;
    private int extraBottomMarginGestureInset;
    private int extraBottomMarginAnchorView;

    private boolean pendingShowingView;

    private List<BaseCallback<B>> callbacks;

    private Behavior behavior;

    @Nullable
    protected final AccessibilityManager accessibilityManager;

    @NonNull
    public static SnackBar makeInternal(
            @Nullable Context context,
            @NonNull View view,
            @NonNull CharSequence text,
            @Duration int duration) {
        final ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException(
                    "No suitable parent found from the given view. Please provide a valid view.");
        }

        if (context == null) {
            context = parent.getContext();
        }

        final LayoutInflater inflater = LayoutInflater.from(context);
        final SnackbarContentLayout content =
                (SnackbarContentLayout)
                        inflater.inflate(
                                getSnackbarContentLayoutResId(SnackBarStyle.CLASSIC, context),
                                parent,
                                false);
        final SnackBar snackbar = new SnackBar(context, parent, content, content);
        snackbar.setText(text);
        snackbar.setDuration(duration);
        return snackbar;
    }

    public SnackBar<B> setText(@NonNull CharSequence message) {
        getMessageView().setText(message);
        return this;
    }


    @NonNull
    public SnackBar<B> setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }

    @NonNull
    public SnackBar<B> setAction(@StringRes int resId, View.OnClickListener listener) {
        return setAction(getContext().getText(resId), listener);
    }

    protected static int getSnackbarContentLayoutResId(SnackBarStyle snackBarStyle, Context context) {
        if (snackBarStyle == SnackBarStyle.AUTO && hasSnackbarContentStyleAttrs(context)) {
            return R.layout.mtrl_layout_snackbar_include;
        }

        return snackBarStyle == SnackBarStyle.MATERIAL ? R.layout.default_mtrl_layout_snackbar : R.layout.design_layout_snackbar_include;
    }


    protected static boolean hasSnackbarContentStyleAttrs(@NonNull Context context) {
        TypedArray a = context.obtainStyledAttributes(SNACKBAR_CONTENT_STYLE_ATTRS);
        int snackbarButtonStyleResId = a.getResourceId(0, -1);
        int snackbarTextViewStyleResId = a.getResourceId(1, -1);
        a.recycle();
        return snackbarButtonStyleResId != -1 && snackbarTextViewStyleResId != -1;
    }

    protected SnackBar(
            @NonNull ViewGroup parent,
            @NonNull View content,
            @NonNull ContentViewCallback contentViewCallback) {
        this(parent.getContext(), parent, content, contentViewCallback);
    }

    protected SnackBar(
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
        accessibilityManager =
                (AccessibilityManager) parent.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        ThemeEnforcement.checkAppCompatTheme(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        // Note that for backwards compatibility reasons we inflate a layout that is defined
        // in the extending Snackbar class. This is to prevent breakage of apps that have custom
        // coordinator layout behaviors that depend on that layout.
        view = (SnackbarBaseLayout) inflater.inflate(getSnackbarBaseLayoutResId(), targetParent, false);
        view.setSnackBar(this);
        if (content instanceof SnackbarContentLayout) {
            ((SnackbarContentLayout) content)
                    .updateActionTextColorAlphaIfNeeded(view.getActionTextColorAlpha());
            ((SnackbarContentLayout) content).setMaxInlineActionWidth(view.getMaxInlineActionWidth());
        }
        view.addView(content);

        ViewCompat.setAccessibilityLiveRegion(view, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);
        ViewCompat.setImportantForAccessibility(view, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);

        // Make sure that we fit system windows and have a listener to apply any insets
        ViewCompat.setFitsSystemWindows(view, true);
        ViewCompat.setOnApplyWindowInsetsListener(
                view,
                new OnApplyWindowInsetsListener() {
                    @NonNull
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(
                            View v, @NonNull WindowInsetsCompat insets) {
                        // Save window insets for additional margins, e.g., to dodge the system navigation bar
                        extraBottomMarginWindowInset = insets.getSystemWindowInsetBottom();
                        extraLeftMarginWindowInset = insets.getSystemWindowInsetLeft();
                        extraRightMarginWindowInset = insets.getSystemWindowInsetRight();
                        updateMargins();
                        return insets;
                    }
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

//        accessibilityManager =
//                (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);

        animationSlideDuration = MotionUtils.resolveThemeDuration(context, R.attr.motionDurationLong2,
                DEFAULT_SLIDE_ANIMATION_DURATION);
        animationFadeInDuration = MotionUtils.resolveThemeDuration(context, R.attr.motionDurationLong2,
                DEFAULT_ANIMATION_FADE_IN_DURATION);
        animationFadeOutDuration =
                MotionUtils.resolveThemeDuration(
                        context, R.attr.motionDurationMedium1, DEFAULT_ANIMATION_FADE_OUT_DURATION);
        animationFadeInterpolator =
                MotionUtils.resolveThemeInterpolator(
                        context,
                        R.attr.motionEasingEmphasizedInterpolator,
                        DEFAULT_ANIMATION_FADE_INTERPOLATOR);
        animationScaleInterpolator =
                MotionUtils.resolveThemeInterpolator(
                        context,
                        R.attr.motionEasingEmphasizedInterpolator,
                        DEFAULT_ANIMATION_SCALE_INTERPOLATOR);
        animationSlideInterpolator =
                MotionUtils.resolveThemeInterpolator(
                        context,
                        R.attr.motionEasingEmphasizedInterpolator,
                        DEFAULT_ANIMATION_SLIDE_INTERPOLATOR);
    }

    private void updateMargins() {
        LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof MarginLayoutParams) || view.originalMargins == null) {
            Log.w(TAG, "Unable to update margins because layout params are not MarginLayoutParams");
            return;
        }
        if (view.getParent() == null) {
            // Parent will set layout params to view again. Wait for addView() is done to update layout
            // params, in case we save the already updated margins as the original margins.
            return;
        }

        int extraBottomMargin =
                getAnchorView() != null ? extraBottomMarginAnchorView : extraBottomMarginWindowInset;
        MarginLayoutParams marginParams = (MarginLayoutParams) layoutParams;
        marginParams.bottomMargin = view.originalMargins.bottom + extraBottomMargin;
        marginParams.leftMargin = view.originalMargins.left + extraLeftMarginWindowInset;
        marginParams.rightMargin = view.originalMargins.right + extraRightMarginWindowInset;
        marginParams.topMargin = view.originalMargins.top;
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
        if (snackBarStyle == SnackBarStyle.AUTO && hasSnackbarStyleAttr()) {
            return R.layout.mtrl_layout_snackbar;
        }
        return snackBarStyle == SnackBarStyle.MATERIAL ? R.layout.default_mtrl_layout_snackbar : R.layout.design_layout_snackbar;
    }


    protected boolean hasSnackbarStyleAttr() {
        TypedArray a = context.obtainStyledAttributes(SNACKBAR_STYLE_ATTR);
        int snackbarStyleResId = a.getResourceId(0, -1);
        a.recycle();
        return snackbarStyleResId != -1;
    }


    @Nullable
    public static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof CoordinatorLayout) {
                // We've found a CoordinatorLayout, use it
                return (ViewGroup) view;
            } else if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                } else {
                    // It's not the content view but we'll use it as our fallback
                    fallback = (ViewGroup) view;
                }
            }

            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return fallback;
    }

    @NonNull
    public B setDuration(@Duration int duration) {
        this.duration = duration;
        return (B) this;
    }

    /**
     * Return the duration.
     *
     * @see #setDuration
     */
    @Duration
    public int getDuration() {
        int userSetDuration = duration;
        if (userSetDuration == LENGTH_INDEFINITE) {
            return LENGTH_INDEFINITE;
        }

        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            int controlsFlag = hasAction ? FLAG_CONTENT_CONTROLS : 0;
            return accessibilityManager.getRecommendedTimeoutMillis(
                    userSetDuration, controlsFlag | FLAG_CONTENT_ICONS | FLAG_CONTENT_TEXT);
        }

        // If touch exploration is enabled override duration to give people chance to interact.
        return hasAction && accessibilityManager.isTouchExplorationEnabled()
                ? LENGTH_INDEFINITE
                : userSetDuration;
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

    /**
     * Returns whether this bottom bar should adjust it's position based on the system gesture area on
     * Android Q and above. See {@link #setGestureInsetBottomIgnored(boolean)}.
     */
    public boolean isGestureInsetBottomIgnored() {
        return gestureInsetBottomIgnored;
    }

    /**
     * Returns the animation mode.
     *
     * @see #setAnimationMode(int)
     */
    @AnimationMode
    public int getAnimationMode() {
        return view.getAnimationMode();
    }

    /**
     * Sets the animation mode.
     *
     * @param animationMode of {@link #ANIMATION_MODE_SLIDE} or {@link #ANIMATION_MODE_FADE}.
     * @see #getAnimationMode()
     */
    @NonNull
    public B setAnimationMode(@AnimationMode int animationMode) {
        view.setAnimationMode(animationMode);
        return (B) this;
    }

    /**
     * Returns the anchor view for this {@link SnackBar}.
     *
     * @see #setAnchorView(View)
     */
    @Nullable
    public View getAnchorView() {
        return anchor == null ? null : anchor.getAnchorView();
    }

    /**
     * Sets the view the {@link SnackBar} should be anchored above.
     */
    @NonNull
    public B setAnchorView(@Nullable View anchorView) {
        if (this.anchor != null) {
            this.anchor.unanchor();
        }
        this.anchor = anchorView == null ? null : Anchor.anchor(this, anchorView);
        return (B) this;
    }

    /**
     * Sets the view the {@link SnackBar} should be anchored above by id.
     *
     * @throws IllegalArgumentException if the anchor view is not found.
     */
    @NonNull
    public B setAnchorView(@IdRes int anchorViewId) {
        View anchorView = targetParent.findViewById(anchorViewId);
        if (anchorView == null) {
            throw new IllegalArgumentException("Unable to find anchor view with id: " + anchorViewId);
        }
        return setAnchorView(anchorView);
    }

    /**
     * Returns whether the anchor view layout listener is enabled.
     *
     * @see #setAnchorViewLayoutListenerEnabled(boolean)
     */
    public boolean isAnchorViewLayoutListenerEnabled() {
        return anchorViewLayoutListenerEnabled;
    }

    /**
     * Sets whether the anchor view layout listener is enabled. If enabled, the {@link
     * SnackBar} will recalculate and update its position when the position of the
     * anchor view is changed.
     */
    public void setAnchorViewLayoutListenerEnabled(boolean anchorViewLayoutListenerEnabled) {
        this.anchorViewLayoutListenerEnabled = anchorViewLayoutListenerEnabled;
    }

    /**
     * Sets the {@link Behavior} to be used in this {@link
     * SnackBar}.
     *
     * @param behavior {@link Behavior} to be applied.
     */
    @NonNull
    public B setBehavior(Behavior behavior) {
        this.behavior = behavior;
        return (B) this;
    }

    /**
     * Return the behavior.
     *
     * @see #setBehavior(Behavior)
     */
    public Behavior getBehavior() {
        return behavior;
    }

    /**
     * Returns the {@link SnackBar}'s context.
     */
    @NonNull
    public Context getContext() {
        return context;
    }

    /**
     * Returns the {@link SnackBar}'s view.
     */
    @NonNull
    public View getView() {
        return view;
    }

    /**
     * Show the {@link SnackBar}.
     */
    public void show() {
        SnackbarManager.getInstance().show(getDuration(), managerCallback);
    }

    /**
     * Dismiss the {@link SnackBar}.
     */
    public void dismiss() {
        dispatchDismiss(BaseCallback.DISMISS_EVENT_MANUAL);
    }

    protected void dispatchDismiss(@BaseCallback.DismissEvent int event) {
        SnackbarManager.getInstance().dismiss(managerCallback, event);
    }

    /**
     * Adds the specified callback to the list of callbacks that will be notified of transient bottom
     * bar events.
     *
     * @param callback Callback to notify when transient bottom bar events occur.
     * @see #removeCallback(BaseCallback)
     */
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

    /**
     * Removes the specified callback from the list of callbacks that will be notified of transient
     * bottom bar events.
     *
     * @param callback Callback to remove from being notified of transient bottom bar events
     * @see #addCallback(BaseCallback)
     */
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

    /**
     * Return whether this {@link SnackBar} is currently being shown.
     */
    public boolean isShown() {
        return SnackbarManager.getInstance().isCurrent(managerCallback);
    }

    /**
     * Returns whether this {@link SnackBar} is currently being shown, or is queued to
     * be shown next.
     */
    public boolean isShownOrQueued() {
        return SnackbarManager.getInstance().isCurrentOrNext(managerCallback);
    }

    @NonNull
    SnackbarManager.Callback managerCallback =
            new SnackbarManager.Callback() {
                @Override
                public void show() {
                    handler.sendMessage(handler.obtainMessage(MSG_SHOW, SnackBar.this));
                }

                @Override
                public void dismiss(int event) {
                    handler.sendMessage(
                            handler.obtainMessage(MSG_DISMISS, event, 0, SnackBar.this));
                }
            };

    @NonNull
    protected SwipeDismissBehavior<? extends View> getNewBehavior() {
        return new Behavior();
    }

    final void showView() {
        if (this.view.getParent() == null) {
            LayoutParams lp = this.view.getLayoutParams();

            if (lp instanceof CoordinatorLayout.LayoutParams) {
                setUpBehavior((CoordinatorLayout.LayoutParams) lp);
            }

            this.view.addToTargetParent(targetParent);
            recalculateAndUpdateMargins();

            // Set view to INVISIBLE so it doesn't flash on the screen before the inset adjustment is
            // handled and the enter animation is started
            view.setVisibility(View.INVISIBLE);
        }

        if (ViewCompat.isLaidOut(this.view)) {
            showViewImpl();
            return;
        }

        // Otherwise, show it in when laid out
        pendingShowingView = true;
    }

    void onAttachedToWindow() {
        if (VERSION.SDK_INT >= VERSION_CODES.Q) {
            WindowInsets insets = view.getRootWindowInsets();
            if (insets != null) {
                extraBottomMarginGestureInset = insets.getMandatorySystemGestureInsets().bottom;
                updateMargins();
            }
        }
    }

    void onDetachedFromWindow() {
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

    void onLayoutChange() {
        if (pendingShowingView) {
            SnackBar.this.showViewImpl();
            pendingShowingView = false;
        }
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

        if (behavior instanceof SnackBar.Behavior) {
            ((Behavior) behavior).setSnackBar(this);
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
                                SnackbarManager.getInstance().pauseTimeout(managerCallback);
                                break;
                            case SwipeDismissBehavior.STATE_IDLE:
                                // If the view has been released and is idle, restore the timeout
                                SnackbarManager.getInstance().restoreTimeoutIfPaused(managerCallback);
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
        int newBottomMarginAnchorView = calculateBottomMarginForAnchorView();
        if (newBottomMarginAnchorView == extraBottomMarginAnchorView) {
            return;
        }
        extraBottomMarginAnchorView = newBottomMarginAnchorView;
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
        animatorSet.setDuration(animationFadeInDuration);
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
        animator.setDuration(animationFadeOutDuration);
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
        animator.setInterpolator(animationFadeInterpolator);
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
        animator.setInterpolator(animationScaleInterpolator);
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
        animator.setInterpolator(animationSlideInterpolator);
        animator.setDuration(animationSlideDuration);
        animator.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        contentViewCallback.animateContentIn(
                                animationSlideDuration - animationFadeInDuration,
                                animationFadeInDuration);
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
        animator.setInterpolator(animationSlideInterpolator);
        animator.setDuration(animationSlideDuration);
        animator.addListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        contentViewCallback.animateContentOut(0, animationFadeOutDuration);
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
        return translationY;
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
        SnackbarManager.getInstance().onShown(managerCallback);
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
        SnackbarManager.getInstance().onDismissed(managerCallback);
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

    /**
     * @hide
     */
    public static final class SnackBarLayout extends SnackbarBaseLayout {
        public SnackBarLayout(Context context) {
            super(context);
        }

        public SnackBarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    }

    public static class SnackbarBaseLayout extends FrameLayout {
        private static final OnTouchListener consumeAllTouchListener =
                new OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        // Prevent touches from passing through this view.
                        return true;
                    }
                };

        @Nullable
        private SnackBar<?> SnackBar;
        @AnimationMode
        private int animationMode;
        private final float backgroundOverlayColorAlpha;
        private final float actionTextColorAlpha;
        private final int maxWidth;
        private final int maxInlineActionWidth;
        private ColorStateList backgroundTint;
        private PorterDuff.Mode backgroundTintMode;
        @Nullable
        Object shapeAppearanceModel;
        @Nullable
        private Rect originalMargins;
        private boolean addingToTargetParent;

        protected SnackbarBaseLayout(@NonNull Context context) {
            this(context, null);
        }

        protected SnackbarBaseLayout(@NonNull Context context, AttributeSet attrs) {
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
            if (a.hasValue(R.styleable.SnackbarLayout_shapeAppearance)
                    || a.hasValue(R.styleable.SnackbarLayout_shapeAppearanceOverlay)) {
                Class<?> shapeModelClass = null;
                try {
                    shapeModelClass = Class.forName("com.google.android.material.shape.ShapeAppearanceModel");
                    Object builder = shapeModelClass.getDeclaredMethod("builder", Context.class, AttributeSet.class, int.class, int.class)
                            .invoke(null, context, attrs, 0, 0);
                    shapeAppearanceModel = builder.getClass().getDeclaredMethod("build")
                            .invoke(builder);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
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

            int childCount = getChildCount();
            int availableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child.getLayoutParams().width == ViewGroup.LayoutParams.MATCH_PARENT) {
                    child.measure(
                            MeasureSpec.makeMeasureSpec(availableWidth, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), MeasureSpec.EXACTLY));
                }
            }
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            if (SnackBar != null) {
                SnackBar.onLayoutChange();
            }
        }

        @Override
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (SnackBar != null) {
                SnackBar.onAttachedToWindow();
            }
            ViewCompat.requestApplyInsets(this);
        }

        @Override
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (SnackBar != null) {
                SnackBar.onDetachedFromWindow();
            }
        }

        @Override
        public void setLayoutParams(ViewGroup.LayoutParams params) {
            super.setLayoutParams(params);
            if (!addingToTargetParent && params instanceof MarginLayoutParams) {
                // Do not update the original margins when the layout is being added to its target parent,
                // since the margins are just copied from the existing layout params, which can already be
                // updated with extra margins.
                updateOriginalMargins((MarginLayoutParams) params);
                if (SnackBar != null) {
                    SnackBar.updateMargins();
                }
            }
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

        void addToTargetParent(ViewGroup targetParent) {
            addingToTargetParent = true;
            targetParent.addView(this);
            addingToTargetParent = false;
        }

        private void setSnackBar(SnackBar<?> SnackBar) {
            this.SnackBar = SnackBar;
        }

        private void updateOriginalMargins(MarginLayoutParams params) {
            originalMargins =
                    new Rect(params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin);
        }

        @NonNull
        private Drawable createThemedBackground() {
            int backgroundColor =
                    MaterialColors.layer(ContextCompat.getColor(getContext(), R.color.colorSurface),
                            ContextCompat.getColor(getContext(), R.color.colorOnSurface), getBackgroundOverlayColorAlpha());
            // Only use newer MaterialShapeDrawable background approach if shape appearance is set, in
            // order to preserve the original GradientDrawable background for pre-M3 Snackbars.
            Drawable background = shapeAppearanceModel != null ? createMaterialShapeDrawableBackground(backgroundColor, shapeAppearanceModel) : createGradientDrawableBackground(backgroundColor, getResources());
            if (backgroundTint != null) {
                Drawable wrappedDrawable = DrawableCompat.wrap(background);
                DrawableCompat.setTintList(wrappedDrawable, backgroundTint);
                return wrappedDrawable;
            } else {
                return DrawableCompat.wrap(background);
            }
        }
    }

    @NonNull
    private static Drawable createMaterialShapeDrawableBackground(
            @ColorInt int backgroundColor, @NonNull Object shapeAppearanceModel) {
        Object background;
        try {
            Class<?> shapeDrawableClass = Class.forName("com.google.android.material.shape.MaterialShapeDrawable");
            background = shapeDrawableClass.getDeclaredConstructor(shapeAppearanceModel.getClass())
                    .newInstance(shapeAppearanceModel);
            shapeDrawableClass.getDeclaredMethod("setFillColor", ColorStateList.class)
                    .invoke(background, ColorStateList.valueOf(backgroundColor));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }

        return ((Drawable) background);
    }

    @NonNull
    private static GradientDrawable createGradientDrawableBackground(
            @ColorInt int backgroundColor, @NonNull Resources resources) {
        float cornerRadius = resources.getDimension(R.dimen.mtrl_snackbar_background_corner_radius);
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setCornerRadius(cornerRadius);
        background.setColor(backgroundColor);
        return background;
    }


    public static class Behavior extends SwipeDismissBehavior<View> {
        @NonNull
        private final BehaviorDelegate delegate;

        public Behavior() {
            delegate = new BehaviorDelegate(this);
        }

        private void setSnackBar(
                @NonNull SnackBar<?> SnackBar) {
            delegate.setSnackBar(SnackBar);
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

    public static class BehaviorDelegate {
        private SnackbarManager.Callback managerCallback;

        public BehaviorDelegate(@NonNull SwipeDismissBehavior<?> behavior) {
            behavior.setStartAlphaSwipeDistance(0.1f);
            behavior.setEndAlphaSwipeDistance(0.6f);
            behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END);
        }

        public void setSnackBar(
                @NonNull SnackBar<?> SnackBar) {
            this.managerCallback = SnackBar.managerCallback;
        }

        public boolean canSwipeDismissView(View child) {
            return child instanceof SnackbarBaseLayout;
        }

        public void onInterceptTouchEvent(
                @NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    // We want to make sure that we disable any Snackbar timeouts if the user is
                    // currently touching the Snackbar. We restore the timeout when complete
                    if (parent.isPointInChildBounds(child, (int) event.getX(), (int) event.getY())) {
                        SnackbarManager.getInstance().pauseTimeout(managerCallback);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    SnackbarManager.getInstance().restoreTimeoutIfPaused(managerCallback);
                    break;
                default:
                    break;
            }
        }
    }

    @SuppressWarnings("rawtypes") // Generic type of SnackBar doesn't matter here.
    static class Anchor
            implements View.OnAttachStateChangeListener, OnGlobalLayoutListener {
        @NonNull
        private final WeakReference<SnackBar> transientBottomBar;

        @NonNull
        private final WeakReference<View> anchorView;

        static Anchor anchor(
                @NonNull SnackBar transientBottomBar, @NonNull View anchorView) {
            Anchor anchor = new Anchor(transientBottomBar, anchorView);
            if (ViewCompat.isAttachedToWindow(anchorView)) {
                ViewUtils.addOnGlobalLayoutListener(anchorView, anchor);
            }
            anchorView.addOnAttachStateChangeListener(anchor);
            return anchor;
        }

        private Anchor(
                @NonNull SnackBar transientBottomBar, @NonNull View anchorView) {
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

    public TextView getMessageView() {
        return getContentLayout().getMessageView();
    }

    public Button getActionView() {
        return getContentLayout().getActionView();
    }

    public SnackbarContentLayout getContentLayout() {
        return (SnackbarContentLayout) view.getChildAt(0);
    }


    @NonNull
    public SnackBar<B> setAction(
            @Nullable CharSequence text, @Nullable final View.OnClickListener listener) {
        final TextView tv = getActionView();
        if (TextUtils.isEmpty(text) || listener == null) {
            tv.setVisibility(View.GONE);
            tv.setOnClickListener(null);
            hasAction = false;
        } else {
            hasAction = true;
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
            tv.setOnClickListener(
                    view -> {
                        listener.onClick(view);
                        // Now dismiss the Snackbar
                        dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION);
                    });
        }
        return this;
    }

    @NonNull
    public SnackBar<B> setBackgroundTint(@ColorInt int color) {
        return setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @NonNull
    public SnackBar<B> setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        view.setBackgroundTintList(colorStateList);
        return this;
    }

    @NonNull
    public SnackBar<B> setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        view.setBackgroundTintMode(mode);
        return this;
    }

    /**
     * Sets the text color of the message specified in {@link #setText(CharSequence)} and {@link
     * #setText(int)}.
     */
    @NonNull
    public SnackBar<B> setTextColor(ColorStateList colors) {
        getMessageView().setTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the message specified in {@link #setText(CharSequence)} and {@link
     * #setText(int)}.
     */
    @NonNull
    public SnackBar<B> setTextColor(@ColorInt int color) {
        getMessageView().setTextColor(color);
        return this;
    }

    @NonNull
    public SnackBar<B> setTextMaxLines(int maxLines) {
        getMessageView().setMaxLines(maxLines);
        return this;
    }

    @NonNull
    public SnackBar<B> setActionTextColor(ColorStateList colors) {
        getActionView().setTextColor(colors);
        return this;
    }

    @NonNull
    public SnackBar<B> setMaxInlineActionWidth(@Dimension int width) {
        getContentLayout().setMaxInlineActionWidth(width);
        return this;
    }

    @NonNull
    public SnackBar<B> setActionTextColor(@ColorInt int color) {
        getActionView().setTextColor(color);
        return this;
    }
}
