package com.coder.zzq.smartshow.dialog.type.impl;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.type.IEnsureDialogBuilder;

public class EnsureDialogBuilder extends MessageDialogBuilder<IEnsureDialogBuilder> implements IEnsureDialogBuilder {
    @Override
    public IEnsureDialogBuilder negativeBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mNegativeBtn.setText(label);
        mOnNegativeBtnClickListener = clickListener;
        return this;
    }
}
