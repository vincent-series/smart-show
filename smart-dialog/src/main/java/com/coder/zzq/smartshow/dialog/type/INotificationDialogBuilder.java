package com.coder.zzq.smartshow.dialog.type;

import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;

public interface INotificationDialogBuilder<B> extends IDialogBuilder<B> {
    B title(CharSequence title);

    B confirmBtn(CharSequence label, DialogBtnClickListener clickListener);

    B confirmBtnTextStyle(@ColorInt int textColor, float textSizeSp);

    B message(CharSequence msg);
}
