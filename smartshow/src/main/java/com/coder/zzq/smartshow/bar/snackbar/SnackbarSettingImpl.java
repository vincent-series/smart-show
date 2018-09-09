package com.coder.zzq.smartshow.bar.snackbar;

import android.support.annotation.ColorInt;
import android.support.design.widget.Snackbar;

import com.coder.zzq.smartshow.Utils;
import com.coder.zzq.smartshow.bar.base.BarSettingImpl;

public final class SnackbarSettingImpl extends BarSettingImpl<Snackbar.SnackbarLayout, ISnackbarSetting>
        implements ISnackbarSetting {
    @ColorInt
    private int mBgColor;

    @Override
    public SnackbarSettingImpl backgroundColor(int color) {
        mBgColor = color;
        return this;
    }

    @Override
    public SnackbarSettingImpl backgroundColorRes(int colorRes) {
        return backgroundColor(Utils.getColorFromRes(colorRes));
    }

    public boolean backgroundHasSetup() {
        return mBgColor != -1;
    }

    @ColorInt
    public int getBackgroundColor() {
        return mBgColor;
    }

}
