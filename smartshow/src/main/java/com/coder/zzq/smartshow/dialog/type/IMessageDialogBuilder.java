package com.coder.zzq.smartshow.dialog.type;

public interface IMessageDialogBuilder<B> extends INormalDialogBuilder<B> {
    B message(CharSequence msg);
}
