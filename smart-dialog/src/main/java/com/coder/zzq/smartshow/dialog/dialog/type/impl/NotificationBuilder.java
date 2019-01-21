package com.coder.zzq.smartshow.dialog.dialog.type.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.coder.zzq.smartshow.dialog.dialog.type.INotificationBuilder;

public class NotificationBuilder extends MessageBuilder<INotificationBuilder> implements INotificationBuilder {
    public NotificationBuilder(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }
}
