package com.coder.zzq.smartshow.dialog.dialog;

import com.coder.zzq.smartshow.dialog.dialog.type.INormalDialogCreator;

public interface IMessage<B> extends ITitleBuilder<B>, IConfirmBtnBuilder<B>,INormalDialogCreator<B> {
    B message(CharSequence msg);
}
