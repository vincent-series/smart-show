package com.coder.zzq.smartshow.dialog.dialog;

import android.content.DialogInterface;
import android.support.annotation.ColorInt;

public interface IConfirmBtnBuilder<B> {
    B confirmBtn(CharSequence label, DialogBtnClickListener clickListener);

    B confirmBtnTextStyle(@ColorInt int color, float textSizeSp, boolean bold);

    B secondsDelayConfirm(int seconds);
}
