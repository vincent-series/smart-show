package com.coder.zzq.smartshow.toast;


import android.view.Gravity;
import android.widget.Toast;

import com.coder.zzq.toolkit.Utils;

public abstract class TextToastVariety extends AbstractToastVariety implements ITextShow {

    public TextToastVariety(int tag) {
        super(tag);
    }

    @Override
    protected abstract Toast createToast();

    @Override
    public void show(CharSequence msg) {
        showHelper(msg,EMOTION_NONE, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, mVerticalAxisOffsetWhenBottom, Toast.LENGTH_SHORT);
    }

    @Override
    public void show(int msgRes) {
        show(Utils.getStringFromRes(msgRes));
    }

    @Override
    public void showAtTop(CharSequence msg) {
        showHelper(msg,EMOTION_NONE, Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, mVerticalAxisOffsetWhenTop, Toast.LENGTH_SHORT);
    }

    @Override
    public void showAtTop(int msg) {
        showAtTop(Utils.getStringFromRes(msg));
    }

    @Override
    public void showInCenter(CharSequence msg) {
        showHelper(msg,EMOTION_NONE, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }

    @Override
    public void showInCenter(int msg) {
        showInCenter(Utils.getStringFromRes(msg));
    }

    @Override
    public void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showHelper(msg,EMOTION_NONE, gravity, Utils.dpToPx(xOffsetDp), Utils.dpToPx(yOffsetDp), Toast.LENGTH_SHORT);
    }

    @Override
    public void showAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showAtLocation(Utils.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp);
    }

    @Override
    public void showLong(CharSequence msg) {
        showHelper(msg,EMOTION_NONE, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, mVerticalAxisOffsetWhenBottom, Toast.LENGTH_LONG);
    }

    @Override
    public void showLong(int msg) {
        showLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void showLongAtTop(CharSequence msg) {

        showHelper(msg,EMOTION_NONE, Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, mVerticalAxisOffsetWhenTop, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongAtTop(int msg) {
        showLongAtTop(Utils.getStringFromRes(msg));
    }

    @Override
    public void showLongInCenter(CharSequence msg) {
        showHelper(msg,EMOTION_NONE, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongInCenter(int msg) {
        showLongInCenter(Utils.getStringFromRes(msg));
    }

    @Override
    public void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showHelper(msg,EMOTION_NONE, gravity, Utils.dpToPx(xOffsetDp), Utils.dpToPx(yOffsetDp), Toast.LENGTH_LONG);
    }

    @Override
    public void showLongAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showLongAtLocation(Utils.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp);
    }
}
