package com.coder.zzq.smartshow.dialog.dialog;

import android.content.DialogInterface;
import android.support.annotation.ColorInt;

public interface ICancelBtnBuilder<B> {
    B cancelBtn(CharSequence label, DialogInterface.OnClickListener clickListener);

    B cancelBtnTextStyle(@ColorInt int color, float textSizeSp, boolean bold);
}
