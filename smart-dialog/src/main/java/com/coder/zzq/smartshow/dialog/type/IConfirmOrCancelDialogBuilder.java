package com.coder.zzq.smartshow.dialog.type;

public interface IConfirmOrCancelDialogBuilder<B> extends INotificationDialogBuilder<B> {
    B cancelable(boolean b);

    B cancelableOnTouchOutside(boolean b);
}
