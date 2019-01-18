package com.coder.zzq.smartshow.dialog.type.impl;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.type.IEnsureDialogBuilder;

public class EnsureDialogBuilder extends MessageDialogBuilder<IEnsureDialogBuilder> implements IEnsureDialogBuilder {
    private static EnsureDialogBuilder sEnsureDialogBuilder;

    @Override
    public IEnsureDialogBuilder negativeBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mOnNegativeBtnClickListener = clickListener;
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
