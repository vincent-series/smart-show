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

    public D confirmBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mConfirmLabel = label;
        mOnConfirmClickListener = clickListener;
        return (D) this;
    }

    public D confirmBtn(CharSequence label) {
        return confirmBtn(label, null);
    }

    public D confirmBtn(CharSequence label, int color) {
        mConfirmLabel = label;
        mConfirmLabelColor = color;
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
        return (D) this;
    }

    public D cancelBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mCancelLabel = label;
        mOnCancelClickListener = clickListener;
        return (D) this;
    }

    public D cancelBtn(CharSequence label) {
        return cancelBtn(label, null);
    }

    public D cancelBtn(CharSequence label, int color) {
        mCancelLabel = label;
        mCancelLabelColor = color;
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
    protected void applyHeader() {
        super.applyHeader();
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
    protected void applyFooter() {
        setBtnStyle(mConfirmBtn, mConfirmLabel, mConfirmLabelColor, mConfirmLabelTextSizeSp,
                mConfirmLabelBold, mOnConfirmClickListener);
        setBtnStyle(mCancelBtn, mCancelLabel, mCancelLabelColor, mCancelLabelTextSizeSp,
                mCancelLabelBold, mOnCancelClickListener);
    }

    protected void setBtnStyle(TextView btn, CharSequence btnLabel, int btnLabelColor, float btnLabelTextSizeSp, boolean btnLabelBold, DialogBtnClickListener onBtnClickListener) {
        if (!Utils.isEmpty(btnLabel)) {
            btn.setText(btnLabel);
        }
        if (btnLabelColor != 0) {
            btn.setTextColor(btnLabelColor);
        }
        if (btnLabelTextSizeSp > 0) {
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, btnLabelTextSizeSp);
        }
        btn.getPaint().setFakeBoldText(btnLabelBold);
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
