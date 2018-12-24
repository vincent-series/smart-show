package com.coder.zzq.smartshow.dialog.type;

import android.app.Activity;
import android.app.Dialog;

public interface ILoadingDialogBuilder {

    ILoadingDialogBuilder msg(CharSequence msg);

    ILoadingDialogBuilder large();

    ILoadingDialogBuilder middle();

    ILoadingDialogBuilder small();

    Dialog create(Activity activity);

}
