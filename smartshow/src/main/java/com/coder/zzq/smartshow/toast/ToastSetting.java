package com.coder.zzq.smartshow.toast;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.view.View;
/**
 * Created by 朱志强 on 2018/08/19.
 */
public interface ToastSetting {


    ToastSetting view(View view);
    ToastSetting view(@LayoutRes int layout);

    ToastSetting backgroundColor(@ColorInt int color);
    ToastSetting backgroundColorRes(@ColorRes int colorRes);
    ToastSetting textColor(@ColorInt int color);
    ToastSetting textColorRes(@ColorRes int color);
    ToastSetting textSizeSp(int sp);
    ToastSetting textBold(boolean bold);
    ToastSetting dismissOnLeave(boolean b);
    ToastSetting processView(ProcessViewCallback callback);


}
