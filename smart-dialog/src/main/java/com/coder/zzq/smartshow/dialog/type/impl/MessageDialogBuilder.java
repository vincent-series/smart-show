package com.coder.zzq.smartshow.dialog.type.impl;

import android.widget.TextView;

import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.type.IMessageDialogBuilder;

public class MessageDialogBuilder extends NormalDialogBuilder<IMessageDialogBuilder> implements IMessageDialogBuilder {

    @Override
    protected int getContentPartLayoutRes() {
        return R.layout.smart_show_message_content;
    }

    @Override
    public IMessageDialogBuilder message(CharSequence msg) {
        ((TextView) mContentPartView).setText(msg);
        return this;
    }

}
