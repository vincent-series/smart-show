package com.coder.zzq.smartshow.basebar;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

public interface IBarSetting<View, BarSetting> {
    @ColorInt
    int SNACK_BAR_COLOR = Color.parseColor("#323232");

    BarSetting backgroundColor(int color);

    BarSetting backgroundColorRes(int colorRes);

    BarSetting msgTextColor(@ColorInt int color);

    BarSetting msgTextColorRes(@ColorRes int colorRes);

    BarSetting msgTextSizeSp(float textSizeSp);

    BarSetting actionColor(@ColorInt int color);

    BarSetting actionColorRes(@ColorRes int colorRes);

    BarSetting actionSizeSp(float textSizeSp);

    BarSetting defaultActionTextForIndefinite(String actionText);

    BarSetting dismissOnLeave(boolean b);

    BarSetting processView(IProcessBarCallback callback);

}
