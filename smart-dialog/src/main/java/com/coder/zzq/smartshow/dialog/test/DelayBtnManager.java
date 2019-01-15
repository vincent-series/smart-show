package com.coder.zzq.smartshow.dialog.test;

import android.view.View;
import android.view.ViewGroup;

public class DelayBtnManager extends DoubleBtnManager<DelayBtnManager> implements View.OnAttachStateChangeListener, Runnable {
    private int mDelaySeconds;
    private int mCurrentSeconds;
    private StringBuilder mConfirmLabelOnDelay;

    public DelayBtnManager(int layoutRes, ViewGroup parent) {
        super(layoutRes, parent);
    }

    public DelayBtnManager delaySecondsConfirm(int seconds) {
        mDelaySeconds = seconds;
        return this;
    }

    @Override
    public void apply() {
        super.apply();
        mConfirmBtn.setEnabled(true);
        mCurrentSeconds = 0;
        mView.removeCallbacks(this);
        mConfirmBtn.removeOnAttachStateChangeListener(this);
        if (mDelaySeconds > 0) {
            mConfirmLabelOnDelay = new StringBuilder(mConfirmLabel);
            mConfirmBtn.setEnabled(false);
            mConfirmBtn.addOnAttachStateChangeListener(this);
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        mView.postDelayed(this, 1000);
    }

    @Override
    public void onViewDetachedFromWindow(View v) {

    }


    @Override
    public void run() {
        if (mCurrentSeconds > 0) {

            mConfirmLabelOnDelay.delete(mConfirmLabel.length(), mConfirmLabelOnDelay.length());
            mConfirmLabelOnDelay.append("(")
                    .append(mCurrentSeconds)
                    .append("s)");
            mConfirmBtn.setText(mConfirmLabelOnDelay.toString());
            mCurrentSeconds--;
            mView.postDelayed(this, 1000);
        } else {
            mConfirmBtn.setEnabled(true);
        }
    }
}
