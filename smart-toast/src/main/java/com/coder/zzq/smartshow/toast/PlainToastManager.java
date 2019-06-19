package com.coder.zzq.smartshow.toast;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.RestrictTo;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Toast;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class PlainToastManager extends BaseToastManager implements IPlainShow {

    private int mXOffset;
    private int mYOffset;
    private int mVerticalAxisOffsetWhenBottom;
    private int mVerticalAxisOffsetWhenTop;
    private int mGravity;
    private Drawable mSrcBg;
    private Drawable mColorDrawable;

    public PlainToastManager() {
        super();
        setupInitialPosInfo();
    }

    @Override
    protected Toast createToast() {
        if (ToastDelegate.get().hasPlainSetting()
                && ToastDelegate.get().getPlainSetting().getCustomViewCallback() != null) {

            mView = Utils.requireNonNull(
                    ToastDelegate.get().getPlainSetting().getCustomViewCallback().createToastView(Utils.getInflater()),
                    "customViewCallback must return a non null view!"
            );
            mMsgView = mView.findViewById(R.id.custom_toast_msg);
            mToast = new Toast(SmartShow.getContext());
            mToast.setView(mView);
        } else {
            mToast = Toast.makeText(SmartShow.getContext(), "", Toast.LENGTH_SHORT);
            mView = mToast.getView();
            if (mSrcBg == null) {
                mSrcBg = mView.getBackground();
                if (mSrcBg != null) {
                    mSrcBg.mutate();
                }
            }
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
    protected void applySetting() {
        super.applySetting();
        if (!ToastDelegate.get().hasPlainSetting()) {
            return;
        }

        int bgMode = ToastDelegate.get().getPlainSetting().getBgMode();
        switch (bgMode) {
            case IPlainToastSetting.BG_MODE_SRC:
                if (mSrcBg == null) {
                    mSrcBg = Utils.getDrawableFromRes(android.R.drawable.toast_frame);
                    mSrcBg.mutate();
                }
                ViewCompat.setBackground(mView, mSrcBg);
                break;
            case IPlainToastSetting.BG_MODE_COLOR:
                int color = ToastDelegate.get().getPlainSetting().getBgColor();
                if (color != 0) {
                    if (getColorDrawable() instanceof GradientDrawable) {
                        ((GradientDrawable) getColorDrawable()).setColor(color);
                    } else {
                        DrawableCompat.setTint(getColorDrawable(), color);
                    }
                    ViewCompat.setBackground(mView, getColorDrawable());
                }
                break;
            case IPlainToastSetting.BG_MODE_DRAWABLE:
                int drawableRes = ToastDelegate.get().getPlainSetting().getBgDrawableRes();
                if (drawableRes != 0) {
                    mView.setBackgroundResource(drawableRes);
                }
                break;
        }


        if (mMsgView == null) {
            return;
        }

        int color = ToastDelegate.get().getPlainSetting().getTextColor();
        if (color != 0) {
            mMsgView.setTextColor(color);
        }

        float textSizeSp = ToastDelegate.get().getPlainSetting().getTextSizeSp();
        if (textSizeSp != 0) {
            mMsgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        }

        mMsgView.getPaint().setFakeBoldText(ToastDelegate.get().getPlainSetting().isTextBold());

        mMsgView.setGravity(Gravity.CENTER);
    }

    public Drawable getColorDrawable() {
        if (mColorDrawable == null) {
            mColorDrawable = Utils.getDrawableFromRes(android.R.drawable.toast_frame);
            mColorDrawable.mutate();
        }
        return mColorDrawable;
    }

    @Override
    protected int getToastType() {
        return PLAIN_TOAST;
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

        boolean needInvokeShow = !isShowing();

        EasyLogger.d("contentChanged:" + contentChanged + ",posChanged:" + locationChanged);

        if (isShowing() && (locationChanged || contentChanged)) {
            dismiss();
            needInvokeShow = true;
        }

        mCurMsg = msg;
        mDuration = duration;
        mGravity = gravity;
        mXOffset = xOffset;
        mYOffset = yOffset;
        updateToast();
        mToast.setGravity(mGravity, mXOffset, mYOffset);
        mToast.setDuration(mDuration);
        if (needInvokeShow) {
            showToast();
        }
    }

    private boolean locationChanged(int gravity, int xOffset, int yOffset) {
        return mGravity != gravity || mXOffset != xOffset || mYOffset != yOffset;
    }

}
