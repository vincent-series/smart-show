package com.coder.zzq.smartshow.dialog.type;

public interface IDelayConfirmDialogBuilder<B> extends IConfirmOrCancelDialogBuilder<B> {
    B delaySecondsConfirm(int delaySeconds);
}
