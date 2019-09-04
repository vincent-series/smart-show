package com.coder.zzq.smartshow.dialog;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatDialog;

public class EnsureDialog extends MessageDialog<EnsureDialog> {
    protected CharSequence mCancelLabel;
    protected DialogBtnClickListener<EnsureDialog> mOnCancelClickListener;
    protected float mCancelLabelTextSizeSp;
    @ColorInt
    protected int mCancelLabelColor;
    protected boolean mCancelLabelBold;


    private TextView mCancelBtn;

    public EnsureDialog() {

    }

    public EnsureDialog cancelBtn(CharSequence label) {
        mCancelLabel = label;
        applyBtnLabel(mNestedDialog, mCancelBtn, mCancelLabel);
        return this;
    }

    public EnsureDialog cancelBtn(CharSequence label, DialogBtnClickListener clickListener) {
        cancelBtn(label);
        mOnCancelClickListener = clickListener;
        return this;
    }

    public EnsureDialog cancelBtn(CharSequence label, int color) {
        cancelBtn(label);
        cancelBtnTextStyle(color, mCancelLabelTextSizeSp, mCancelLabelBold);
        return this;
    }

    public EnsureDialog cancelBtn(CharSequence label, int color, DialogBtnClickListener clickListener) {
        cancelBtn(label, color);
        mOnCancelClickListener = clickListener;
        return this;
    }

    public EnsureDialog cancelBtnTextStyle(int color, float textSizeSp, boolean bold) {
        mCancelLabelColor = color;
        mCancelLabelTextSizeSp = textSizeSp;
        mCancelLabelBold = bold;
        applyBtnStyle(mNestedDialog, mCancelBtn, mCancelLabelTextSizeSp, mCancelLabelColor, mCancelLabelBold);
        return this;
    }

    @Override
    protected int provideFooterLayout() {
        return R.layout.smart_show_default_double_btn;
    }

    @Override
    protected void initFooter(AppCompatDialog dialog, FrameLayout footerViewWrapper) {
        super.initFooter(dialog, footerViewWrapper);
        mCancelBtn = footerViewWrapper.findViewById(R.id.smart_show_dialog_cancel_btn);
        mCancelBtn.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void applyFooter(AppCompatDialog dialog) {
        super.applyFooter(dialog);
        applyBtnLabel(dialog, mCancelBtn, mCancelLabel);
        applyBtnStyle(dialog, mCancelBtn, mCancelLabelTextSizeSp, mCancelLabelColor, mCancelLabelBold);
    }

    @Override
    protected void onBtnClick(View v) {
        super.onBtnClick(v);
        if (v.getId() == R.id.smart_show_dialog_cancel_btn) {
            if (mOnCancelClickListener == null) {
                dismiss();
            } else {
                onCancelBtnClick();
            }
        }
    }


    protected void onCancelBtnClick() {
        mOnCancelClickListener.onBtnClick(this, DialogBtnClickListener.BTN_CANCEL, null);
    }
}
