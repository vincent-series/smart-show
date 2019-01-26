package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.FrameLayout;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.creator.type.IEnsureDialogCreator;

class EnsureDialogCreator extends MessageDialogCreator<IEnsureDialogCreator> implements IEnsureDialogCreator {
    protected CharSequence mCancelLabel;
    protected DialogBtnClickListener mOnCancelClickListener;
    protected float mCancelLabelTextSizeSp;
    @ColorInt
    protected int mCancelLabelColor;
    protected boolean mCancelLabelBold;

    public EnsureDialogCreator() {

    }

    @Override
    public IEnsureDialogCreator cancelBtn(CharSequence label) {
        return cancelBtn(label, null);
    }

    @Override
    public IEnsureDialogCreator cancelBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mCancelLabel = label;
        mOnCancelClickListener = clickListener;
        return this;
    }

    @Override
    public IEnsureDialogCreator cancelBtnTextStyle(int color, float textSizeSp, boolean bold) {
        mCancelLabelColor = color;
        mCancelLabelTextSizeSp = textSizeSp;
        mCancelLabelBold = bold;
        return this;
    }

    @Override
    protected void initFooter(FrameLayout footerViewWrapper) {
        super.initFooter(footerViewWrapper);
        setBtn(footerViewWrapper, R.id.smart_show_dialog_cancel_btn, mCancelLabel, mConfirmLabelColor, mCancelLabelTextSizeSp, mCancelLabelBold);
    }

    @Override
    protected int provideFooterView() {
        return R.layout.smart_show_default_double_btn;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.smart_show_dialog_cancel_btn) {
            onBtnClick(mOnCancelClickListener, DialogBtnClickListener.BTN_CANCEL, null);
        }
    }
}
