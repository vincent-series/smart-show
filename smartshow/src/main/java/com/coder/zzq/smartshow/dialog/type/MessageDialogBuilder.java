package com.coder.zzq.smartshow.dialog.type;

import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import com.coder.zzq.smartshow.SmartShow;

public class MessageDialogBuilder extends DialogBuilder<TextView, IMessageDialog>
        implements IMessageDialog {
    public MessageDialogBuilder(int layoutRes) {
        super(layoutRes);
    }

    public MessageDialogBuilder(View contentPartView) {
        super(contentPartView);
    }

    @Override
    public IMessageDialog setMessage(int msgRes) {
        return setMessage(SmartShow.getContext().getString(msgRes));
    }

    @Override
    public IMessageDialog setMessage(CharSequence msg) {
        mContentPart.setText(msg);
        return this;
    }
}
