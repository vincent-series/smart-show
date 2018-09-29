package com.coder.zzq.smartshow.dialog;

import android.widget.TextView;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.dialog.type.DialogBuilder;
import com.coder.zzq.smartshow.dialog.type.IMessageDialog;
import com.coder.zzq.smartshow.dialog.type.MessageDialogBuilder;

public final class DialogDelegate {

    private static DialogDelegate sDialogDelegate;

    private static LoadingDialog sLoadingDialog;


    private DialogDelegate() {

    }

    public static DialogDelegate get() {
        if (sDialogDelegate == null) {
            sDialogDelegate = new DialogDelegate();
        }
        return sDialogDelegate;
    }

    public void loading(String msg) {
        if (sLoadingDialog == null) {
            sLoadingDialog = new LoadingDialog.Builder()
                    .create(msg);
        } else if (sLoadingDialog.isInvalid()) {
            sLoadingDialog.destroy();
            sLoadingDialog = new LoadingDialog.Builder()
                    .create(msg);
        } else {
            sLoadingDialog.changeMsg(msg);
        }

        sLoadingDialog.show();
    }

    public static void destroyDelegate() {
        if (sLoadingDialog != null) {
            sLoadingDialog.destroy();
            sLoadingDialog = null;
        }

        sDialogDelegate = null;

    }

    public static boolean hasCreate() {
        return sDialogDelegate != null;
    }

    public void dismissLoading() {
        if (sLoadingDialog != null) {
            sLoadingDialog.dismiss();
        }
    }

    public IMessageDialog message(String msg) {
        return new MessageDialogBuilder(R.layout.smart_show_message_content)
                .setMessage(msg);
    }
}
