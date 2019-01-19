package com.coder.zzq.smartshow.dialog.dialog;

import com.coder.zzq.smartshow.dialog.dialog.type.INormalBuilder;

public interface IMessage<B> extends ITitleBuilder<B>, IConfirmBtnBuilder<B>,INormalBuilder<B> {
    B message(CharSequence msg);
}
