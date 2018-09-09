package com.coder.zzq.smartshow.bar.topbar;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.bar.base.IBarSetting;
import com.coder.zzq.smartshow.bar.topbar.view.TopBar;

public interface ITopbarSetting extends IBarSetting<TopBar.TopbarLayout, ITopbarSetting> {
    @ColorInt
    int SNACK_BAR_COLOR = Color.parseColor("#323232");
    ITopbarSetting backgroundColorWhenBelowStatusBar(@ColorInt int color);
    ITopbarSetting backgroundColorWhenBelowActionBar(@ColorInt int color);
}
