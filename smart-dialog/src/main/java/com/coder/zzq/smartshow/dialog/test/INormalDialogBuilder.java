package com.coder.zzq.smartshow.dialog.test;

import android.app.Activity;

public interface INormalDialogBuilder<B extends INormalDialogBuilder> {
    B cancelable(boolean b);

    B cancelableOnTouchOutside(boolean b);

    boolean createAndShow(Activity activity, int tag);
}
