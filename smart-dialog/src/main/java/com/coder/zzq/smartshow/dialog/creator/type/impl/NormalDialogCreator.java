package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.creator.type.INormalDialogCreator;

abstract class NormalDialogCreator<B> extends DialogCreator implements INormalDialogCreator<B> {
    protected boolean mDarkAroundWhenShow;
    protected boolean mCancelableOnTouchOutside;
    protected boolean mCancelable;
    @DrawableRes
    protected int mWindowBackground;

    protected DialogInterface.OnShowListener mOnShowListener;
    protected DialogInterface.OnDismissListener mOnDismissListener;
    protected DialogInterface.OnCancelListener mOnCancelListener;

    public NormalDialogCreator() {
        mWindowBackground = R.drawable.smart_show_round_dialog_bg;
        mCancelable = true;
        mCancelableOnTouchOutside = true;
        mDarkAroundWhenShow = true;
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
        mCancelable = b;
        if (!b) {
            mCancelableOnTouchOutside = false;
        }
        return (B) this;
    }

    @Override
    public B cancelableOnTouchOutside(boolean b) {
        mCancelableOnTouchOutside = b;
        return (B) this;
    }

    @Override
    public B cancelListener(DialogInterface.OnCancelListener cancelListener) {
        mOnCancelListener = cancelListener;
        return (B) this;
    }

    @Override
    public B showListener(DialogInterface.OnShowListener showListener) {
        mOnShowListener = showListener;
        return (B) this;
    }

    @Override
    public B dismissListener(DialogInterface.OnDismissListener dismissListener) {
        mOnDismissListener = dismissListener;
        return (B) this;
    }

    @Override
    public Dialog createDialog(Activity activity) {
        Dialog dialog = new AppCompatDialog(activity, provideDialogStyle());
        if (mDarkAroundWhenShow) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        if (mWindowBackground != 0) {
            dialog.getWindow().setBackgroundDrawableResource(mWindowBackground);
        }

        dialog.setCanceledOnTouchOutside(mCancelable ? mCancelableOnTouchOutside : false);
        dialog.setCancelable(mCancelable);
        if (mOnShowListener != null) {
            dialog.setOnShowListener(mOnShowListener);
        }

        if (mOnDismissListener != null) {
            dialog.setOnDismissListener(mOnDismissListener);
        }

        if (mOnCancelListener != null) {
            dialog.setOnCancelListener(mOnCancelListener);
        }

        View dialogRootView = Utils.inflate(provideDialogRootView(), null);
        initView(dialog, dialogRootView);
        ViewGroup.MarginLayoutParams rootLp = new ViewGroup.MarginLayoutParams(provideDialogWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(dialogRootView, rootLp);
        return dialog;
    }

    protected int provideDialogStyle() {
        return R.style.smart_show_dialog;
    }

    protected void initView(Dialog dialog, View dialogRootView) {

    }

    protected int provideDialogWidth() {
        return Math.min(Utils.screenWidth() - Utils.dpToPx(70), Utils.dpToPx(290));
    }

    @LayoutRes
    protected abstract int provideDialogRootView();
}
