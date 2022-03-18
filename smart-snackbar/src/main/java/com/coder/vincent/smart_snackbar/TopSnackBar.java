package com.coder.vincent.smart_snackbar;

import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_CONTROLS;
import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_ICONS;
import static android.view.accessibility.AccessibilityManager.FLAG_CONTENT_TEXT;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.coder.vincent.smart_snackbar.view.ContentViewCallback;
import com.coder.vincent.smart_snackbar.view.TopSnackBarContentLayout;
import com.google.android.material.R;

public class TopSnackBar extends BaseTransientTopBar<TopSnackBar> {

    @Nullable
    private final AccessibilityManager accessibilityManager;
    private boolean hasAction;

    private static final int[] SNACKBAR_BUTTON_STYLE_ATTR = new int[]{R.attr.snackbarButtonStyle};
    private static final int[] SNACKBAR_CONTENT_STYLE_ATTRS =
            new int[]{R.attr.snackbarButtonStyle, R.attr.snackbarTextViewStyle};

    public static class Callback extends BaseCallback<TopSnackBar> {
        public static final int DISMISS_EVENT_SWIPE = BaseCallback.DISMISS_EVENT_SWIPE;
        public static final int DISMISS_EVENT_ACTION = BaseCallback.DISMISS_EVENT_ACTION;
        public static final int DISMISS_EVENT_TIMEOUT = BaseCallback.DISMISS_EVENT_TIMEOUT;
        public static final int DISMISS_EVENT_MANUAL = BaseCallback.DISMISS_EVENT_MANUAL;
        public static final int DISMISS_EVENT_CONSECUTIVE = BaseCallback.DISMISS_EVENT_CONSECUTIVE;

        @Override
        public void onShown(TopSnackBar sb) {
            // Stub implementation to make API check happy.
        }

        @Override
        public void onDismissed(TopSnackBar transientBottomBar, @DismissEvent int event) {
            // Stub implementation to make API check happy.
        }
    }

    @Nullable
    private BaseCallback<TopSnackBar> callback;

    private TopSnackBar(
            @NonNull Context context,
            @NonNull ViewGroup parent,
            @NonNull View content,
            @NonNull ContentViewCallback contentViewCallback) {
        super(context, parent, content, contentViewCallback);
        accessibilityManager =
                (AccessibilityManager) parent.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    // TODO: Delete this once custom Robolectric shadows no longer depend on this method being present
    // (and instead properly utilize BaseTransientBottomBar hierarchy).
    @Override
    public void show() {
        super.show();
    }

    // TODO: Delete this once custom Robolectric shadows no longer depend on this method being present
    // (and instead properly utilize BaseTransientBottomBar hierarchy).
    @Override
    public void dismiss() {
        super.dismiss();
    }

    // TODO: Delete this once custom Robolectric shadows no longer depend on this method being present
    // (and instead properly utilize BaseTransientBottomBar hierarchy).
    @Override
    public boolean isShown() {
        return super.isShown();
    }

    @NonNull
    public static TopSnackBar make(
            @NonNull View view, @NonNull CharSequence text, @Duration int duration) {
        return makeInternal(/* context= */ null, view, text, duration);
    }

    @NonNull
    public static TopSnackBar make(
            @NonNull Context context,
            @NonNull View view,
            @NonNull CharSequence text,
            @Duration int duration) {
        return makeInternal(context, view, text, duration);
    }

    @NonNull
    private static TopSnackBar makeInternal(
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
        final TopSnackBarContentLayout content =
                (TopSnackBarContentLayout)
                        inflater.inflate(
                                hasSnackbarContentStyleAttrs(context)
                                        ? com.coder.vincent.smart_snackbar.R.layout.mtrl_layout_top_snackbar_include
                                        : com.coder.vincent.smart_snackbar.R.layout.design_layout_top_snackbar_include,
                                parent,
                                false);
        final TopSnackBar topSnackbar = new TopSnackBar(context, parent, content, content);
        topSnackbar.setText(text);
        topSnackbar.setDuration(duration);
        return topSnackbar;
    }

    @Deprecated
    protected static boolean hasSnackbarButtonStyleAttr(@NonNull Context context) {
        TypedArray a = context.obtainStyledAttributes(SNACKBAR_BUTTON_STYLE_ATTR);
        int snackbarButtonStyleResId = a.getResourceId(0, -1);
        a.recycle();
        return snackbarButtonStyleResId != -1;
    }

    private static boolean hasSnackbarContentStyleAttrs(@NonNull Context context) {
        TypedArray a = context.obtainStyledAttributes(SNACKBAR_CONTENT_STYLE_ATTRS);
        int snackbarButtonStyleResId = a.getResourceId(0, -1);
        int snackbarTextViewStyleResId = a.getResourceId(1, -1);
        a.recycle();
        return snackbarButtonStyleResId != -1 && snackbarTextViewStyleResId != -1;
    }

    @NonNull
    public static TopSnackBar make(@NonNull View view, @StringRes int resId, @Duration int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    @Nullable
    private static ViewGroup findSuitableParent(View view) {
        return (ViewGroup) view;
    }

    @NonNull
    public TopSnackBar setText(@NonNull CharSequence message) {
        final TopSnackBarContentLayout contentLayout = (TopSnackBarContentLayout) view.getChildAt(0);
        final TextView tv = contentLayout.getMessageView();
        tv.setText(message);
        return this;
    }

    @NonNull
    public TopSnackBar setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }


    @NonNull
    public TopSnackBar setAction(@StringRes int resId, View.OnClickListener listener) {
        return setAction(getContext().getText(resId), listener);
    }

    @NonNull
    public TopSnackBar setAction(
            @Nullable CharSequence text, @Nullable final View.OnClickListener listener) {
        final TopSnackBarContentLayout contentLayout = (TopSnackBarContentLayout) this.view.getChildAt(0);
        final TextView tv = contentLayout.getActionView();
        if (TextUtils.isEmpty(text) || listener == null) {
            tv.setVisibility(View.GONE);
            tv.setOnClickListener(null);
            hasAction = false;
        } else {
            hasAction = true;
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
            tv.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listener.onClick(view);
                            // Now dismiss the Snackbar
                            dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION);
                        }
                    });
        }
        return this;
    }

    @Override
    @Duration
    public int getDuration() {
        int userSetDuration = super.getDuration();
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

    @NonNull
    public TopSnackBar setTextColor(ColorStateList colors) {
        final TopSnackBarContentLayout contentLayout = (TopSnackBarContentLayout) view.getChildAt(0);
        final TextView tv = contentLayout.getMessageView();
        tv.setTextColor(colors);
        return this;
    }

    @NonNull
    public TopSnackBar setTextColor(@ColorInt int color) {
        final TopSnackBarContentLayout contentLayout = (TopSnackBarContentLayout) view.getChildAt(0);
        final TextView tv = contentLayout.getMessageView();
        tv.setTextColor(color);
        return this;
    }

    @NonNull
    public TopSnackBar setActionTextColor(ColorStateList colors) {
        final TopSnackBarContentLayout contentLayout = (TopSnackBarContentLayout) view.getChildAt(0);
        final TextView tv = contentLayout.getActionView();
        tv.setTextColor(colors);
        return this;
    }

    @NonNull
    public TopSnackBar setMaxInlineActionWidth(@Dimension int width) {
        final TopSnackBarContentLayout contentLayout = (TopSnackBarContentLayout) view.getChildAt(0);
        contentLayout.setMaxInlineActionWidth(width);
        return this;
    }

    @NonNull
    public TopSnackBar setActionTextColor(@ColorInt int color) {
        final TopSnackBarContentLayout contentLayout = (TopSnackBarContentLayout) view.getChildAt(0);
        final TextView tv = contentLayout.getActionView();
        tv.setTextColor(color);
        return this;
    }


    @NonNull
    public TopSnackBar setBackgroundTint(@ColorInt int color) {
        return setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @NonNull
    public TopSnackBar setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        view.setBackgroundTintList(colorStateList);
        return this;
    }

    @NonNull
    public TopSnackBar setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        view.setBackgroundTintMode(mode);
        return this;
    }

    public static final class TopSnackBarLayout extends TopSnackbarBaseLayout {
        public TopSnackBarLayout(Context context) {
            super(context);
        }

        public TopSnackBarLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            // Work around our backwards-compatible refactoring of Snackbar and inner content
            // being inflated against snackbar's parent (instead of against the snackbar itself).
            // Every child that is width=MATCH_PARENT is remeasured again and given the full width
            // minus the paddings.
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
    }
}
