package com.coder.zzq.smartshow.dialog;

import android.support.annotation.IntDef;
import android.support.v7.app.AppCompatDialog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface DialogBtnClickListener {
    int BTN_CONFIRM = 0;
    int BTN_CANCEL = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BTN_CONFIRM, BTN_CANCEL})
    @interface DialogBtn {
    }

    void onBtnClick(AppCompatDialog dialog, @DialogBtn int which, Object data);
}
