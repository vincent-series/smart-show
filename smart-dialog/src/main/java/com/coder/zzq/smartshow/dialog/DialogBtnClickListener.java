package com.coder.zzq.smartshow.dialog;

import android.app.Dialog;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface DialogBtnClickListener {
    public static final int BTN_CONFIRM = 0;
    public static final int BTN_CANCEL = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BTN_CONFIRM, BTN_CANCEL})
    @interface DialogBtn {
    }

    void onBtnClick(Dialog dialog, @DialogBtn int which);
}
