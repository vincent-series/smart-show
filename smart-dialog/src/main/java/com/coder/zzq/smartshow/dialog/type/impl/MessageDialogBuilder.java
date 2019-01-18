package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.widget.TextView;

import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.type.IMessageDialogBuilder;

public class MessageDialogBuilder<B> extends NormalDialogBuilder<B> implements IMessageDialogBuilder<B> {
    protected CharSequence mMessage;

    @Override
    protected int getContentPartLayoutRes() {
        return R.layout.smart_show_message_content;
    }

    @Override
    public B message(CharSequence msg) {
        mMessage = msg;
        return (B) this;
    }

    @Override
    public void reset() {
        super.reset();
        mMessage = null;
        mContentPartView = null;
    }

    @Override
    public Dialog createDialog(Activity activity) {
        Dialog dialog = super.createDialog(activity);
        ((TextView) mContentPartView).setText(mMessage);
        return dialog;
    }

    @Override
    public void resetDialog(Dialog dialog) {
        super.resetDialog(dialog);
        ((TextView) mContentPartView).setText(mMessage);
    }
}
