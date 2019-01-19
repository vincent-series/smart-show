package com.coder.zzq.smartshow.dialog.type;

import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.dialog.widget.DialogBtn;

public interface IInputTextDialogBuilder extends INormalDialogBuilder<IInputTextDialogBuilder> {
    IInputTextDialogBuilder hint(CharSequence hintMsg);

    IInputTextDialogBuilder inputAtMost(int num);

    IInputTextDialogBuilder negativeBtn(CharSequence label, DialogBtn.onDialogBtnClickListener clickListener);

    IInputTextDialogBuilder negativeBtnTextStyle(@ColorInt int color, float textSizeSp, boolean bold);
    IInputTextDialogBuilder inputNumIndicatorColor(@ColorInt int color);
}
