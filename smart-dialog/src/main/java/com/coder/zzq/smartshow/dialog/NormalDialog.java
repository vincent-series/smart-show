package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.Utils;

public abstract class NormalDialog<D extends SmartDialog> extends SmartDialog<AppCompatDialog> {

    protected boolean mDarkAroundWhenShow;
    protected boolean mCancelableOnTouchOutside;
    protected boolean mCancelable;
    @DrawableRes
    protected int mWindowBackground;

    protected DialogInterface.OnShowListener mOnShowListener;
    protected DialogInterface.OnDismissListener mOnDismissListener;
    protected DialogInterface.OnCancelListener mOnCancelListener;

    protected View mContentView;

    public NormalDialog() {
        mWindowBackground = R.drawable.smart_show_round_dialog_bg;
        mCancelable = true;
        mCancelableOnTouchOutside = true;
        mDarkAroundWhenShow = true;
    }

    public D darkAroundWhenShow(boolean dim) {
        mDarkAroundWhenShow = dim;
        return (D) this;
    }

    public D windowBackground(int bgRes) {
        mWindowBackground = bgRes;
        return (D) this;
    }

    public D cancelable(boolean b) {
        mCancelable = b;
        if (!b) {
            mCancelableOnTouchOutside = false;
        }
        return (D) this;
    }

    public D cancelableOnTouchOutside(boolean b) {
        mCancelableOnTouchOutside = b;
        return (D) this;
    }

    public D dialogCancelListener(DialogInterface.OnCancelListener cancelListener) {
        mOnCancelListener = cancelListener;
        return (D) this;
    }

    public D dialogShowListener(DialogInterface.OnShowListener showListener) {
        mOnShowListener = showListener;
        return (D) this;
    }

    public D dialogDismissListener(DialogInterface.OnDismissListener dismissListener) {
        mOnDismissListener = dismissListener;
        return (D) this;
    }

    @NonNull
    @Override
    protected AppCompatDialog createDialog(Activity activity) {
        AppCompatDialog dialog = new AppCompatDialog(activity, provideDialogStyle());
        mContentView = Utils.inflate(provideContentLayout(), null);
        initView(dialog, mContentView);
        applyNewSetting(dialog);
        ViewGroup.MarginLayoutParams rootLp = new ViewGroup.MarginLayoutParams(provideDialogWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(mContentView, rootLp);
        return dialog;
    }

    public SmartDialog apply() {
        if (mNestedDialog != null) {
            applyNewSetting(mNestedDialog);
        }
        return this;
    }

    protected void applyNewSetting(AppCompatDialog dialog) {
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
        dialog.setOnShowListener(mOnShowListener);
        dialog.setOnDismissListener(mOnDismissListener);
        dialog.setOnCancelListener(mOnCancelListener);
    }

    protected int provideDialogStyle() {
        return R.style.smart_show_dialog;
    }

    protected void initView(AppCompatDialog dialog, View contentView) {

    }

    protected int provideDialogWidth() {
        return Math.min(Utils.screenWidth() - Utils.dpToPx(70), Utils.dpToPx(290));
    }

    @LayoutRes
    protected abstract int provideContentLayout();
}
