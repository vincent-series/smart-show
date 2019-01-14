package com.coder.zzq.smartshow.dialog.type;

public interface ILoadingDialogBuilder extends IDialogBuilder<ILoadingDialogBuilder> {

    ILoadingDialogBuilder msg(CharSequence msg);

    ILoadingDialogBuilder large();

    ILoadingDialogBuilder middle();

    ILoadingDialogBuilder small();
}
