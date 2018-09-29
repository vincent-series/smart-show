package com.coder.zzq.smartshow.dialog.type;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.Utils;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;

public class DialogBuilder<V extends View, Type> implements IDialogShow<Type>, View.OnClickListener {
    protected Dialog mDialog;
    protected View mDialogView;
    protected V mContentPart;
    protected TextView mTitleView;
    protected TextView mEnsureBtn;
    protected TextView mCancelBtn;
    protected View mSeparatorBetweenBtn;
    protected View mSeparatorBetweenBtnAndContent;
    protected View.OnClickListener mEnsureListener;
    protected View.OnClickListener mCancelListener;

    public DialogBuilder(@LayoutRes int layoutRes) {
        this(LayoutInflater.from(SmartShow.getContext())
                .inflate(layoutRes, null));
    }

    public DialogBuilder(View contentPartView) {
        mDialogView = LayoutInflater.from(SmartShow.getContext())
                .inflate(R.layout.smart_show_normal_dialog, null);
        mTitleView = mDialogView.findViewById(R.id.dialog_title);
        mEnsureBtn = mDialogView.findViewById(R.id.dialog_ensure_btn);
        mCancelBtn = mDialogView.findViewById(R.id.dialog_cancel_btn);
        mEnsureBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        mSeparatorBetweenBtn = mDialogView.findViewById(R.id.separator_between_btn);
        mSeparatorBetweenBtnAndContent = mDialogView.findViewById(R.id.separator_between_btn_and_content);

        FrameLayout viewGroup = mDialogView.findViewById(R.id.dialog_content);
        mContentPart = (V) LayoutInflater.from(SmartShow.getContext()).inflate(R.layout.smart_show_message_content,
                viewGroup, false);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentPart.getLayoutParams();
        lp.gravity = Gravity.CENTER;
        lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
        lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
        viewGroup.addView(mContentPart);
    }


    @Override
    public Type title(CharSequence title) {
        mTitleView.setVisibility(View.VISIBLE);
        mTitleView.setText(title);
        return (Type) this;
    }

    @Override
    public Type buttonMode(int mode) {
        switch (mode) {
            case IDialogShow.ONLY_ENSURE_BUTTON:
                mCancelBtn.setVisibility(View.GONE);
                mSeparatorBetweenBtn.setVisibility(View.GONE);
                break;
            case IDialogShow.BOTH_ENSURE_AND_CANCEL:
                mCancelBtn.setVisibility(View.VISIBLE);
                mSeparatorBetweenBtn.setVisibility(View.VISIBLE);
                break;
        }
        return (Type) this;
    }

    @Override
    public Type ensureBtn(CharSequence text) {
        mEnsureBtn.setText(text);
        return (Type) this;
    }

    @Override
    public Type ensureClick(View.OnClickListener listener) {
        mEnsureListener = listener;
        return (Type) this;
    }

    @Override
    public Type cancelBtn(CharSequence text) {
        mCancelBtn.setText(text);
        return (Type) this;
    }

    @Override
    public Type cancelClick(View.OnClickListener listener) {
        mCancelListener = listener;
        return null;
    }

    @Override
    public void show() {
        Activity activity = ActivityStack.getTop();
        if (activity != null) {
            mDialog = new Dialog(activity, R.style.smart_show_normal_dialog);
            int width = Utils.screenWidth() - Utils.dpToPx(70);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(width,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mDialog.setContentView(mDialogView, lp);
            mDialog.show();
        }
    }


    @Override
    public void onClick(View v) {
        if (mDialog != null) {
            mDialog.dismiss();
        }

        if (v.getId() == R.id.dialog_ensure_btn && mEnsureListener != null) {
            mEnsureListener.onClick(v);
            return;
        }

        if (v.getId() == R.id.dialog_cancel_btn && mCancelListener != null){
            mCancelListener.onClick(v);
        }

    }
}
