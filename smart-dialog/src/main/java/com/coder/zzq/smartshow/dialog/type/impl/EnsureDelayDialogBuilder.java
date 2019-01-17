package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.coder.zzq.smartshow.dialog.DelayShowTimer;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.type.IEnsureDelayDialogBuilder;

public class EnsureDelayDialogBuilder extends MessageDialogBuilder<IEnsureDelayDialogBuilder> implements IEnsureDelayDialogBuilder {
    private int mDelaySeconds = 10;
    private StringBuilder mPositiveLabel;

    @Override
    public IEnsureDelayDialogBuilder negativeBtn(String label, DialogBtnClickListener clickListener) {
        mNegativeBtn.setText(label);
        mOnNegativeBtnClickListener = clickListener;
        return this;
    }

    @Override
    public IEnsureDelayDialogBuilder delay(int seconds) {
        mDelaySeconds = seconds;
        return this;
    }

    @Override
    public IEnsureDelayDialogBuilder positiveBtn(CharSequence label, DialogBtnClickListener clickListener) {
        return null;
    }

    @Override
    public Dialog create(Activity activity) {
        Dialog dialog = super.create(activity);
        mPositiveBtn.setEnabled(false);
        mPositiveLabel = new StringBuilder();

        mPositiveBtn.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            private DelayShowTimer mCountDownTimer;

            @Override
            public void onViewAttachedToWindow(View v) {
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancelTask();
                }
                mCountDownTimer = new DelayShowTimer(mDelaySeconds * 1000,1000);
                mCountDownTimer.setBtn(mPositiveBtn);


                mCountDownTimer.start();
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });

        return dialog;
    }
}
