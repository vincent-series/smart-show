package com.coder.zzq.smartshow.dialog.type.impl;

import android.view.View;

import com.coder.zzq.smartshow.dialog.type.INotificationDialogBuilder;

public class NotificationDialogBuilder extends MessageDialogBuilder<INotificationDialogBuilder> implements INotificationDialogBuilder {
    public NotificationDialogBuilder() {
        super();
        mNegativeBtn.setVisibility(View.GONE);
        mSeparatorBetweenBtn.setVisibility(View.GONE);
    }
}
