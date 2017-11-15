package com.coder.zzq.smartshow.snackbar;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

/**
 * Created by 朱志强 on 2017/11/12.
 */

public interface SnackbarSetting {
    SnackbarSetting backgroundColor(@ColorInt int color);

    SnackbarSetting backgroundColorRes(@ColorRes int colorRes);

    SnackbarSetting msgTextColor(@ColorInt int color);

    SnackbarSetting msgTextColorRes(@ColorRes int colorRes);

    SnackbarSetting msgTextSizeSp(int textSizeSp);

    SnackbarSetting actionColor(@ColorInt int color);

    SnackbarSetting actionColorRes(@ColorRes int colorRes);

    SnackbarSetting actionSizeSp(int textSizeSp);

    SnackbarSetting processView(ProcessViewCallback callback);
}
