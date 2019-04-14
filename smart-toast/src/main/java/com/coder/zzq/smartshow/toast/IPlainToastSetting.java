package com.coder.zzq.smartshow.toast;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;

public interface IPlainToastSetting {

    int BG_MODE_SRC = 0;
    int BG_MODE_COLOR = 1;
    int BG_MODE_DRAWABLE = 2;

    IPlainToastSetting customView(CustomViewCallback callback);

    IPlainToastSetting backgroundColor(@ColorInt int color);

    IPlainToastSetting backgroundColorRes(@ColorRes int colorRes);

    IPlainToastSetting backgroundDrawableRes(@DrawableRes int drawableRes);

    IPlainToastSetting srcBackground();

    IPlainToastSetting textColor(@ColorInt int color);

    IPlainToastSetting textColorRes(@ColorRes int colorRes);

    IPlainToastSetting textSizeSp(float sp);

    IPlainToastSetting textBold(boolean bold);

    interface CustomViewCallback {
        View createToastView(LayoutInflater inflater);
    }
}
