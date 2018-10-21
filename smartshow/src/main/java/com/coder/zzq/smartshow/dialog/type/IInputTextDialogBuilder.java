package com.coder.zzq.smartshow.dialog.type;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;

public interface IInputTextDialogBuilder extends INormalDialogBuilder<IInputTextDialogBuilder> {
    IInputTextDialogBuilder hint(CharSequence hintMsg);

    IInputTextDialogBuilder inputAtMost(int num);

    IInputTextDialogBuilder negativeBtn(CharSequence label, DialogBtnClickListener clickListener);
}
