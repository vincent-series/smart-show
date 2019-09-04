package com.coder.zzq.smartshow.topbar.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.coder.zzq.smartshow.topbar.R;


public final class TopBar extends BaseTopBar<TopBar> {

    public static final int LENGTH_INDEFINITE = BaseTopBar.LENGTH_INDEFINITE;

    public static final int LENGTH_SHORT = BaseTopBar.LENGTH_SHORT;

    public static final int LENGTH_LONG = BaseTopBar.LENGTH_LONG;


    public static class Callback extends BaseCallback<TopBar> {

        public static final int DISMISS_EVENT_SWIPE = BaseCallback.DISMISS_EVENT_SWIPE;
        public static final int DISMISS_EVENT_ACTION = BaseCallback.DISMISS_EVENT_ACTION;
        public static final int DISMISS_EVENT_TIMEOUT = BaseCallback.DISMISS_EVENT_TIMEOUT;
        public static final int DISMISS_EVENT_MANUAL = BaseCallback.DISMISS_EVENT_MANUAL;
        public static final int DISMISS_EVENT_CONSECUTIVE = BaseCallback.DISMISS_EVENT_CONSECUTIVE;

        @Override
        public void onShown(TopBar topBar) {

        }

        @Override
        public void onDismissed(TopBar topBar, @DismissEvent int event) {

        }
    }

    @Nullable
    private BaseCallback<TopBar> mCallback;

    private TopBar(ViewGroup parent, View content, ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
    }

    @NonNull
    public static TopBar make(@NonNull View view, @NonNull CharSequence text,
                              @Duration int duration) {
        final ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. "
                    + "Please provide a valid view.");
        }

        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final ContentLayout content =
                (ContentLayout) inflater.inflate(
                        R.layout.topbar_include,
                        parent, false);

        final TopBar snackbar = new TopBar(parent, content, content);
        snackbar.setText(text);
        snackbar.setDuration(duration);
        return snackbar;
    }

    @NonNull
    public static TopBar make(@NonNull View view, @StringRes int resId, @Duration int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    private static ViewGroup findSuitableParent(View view) {
        return (ViewGroup) view;
    }


    @NonNull
    public TopBar setText(@NonNull CharSequence message) {
        final ContentLayout contentLayout = (ContentLayout) mView.getChildAt(0);
        final TextView tv = contentLayout.getMessageView();
        tv.setText(message);
        return this;
    }


    @NonNull
    public TopBar setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }

    @NonNull
    public TopBar setAction(@StringRes int resId, View.OnClickListener listener) {
        return setAction(getContext().getText(resId), listener);
    }

    @NonNull
    public TopBar setAction(CharSequence text, final View.OnClickListener listener) {
        final ContentLayout contentLayout = (ContentLayout) mView.getChildAt(0);
        final TextView tv = contentLayout.getActionView();

        if (TextUtils.isEmpty(text) || listener == null) {
            tv.setVisibility(View.GONE);
            tv.setOnClickListener(null);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
            tv.setOnClickListener(new View.OnClickListener() {
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


    @NonNull
    public TopBar setActionTextColor(ColorStateList colors) {
        final ContentLayout contentLayout = (ContentLayout) mView.getChildAt(0);
        final TextView tv = contentLayout.getActionView();
        tv.setTextColor(colors);
        return this;
    }

    @NonNull
    public TopBar setActionTextColor(@ColorInt int color) {
        final ContentLayout contentLayout = (ContentLayout) mView.getChildAt(0);
        final TextView tv = contentLayout.getActionView();
        tv.setTextColor(color);
        return this;
    }


    @Deprecated
    @NonNull
    public TopBar setCallback(Callback callback) {
        // The logic in this method emulates what we had before support for multiple
        // registered callbacks.
        if (mCallback != null) {
            removeCallback(mCallback);
        }
        if (callback != null) {
            addCallback(callback);
        }
        // Update the deprecated field so that we can remove the passed callback the next
        // time we're called
        mCallback = callback;
        return this;
    }


    public static final class TopbarLayout extends BaseTopBar.TopbarBaseLayout {
        public TopbarLayout(Context context) {
            super(context);
        }

        public TopbarLayout(Context context, AttributeSet attrs) {
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
                    child.measure(View.MeasureSpec.makeMeasureSpec(availableWidth, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(),
                                    View.MeasureSpec.EXACTLY));
                }
            }
        }
    }
}

