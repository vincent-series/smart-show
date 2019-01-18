package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.DialogCreator;
import com.coder.zzq.smartshow.dialog.DialogWrapper;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.type.INormalDialogBuilder;

public abstract class NormalDialogBuilder<B> extends DialogCreator implements INormalDialogBuilder<B> {
    protected LinearLayout mDialogRootView;
    protected TextView mTitleView;
    protected CharSequence mTitle;
    protected TextView mPositiveBtn;
    protected CharSequence mPositiveLabel;
    protected TextView mNegativeBtn;
    protected CharSequence mNegativeLabel;
    protected FrameLayout mContentWrapperView;
    protected View mSeparatorBetweenBtn;
    protected View mSeparatorBetweenBtnAndContent;
    protected View mContentPartView;
    protected DialogBtnClickListener mOnPositiveBtnClickListener;
    protected DialogBtnClickListener mOnNegativeBtnClickListener;
    protected boolean mCancelabel;
    protected boolean mCanceledOnTouchOutside;

    public NormalDialogBuilder() {

    }

    public void reset() {
        mCancelabel = true;
        mCanceledOnTouchOutside = true;
        mDialogRootView = null;
        mTitleView = null;
        mTitle = null;
        mPositiveBtn = null;
        mPositiveLabel = "确定";
        mNegativeBtn = null;
        mNegativeLabel = "取消";
        mContentWrapperView = null;
        mSeparatorBetweenBtn = null;
        mSeparatorBetweenBtnAndContent = null;
        mContentPartView = null;
        mOnPositiveBtnClickListener = null;
        mOnNegativeBtnClickListener = null;
    }

    @LayoutRes
    protected abstract int getContentPartLayoutRes();

    @Override
    public B title(CharSequence title) {
        if (title != null && !title.toString().trim().isEmpty()) {
            mTitle = title;
        }
        return (B) this;
    }

    @Override
    public B positiveBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mPositiveLabel = label;
        mOnPositiveBtnClickListener = clickListener;
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
    public Dialog createDialog(Activity activity) {
        EasyLogger.d("create dialog");

        final Dialog dialog = new Dialog(activity, R.style.smart_show_normal_dialog);
        dialog.setCancelable(mCancelabel);
        dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        mDialogRootView = (LinearLayout) LayoutInflater.from(SmartShow.getContext())
                .inflate(R.layout.smart_show_normal_dialog, null);
        initViews(dialog);

        mContentPartView = LayoutInflater.from(SmartShow.getContext()).inflate(getContentPartLayoutRes(),
                mContentWrapperView, false);
        mContentPartView.setTranslationY(mTitleView.getVisibility() == View.VISIBLE ? 0 : Utils.dpToPx(7));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentPartView.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
        lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
        mContentWrapperView.addView(mContentPartView);
        int width = Utils.screenWidth() - Utils.dpToPx(70);
        ViewGroup.LayoutParams rootLp = new ViewGroup.LayoutParams(width,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(mDialogRootView, rootLp);
        return dialog;
    }

    @Override
    public void resetDialog(Dialog dialog) {
        ViewGroup parent = dialog.findViewById(android.R.id.content);
        mDialogRootView = (LinearLayout) parent.getChildAt(0);
        initViews(dialog);
        mContentPartView = mContentWrapperView.getChildAt(0);
        mContentPartView.setTranslationY(mTitleView.getVisibility() == View.VISIBLE ? 0 : Utils.dpToPx(7));
    }

    private void initViews(final Dialog dialog) {
        mContentWrapperView = mDialogRootView.findViewById(R.id.dialog_content_wrapper);
        mTitleView = mDialogRootView.findViewById(R.id.dialog_title);
        mTitleView.setText(mTitle);
        mTitleView.setVisibility(TextUtils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);

        mPositiveBtn = mDialogRootView.findViewById(R.id.dialog_positive_btn);
        mPositiveBtn.setText(mPositiveLabel);

        mSeparatorBetweenBtn = mDialogRootView.findViewById(R.id.separator_between_btn);
        mSeparatorBetweenBtnAndContent = mDialogRootView.findViewById(R.id.separator_between_btn_and_content);

        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (mOnPositiveBtnClickListener != null) {
                    mOnPositiveBtnClickListener.onBtnClick((TextView) v, null);
                    return;
                }
            }
        });
        mNegativeBtn = mDialogRootView.findViewById(R.id.dialog_negative_btn);
        mNegativeBtn.setText(mNegativeLabel);
        mNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (mOnNegativeBtnClickListener != null) {
                    mOnNegativeBtnClickListener.onBtnClick((TextView) v, null);
                }
            }
        });
    }


    @Override
    public boolean createAndShow(Activity activity, DialogWrapper dialogWrapper) {
        EasyLogger.d("create and show");
        return SmartDialog.show(activity, this, dialogWrapper);
    }


}
