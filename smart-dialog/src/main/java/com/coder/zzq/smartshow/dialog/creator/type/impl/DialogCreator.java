package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.Utils;

public abstract class DialogCreator {
    protected Dialog mDialog;

    public abstract Dialog createDialog(Activity activity);

    public void resetDialog(Dialog dialog) {

    }

    public Dialog getDialog(@NonNull Activity activity) {
        if (activity == null || activity.isFinishing() || Utils.isActivityDestroyed(activity)) {
            EasyLogger.d("activity is invalid,so don't create any dialog");
            return null;
        }
        if (mDialog == null) {
            mDialog = createDialog(activity);
            EasyLogger.d("cache dialog is null,so create a dialog first");
        } else {
            resetDialog(mDialog);
            EasyLogger.d("cache dialog is not null,so reuse and reset it");
        }
        return mDialog;
    }
}
