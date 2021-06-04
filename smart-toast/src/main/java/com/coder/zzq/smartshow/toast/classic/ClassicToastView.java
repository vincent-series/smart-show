package com.coder.zzq.smartshow.toast.classic;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.coder.zzq.smartshow.toast.PlainToastApi;

public interface ClassicToastView {
    interface Overall extends PlainToastApi {
        ConfigSetter config();
    }

    interface ConfigSetter {

        ConfigSetter transition(boolean b);

        ConfigSetter cancelOnActivityExit(boolean b);

        ConfigSetter backgroundColor(@ColorInt int color);

        ConfigSetter backgroundColorResource(@ColorRes int color);

        ConfigSetter backgroundDrawableResource(@DrawableRes int background);

        ConfigSetter msgColor(@ColorInt int color);

        ConfigSetter msgColorResource(@ColorRes int color);


        ConfigSetter msgSize(float size);

        ConfigSetter msgBold(boolean b);

        ConfigSetter iconResource(@DrawableRes int icon);

        ConfigSetter iconPosition(@ClassicToast.Config.IconPosition int position);

        ConfigSetter iconSizeDp(float iconSizeDp);

        ConfigSetter iconPaddingDp(float iconPaddingDp);

        PlainToastApi apply();
    }
}
