package com.coder.zzq.smartshow.toast;

import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Toast;

import com.coder.zzq.smartshow.R;

import static com.coder.zzq.smartshow.toast.IToastSetting.ACTION_IGNORE;
import static com.coder.zzq.smartshow.toast.IToastSetting.ACTION_REPEAT;

public class PlainToastManager extends ToastManager implements IPlainShow {

    private int mXOffset;
    private int mYOffset;
    private int mVerticalAxisOffset;
    private int mGravity;

    public PlainToastManager(ToastSettingImpl toastSetting) {
        super(toastSetting);
        setupInitialPosInfo();
    }

    @Override
    protected Toast createToast() {
        if (mToastSetting.isCustom()) {
            mView = mToastSetting.getCustomView();
            mMsgView = mView.findViewById(R.id.custom_toast_msg);
            mToast = new Toast(SmartToast.getContext());
            mToast.setView(mView);
        } else {
            mToast = Toast.makeText(SmartToast.getContext(), "", Toast.LENGTH_SHORT);
            mView = mToast.getView();
            mMsgView = mView.findViewById(android.R.id.message);
        }
        return mToast;
    }

    @Override
    protected void rebuildToast() {
        mToast = createToast();
        setupReflectInfo();
        setupToast();
        setupInitialPosInfo();
    }


    private void setupInitialPosInfo() {
        mGravity = mToast.getGravity();
        mXOffset = mToast.getXOffset();
        mVerticalAxisOffset = mYOffset = mToast.getYOffset();
    }

    @Override
    protected void setupToast() {
        super.setupToast();
        if (mToastSetting.isBgColorSetup()) {
            if (mToastSetting.isCustom()) {
                mView.setBackgroundColor(mToastSetting.getBgColor());
            } else {
                DrawableCompat.setTint(mView.getBackground(), mToastSetting.getBgColor());
            }
        }

        if (mToastSetting.isTextColorSetup()) {
            mMsgView.setTextColor(mToastSetting.getTextColor());
        }
        if (mToastSetting.isTextSizeSetup()) {
            mMsgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mToastSetting.getTextSizeSp());
        }

        mMsgView.setGravity(Gravity.CENTER);
        mMsgView.getPaint().setFakeBoldText(mToastSetting.isTextBold());
        if (mToastSetting.isViewCallbackSetup()) {
            mToastSetting.getIProcessToastCallback().processView(mToastSetting.isCustom(), mView, mMsgView);
        }
    }

    @Override
    public void show(CharSequence msg) {
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, mVerticalAxisOffset, Toast.LENGTH_SHORT);
    }

    @Override
    public void showAtTop(CharSequence msg) {
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, mVerticalAxisOffset, Toast.LENGTH_SHORT);
    }

    @Override
    public void showInCenter(CharSequence msg) {
        showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }

    @Override
    public void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        int xOffset = dpToPx(xOffsetDp);
        int yOffset = dpToPx(yOffsetDp);
        showHelper(msg, gravity, xOffset, yOffset, Toast.LENGTH_SHORT);
    }

    @Override
    public void showLong(CharSequence msg) {
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,mVerticalAxisOffset, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongAtTop(CharSequence msg) {
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, mVerticalAxisOffset, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongInCenter(CharSequence msg) {
        showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        int xOffset = dpToPx(xOffsetDp);
        int yOffset = dpToPx(yOffsetDp);
        showHelper(msg, gravity, xOffset, yOffset, Toast.LENGTH_LONG);
    }

    private void showHelper(CharSequence msg, int gravity, int xOffset, int yOffset, int duration) {
        SmartToast.dismissTypeShowIfNeed();
        msg = (msg == null) ? "" : msg;
        //位置是否改变
        boolean locationChanged = locationChanged(gravity, xOffset, yOffset);
        //文本是否改变
        boolean contentChanged = !mCurMsg.equals(msg);

        mCurMsg = msg;
        mDuration = duration;
        mGravity = gravity;
        mXOffset = xOffset;
        mYOffset = yOffset;


        switch (mToastSetting.getActionWhenToastDuplicate()) {
            case ACTION_IGNORE:
                if (isShowing() && (locationChanged || contentChanged)) {
                    dismiss();
                } else {
                    updateToast();
                }
                break;
            case ACTION_REPEAT:
                dismiss();
                updateToast();
                break;
        }


        mToast.setGravity(mGravity, mXOffset, mYOffset);
        mToast.setDuration(mDuration);
        mToast.show();
    }

    private boolean locationChanged(int gravity, int xOffset, int yOffset) {
        return mGravity != gravity || mXOffset != xOffset || mYOffset != yOffset;
    }

    public void cancel() {
        mToast.cancel();
        rebuildToast();
    }
}
