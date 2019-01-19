package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.ColorInt;
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
import com.coder.zzq.smartshow.dialog.widget.DialogBtn;
import com.coder.zzq.smartshow.dialog.DialogCreator;
import com.coder.zzq.smartshow.dialog.DialogWrapper;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.type.INormalDialogBuilder;

public abstract class NormalDialogBuilder<B> extends DialogCreator implements INormalDialogBuilder<B> {
    protected LinearLayout mDialogRootView;
    protected TextView mTitleView;
    protected CharSequence mTitle;
    protected DialogBtn mPositiveBtn;
    protected CharSequence mPositiveLabel;
    @ColorInt
    protected int mPositiveLabelColor;
    protected float mPositiveTextSizeSp;
    protected boolean mPositiveTextBold;
    protected int mDelaySeconds;

    protected DialogBtn mNegativeBtn;
    protected CharSequence mNegativeLabel;
    @ColorInt
    protected int mNegativeLabelColor;
    protected float mNegativeTextSizeSp;
    protected boolean mNegativeTextBold;

    protected FrameLayout mContentWrapperView;
    protected View mSeparatorBetweenBtn;
    protected View mSeparatorBetweenBtnAndContent;
    protected View mContentPartView;
    protected DialogBtn.onDialogBtnClickListener mOnPositiveBtnClickListener;
    protected DialogBtn.onDialogBtnClickListener mOnNegativeBtnClickListener;
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
        mPositiveLabelColor = -1;
        mPositiveTextSizeSp = 0;
        mPositiveTextBold = false;
        mDelaySeconds = 0;
        mNegativeBtn = null;
        mNegativeLabel = "取消";
        mNegativeLabelColor = -1;
        mNegativeTextSizeSp = 0;
        mNegativeTextBold = false;
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
    public B positiveBtn(CharSequence label, DialogBtn.onDialogBtnClickListener clickListener) {
        mPositiveLabel = label;
        mOnPositiveBtnClickListener = clickListener;
        return (B) this;
    }

    @Override
    public B positiveBtnTextStyle(int color, float textSizeSp, boolean bold) {
        mPositiveLabelColor = color;
        mPositiveTextSizeSp = textSizeSp;
        mPositiveTextBold = bold;
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
        setBtnListener(mPositiveBtn, dialog);
        setBtnListener(mNegativeBtn, dialog);
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

    private void setBtnListener(DialogBtn dialogBtn, final Dialog dialog) {
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBtn dialogBtn = (DialogBtn) v;
                if (dialogBtn.getOnDialogBtnClickListener() == null) {
                    dialog.dismiss();
                } else {
                    dialogBtn.getOnDialogBtnClickListener().onDialogBtnClick(dialog, dialogBtn, dialogBtn.getDataProvider() == null ? null : dialogBtn.getDataProvider().getDataWhenClick());
                }
            }
        });
    }

    @Override
    public void resetDialog(Dialog dialog) {
        ViewGroup parent = dialog.findViewById(android.R.id.content);
        mDialogRootView = (LinearLayout) parent.getChildAt(0);
        initViews(dialog);
        mContentPartView = mContentWrapperView.getChildAt(0);
        mContentPartView.setTranslationY(mTitleView.getVisibility() == View.VISIBLE ? 0 : Utils.dpToPx(7));
    }

    protected void initViews(final Dialog dialog) {

        mContentWrapperView = mDialogRootView.findViewById(R.id.dialog_content_wrapper);
        mSeparatorBetweenBtn = mDialogRootView.findViewById(R.id.separator_between_btn);
        mSeparatorBetweenBtnAndContent = mDialogRootView.findViewById(R.id.separator_between_btn_and_content);

        mTitleView = mDialogRootView.findViewById(R.id.dialog_title);
        mTitleView.setText(mTitle);
        mTitleView.setVisibility(TextUtils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);

        mPositiveBtn = mDialogRootView.findViewById(R.id.dialog_positive_btn);
        mPositiveBtn.setLabelAndClickListener(mPositiveLabel, mOnPositiveBtnClickListener);
        mPositiveBtn.setTextStyle(mPositiveLabelColor, mPositiveTextSizeSp, mPositiveTextBold);
        mPositiveBtn.delayEnable(mDelaySeconds);

        mNegativeBtn = mDialogRootView.findViewById(R.id.dialog_negative_btn);
        mNegativeBtn.setLabelAndClickListener(mNegativeLabel, mOnNegativeBtnClickListener);
        mNegativeBtn.setTextStyle(mNegativeLabelColor, mNegativeTextSizeSp, mNegativeTextBold);
    }


    @Override
    public boolean createAndShow(Activity activity, DialogWrapper dialogWrapper) {
        EasyLogger.d("create and show");
        return SmartDialog.show(activity, this, dialogWrapper);
    }


}
