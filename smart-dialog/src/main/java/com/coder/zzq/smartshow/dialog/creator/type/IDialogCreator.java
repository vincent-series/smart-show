package com.coder.zzq.smartshow.dialog.creator.type;

import android.app.Activity;
import android.app.Dialog;

public interface IDialogCreator {
    Dialog createDialog(Activity activity);

    void resetDialogPerShow(Dialog dialog);
}
