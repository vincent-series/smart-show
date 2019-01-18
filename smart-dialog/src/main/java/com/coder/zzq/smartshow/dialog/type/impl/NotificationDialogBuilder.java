package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import com.coder.zzq.smartshow.dialog.type.INotificationDialogBuilder;

public class NotificationDialogBuilder extends MessageDialogBuilder<INotificationDialogBuilder> implements INotificationDialogBuilder {
    private static NotificationDialogBuilder sNotificationDialogBuilder;

    public NotificationDialogBuilder() {
        super();
    }

    @Override
    public Dialog createDialog(Activity activity) {
        Dialog dialog = super.createDialog(activity);
        mNegativeBtn.setVisibility(View.GONE);
        mSeparatorBetweenBtn.setVisibility(View.GONE);
        return dialog;
    }

    public static INotificationDialogBuilder getInstance() {
        if (sNotificationDialogBuilder == null) {
            sNotificationDialogBuilder = new NotificationDialogBuilder();
        }
        sNotificationDialogBuilder.reset();
        return sNotificationDialogBuilder;
    }
}
