package com.coder.zzq.smartshow.toast;

import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.core.Utils;

public class TypeToastSetting implements ITypeToastSetting {
    @ColorInt
    private int mThemeColor;

    @Override
    public ITypeToastSetting themeColor(int color) {
        mThemeColor = color;
        return this;
    }

    @Override
    public ITypeToastSetting themeColorRes(int colorRes) {
        return themeColor(Utils.getColorFromRes(colorRes));
    }

    public int getThemeColor() {
        return mThemeColor;
    }
}
