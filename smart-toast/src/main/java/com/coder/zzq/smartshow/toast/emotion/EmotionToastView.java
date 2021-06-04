package com.coder.zzq.smartshow.toast.emotion;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

public interface EmotionToastView {
    interface Overall extends EmotionToastApi {
        ConfigSetter config();
    }

    interface ConfigSetter {

        ConfigSetter transition(boolean b);

        ConfigSetter cancelOnActivityExit(boolean b);

        ConfigSetter backgroundColor(@ColorInt int color);

        ConfigSetter backgroundColorResource(@ColorRes int colorResource);

        ConfigSetter iconResource(@DrawableRes int icon);

        ConfigSetter iconSizeDp(float sizeDp);

        ConfigSetter iconPaddingDp(float padding);

        ConfigSetter messageColor(@ColorInt int color);

        ConfigSetter messageColorResource(@ColorRes int colorResource);

        ConfigSetter messageSizeSp(float sizeSp);

        ConfigSetter messageBold(boolean bold);

        EmotionToastApi apply();

    }
}
