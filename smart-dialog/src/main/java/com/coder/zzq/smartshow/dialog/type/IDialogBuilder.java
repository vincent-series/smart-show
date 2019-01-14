package com.coder.zzq.smartshow.dialog.type;

import android.app.Activity;

public interface IDialogBuilder<B> {
    B cancelable(boolean b);

    B cancelableOnTouchOutside(boolean b);

    boolean createAndShow(Activity activity, int tag);
}
