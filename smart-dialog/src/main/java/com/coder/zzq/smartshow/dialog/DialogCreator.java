package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;

public abstract class DialogCreator {
    public abstract Dialog createDialog(Activity activity);

    public void resetDialog(Dialog dialog){

    }
}
