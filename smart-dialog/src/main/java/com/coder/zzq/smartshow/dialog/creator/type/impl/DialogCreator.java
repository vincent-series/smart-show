package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.app.Activity;
import android.app.Dialog;

import com.coder.zzq.smartshow.dialog.creator.type.IDialogCreator;

public abstract class DialogCreator implements IDialogCreator {

    public abstract Dialog createDialog(Activity activity);

    public void resetDialogPerShow(Dialog dialog) {

    }

}
