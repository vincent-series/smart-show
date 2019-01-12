package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;

public abstract class DialogCreator<D extends Dialog> {
    public abstract D createDialog(@NonNull Activity activity);

    public void resetDialog(D dialog) {

    }
}
