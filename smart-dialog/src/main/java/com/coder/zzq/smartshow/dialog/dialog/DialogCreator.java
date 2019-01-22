package com.coder.zzq.smartshow.dialog.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;

import com.coder.zzq.smartshow.core.Utils;

public abstract class DialogCreator {
    protected Dialog mDialog;

    public abstract Dialog createDialog(Activity activity);

    public void resetDialog(Dialog dialog) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public Dialog getDialog(@NonNull Activity activity) {
        if (activity == null || activity.isFinishing() || Utils.isActivityDestroyed(activity)) {
            return null;
        }
        if (mDialog == null) {
            createDialog(activity);
        } else {
            resetDialog(mDialog);
        }
        return mDialog;
    }
}
