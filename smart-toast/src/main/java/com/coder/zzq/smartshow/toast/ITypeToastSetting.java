package com.coder.zzq.smartshow.toast;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

public interface ITypeToastSetting {
    ITypeToastSetting themeColor(@ColorInt int color);

    ITypeToastSetting themeColorRes(@ColorRes int colorRes);
}
