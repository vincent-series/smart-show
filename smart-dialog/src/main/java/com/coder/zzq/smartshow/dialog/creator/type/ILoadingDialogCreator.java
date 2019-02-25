package com.coder.zzq.smartshow.dialog.creator.type;

public interface ILoadingDialogCreator extends INormalDialogCreator<ILoadingDialogCreator> {
    ILoadingDialogCreator message(CharSequence msg);

    ILoadingDialogCreator withoutMsgTip();

    ILoadingDialogCreator large();

    ILoadingDialogCreator middle();

    ILoadingDialogCreator small();
}
