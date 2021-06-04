package com.coder.zzq.smartshow.toast.classic;

import android.view.Gravity;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.Constants;
import com.coder.zzq.smartshow.toast.PlainToastApi;
import com.coder.zzq.smartshow.toast.compact.CompactToast;
import com.coder.zzq.smartshow.toast.schedule.ToastScheduler;
import com.coder.zzq.toolkit.Utils;

public class ClassicToastInvoker implements ClassicToastView.Overall, ClassicToastView.ConfigSetter {
    private final ClassicToast.Config mConfig = new ClassicToast.Config();

    @Override
    public ClassicToastView.ConfigSetter config() {
        return this;
    }

    @Override
    public void show(CharSequence msg) {
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, Constants.DEFAULT_VALUE, Toast.LENGTH_SHORT);
    }

    @Override
    public void show(int msg) {
        show(Utils.getStringFromRes(msg));
    }

    @Override
    public void showAtTop(CharSequence msg) {
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, Utils.getToolbarHeight() + Utils.dpToPx(40), Toast.LENGTH_SHORT);
    }

    @Override
    public void showAtTop(int msg) {
        showAtTop(Utils.getStringFromRes(msg));
    }

    @Override
    public void showInCenter(CharSequence msg) {
        showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }

    @Override
    public void showInCenter(int msg) {
        showInCenter(Utils.getStringFromRes(msg));
    }

    @Override
    public void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showHelper(msg, gravity, Utils.dpToPx(xOffsetDp), Utils.dpToPx(yOffsetDp), Toast.LENGTH_SHORT);
    }

    @Override
    public void showAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showAtLocation(Utils.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp);
    }

    @Override
    public void showLong(CharSequence msg) {
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, Constants.DEFAULT_VALUE, Toast.LENGTH_LONG);
    }

    @Override
    public void showLong(int msg) {
        showLong(Utils.getStringFromRes(msg));
    }

    @Override
    public void showLongAtTop(CharSequence msg) {
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, Utils.getToolbarHeight() + Utils.dpToPx(40), Toast.LENGTH_LONG);
    }

    @Override
    public void showLongAtTop(int msg) {
        showLongAtTop(Utils.getStringFromRes(msg));
    }

    @Override
    public void showLongInCenter(CharSequence msg) {
        showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongInCenter(int msg) {
        showLongInCenter(Utils.getStringFromRes(msg));
    }

    @Override
    public void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showHelper(msg, gravity, Utils.dpToPx(xOffsetDp), Utils.dpToPx(yOffsetDp), Toast.LENGTH_LONG);
    }

    @Override
    public void showLongAtLocation(int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        showLongAtLocation(Utils.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp);
    }

    private void showHelper(CharSequence msg, int gravity, int xOffsetDp, int yOffsetDp, int duration) {
        mConfig.mMsg = msg;
        mConfig.mGravity = gravity;
        mConfig.mXOffset = xOffsetDp;
        mConfig.mYOffset = yOffsetDp;
        mConfig.mDuration = duration;
        ToastScheduler.get().schedule(new CompactToast(ClassicToastFactory.get(), mConfig));
    }

    @Override
    public ClassicToastView.ConfigSetter transition(boolean b) {
        mConfig.mTransition = b;
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter cancelOnActivityExit(boolean b) {
        mConfig.mCancelOnActivityExit = b;
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter backgroundColor(int color) {
        mConfig.mBackgroundColor = color;
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter backgroundColorResource(int color) {
        backgroundColor(Utils.getColorFromRes(color));
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter backgroundDrawableResource(int background) {
        mConfig.mBackgroundResource = background;
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter msgColor(int color) {
        mConfig.mMsgColor = color;
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter msgColorResource(int color) {
        msgColor(Utils.getColorFromRes(color));
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter msgSize(float size) {
        mConfig.mMsgSize = size;
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter msgBold(boolean b) {
        mConfig.mMsgBold = b;
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter iconResource(int icon) {
        mConfig.mIcon = icon;
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter iconPosition(int position) {
        mConfig.mIconPosition = position;
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter iconSizeDp(float iconSizeDp) {
        mConfig.mIconSizeDp = Utils.dpToPx(iconSizeDp);
        return this;
    }

    @Override
    public ClassicToastView.ConfigSetter iconPaddingDp(float iconPaddingDp) {
        mConfig.mIconPaddingDp = Utils.dpToPx(iconPaddingDp);
        return this;
    }

    @Override
    public PlainToastApi apply() {
        return this;
    }
}
