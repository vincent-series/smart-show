package com.coder.zzq.smartshow.toast.emotion;

import android.view.Gravity;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.schedule.ToastScheduler;
import com.coder.zzq.toolkit.Utils;

public class EmotionToastInvoker implements EmotionToastView.Overall, EmotionToastView.ConfigSetter {
    private final EmotionToast.Config mConfig = new EmotionToast.Config();

    @Override
    public EmotionToastView.ConfigSetter config() {
        return this;
    }

    @Override
    public EmotionToastView.ConfigSetter cancelOnActivityExit(boolean b) {
        mConfig.mCancelOnActivityExit = b;
        return this;
    }

    @Override
    public EmotionToastView.ConfigSetter backgroundColor(int color) {
        mConfig.mBackgroundColor = color;
        return this;
    }

    @Override
    public EmotionToastView.ConfigSetter backgroundColorResource(int colorResource) {
        return backgroundColor(Utils.getColorFromRes(colorResource));
    }

    @Override
    public EmotionToastView.ConfigSetter iconResource(int icon) {
        mConfig.mIcon = icon;
        return this;
    }

    @Override
    public EmotionToastView.ConfigSetter iconSizeDp(float sizeDp) {
        mConfig.mIconSize = Utils.dpToPx(sizeDp);
        return this;
    }

    @Override
    public EmotionToastView.ConfigSetter iconPaddingDp(float padding) {
        mConfig.mIconPadding = Utils.dpToPx(padding);
        return this;
    }

    @Override
    public EmotionToastView.ConfigSetter messageColor(int color) {
        mConfig.mMsgColor = color;
        return this;
    }

    @Override
    public EmotionToastView.ConfigSetter messageColorResource(int colorResource) {
        return messageColor(Utils.getColorFromRes(colorResource));
    }

    @Override
    public EmotionToastView.ConfigSetter messageSizeSp(float sizeSp) {
        mConfig.mMsgSizeSp = sizeSp;
        return this;
    }

    @Override
    public EmotionToastView.ConfigSetter messageBold(boolean bold) {
        mConfig.mMsgBold = bold;
        return this;
    }

    @Override
    public EmotionToastApi apply() {
        return this;
    }

    @Override
    public void info(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_INFO, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void info(int msg) {
        info(Utils.getStringFromRes(msg));
    }

    @Override
    public void infoLong(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_INFO, msg, Toast.LENGTH_LONG);
    }

    @Override
    public void infoLong(int msg) {
        infoLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void warning(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_WARNING, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void warning(int msg) {
        warning(Utils.getStringFromRes(msg));
    }

    @Override
    public void warningLong(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_WARNING, msg, Toast.LENGTH_LONG);
    }

    @Override
    public void warningLong(int msg) {
        warningLong(msg);
    }

    @Override
    public void success(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_SUCCESS, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void success(int msg) {
        success(Utils.getStringFromRes(msg));
    }

    @Override
    public void successLong(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_SUCCESS, msg, Toast.LENGTH_LONG);
    }

    @Override
    public void successLong(int msg) {
        successLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void error(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_ERROR, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void error(int msg) {
        error(Utils.getStringFromRes(msg));
    }

    @Override
    public void errorLong(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_ERROR, msg, Toast.LENGTH_LONG);
    }

    @Override
    public void errorLong(int msg) {
        errorLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void fail(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_FAIL, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void fail(int msg) {
        fail(Utils.getStringFromRes(msg));
    }

    @Override
    public void failLong(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_FAIL, msg, Toast.LENGTH_LONG);
    }

    @Override
    public void failLong(int msg) {
        failLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void complete(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_COMPLETE, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void complete(int msg) {
        complete(Utils.getStringFromRes(msg));
    }

    @Override
    public void completeLong(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_COMPLETE, msg, Toast.LENGTH_LONG);
    }

    @Override
    public void completeLong(int msg) {
        completeLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void forbid(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_FORBID, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void forbid(int msg) {
        forbid(Utils.getStringFromRes(msg));
    }

    @Override
    public void forbidLong(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_FORBID, msg, Toast.LENGTH_LONG);
    }

    @Override
    public void forbidLong(int msg) {
        forbidLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void waiting(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_WAITING, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void waiting(int msg) {
        waiting(Utils.getStringFromRes(msg));
    }

    @Override
    public void waitingLong(CharSequence msg) {
        showHelper(EmotionToast.Config.EMOTION_TYPE_WAITING, msg, Toast.LENGTH_LONG);
    }

    @Override
    public void waitingLong(int msg) {
        waitingLong(Utils.getStringFromRes(msg));
    }

    public void showHelper(@EmotionToast.Config.EmotionType int emotionType, CharSequence msg, int duration) {
        mConfig.mEmotionType = emotionType;
        mConfig.mMsg = msg;
        mConfig.mDuration = duration;
        mConfig.mGravity = Gravity.CENTER;
        mConfig.mXOffset = 0;
        mConfig.mYOffset = 0;
        ToastScheduler.get().schedule(EmotionToastFactory.get().produceToast(mConfig));
    }
}
