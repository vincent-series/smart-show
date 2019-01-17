package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.DialogContentCallback;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.type.INormalDialogBuilder;

public abstract class NormalDialogBuilder<B> implements INormalDialogBuilder<B>, View.OnClickListener {
    protected Dialog mDialog;
    protected LinearLayout mDialogRootView;
    protected TextView mTitleView;
    protected TextView mPositiveBtn;
    protected TextView mNegativeBtn;
    protected FrameLayout mContentWrapperView;
    protected View mSeparatorBetweenBtn;
    protected View mSeparatorBetweenBtnAndContent;
    protected View mContentPartView;
    protected DialogBtnClickListener mOnPositiveBtnClickListener;
    protected DialogBtnClickListener mOnNegativeBtnClickListener;
    protected DialogContentCallback mDialogContentCallback;

    protected boolean mCancelabel = true;
    protected boolean mCanceledOnTouchOutside = true;

    public NormalDialogBuilder() {
        mDialogRootView = (LinearLayout) LayoutInflater.from(SmartShow.getContext())
                .inflate(R.layout.smart_show_normal_dialog, null);
        mTitleView = mDialogRootView.findViewById(R.id.dialog_title);
        mPositiveBtn = mDialogRootView.findViewById(R.id.dialog_positive_btn);
        mPositiveBtn.setOnClickListener(this);
        mNegativeBtn = mDialogRootView.findViewById(R.id.dialog_negative_btn);
        mNegativeBtn.setOnClickListener(this);
        mSeparatorBetweenBtn = mDialogRootView.findViewById(R.id.separator_between_btn);
        mSeparatorBetweenBtnAndContent = mDialogRootView.findViewById(R.id.separator_between_btn_and_content);

        mContentWrapperView = mDialogRootView.findViewById(R.id.dialog_content_wrapper);
        mContentPartView = LayoutInflater.from(SmartShow.getContext()).inflate(getContentPartLayoutRes(),
                mContentWrapperView, false);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentPartView.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
        lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
        mContentWrapperView.addView(mContentPartView);
    }

    @LayoutRes
    protected abstract int getContentPartLayoutRes();

    @Override
    public B title(CharSequence title) {
        if (title != null && !title.toString().trim().isEmpty()) {
            mTitleView.setVisibility(View.VISIBLE);
            mTitleView.setText(title);
        }
        return (B) this;
    }

    @Override
    public B positiveBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mPositiveBtn.setText(label);
        mOnPositiveBtnClickListener = clickListener;
        return (B) this;
    }


    @Override
    public B processContent(DialogContentCallback callback) {
        mDialogContentCallback = callback;
        return (B) this;
    }

    @Override
    public B cancelable(boolean b) {
        mCancelabel = b;
        return (B) this;
    }

    @Override
    public B cancelableOnTouchOutside(boolean b) {
        mCanceledOnTouchOutside = b;
        return (B) this;
    }

    @Override
    public Dialog create(Activity activity) {
        if (mDialogContentCallback != null) {
            mDialogContentCallback.processContentView(mContentWrapperView
                    , mContentPartView);
        }

        if (mTitleView.getVisibility() != View.VISIBLE) {
            mContentWrapperView.setPadding(mContentWrapperView.getPaddingLeft(),
                    mContentWrapperView.getPaddingTop() + Utils.dpToPx(7),
                    mContentWrapperView.getPaddingRight(),
                    mContentWrapperView.getPaddingBottom());
        }
        mDialog = new Dialog(activity, R.style.smart_show_normal_dialog);
        mDialog.setCancelable(mCancelabel);
        mDialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        int width = Utils.screenWidth() - Utils.dpToPx(70);
        ViewGroup.LayoutParams rootLp = new ViewGroup.LayoutParams(width,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.setContentView(mDialogRootView, rootLp);
        return mDialog;
    }

    @Override
    public void onClick(View v) {
        mDialog.dismiss();
        if (mOnPositiveBtnClickListener != null && v.getId() == R.id.dialog_positive_btn) {
            mOnPositiveBtnClickListener.onBtnClick((TextView) v, null);
            return;
        }

        if (mOnNegativeBtnClickListener != null && v.getId() == R.id.dialog_negative_btn) {
            mOnNegativeBtnClickListener.onBtnClick((TextView) v, null);
        }
    }
}
