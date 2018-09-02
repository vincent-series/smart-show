package com.coder.zzq.smartshow.toast;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.view.View;
/**
 * Created by 朱志强 on 2018/08/19.
 */
public interface IToastSetting {


    int ACTION_IGNORE = 0;
    int ACTION_REPEAT = 1;

    IToastSetting view(View view);
    IToastSetting view(@LayoutRes int layout);
    IToastSetting backgroundColor(@ColorInt int color);
    IToastSetting backgroundColorRes(@ColorRes int colorRes);
    IToastSetting textColor(@ColorInt int color);
    IToastSetting textColorRes(@ColorRes int color);
    IToastSetting textSizeSp(float sp);
    IToastSetting textBold(boolean bold);
    IToastSetting dismissOnLeave(boolean b);
    IToastSetting actionWhenDuplicate(int action);
    IToastSetting processView(IProcessToastCallback callback);

    IToastSetting typeInfoToastThemeColor(@ColorInt int color);
    IToastSetting typeInfoToastThemeColorRes(@ColorRes int colorRes);

}
