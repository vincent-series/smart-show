package com.coder.zzq.smartshow.snackbar.top;

import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.snackbar.base.BarSettingImpl;
import com.coder.zzq.smartshow.snackbar.top.view.TopBar;

public class TopbarSettingImpl extends BarSettingImpl<TopBar.TopbarLayout, ITopbarSetting>
        implements ITopbarSetting {

    @ColorInt
    private int mBgColorWhenBelowStatusBar;
    @ColorInt
    private int mBgColorWhenBelowActionBar;

    public TopbarSettingImpl() {
        mBgColorWhenBelowStatusBar = -1;
        mBgColorWhenBelowActionBar = -1;
    }

    @Override
    public ITopbarSetting backgroundColorWhenBelowStatusBar(@ColorInt int color) {
        mBgColorWhenBelowStatusBar = color;
        return this;
    }


    @ColorInt
    public int getBgColorWhenBelowStatusBar() {
        return mBgColorWhenBelowStatusBar;
    }

    @Override
    public ITopbarSetting backgroundColorWhenBelowActionBar(@ColorInt int color) {
        mBgColorWhenBelowActionBar = color;
        return this;
    }

    @ColorInt
    public int getBgColorWhenBelowActionBar() {
        return mBgColorWhenBelowActionBar;
    }
}
