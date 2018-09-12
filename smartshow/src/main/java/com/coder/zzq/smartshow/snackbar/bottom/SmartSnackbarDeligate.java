package com.coder.zzq.smartshow.snackbar.bottom;

import android.support.annotation.RestrictTo;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coder.zzq.smartshow.snackbar.base.IBarShowCallback;
import com.coder.zzq.smartshow.snackbar.base.SmartBarDelegate;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SmartSnackbarDeligate extends SmartBarDelegate<Snackbar, Snackbar.SnackbarLayout, SnackbarSettingImpl> {

    private SmartSnackbarDeligate() {
        super();
    }

    @Override
    protected boolean isDismissByGesture() {
        return mBar.getView().getVisibility() != View.VISIBLE;
    }


    private static SmartSnackbarDeligate sDeligate;

    public static boolean hasCreated(){
        return sDeligate != null;
    }

    public static SmartSnackbarDeligate get() {

        if (sDeligate == null) {
            sDeligate = new SmartSnackbarDeligate();
        }

        return sDeligate;
    }

    private Snackbar.Callback mCallback;

    @Override
    protected Snackbar createBar(View view) {
        return Snackbar.make(view, "", Snackbar.LENGTH_SHORT);
    }


    @Override
    protected void setupBackgroundWhenNoBarSetting() {

    }

    @Override
    protected void setupBackgroundWhenHasBarSetting() {
        if (mBarSetting.backgroundHasSetup()){
            DrawableCompat.setTint(mBar.getView().getBackground(),mBarSetting.getBackgroundColor());
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
        mBar.setText(mCurMsg).setAction(mCurActionText,mOnActionClickListener)
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
        if (mBar != null){
            mBar.dismiss();
        }
    }

    @Override
    protected void createBarSetting() {
        mBarSetting = new SnackbarSettingImpl();
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
