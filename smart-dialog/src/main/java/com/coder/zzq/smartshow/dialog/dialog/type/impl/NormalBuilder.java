package com.coder.zzq.smartshow.dialog.dialog.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.dialog.DialogCreator;
import com.coder.zzq.smartshow.dialog.dialog.type.INormalBuilder;

public class NormalBuilder<B> extends AlertDialog.Builder implements INormalBuilder<B>, DialogInterface.OnShowListener {

    protected boolean mDarkAroundWhenShow;
    protected boolean mCancelableOnTouchOutside;
    protected boolean mCancelable;
    @DrawableRes
    protected int mWindowBackground;

    public NormalBuilder(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    public B darkAroundWhenShow(boolean dim) {
        mDarkAroundWhenShow = dim;
        return (B) this;
    }

    @Override
    public B windowBackground(int bgRes) {
        mWindowBackground = bgRes;
        return (B) this;
    }

    @Override
    public B cancelable(boolean b) {
        mCancelable = true;
        return (B) this;
    }

    @Override
    public B cancelableOnTouchOutside(boolean b) {
        mCancelableOnTouchOutside = b;
        return (B) this;
    }

    @Override
    public boolean createAndShow(Activity activity,DialogCreator dialogCreator) {
        return SmartDialog.show(activity, dialogCreator);
    }

    @Override
    public Dialog createDialog(Activity activity) {
        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setCancelable(mCancelable)
                .
        dialog.getWindow().setBackgroundDrawableResource(mWindowBackground);
        dialog.setOnShowListener(this);
        dialog.getOwnerActivity()
        return dialog;
    }

    protected void adjustDialogLayout(AlertDialog dialog) {

    }

    protected void setDialogDarkAround(Dialog dialog, boolean dim) {
        if (dialog != null) {
            return;
        }

        if (dim) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (mDialog == null) {
            AlertDialog alertDialog = (AlertDialog) dialog;
            alertDialog.getWindow().getDecorView().findViewById(android.R.id.content).getLayoutParams().width = Utils.screenWidth() - Utils.dpToPx(70);
            adjustDialogLayout(alertDialog);
            mDialog = alertDialog;
        }
    }
}
