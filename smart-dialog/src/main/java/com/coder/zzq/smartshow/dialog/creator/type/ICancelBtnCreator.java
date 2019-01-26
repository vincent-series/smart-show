package com.coder.zzq.smartshow.dialog.creator.type;

import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;


public interface ICancelBtnCreator<B> {
    B cancelBtn(CharSequence label);

    B cancelBtn(CharSequence label, DialogBtnClickListener clickListener);

    B cancelBtnTextStyle(@ColorInt int color, float textSizeSp, boolean bold);
}
