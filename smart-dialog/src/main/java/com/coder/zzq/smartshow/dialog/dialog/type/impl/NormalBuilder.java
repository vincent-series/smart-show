package com.coder.zzq.smartshow.dialog.dialog.type.impl;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.dialog.DialogCreator;
import com.coder.zzq.smartshow.dialog.dialog.DialogWrapper;
import com.coder.zzq.smartshow.dialog.dialog.type.INormalBuilder;

public class NormalBuilder<B> extends DialogCreator implements INormalBuilder<B> {
    protected boolean mDarkAroundWhenShow = true;
    protected boolean mCancelableOnTouchOutside = true;
    @DrawableRes
    protected int mWindowBackground = R.drawable.smart_show_round_dialog_bg;

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
        setCancelable(b);
        return (B) this;
    }

    @Override
    public B cancelableOnTouchOutside(boolean b) {
        mCancelableOnTouchOutside = b;
        return (B) this;
    }

    @Override
    public boolean createAndShow(Activity activity, DialogWrapper dialogWrapper) {
        return SmartDialog.show(activity, this, dialogWrapper);
    }

    @Override
    public AlertDialog createDialog(Activity activity) {
        final AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(mCancelableOnTouchOutside);
        if (mDarkAroundWhenShow) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        dialog.getWindow().setBackgroundDrawableResource(mWindowBackground);
        dialog.getWindow().getDecorView().getViewTreeObserver()
                .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        dialog.getWindow().getDecorView().findViewById(android.R.id.content).getLayoutParams().width = Utils.screenWidth() - Utils.dpToPx(70);
                        adjustDialogLayout(dialog);
                        dialog.getWindow().getDecorView().getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });

        return dialog;
    }

    protected void adjustDialogLayout(AlertDialog dialog) {

    }

    @Override
    public void resetDialog(AlertDialog dialog) {

    }
}
