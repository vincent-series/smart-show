package com.coder.zzq.smartshow.dialog.creator;

import android.app.Activity;
import android.app.Dialog;

import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.dialog.DialogCreator;
import com.coder.zzq.smartshow.dialog.dialog.type.impl.NotificationBuilder;

public class NotificationCreator extends DialogCreator {
    @Override
    public Dialog createDialog(Activity activity) {
        new NotificationBuilder(activity, R.style.smart_show_normal_dialog);
        return null;
    }
}
