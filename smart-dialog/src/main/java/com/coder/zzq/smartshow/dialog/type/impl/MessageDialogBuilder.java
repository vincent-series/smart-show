package com.coder.zzq.smartshow.dialog.type.impl;

import android.widget.TextView;

import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.type.IMessageDialogBuilder;

public class MessageDialogBuilder<B> extends NormalDialogBuilder<B> implements IMessageDialogBuilder<B> {

    @Override
    protected int getContentPartLayoutRes() {
        return R.layout.smart_show_message_content;
    }

    @Override
    public B message(CharSequence msg) {
        ((TextView) mContentPartView).setText(msg);
        return (B) this;
    }
}
