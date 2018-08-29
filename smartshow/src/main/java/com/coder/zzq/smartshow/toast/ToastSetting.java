package com.coder.zzq.smartshow.toast;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.view.View;
/**
 * Created by 朱志强 on 2018/08/19.
 */
public interface ToastSetting {


    int ACTION_IGNORE = 0;
    int ACTION_REPEAT_SHOW_LIKE_SNACKBAR = 1;

    ToastSetting view(View view);
    ToastSetting view(@LayoutRes int layout);

    ToastSetting backgroundColor(@ColorInt int color);
    ToastSetting backgroundColorRes(@ColorRes int colorRes);
    ToastSetting textColor(@ColorInt int color);
    ToastSetting textColorRes(@ColorRes int color);
    ToastSetting textSizeSp(int sp);
    ToastSetting textBold(boolean bold);
    ToastSetting dismissOnLeave(boolean b);
    ToastSetting actionWhenDuplicate(int action);
    ToastSetting processView(ProcessToastCallback callback);



}
