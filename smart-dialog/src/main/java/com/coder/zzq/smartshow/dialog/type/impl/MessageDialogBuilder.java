package com.coder.zzq.smartshow.dialog.type.impl;

import android.widget.TextView;

import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.type.INotificationDialogBuilder;

public class MessageDialogBuilder extends NormalDiaBuilder<INotificationDialogBuilder> implements INotificationDialogBuilder {

    @Override
    protected int getContentPartLayoutRes() {
        return R.layout.smart_show_message_content;
    }

    @Override
    public INotificationDialogBuilder message(CharSequence msg) {
        ((TextView) mContentPartView).setText(msg);
        return this;
    }

}
