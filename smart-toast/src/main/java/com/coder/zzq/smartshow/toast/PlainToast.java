package com.coder.zzq.smartshow.toast;

import android.view.Gravity;

import com.coder.zzq.toolkit.Utils;

class PlainToast<ToastType extends IToast> extends AbstractToast<ToastType, IPlainToastShow> implements IPlainToastShow {
    private int mVerticalAxisOffsetWhenTop;

    PlainToast() {
        mVerticalAxisOffsetWhenTop = com.coder.zzq.toolkit.Utils.getToolbarHeight() + com.coder.zzq.toolkit.Utils.dpToPx(40);
    }

    @Override
    public void show(CharSequence msg) {
        message(msg);
        showUI(true);
    }

    @Override
    public void show(int msg) {
        message(msg).
                showUI(true);
    }

    @Override
    public void showAtTop(CharSequence msg) {
        message(msg).
                gravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL).
                xOffset(0).
                yOffset(mVerticalAxisOffsetWhenTop).
                showUI(true);
    }

    @Override
    public void showAtTop(int msg) {
        showAtTop(com.coder.zzq.toolkit.Utils.getStringFromRes(msg));
    }

    @Override
    public void showInCenter(CharSequence msg) {
        message(msg).
                gravity(Gravity.CENTER).
                xOffset(0).
                yOffset(0).
                showUI(true);
    }

    @Override
    public void showInCenter(int msg) {
        showInCenter(com.coder.zzq.toolkit.Utils.getStringFromRes(msg));
    }

    @Override
    public void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        message(msg).
                gravity(gravity).
                xOffset(com.coder.zzq.toolkit.Utils.dpToPx(xOffsetDp)).
                yOffset(com.coder.zzq.toolkit.Utils.dpToPx(yOffsetDp)).
                showUI(true);
    }

    @Override
    public void showAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showAtLocation(com.coder.zzq.toolkit.Utils.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp);
    }

    @Override
    public void showLong(CharSequence msg) {
        message(msg).
                showUI(false);
    }

    @Override
    public void showLong(int msg) {
        message(msg).
                showUI(false);
    }

    @Override
    public void showLongAtTop(CharSequence msg) {
        message(msg).
                gravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL).
                xOffset(0).
                yOffset(mVerticalAxisOffsetWhenTop).
                showUI(false);
    }

    @Override
    public void showLongAtTop(int msg) {
        showLongAtTop(com.coder.zzq.toolkit.Utils.getStringFromRes(msg));
    }

    @Override
    public void showLongInCenter(CharSequence msg) {
        message(msg);
        gravity(Gravity.CENTER);
        xOffset(0);
        yOffset(0);
        showUI(false);
    }

    @Override
    public void showLongInCenter(int msg) {
        showLongInCenter(com.coder.zzq.toolkit.Utils.getStringFromRes(msg));
    }

    @Override
    public void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        message(msg).
                gravity(gravity).
                xOffset(com.coder.zzq.toolkit.Utils.dpToPx(xOffsetDp)).
                yOffset(com.coder.zzq.toolkit.Utils.dpToPx(yOffsetDp)).
                showUI(false);
    }

    @Override
    public void showLongAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showLongAtLocation(Utils.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp);
    }
}
