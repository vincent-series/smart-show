package com.coder.zzq.smartshow.dialog.type.impl;

import com.coder.zzq.smartshow.dialog.widget.DialogBtn;
import com.coder.zzq.smartshow.dialog.type.IEnsureDialogBuilder;

public class EnsureDialogBuilder extends MessageDialogBuilder<IEnsureDialogBuilder> implements IEnsureDialogBuilder {
    private static EnsureDialogBuilder sEnsureDialogBuilder;

    @Override
    public IEnsureDialogBuilder negativeBtn(CharSequence label, DialogBtn.onDialogBtnClickListener clickListener) {
        mOnNegativeBtnClickListener = clickListener;
        return this;
    }

    @Override
    public IEnsureDialogBuilder negativeBtnTextStyle(int color, float textSizeSp, boolean bold) {
        mNegativeLabelColor = color;
        mNegativeTextSizeSp = textSizeSp;
        mNegativeTextBold = bold;
        return this;
    }

    @Override
    public IEnsureDialogBuilder delaySeconds(int seconds) {
        mDelaySeconds = seconds;
        return this;
    }


    public static IEnsureDialogBuilder getInstance() {
        if (sEnsureDialogBuilder == null) {
            sEnsureDialogBuilder = new EnsureDialogBuilder();
        }
        sEnsureDialogBuilder.reset();
        return sEnsureDialogBuilder;
    }
}
