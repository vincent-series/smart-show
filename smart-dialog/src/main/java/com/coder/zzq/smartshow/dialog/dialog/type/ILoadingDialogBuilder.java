package com.coder.zzq.smartshow.dialog.dialog.type;

import android.app.Activity;

import com.coder.zzq.smartshow.dialog.dialog.DialogWrapper;

public interface ILoadingDialogBuilder {

    ILoadingDialogBuilder msg(CharSequence msg);

    ILoadingDialogBuilder large();

    ILoadingDialogBuilder middle();

    ILoadingDialogBuilder small();

    boolean createAndShow(Activity activity, DialogWrapper dialogWrapper);
}
