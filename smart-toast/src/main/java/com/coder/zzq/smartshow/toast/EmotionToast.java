package com.coder.zzq.smartshow.toast;

import android.view.Gravity;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.coder.zzq.toolkit.Utils;

class EmotionToast extends AbstractToast<IEmotionToast, IEmotionToastShow> implements IEmotionToast, IEmotionToastShow {

    public EmotionToast() {
        toastUI(new EmotionToastUI());
    }

    public IEmotionToast backgroundColor(@ColorInt int color) {
        addArg(UIArguments.ARGUMENT_BACKGROUND_COLOR, color);
        return this;
    }

    public IEmotionToast backgroundColorRes(@ColorRes int color) {
        return backgroundColor(Utils.getColorFromRes(color));
    }

    public IEmotionToast icon(@DrawableRes int icon) {
        return addArg(UIArguments.ARGUMENT_ICON, icon);
    }

    protected IEmotionToast defaultIcon(@DrawableRes int icon) {
        return addArg(UIArguments.ARGUMENT_DEFAULT_ICON, icon);
    }

    @Override
    public IEmotionToast addArg(@NonNull String argName, Object argValue) {
        return (IEmotionToast) super.addArg(argName, argValue);
    }

    private void show(CharSequence msg, boolean shortDuration, @DrawableRes int defaultIcon) {
        defaultIcon(defaultIcon);
        message(msg);
        gravity(Gravity.CENTER);
        xOffset(0);
        yOffset(0);
        showUI(shortDuration);

    }

    private void show(@StringRes int msg, boolean shortDuration, @DrawableRes int icon) {
        show(Utils.getStringFromRes(msg), shortDuration, icon);
    }

    @Override
    public void info(CharSequence msg) {
        show(msg, true, R.drawable.emotion_info);
    }

    @Override
    public void info(@StringRes int msg) {
        show(msg, true, R.drawable.emotion_info);
    }

    @Override
    public void infoLong(CharSequence msg) {
        show(msg, false, R.drawable.emotion_info);
    }

    @Override
    public void infoLong(@StringRes int msg) {
        show(msg, false, R.drawable.emotion_info);
    }

    @Override
    public void warning(CharSequence msg) {
        show(msg, true, R.drawable.emotion_warning);
    }

    @Override
    public void warning(@StringRes int msg) {
        show(msg, true, R.drawable.emotion_warning);
    }

    @Override
    public void warningLong(CharSequence msg) {
        show(msg, false, R.drawable.emotion_warning);
    }

    @Override
    public void warningLong(@StringRes int msg) {
        show(msg, false, R.drawable.emotion_warning);
    }

    @Override
    public void success(CharSequence msg) {
        show(msg, true, R.drawable.emotion_success);
    }

    @Override
    public void success(@StringRes int msg) {
        show(msg, true, R.drawable.emotion_success);
    }

    @Override
    public void successLong(CharSequence msg) {
        show(msg, false, R.drawable.emotion_success);
    }

    @Override
    public void successLong(@StringRes int msg) {
        show(msg, false, R.drawable.emotion_success);
    }

    @Override
    public void error(CharSequence msg) {
        show(msg, true, R.drawable.emotion_error);
    }

    @Override
    public void error(@StringRes int msg) {
        show(msg, true, R.drawable.emotion_error);
    }

    @Override
    public void errorLong(CharSequence msg) {
        show(msg, false, R.drawable.emotion_error);
    }

    @Override
    public void errorLong(@StringRes int msg) {
        show(msg, false, R.drawable.emotion_error);
    }

    @Override
    public void fail(CharSequence msg) {
        show(msg, true, R.drawable.emotion_fail);
    }

    @Override
    public void fail(@StringRes int msg) {
        show(msg, true, R.drawable.emotion_fail);
    }

    @Override
    public void failLong(CharSequence msg) {
        show(msg, false, R.drawable.emotion_fail);
    }

    @Override
    public void failLong(@StringRes int msg) {
        show(msg, false, R.drawable.emotion_fail);
    }

    @Override
    public void complete(CharSequence msg) {
        show(msg, true, R.drawable.emotion_complete);
    }

    @Override
    public void complete(@StringRes int msg) {
        show(msg, true, R.drawable.emotion_complete);
    }

    @Override
    public void completeLong(CharSequence msg) {
        show(msg, false, R.drawable.emotion_complete);
    }

    @Override
    public void completeLong(@StringRes int msg) {
        show(msg, false, R.drawable.emotion_complete);
    }

    @Override
    public void forbid(CharSequence msg) {
        show(msg, true, R.drawable.emotion_forbid);
    }

    @Override
    public void forbid(@StringRes int msg) {
        show(msg, true, R.drawable.emotion_forbid);
    }

    @Override
    public void forbidLong(CharSequence msg) {
        show(msg, false, R.drawable.emotion_forbid);
    }

    @Override
    public void forbidLong(@StringRes int msg) {
        show(msg, false, R.drawable.emotion_forbid);
    }

    @Override
    public void waiting(CharSequence msg) {
        show(msg, true, R.drawable.emotion_waiting);
    }

    @Override
    public void waiting(@StringRes int msg) {
        show(msg, true, R.drawable.emotion_waiting);
    }

    @Override
    public void waitingLong(CharSequence msg) {
        show(msg, false, R.drawable.emotion_waiting);
    }

    @Override
    public void waitingLong(@StringRes int msg) {
        show(msg, false, R.drawable.emotion_waiting);
    }
}
