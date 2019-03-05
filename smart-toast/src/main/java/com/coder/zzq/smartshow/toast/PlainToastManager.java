package com.coder.zzq.smartshow.toast;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Toast;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class PlainToastManager extends BaseToastManager implements IPlainShow {

    private int mXOffset;
    private int mYOffset;
    private int mVerticalAxisOffsetWhenBottom;
    private int mVerticalAxisOffsetWhenTop;
    private int mGravity;

    public PlainToastManager() {
        super();
        setupInitialPosInfo();
    }

    @Override
    protected Toast createToast() {
        if (ToastDelegate.get().hasToastSetting()
                && ToastDelegate.get().getToastSetting().isCustom()) {

            mView = ToastDelegate.get().getToastSetting().getCustomView();
            mMsgView = mView.findViewById(R.id.custom_toast_msg);
            mToast = new Toast(SmartShow.getContext());
            mToast.setView(mView);

        } else {
            mToast = Toast.makeText(SmartShow.getContext(), "", Toast.LENGTH_SHORT);
            mView = mToast.getView();
            mMsgView = mView.findViewById(android.R.id.message);
        }

        return mToast;
    }

    @Override
    protected void rebuildToast() {
        super.rebuildToast();
        setupInitialPosInfo();
    }


    private void setupInitialPosInfo() {
        mGravity = mToast.getGravity();
        mXOffset = mToast.getXOffset();
        mVerticalAxisOffsetWhenBottom = mYOffset = mToast.getYOffset();
        mVerticalAxisOffsetWhenTop = Utils.getToolbarHeight() + Utils.dpToPx(40);
    }

    @Override
    protected void setupToast() {
        super.setupToast();
        if (ToastDelegate.get().hasToastSetting()) {
            if (ToastDelegate.get().getToastSetting().isBgDrawableSetup()) {
                mView.setBackgroundResource(ToastDelegate.get().getToastSetting().getBgDrawableRes());
            } else if (ToastDelegate.get().getToastSetting().isBgColorSetup()) {
                if (ToastDelegate.get().getToastSetting().isCustom()) {
                    mView.setBackgroundColor(ToastDelegate.get().getToastSetting().getBgColor());
                } else {
                    Drawable bg = mView.getBackground();
                    if (bg instanceof GradientDrawable) {
                        ((GradientDrawable) bg).setColor(ToastDelegate.get().getToastSetting().getBgColor());
                    } else {
                        DrawableCompat.setTint(bg, ToastDelegate.get().getToastSetting().getBgColor());
                    }

                    mView.setBackgroundDrawable(bg);
                }
            }

            if (ToastDelegate.get().getToastSetting().isTextColorSetup()) {
                mMsgView.setTextColor(ToastDelegate.get().getToastSetting().getTextColor());
            }
            if (ToastDelegate.get().getToastSetting().isTextSizeSetup()) {
                mMsgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, ToastDelegate.get().getToastSetting().getTextSizeSp());
            }

            mMsgView.setGravity(Gravity.CENTER);
            mMsgView.getPaint().setFakeBoldText(ToastDelegate.get().getToastSetting().isTextBold());
            if (ToastDelegate.get().getToastSetting().isViewCallbackSetup()) {
                ToastDelegate.get().getToastSetting().getIProcessToastCallback().processView(mView, mMsgView);
            }

        }

    }


    @Override
    public void show(CharSequence msg) {
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, mVerticalAxisOffsetWhenBottom, Toast.LENGTH_SHORT);
    }

    @Override
    public void showAtTop(CharSequence msg) {
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, mVerticalAxisOffsetWhenTop, Toast.LENGTH_SHORT);
    }

    @Override
    public void showInCenter(CharSequence msg) {
        showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }

    @Override
    public void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        int xOffset = Utils.dpToPx(xOffsetDp);
        int yOffset = Utils.dpToPx(yOffsetDp);
        showHelper(msg, gravity, xOffset, yOffset, Toast.LENGTH_SHORT);
    }

    @Override
    public void showLong(CharSequence msg) {
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, mVerticalAxisOffsetWhenBottom, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongAtTop(CharSequence msg) {
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, mVerticalAxisOffsetWhenTop, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongInCenter(CharSequence msg) {
        showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }

    @Override
    public void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        int xOffset = Utils.dpToPx(xOffsetDp);
        int yOffset = Utils.dpToPx(yOffsetDp);
        showHelper(msg, gravity, xOffset, yOffset, Toast.LENGTH_LONG);
    }

    private void showHelper(CharSequence msg, int gravity, int xOffset, int yOffset, int duration) {
        ToastDelegate.get().dismissTypeShowIfNeed();
        msg = (msg == null) ? "" : msg;
        //位置是否改变
        boolean locationChanged = locationChanged(gravity, xOffset, yOffset);
        //文本是否改变
        boolean contentChanged = !mCurMsg.equals(msg);

        boolean needInvodeShow = !isShowing();

        if (isShowing() && (locationChanged || contentChanged)) {
            dismiss();
            needInvodeShow = true;
        }

        mCurMsg = msg;
        mDuration = duration;
        mGravity = gravity;
        mXOffset = xOffset;
        mYOffset = yOffset;
        updateToast();
        mToast.setGravity(mGravity, mXOffset, mYOffset);
        mToast.setDuration(mDuration);
        if (needInvodeShow) {
            mToast.show();
        }
    }

    private boolean locationChanged(int gravity, int xOffset, int yOffset) {
        return mGravity != gravity || mXOffset != xOffset || mYOffset != yOffset;
    }

}
