package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DelayShowTimer;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.DialogContentCallback;
import com.coder.zzq.smartshow.dialog.DialogCreator;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.type.INormalDialogBuilder;

public abstract class NormalDiaBuilder<B> extends DialogCreator implements INormalDialogBuilder<B>, View.OnClickListener {
    protected Dialog mDialog;
    protected LinearLayout mDialogRootView;
    protected TextView mTitleView;
    @BUTTON_MODE
    protected int mButtonMode = MODE_BOTN_CONFIRM_AND_CANCEL;
    protected TextView mConfirmBtn;
    private int mDelaySeconds;
    private CharSequence mConfirmLabel = "确定";
    @ColorInt
    private int mConfirmColor = Utils.getColorFromRes(R.color.colorPrimary);
    protected TextView mCancelBtn;
    protected FrameLayout mContentWrapperView;
    protected View mSeparatorBetweenBtn;
    protected View mSeparatorBetweenBtnAndContent;
    protected View mContentPartView;
    protected DialogBtnClickListener mOnConfirmBtnClickListener;
    protected DialogBtnClickListener mOnCancelBtnClickListener;
    protected DialogContentCallback mDialogContentCallback;

    protected boolean mCancelabel = true;
    protected boolean mCanceledOnTouchOutside = true;

    public NormalDiaBuilder() {
        mDialogRootView = (LinearLayout) LayoutInflater.from(SmartShow.getContext())
                .inflate(R.layout.smart_show_normal_dialog, null);
        mTitleView = mDialogRootView.findViewById(R.id.dialog_title);
        mConfirmBtn = mDialogRootView.findViewById(R.id.dialog_positive_btn);
        mConfirmBtn.setOnClickListener(this);
        mCancelBtn = mDialogRootView.findViewById(R.id.dialog_negative_btn);
        mCancelBtn.setOnClickListener(this);
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
    public B buttonMode(int buttonMode) {
        mButtonMode = buttonMode;
        return (B) this;
    }

    @Override
    public B confirmBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mConfirmLabel = label;
        mConfirmBtn.setText(label);
        mOnConfirmBtnClickListener = clickListener;
        return (B) this;
    }

    @Override
    public B confirmBtnTextStyle(@ColorInt int textColor, float textSizeSp) {
        mConfirmColor = textColor;
        mConfirmBtn.setTextColor(textColor);
        mConfirmBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        return (B) this;
    }

    @Override
    public B delaySecondsConfirm(int delaySeconds) {
        mDelaySeconds = delaySeconds;
        return (B) this;
    }

    @Override
    public B cancelBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mCancelBtn.setText(label);
        mOnCancelBtnClickListener = clickListener;
        return (B) this;
    }

    @Override
    public B cancelBtnTextStyle(int textColor, float textSizeSp) {
        mCancelBtn.setTextColor(textColor);
        mCancelBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
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
    public boolean createAndShow(Activity activity, int tag) {
        return SmartDialog.show(activity, this, tag);
    }

    protected void initPartView(View contentPartView) {

    }

    @Override
    public Dialog createDialog(Activity activity) {
        initPartView(mContentPartView);
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

        switch (mButtonMode) {
            case MODE_ONLY_CONFIRM:
                mConfirmBtn.setVisibility(View.VISIBLE);
                mCancelBtn.setVisibility(View.GONE);
                mSeparatorBetweenBtn.setVisibility(View.GONE);
                break;
            case MODE_BOTN_CONFIRM_AND_CANCEL:
                mConfirmBtn.setVisibility(View.VISIBLE);
                mCancelBtn.setVisibility(View.VISIBLE);
                mSeparatorBetweenBtn.setVisibility(View.VISIBLE);
                break;
        }
        if (mDelaySeconds > 0) {
            mConfirmBtn.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                private DelayShowTimer mCountDownTimer;

                @Override
                public void onViewAttachedToWindow(View v) {
                    if (mCountDownTimer != null) {
                        mCountDownTimer.cancelTask();
                    }
                    mCountDownTimer = new DelayShowTimer(mDelaySeconds * 1000, 1000);
                    mCountDownTimer.setBtn(mConfirmBtn);
                    mCountDownTimer.setFinalColor(mConfirmColor);
                    mCountDownTimer.setFinalLable(mConfirmLabel);
                    mCountDownTimer.start();
                }

                @Override
                public void onViewDetachedFromWindow(View v) {

                }
            });
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
        if (mOnConfirmBtnClickListener != null && v.getId() == R.id.dialog_positive_btn) {
            mOnConfirmBtnClickListener.onBtnClick((TextView) v, null);
            return;
        }

        if (mOnCancelBtnClickListener != null && v.getId() == R.id.dialog_negative_btn) {
            mOnCancelBtnClickListener.onBtnClick((TextView) v, null);
        }
    }
}
