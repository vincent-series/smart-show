package com.coder.zzq.smartshow.dialog.type;

import android.support.annotation.StringRes;

public interface IMessageDialogBuilder<B> extends INormalDialogBuilder<B> {
    B message(CharSequence msg);
}
