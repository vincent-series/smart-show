package com.coder.zzq.smartshow.dialog;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.Utils;

public class DelayShowTimer extends CountDownTimer {
    private TextView mBtn;
    private StringBuilder mPositiveLabel;

    public DelayShowTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        mPositiveLabel = new StringBuilder();
    }

    public void setBtn(TextView btn) {
        mBtn = btn;
        mBtn.setTextColor(Color.LTGRAY);
        mBtn.setEnabled(false);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (mBtn != null) {
            mPositiveLabel.delete(0, mPositiveLabel.length());
            mPositiveLabel.append("确定")
                    .append("(")
                    .append(millisUntilFinished / 1000)
                    .append("s)");
            mBtn.setText(mPositiveLabel);
        }

    }

    @Override
    public void onFinish() {
        if (mBtn != null) {
            mBtn.setEnabled(true);
            mBtn.setTextColor(Utils.getColorFromRes(R.color.colorPrimary));
            mBtn.setText("确定");
        }
    }


    public void cancelTask(){
        super.cancel();
        mBtn = null;
    }
}
