package com.coder.zzq.smartshow.toast;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by 朱志强 on 2018/08/19.
 */
public interface IToastSetting {
    IToastSetting view(View view);

    IToastSetting view(@LayoutRes int layout);

    IToastSetting backgroundColor(@ColorInt int color);

    IToastSetting backgroundColorRes(@ColorRes int colorRes);

    IToastSetting backgroundDrawableRes(@DrawableRes int drawableRes);

    IToastSetting textColor(@ColorInt int color);

    IToastSetting textColorRes(@ColorRes int color);

    IToastSetting textSizeSp(float sp);

    IToastSetting textBold(boolean bold);

    IToastSetting dismissOnLeave(boolean b);

    IToastSetting processView(IProcessToastCallback callback);

    IToastSetting typeInfoToastThemeColor(@ColorInt int color);

    IToastSetting typeInfoToastThemeColorRes(@ColorRes int colorRes);

}
