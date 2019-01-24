package com.coder.zzq.smartshow.dialog.creator.type;

public interface IMessageTipCreator<B> extends ITitleCreator<B>, IConfirmBtnCreator<B>, INormalDialogCreator<B>,
        IDelayConfirmCreator<B> {
    B message(CharSequence msg);
}
