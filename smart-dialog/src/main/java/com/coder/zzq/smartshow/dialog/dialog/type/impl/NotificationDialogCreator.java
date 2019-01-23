package com.coder.zzq.smartshow.dialog.dialog.type.impl;

import android.widget.FrameLayout;

import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.dialog.type.INotificationBuilder;

public class NotificationDialogCreator extends MessageDialogCreator<INotificationBuilder> implements INotificationBuilder {

    @Override
    protected int provideTitleView() {
        return R.layout.default_title;
    }

    @Override
    protected int provideBodyView() {
        return R.layout.default_message;
    }

    @Override
    protected int provideBtnView() {
        return R.layout.smart_show_default_single_button;
    }

    @Override
    protected void initBody(FrameLayout wrapper) {
        super.initBody(wrapper);
    }
}
