package com.coder.zzq.smartshow.toast;

import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.toolkit.Utils;


final class EmotionToastVariety extends AbstractToastVariety implements IEmotionShow {

    protected ImageView mIconView;
    protected int mThemeColor;

    public EmotionToastVariety(@ColorInt int themeColor) {
        super(TOAST_TAG_EMOTION);
        mThemeColor = themeColor;
    }

    @Override
    protected Toast createToast() {
        mToast = new Toast(Toolkit.getContext());
        mView = LayoutInflater.from(Toolkit.getContext()).inflate(R.layout.layout_emotion_toast, null);
        mMsgView = mView.findViewById(R.id.type_info_message);
        mIconView = mView.findViewById(R.id.type_info_icon);
        mToast.setView(mView);
        return mToast;
    }

    @Override
    protected void updateToast() {
        super.updateToast();
        int iconRes = R.drawable.emotion_info;

        switch (mCurEmotionType) {
            case EMOTION_INFO:
                iconRes = R.drawable.emotion_info;
                break;
            case EMOTION_WARNING:
                iconRes = R.drawable.emotion_warning;
                break;
            case EMOTION_SUCCESS:
                iconRes = R.drawable.emotion_success;
                break;
            case EMOTION_FAIL:
                iconRes = R.drawable.emotion_fail;
                break;
            case EMOTION_FORBID:
                iconRes = R.drawable.emotion_forbid;
                break;

            case EMOTION_WAITING:
                iconRes = R.drawable.emotion_waiting;
                break;
            case EMOTION_ERROR:
                iconRes = R.drawable.emotion_error;
                break;
            case EMOTION_COMPLETE:
                iconRes = R.drawable.emotion_complete;
                break;
        }

        mIconView.setImageResource(iconRes);
        GradientDrawable bg = (GradientDrawable) mToast.getView().getBackground();
        bg.setColor(mThemeColor);
    }


    @Override
    public void info(CharSequence msg) {
        info(msg, R.drawable.emotion_info);
    }

    @Override
    public void info(int msg) {
        info(Utils.getStringFromRes(msg));
    }


    @Override
    public void info(int msg, int iconRes) {
        info(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void info(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_INFO, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void infoLong(CharSequence msg) {
        infoLong(msg, R.drawable.emotion_info);
    }

    @Override
    public void infoLong(int msg) {
        infoLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void infoLong(int msg, int iconRes) {
        infoLong(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void infoLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_INFO, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void warning(CharSequence msg) {
        warning(msg, R.drawable.emotion_warning);
    }

    @Override
    public void warning(int msg) {
        warning(Utils.getStringFromRes(msg));
    }


    @Override
    public void warning(int msg, int iconRes) {
        warning(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void warning(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_WARNING, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void warningLong(CharSequence msg) {
        warningLong(msg, R.drawable.emotion_warning);
    }

    @Override
    public void warningLong(int msg) {
        warningLong(Utils.getStringFromRes(msg));
    }


    @Override
    public void warningLong(int msg, int iconRes) {
        waitingLong(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void warningLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_WAITING, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void success(CharSequence msg) {
        success(msg, R.drawable.emotion_success);
    }

    @Override
    public void success(int msg) {
        success(Utils.getStringFromRes(msg));
    }


    @Override
    public void success(int msg, int iconRes) {
        success(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void success(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_SUCCESS, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void successLong(CharSequence msg) {
        successLong(msg, R.drawable.emotion_success);
    }

    @Override
    public void successLong(int msg) {
        successLong(Utils.getStringFromRes(msg));
    }


    @Override
    public void successLong(int msg, int iconRes) {
        successLong(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void successLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_SUCCESS, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void error(CharSequence msg) {
        error(msg, R.drawable.emotion_error);
    }

    @Override
    public void error(int msg) {
        error(Utils.getStringFromRes(msg));
    }

    @Override
    public void error(int msg, int iconRes) {
        error(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void error(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_ERROR, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void errorLong(CharSequence msg) {
        errorLong(msg, R.drawable.emotion_error);
    }


    @Override
    public void errorLong(int msg) {
        errorLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void errorLong(int msg, int iconRes) {
        errorLong(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void errorLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_ERROR, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void fail(CharSequence msg) {
        fail(msg, R.drawable.emotion_fail);
    }


    @Override
    public void fail(int msg) {
        fail(Utils.getStringFromRes(msg));
    }

    @Override
    public void fail(int msg, int iconRes) {
        fail(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void fail(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_FAIL, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void failLong(CharSequence msg) {
        failLong(msg, R.drawable.emotion_fail);
    }


    @Override
    public void failLong(int msg) {
        failLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void failLong(int msg, int iconRes) {
        failLong(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void failLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_FAIL, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void complete(CharSequence msg) {
        complete(msg, R.drawable.emotion_complete);
    }

    @Override
    public void complete(int msg) {
        complete(Utils.getStringFromRes(msg));
    }

    @Override
    public void complete(int msg, int iconRes) {
        complete(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void complete(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_COMPLETE, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void completeLong(CharSequence msg) {
        completeLong(msg, R.drawable.emotion_complete);
    }

    @Override
    public void completeLong(int msg) {
        completeLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void completeLong(int msg, int iconRes) {
        completeLong(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void completeLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_COMPLETE, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void forbid(CharSequence msg) {
        forbid(msg, R.drawable.emotion_forbid);
    }

    @Override
    public void forbid(int msg) {
        forbid(Utils.getStringFromRes(msg));
    }

    @Override
    public void forbid(int msg, int iconRes) {
        forbid(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void forbid(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_FORBID, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void forbidLong(CharSequence msg) {
        forbidLong(msg, R.drawable.emotion_forbid);
    }


    @Override
    public void forbidLong(int msg) {
        forbidLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void forbidLong(int msg, int iconRes) {
        forbidLong(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void forbidLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_FORBID, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }


    @Override
    public void waiting(CharSequence msg) {
        waiting(msg, R.drawable.emotion_waiting);
    }

    @Override
    public void waiting(int msg) {
        waiting(Utils.getStringFromRes(msg));
    }

    @Override
    public void waiting(int msg, int iconRes) {
        waiting(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void waiting(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_WAITING, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }


    @Override
    public void waitingLong(CharSequence msg) {
        waitingLong(msg, R.drawable.emotion_waiting);
    }

    @Override
    public void waitingLong(int msg) {
        waitingLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void waitingLong(int msg, int iconRes) {
        waitingLong(Utils.getStringFromRes(msg), iconRes);
    }

    @Override
    public void waitingLong(CharSequence msg, int iconRes) {
        showHelper(msg, EMOTION_WAITING, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }
}
