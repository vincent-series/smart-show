package com.coder.zzq.smartshow.topbar;

import com.coder.zzq.smartshow.bar.core.BarSettingImpl;
import com.coder.zzq.smartshow.topbar.view.TopBar;

final class TopbarSettingImpl extends BarSettingImpl<TopBar.TopbarLayout, ITopbarSetting>
        implements ITopbarSetting {

    private boolean mLightBackground;

    public TopbarSettingImpl() {
        super();
    }

    @Override
    public ITopbarSetting darkStatusBarTextAndIcon() {
        mLightBackground = true;
        return this;
    }

    public boolean isLightBackground() {
        return mLightBackground;
    }
}
