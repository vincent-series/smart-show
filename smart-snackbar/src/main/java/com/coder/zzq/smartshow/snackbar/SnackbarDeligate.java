package com.coder.zzq.smartshow.snackbar;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coder.zzq.smartshow.bar.core.BarDelegate;
import com.coder.zzq.smartshow.bar.core.IBarShow;
import com.coder.zzq.smartshow.bar.core.IBarShowCallback;
import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;


@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SnackbarDeligate extends BarDelegate<Snackbar, Snackbar.SnackbarLayout, SnackbarSettingImpl> {
    private SnackbarSettingImpl mSnackbarSetting;

    private SnackbarDeligate() {
        super();
    }

    public IBarShow nestedContentView(Activity activity) {
        //保存当前页面的Context
        mPageContext = activity;
        //取出android.R.id.content
        View view = activity == null ? null : activity.findViewById(android.R.id.content);
        return getFromView(view);
    }

    public IBarShow nestedCoordinatorLayout(@NonNull CoordinatorLayout view) {
        //保存当前页面的Context
        mPageContext = Utils.requireNonNull(view, "传入的CoordinatorLayout不可为null！").getContext();
        return getFromView(view);
    }

    @Override
    protected boolean isDismissByGesture() {
        return mBar.getView().getVisibility() != View.VISIBLE;
    }


    private static SnackbarDeligate sDeligate;

    public static boolean hasCreated() {
        return sDeligate != null;
    }

    public static SnackbarDeligate get() {

        if (sDeligate == null) {
            sDeligate = new SnackbarDeligate();
            SmartShow.setSnackbarCallback(new SnackbarCallback());
        }

        return sDeligate;
    }

    private Snackbar.Callback mCallback;

    @Override
    protected Snackbar createBar(View view) {
        return Snackbar.make(view, "", Snackbar.LENGTH_SHORT);
    }


    @Override
    public void setup() {
        if (getBarSetting().getBackgroundColor() != -1) {
            mBar.getView().setBackgroundColor(getBarSetting().getBackgroundColor());
        }
    }

    @Override
    protected Snackbar.SnackbarLayout getBarView() {
        return (Snackbar.SnackbarLayout) mBar.getView();
    }

    @Override
    protected Button getActionView() {
        return mBar.getView().findViewById(android.support.design.R.id.snackbar_action);
    }

    @Override
    protected TextView getMsgView() {
        return mBar.getView().findViewById(android.support.design.R.id.snackbar_text);
    }


    @Override
    protected void normalShow() {
        setMsgIcon();
        mBar.setText(mCurMsg).setAction(mCurActionText, mOnActionClickListener)
                .setDuration(mDuration).show();
    }

    @Override
    protected int getShortDuration() {
        return Snackbar.LENGTH_SHORT;
    }

    @Override
    protected int getLongDuration() {
        return Snackbar.LENGTH_LONG;
    }

    @Override
    protected int getIndefiniteDuration() {
        return Snackbar.LENGTH_INDEFINITE;
    }

    @Override
    public boolean isShowing() {
        return mBar != null && mBar.isShown();
    }

    @Override
    public void dismiss() {
        if (mBar != null) {
            mBar.dismiss();
        }
    }

    @Override
    public boolean hasBarSetting() {
        return mSnackbarSetting != null;
    }

    @Override
    protected SnackbarSettingImpl createBarSetting() {
        if (mSnackbarSetting == null) {
            mSnackbarSetting = new SnackbarSettingImpl();
        }
        return mSnackbarSetting;
    }

    @Override
    public SnackbarSettingImpl getBarSetting() {
        return mSnackbarSetting;
    }


    @Override
    protected void setShowCallback() {
        if (mCallback == null) {
            mCallback = new Snackbar.Callback() {
                @Override
                public void onShown(Snackbar sb) {
                    if (mPageContext instanceof IBarShowCallback) {
                        ((IBarShowCallback<Snackbar>) mPageContext).onShown(sb);
                    }
                }

                @Override
                public void onDismissed(Snackbar sb, int event) {
                    if (mPageContext instanceof IBarShowCallback) {
                        ((IBarShowCallback<Snackbar>) mPageContext).onDismissed(sb, event);
                    }
                }
            };
        }

        mBar.addCallback(mCallback);
    }
}
