package com.coder.zzq.smartshow.topbar;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.bar.core.BarDelegate;
import com.coder.zzq.smartshow.bar.core.IBarShow;
import com.coder.zzq.smartshow.bar.core.IBarShowCallback;
import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.topbar.view.BaseTopBar;
import com.coder.zzq.smartshow.topbar.view.TopBar;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class TopbarDelegate extends BarDelegate<TopBar, TopBar.TopbarLayout, TopbarSettingImpl> {

    private TopbarSettingImpl mTopbarSetting;

    private static TopbarDelegate sDelegate;

    public static boolean hasCreated() {
        return sDelegate != null;
    }

    public static TopbarDelegate get() {
        if (sDelegate == null) {
            sDelegate = new TopbarDelegate();
            SmartShow.setTopbarCallback(new TopbarCallback());
        }
        return sDelegate;
    }

    public IBarShow nestedDecorView(Activity activity) {
        //保存当前页面的Context
        mPageContext = activity;
        //取出DecorView
        ViewGroup decorView = activity == null ? null : (ViewGroup) activity.getWindow().getDecorView();
        CoordinatorLayout topbarContainer = null;
        if (decorView != null) {
            topbarContainer = decorView.findViewById(R.id.smart_show_top_bar_container);
            if (topbarContainer == null) {
                topbarContainer = new CoordinatorLayout(activity);
                topbarContainer.setId(R.id.smart_show_top_bar_container);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                ViewCompat.setElevation(topbarContainer, 1f);
                decorView.addView(topbarContainer, lp);
            }

        }
        return getFromView(topbarContainer);
    }


    @Override
    protected TopBar createBar(View view) {
        TopBar topBar = TopBar.make(view, "", BaseTopBar.LENGTH_SHORT);
        ViewGroup.LayoutParams barViewLp = topBar.getView().getLayoutParams();
        barViewLp.height = Utils.getStatusBarHeight() + Utils.getToolbarHeight();
        topBar.getView().setLayoutParams(barViewLp);

        View v = ((ViewGroup) topBar.getView()).getChildAt(0);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        v.setLayoutParams(lp);
        return topBar;
    }

    @Override
    protected boolean isDismissByGesture() {
        return mBar.getView().getVisibility() != View.VISIBLE;
    }

    @Override
    public void setup() {
        mBar.getView().setBackgroundColor(getBarSetting().getBackgroundColor());
        if (getBarSetting().isLightBackground() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mBar.getView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
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
        setMsgIcon();
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
    public boolean hasBarSetting() {
        return mTopbarSetting != null;
    }

    @Override
    protected TopbarSettingImpl createBarSetting() {

        if (mTopbarSetting == null) {
            mTopbarSetting = new TopbarSettingImpl();
        }
        return mTopbarSetting;
    }

    @Override
    public TopbarSettingImpl getBarSetting() {
        return mTopbarSetting;
    }

}
