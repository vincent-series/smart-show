package com.coder.zzq.smartshow.dialog;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.annotation.ColorInt;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;

public class DelayShowTimer extends CountDownTimer {
    private TextView mBtn;
    private CharSequence mFinalLable;
    @ColorInt 
    private int mFinalColor;
    private StringBuilder mDelayLabel;

    public DelayShowTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        mDelayLabel = new StringBuilder();

    }

    public void setBtn(TextView btn) {
        mBtn = btn;
        mBtn.setTextColor(Color.LTGRAY);
        mBtn.setEnabled(false);
    }

    public void setFinalColor(int finalColor) {
        mFinalColor = finalColor;
    }

    public void setFinalLable(CharSequence finalLable) {
        mFinalLable = finalLable;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mBtn != null) {
            mDelayLabel.delete(0, mDelayLabel.length());
            mDelayLabel.append(mFinalLable)
                    .append("(")
                    .append(millisUntilFinished / 1000)
                    .append("s)");
            mBtn.setText(mDelayLabel);
        }

    }

    @Override
    public void onFinish() {
        if (mBtn != null) {
            mBtn.setEnabled(true);
            mBtn.setTextColor(mFinalColor);
            mBtn.setText(mFinalLable);
        }
    }


    public void cancelTask() {
        super.cancel();
        mBtn = null;
    }
}
