package com.coder.zzq.smartshow.toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

import com.coder.zzq.toolkit.Utils;

class OriginalToast extends PlainToast<IOriginalToast> implements IOriginalToast {
    public OriginalToast() {
        toastUI(new OriginalToastUI());
    }

    public IOriginalToast backgroundColor(@ColorInt int color) {
        addArg(UIArguments.ARGUMENT_BACKGROUND_COLOR, color);
        return this;
    }

    public IOriginalToast backgroundColorRes(@ColorRes int color) {
        return backgroundColor(Utils.getColorFromRes(color));
    }

    public IOriginalToast textColor(@ColorInt int color) {
        addArg(UIArguments.ARGUMENT_TEXT_COLOR, color);
        return this;
    }

    public IOriginalToast textColorRes(@ColorRes int color) {
        return textColor(Utils.getColorFromRes(color));
    }


    public IOriginalToast textSizeSp(float sizeSp) {
        addArg(UIArguments.ARGUMENT_TEXT_SIZE_SP, sizeSp);
        return this;
    }

    public IOriginalToast icon(@DrawableRes int icon) {
        addArg(UIArguments.ARGUMENT_ICON, icon);
        return this;
    }

    public IOriginalToast iconPosition(@IconPosition int position) {
        addArg(UIArguments.ARGUMENT_ICON_POSITION, position);
        return this;
    }
}
