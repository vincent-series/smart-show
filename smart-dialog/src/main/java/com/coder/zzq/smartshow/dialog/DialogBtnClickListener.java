package com.coder.zzq.smartshow.dialog;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface DialogBtnClickListener<D extends SmartDialog> {
    int BTN_CONFIRM = 0;
    int BTN_CANCEL = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({BTN_CONFIRM, BTN_CANCEL})
    @interface DialogBtn {
    }

    void onBtnClick(D dialog, @DialogBtn int which, Object data);
}
