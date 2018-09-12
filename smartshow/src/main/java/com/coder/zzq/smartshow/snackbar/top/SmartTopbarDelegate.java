package com.coder.zzq.smartshow.snackbar.top;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.Utils;
import com.coder.zzq.smartshow.snackbar.base.IBarShowCallback;
import com.coder.zzq.smartshow.snackbar.base.SmartBarDelegate;
import com.coder.zzq.smartshow.snackbar.top.view.BaseTopBar;
import com.coder.zzq.smartshow.snackbar.top.view.TopBar;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;

public final class SmartTopbarDelegate extends SmartBarDelegate<TopBar, TopBar.TopbarLayout, TopbarSettingImpl> {


    private static SmartTopbarDelegate sDelegate;

    public static boolean hasCreated() {
        return sDelegate != null;
    }

    public static SmartTopbarDelegate get() {
        if (sDelegate == null) {
            sDelegate = new SmartTopbarDelegate();
        }
        return sDelegate;
    }


    @Override
    protected TopBar createBar(View view) {
        TopBar topBar = TopBar.make(view, "", BaseTopBar.LENGTH_SHORT);
        int height = Utils.hasActionbar(ActivityStack.getTop()) ?
                Utils.dpToPx(30) : Utils.dpToPx(57);
        topBar.getView().getLayoutParams().height = height;

        return topBar;
    }

    @Override
    protected boolean isDismissByGesture() {
        return mBar.getView().getVisibility() != View.VISIBLE;
    }

    @Override
    protected void setupBackgroundWhenNoBarSetting() {
        if (!Utils.hasActionbar(ActivityStack.getTop())){
            mBar.getView().setBackgroundColor(Utils.getStatusBarColor());
        }
    }

    @Override
    protected void setupBackgroundWhenHasBarSetting() {
        int color = Utils.hasActionbar(ActivityStack.getTop()) ?
                mBarSetting.getBgColorWhenBelowActionBar() :
                mBarSetting.getBgColorWhenBelowStatusBar();
        if (color == -1){
            setupBackgroundWhenNoBarSetting();
        }else {
            mBar.getView().setBackgroundColor(color);
        }
    }

    @Override
    protected TopBar.TopbarLayout getBarView() {
        return (TopBar.TopbarLayout) mBar.getView();
    }

    @Override
    protected Button getActionView() {
        return mBar.getView().findViewById(R.id.topbar_action);
    }

    @Override
    protected TextView getMsgView() {

        return mBar.getView().findViewById(R.id.topbar_text);
    }


    private TopBar.Callback mCallback;

    @Override
    protected void setShowCallback() {
        if (mCallback == null) {
            mCallback = new TopBar.Callback() {
                @Override
                public void onShown(TopBar topBar) {
                    if (mPageContext instanceof IBarShowCallback) {
                        ((IBarShowCallback<TopBar>) mPageContext).onShown(topBar);
                    }
                }

                @Override
                public void onDismissed(TopBar topBar, int event) {
                    if (mPageContext instanceof IBarShowCallback) {
                        ((IBarShowCallback<TopBar>) mPageContext).onDismissed(topBar, event);
                    }
                }
            };
        }
        mBar.addCallback(mCallback);
    }

    @Override
    protected void normalShow() {
        mBar.setText(mCurMsg).setAction(mCurActionText, mOnActionClickListener).setDuration(mDuration).show();
    }

    @Override
    protected int getShortDuration() {
        return TopBar.LENGTH_SHORT;
    }

    @Override
    protected int getLongDuration() {
        return TopBar.LENGTH_LONG;
    }

    @Override
    protected int getIndefiniteDuration() {
        return TopBar.LENGTH_INDEFINITE;
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
    protected void createBarSetting() {
        mBarSetting = new TopbarSettingImpl();
    }


}
