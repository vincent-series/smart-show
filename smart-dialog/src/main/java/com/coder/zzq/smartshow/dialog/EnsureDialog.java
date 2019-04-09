package com.coder.zzq.smartshow.dialog;

import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

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
        return cancelBtn(label, null);
    }

    public EnsureDialog cancelBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mCancelLabel = label;
        mOnCancelClickListener = clickListener;
        return this;
    }

    public EnsureDialog cancelBtn(CharSequence label, int color) {
        mCancelLabel = label;
        mCancelLabelColor = color;
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
    protected void applyFooter() {
        super.applyFooter();
        setBtnStyle(mCancelBtn, mCancelLabel, mCancelLabelTextSizeSp, mCancelLabelColor, mCancelLabelBold);
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
