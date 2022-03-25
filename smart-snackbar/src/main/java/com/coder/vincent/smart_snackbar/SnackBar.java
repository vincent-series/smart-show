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

import com.coder.vincent.smart_snackbar.bean.SnackBarStyle;
import com.coder.vincent.smart_snackbar.view.ContentViewCallback;
import com.coder.vincent.smart_snackbar.view.SnackBarContentLayout;
import com.google.android.material.R;

public class SnackBar extends BaseTransientSnackBar<SnackBar> {

    @Nullable
    private final AccessibilityManager accessibilityManager;
    private boolean hasAction;

    private static final int[] SNACKBAR_BUTTON_STYLE_ATTR = new int[]{R.attr.snackbarButtonStyle};
    private static final int[] SNACKBAR_CONTENT_STYLE_ATTRS =
            new int[]{R.attr.snackbarButtonStyle, R.attr.snackbarTextViewStyle};

    public static class Callback extends BaseCallback<SnackBar> {
        public static final int DISMISS_EVENT_SWIPE = BaseCallback.DISMISS_EVENT_SWIPE;
        public static final int DISMISS_EVENT_ACTION = BaseCallback.DISMISS_EVENT_ACTION;
        public static final int DISMISS_EVENT_TIMEOUT = BaseCallback.DISMISS_EVENT_TIMEOUT;
        public static final int DISMISS_EVENT_MANUAL = BaseCallback.DISMISS_EVENT_MANUAL;
        public static final int DISMISS_EVENT_CONSECUTIVE = BaseCallback.DISMISS_EVENT_CONSECUTIVE;

        @Override
        public void onShown(SnackBar sb) {
            // Stub implementation to make API check happy.
        }

        @Override
        public void onDismissed(SnackBar transientBottomBar, @DismissEvent int event) {
            // Stub implementation to make API check happy.
        }
    }

    @Nullable
    private BaseCallback<SnackBar> callback;

    private SnackBar(
            @NonNull Context context,
            @NonNull ViewGroup parent,
            @NonNull View content,
            @NonNull ContentViewCallback contentViewCallback,
            SnackBarStyle style,
            @SnackBarPosition int position) {
        super(context, parent, content, contentViewCallback,style,position);
        accessibilityManager =
                (AccessibilityManager) parent.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public boolean isShown() {
        return super.isShown();
    }

    @NonNull
    public static SnackBar make(
            @NonNull ViewGroup parent,
            @NonNull CharSequence text,
            @Duration int duration,
            SnackBarStyle style,
            @SnackBarPosition int position) {
        return makeInternal(parent, text, duration,style,position);
    }

    @NonNull
    private static SnackBar makeInternal(
            @NonNull ViewGroup parent,
            @NonNull CharSequence text,
            @Duration int duration,
            SnackBarStyle style,
            @SnackBarPosition int position) {
        Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final SnackBarContentLayout content =
                (SnackBarContentLayout)
                        inflater.inflate(
                                style == SnackBarStyle.AUTO && hasSnackbarContentStyleAttrs(context)
                                        ? com.coder.vincent.smart_snackbar.R.layout.mtrl_layout_smart_snackbar_include
                                        : com.coder.vincent.smart_snackbar.R.layout.design_layout_smart_snackbar_include,
                                parent,
                                false);
        final SnackBar topSnackbar = new SnackBar(context, parent, content, content,style,position);
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

    @Nullable
    private static ViewGroup findSuitableParent(View view) {
        return (ViewGroup) view;
    }

    @NonNull
    public SnackBar setText(@NonNull CharSequence message) {
        final SnackBarContentLayout contentLayout = (SnackBarContentLayout) view.getChildAt(0);
        final TextView tv = contentLayout.getMessageView();
        tv.setText(message);
        return this;
    }

    @NonNull
    public SnackBar setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }


    @NonNull
    public SnackBar setAction(@StringRes int resId, View.OnClickListener listener) {
        return setAction(getContext().getText(resId), listener);
    }

    @NonNull
    public SnackBar setAction(
            @Nullable CharSequence text, @Nullable final View.OnClickListener listener) {
        final SnackBarContentLayout contentLayout = (SnackBarContentLayout) this.view.getChildAt(0);
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
    public SnackBar setTextColor(ColorStateList colors) {
        final SnackBarContentLayout contentLayout = (SnackBarContentLayout) view.getChildAt(0);
        final TextView tv = contentLayout.getMessageView();
        tv.setTextColor(colors);
        return this;
    }

    @NonNull
    public SnackBar setTextColor(@ColorInt int color) {
        final SnackBarContentLayout contentLayout = (SnackBarContentLayout) view.getChildAt(0);
        final TextView tv = contentLayout.getMessageView();
        tv.setTextColor(color);
        return this;
    }

    @NonNull
    public SnackBar setActionTextColor(ColorStateList colors) {
        final SnackBarContentLayout contentLayout = (SnackBarContentLayout) view.getChildAt(0);
        final TextView tv = contentLayout.getActionView();
        tv.setTextColor(colors);
        return this;
    }

    @NonNull
    public SnackBar setMaxInlineActionWidth(@Dimension int width) {
        final SnackBarContentLayout contentLayout = (SnackBarContentLayout) view.getChildAt(0);
        contentLayout.setMaxInlineActionWidth(width);
        return this;
    }

    @NonNull
    public SnackBar setActionTextColor(@ColorInt int color) {
        final SnackBarContentLayout contentLayout = (SnackBarContentLayout) view.getChildAt(0);
        final TextView tv = contentLayout.getActionView();
        tv.setTextColor(color);
        return this;
    }


    @NonNull
    public SnackBar setBackgroundTint(@ColorInt int color) {
        return setBackgroundTintList(ColorStateList.valueOf(color));
    }

    @NonNull
    public SnackBar setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        view.setBackgroundTintList(colorStateList);
        return this;
    }

    @NonNull
    public SnackBar setBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
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
