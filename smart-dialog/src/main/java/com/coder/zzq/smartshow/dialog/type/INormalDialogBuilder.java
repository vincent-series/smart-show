package com.coder.zzq.smartshow.dialog.type;

import android.app.Activity;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.DialogWrapper;

public interface INormalDialogBuilder<B> {
    B title(CharSequence title);

    B positiveBtn(CharSequence label, DialogBtnClickListener clickListener);

    B cancelable(boolean b);

    B cancelableOnTouchOutside(boolean b);

    boolean createAndShow(Activity activity, DialogWrapper dialogWrapper);
}
