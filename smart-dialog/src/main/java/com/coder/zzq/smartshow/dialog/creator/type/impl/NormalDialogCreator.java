package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.creator.type.INormalDialogCreator;

abstract class NormalDialogCreator<B> extends DialogCreator implements INormalDialogCreator<B> {
    protected boolean mDarkAroundWhenShow;
    protected boolean mCancelableOnTouchOutside;
    protected boolean mCancelable;
    protected View mDialogRootView;
    @DrawableRes
    protected int mWindowBackground;

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
        mCancelable = true;
        return (B) this;
    }

    @Override
    public B cancelableOnTouchOutside(boolean b) {
        mCancelableOnTouchOutside = b;
        return (B) this;
    }

    @Override
    public boolean createAndShow(Activity activity) {
        return SmartDialog.show(activity, this);
    }

    @Override
    public Dialog createDialog(Activity activity) {
        mDialog = new AppCompatDialog(activity, provideDialogStyle());
        if (mDarkAroundWhenShow) {
            mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        mDialog.getWindow().setBackgroundDrawableResource(mWindowBackground);
        mDialog.setCancelable(mCancelable);
        mDialog.setCanceledOnTouchOutside(mCancelableOnTouchOutside);
        initView();
        ViewGroup.MarginLayoutParams rootLp = new ViewGroup.MarginLayoutParams(provideDialogWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.setContentView(mDialogRootView, rootLp);
        return mDialog;
    }

    protected int provideDialogStyle() {
        return R.style.smart_show_dialog;
    }

    protected void initView() {
        mDialogRootView = Utils.inflate(provideDialogRootView(), null);
    }

    protected int provideDialogWidth() {
        return Utils.screenWidth() - Utils.dpToPx(70);
    }

    @LayoutRes
    protected abstract int provideDialogRootView();
}
