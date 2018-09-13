package com.coder.zzq.smartshow.topbar;

import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.Utils;
import com.coder.zzq.smartshow.basebar.BarSettingImpl;
import com.coder.zzq.smartshow.topbar.view.TopBar;

public class TopbarSettingImpl extends BarSettingImpl<TopBar.TopbarLayout, ITopbarSetting>
        implements ITopbarSetting {

    private boolean mLightBackground;

    public TopbarSettingImpl(){
        super();
        backgroundColor(Utils.getColorFromRes(R.color.colorPrimary));
    }

    @Override
    public ITopbarSetting darkStatusBarTextAndIcon() {
        mLightBackground = true;
        return this;
    }

    public boolean isLightBackground(){
        return mLightBackground;
    }
}
