package com.coder.zzq.smartshow.dialog.test;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;

import com.coder.zzq.smartshow.dialog.DialogCreator;
import com.coder.zzq.smartshow.dialog.SmartDialog;

public class NormalDialogBuilder<B extends INormalDialogBuilder, M extends SingleBtnManage> extends DialogCreator<Dialog> implements INormalDialogBuilder<B> {
    protected boolean mCancelable;
    protected boolean mCancelableOnTouchOutside;

    public NormalDialogBuilder() {
        mCancelable = true;
        mCancelableOnTouchOutside = true;
    }

    @Override
    public B cancelable(boolean b) {
        mCancelable = b;
        return (B) this;
    }

    @Override
    public B cancelableOnTouchOutside(boolean b) {
        mCancelableOnTouchOutside = b;
        return (B) this;
    }

    @Override
    public boolean createAndShow(Activity activity, int tag) {
        return SmartDialog.show(activity, this, tag);
    }

    @Override
    public Dialog createDialog(@NonNull Activity activity) {

        return null;
    }

    @Override
    public void resetDialog(Dialog dialog) {

    }
}
