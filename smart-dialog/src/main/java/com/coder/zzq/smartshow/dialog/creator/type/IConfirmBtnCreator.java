package com.coder.zzq.smartshow.dialog.creator.type;

import android.content.DialogInterface;
import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;


public interface IConfirmBtnCreator<B> {
    B confirmBtn(CharSequence label, DialogBtnClickListener clickListener);

    B confirmBtnTextStyle(@ColorInt int color, float textSizeSp, boolean bold);

    B secondsDelayConfirm(int seconds);
}
