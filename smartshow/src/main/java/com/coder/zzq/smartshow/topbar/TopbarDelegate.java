package com.coder.zzq.smartshow.topbar;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coder.zzq.smartshow.Config;
import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.Utils;
import com.coder.zzq.smartshow.basebar.IBarShow;
import com.coder.zzq.smartshow.basebar.IBarShowCallback;
import com.coder.zzq.smartshow.basebar.BarDelegate;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;
import com.coder.zzq.smartshow.topbar.view.BaseTopBar;
import com.coder.zzq.smartshow.topbar.view.TopBar;
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class TopbarDelegate extends BarDelegate<TopBar, TopBar.TopbarLayout, TopbarSettingImpl> {


    private static TopbarDelegate sDelegate;

    public static boolean hasCreated() {
        return sDelegate != null;
    }

    public static TopbarDelegate get() {
        if (sDelegate == null) {
            sDelegate = new TopbarDelegate();
        }
        return sDelegate;
    }

    public IBarShow nestedDecorView() {
        //保存当前页面的Context
        Activity activity = ActivityStack.getTop();
        mPageContext = activity;
        //取出DecorView
        View view = activity == null ? null : activity.getWindow().getDecorView();

        return getFromView(view);
    }


    @Override
    protected TopBar createBar(View view) {
        TopBar topBar = TopBar.make(view, "", BaseTopBar.LENGTH_SHORT);
        int height = Utils.dpToPx(80);
        topBar.getView().getLayoutParams().height = height;
        topBar.getView().setBackgroundColor(Utils.getColorFromRes(R.color.colorPrimary));
        return topBar;
    }

    @Override
    protected boolean isDismissByGesture() {
        return false;
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
        return Config.hasToastSetting();
    }

    @Override
    protected TopbarSettingImpl createBarSetting() {
        return Config.buildSmartTopBarSetting();
    }

    @Override
    public TopbarSettingImpl getBarSetting() {
        return Config.getTopbarSetting();
    }

    public static void destroyDelegate() {
        sDelegate = null;
    }
}
