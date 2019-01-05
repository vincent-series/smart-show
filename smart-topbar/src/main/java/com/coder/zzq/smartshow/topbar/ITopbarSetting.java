package com.coder.zzq.smartshow.topbar;


import com.coder.zzq.smartshow.bar.core.IBarSetting;
import com.coder.zzq.smartshow.topbar.view.TopBar;

public interface ITopbarSetting extends IBarSetting<TopBar.TopbarLayout, ITopbarSetting> {
    ITopbarSetting darkStatusBarTextAndIcon();
}
