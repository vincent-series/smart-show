package com.coder.zzq.smartshow.dialog.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

public abstract class DialogCreator extends AlertDialog.Builder {
    public DialogCreator(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public abstract AlertDialog createDialog(Activity activity);

    public void resetDialog(AlertDialog dialog) {

    }
}
