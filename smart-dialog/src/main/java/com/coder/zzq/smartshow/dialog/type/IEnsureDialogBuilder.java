package com.coder.zzq.smartshow.dialog.type;

import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.dialog.widget.DialogBtn;

public interface IEnsureDialogBuilder extends IMessageDialogBuilder<IEnsureDialogBuilder> {
    IEnsureDialogBuilder negativeBtn(CharSequence label, DialogBtn.onDialogBtnClickListener clickListener);

    IEnsureDialogBuilder negativeBtnTextStyle(@ColorInt int color, float textSizeSp, boolean bold);

    IEnsureDialogBuilder delaySeconds(int seconds);
}
