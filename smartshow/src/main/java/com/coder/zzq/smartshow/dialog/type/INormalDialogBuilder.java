package com.coder.zzq.smartshow.dialog.type;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.FrameLayout;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.DialogContentCallback;

public interface INormalDialogBuilder<B> {
    B title(CharSequence title);

    B positiveBtn(CharSequence label, DialogBtnClickListener clickListener);

    B cancelable(boolean b);

    B cancelableOnTouchOutside(boolean b);

    B processContent(DialogContentCallback callback);

    Dialog create(Activity activity);

}
