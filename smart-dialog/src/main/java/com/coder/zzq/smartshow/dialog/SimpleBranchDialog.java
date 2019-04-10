package com.coder.zzq.smartshow.dialog;

import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;

public abstract class SimpleBranchDialog<D extends SmartDialog> extends TitleBranchDialog<D> {
    protected CharSequence mConfirmLabel;
    protected DialogBtnClickListener<D> mOnConfirmClickListener;
    protected float mConfirmLabelTextSizeSp;
    @ColorInt
    protected int mConfirmLabelColor;
    protected boolean mConfirmLabelBold;

    protected CharSequence mCancelLabel;
    protected DialogBtnClickListener<D> mOnCancelClickListener;
    protected float mCancelLabelTextSizeSp;
    @ColorInt
    protected int mCancelLabelColor;
    protected boolean mCancelLabelBold;


    protected TextView mConfirmBtn;
    protected TextView mCancelBtn;

    public D confirmBtn(CharSequence label) {
        mConfirmLabel = label;
        applyBtnLabel(mNestedDialog, mConfirmBtn, mConfirmLabel);
        return (D) this;
    }

    public D confirmBtn(CharSequence label, DialogBtnClickListener clickListener) {
        confirmBtn(label);
        mOnConfirmClickListener = clickListener;
        return (D) this;
    }

    public D confirmBtn(CharSequence label, int color) {
        confirmBtn(label);
        confirmBtnTextStyle(color, mConfirmLabelTextSizeSp, mConfirmLabelBold);
        return (D) this;
    }

    public D confirmBtn(CharSequence label, int color, DialogBtnClickListener clickListener) {
        confirmBtn(label, color);
        mOnConfirmClickListener = clickListener;
        return (D) this;
    }

    public D confirmBtnTextStyle(int color, float textSizeSp, boolean bold) {
        mConfirmLabelColor = color;
        mConfirmLabelTextSizeSp = textSizeSp;
        mConfirmLabelBold = bold;
        applyBtnStyle(mNestedDialog, mConfirmBtn, mConfirmLabelTextSizeSp, mConfirmLabelColor, mConfirmLabelBold);
        return (D) this;
    }

    public D cancelBtn(CharSequence label) {
        mCancelLabel = label;
        applyBtnLabel(mNestedDialog, mCancelBtn, mCancelLabel);
        return (D) this;
    }

    public D cancelBtn(CharSequence label, DialogBtnClickListener clickListener) {
        cancelBtn(label);
        mOnCancelClickListener = clickListener;
        return (D) this;
    }

    public D cancelBtn(CharSequence label, int color) {
        cancelBtn(label);
        cancelBtnTextStyle(color, mCancelLabelTextSizeSp, mCancelLabelBold);
        return (D) this;
    }

    public D cancelBtn(CharSequence label, int color, DialogBtnClickListener clickListener) {
        cancelBtn(label, color);
        mOnCancelClickListener = clickListener;
        return (D) this;
    }

    public D cancelBtnTextStyle(int color, float textSizeSp, boolean bold) {
        mCancelLabelColor = color;
        mCancelLabelTextSizeSp = textSizeSp;
        mCancelLabelBold = bold;
        applyBtnStyle(mNestedDialog, mCancelBtn, mCancelLabelTextSizeSp, mCancelLabelColor, mCancelLabelBold);
        return (D) this;
    }

    @Override
    protected int provideHeaderLayout() {
        return super.provideHeaderLayout();
    }

    @Override
    protected void initHeader(AppCompatDialog dialog, FrameLayout headerViewWrapper) {
        super.initHeader(dialog, headerViewWrapper);
    }

    @Override
    protected void applyHeader(AppCompatDialog dialog) {
        super.applyHeader(dialog);
    }

    @Override
    protected abstract int provideBodyLayout();

    @Override
    protected void initBody(AppCompatDialog dialog, FrameLayout bodyViewWrapper) {

    }


    @Override
    protected int provideFooterLayout() {
        return R.layout.smart_show_default_double_btn;
    }

    @Override
    protected void initFooter(AppCompatDialog dialog, FrameLayout footerViewWrapper) {
        super.initFooter(dialog, footerViewWrapper);
        mConfirmBtn = footerViewWrapper.findViewById(R.id.smart_show_dialog_confirm_btn);
        mCancelBtn = footerViewWrapper.findViewById(R.id.smart_show_dialog_cancel_btn);
        mConfirmBtn.setOnClickListener(mOnClickListener);
        mCancelBtn.setOnClickListener(mOnClickListener);
    }

    @Override
    protected void applyFooter(AppCompatDialog dialog) {
        super.applyFooter(dialog);
        applyBtnLabel(dialog, mConfirmBtn, mConfirmLabel);
        applyBtnStyle(dialog, mConfirmBtn, mConfirmLabelTextSizeSp, mConfirmLabelColor, mConfirmLabelBold);
        applyBtnLabel(dialog, mCancelBtn, mCancelLabel);
        applyBtnStyle(dialog, mCancelBtn, mCancelLabelTextSizeSp, mCancelLabelColor, mCancelLabelBold);
    }

    protected void applyBtnLabel(AppCompatDialog dialog, TextView btn, CharSequence btnLabel) {
        if (dialog != null && !Utils.isEmpty(btnLabel)) {
            btn.setText(btnLabel);
        }
    }

    protected void applyBtnStyle(AppCompatDialog dialog, TextView btn, float labelSizeSp, @ColorInt int labelColor, boolean labelBold) {
        if (dialog == null) {
            return;
        }
        if (labelColor != 0) {
            btn.setTextColor(labelColor);
        }
        if (labelSizeSp > 0) {
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, labelSizeSp);
        }
        btn.getPaint().setFakeBoldText(labelBold);
    }

    protected View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.smart_show_dialog_confirm_btn) {
                if (mOnConfirmClickListener == null) {
                    dismiss();
                } else {
                    onConfirmBtnClick();
                }
                return;
            }

            if (v.getId() == R.id.smart_show_dialog_cancel_btn) {
                if (mOnCancelClickListener == null) {
                    dismiss();
                } else {
                    onCancelBtnClick();
                }
            }
        }
    };

    protected void onConfirmBtnClick() {
        mOnConfirmClickListener.onBtnClick((D) this, DialogBtnClickListener.BTN_CONFIRM, null);
    }

    protected void onCancelBtnClick() {
        mOnCancelClickListener.onBtnClick((D) this, DialogBtnClickListener.BTN_CANCEL, null);
    }
}
