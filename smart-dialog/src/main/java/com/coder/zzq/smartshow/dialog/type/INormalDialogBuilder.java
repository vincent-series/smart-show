package com.coder.zzq.smartshow.dialog.type;

import android.app.Activity;
import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.dialog.widget.DialogBtn;
import com.coder.zzq.smartshow.dialog.DialogWrapper;

public interface INormalDialogBuilder<B> {
    B title(CharSequence title);

    B positiveBtn(CharSequence label, DialogBtn.onDialogBtnClickListener clickListener);

    B positiveBtnTextStyle(@ColorInt int color, float textSizeSp, boolean bold);

    B cancelable(boolean b);

    B cancelableOnTouchOutside(boolean b);

    boolean createAndShow(Activity activity, DialogWrapper dialogWrapper);
}
