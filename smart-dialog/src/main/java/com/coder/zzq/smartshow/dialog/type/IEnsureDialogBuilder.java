package com.coder.zzq.smartshow.dialog.type;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;

public interface IEnsureDialogBuilder extends IMessageDialogBuilder<IEnsureDialogBuilder> {
    IEnsureDialogBuilder negativeBtn(CharSequence label, DialogBtnClickListener clickListener);
}
