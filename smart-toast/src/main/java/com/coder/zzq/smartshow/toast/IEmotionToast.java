package com.coder.zzq.smartshow.toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

public interface IEmotionToast extends IToast<IEmotionToast, IEmotionToastShow> {
    IEmotionToast backgroundColor(@ColorInt int color);

    IEmotionToast backgroundColorRes(@ColorRes int color);

    IEmotionToast icon(@DrawableRes int icon);
}
