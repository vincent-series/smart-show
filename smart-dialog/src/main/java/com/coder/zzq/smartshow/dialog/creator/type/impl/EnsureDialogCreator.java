package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.app.Dialog;
import android.support.annotation.ColorInt;
import android.widget.FrameLayout;
import android.widget.TextView;

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
    public IEnsureDialogCreator cancelBtn(CharSequence label, int color) {
        mCancelLabel = label;
        mCancelLabelColor = color;
        return this;
    }

    @Override
    public IEnsureDialogCreator cancelBtn(CharSequence label, int color, DialogBtnClickListener clickListener) {
        cancelBtn(label, color);
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
    protected void initFooter(Dialog dialog, FrameLayout footerViewWrapper) {
        super.initFooter(dialog, footerViewWrapper);
        TextView cancelBtn = footerViewWrapper.findViewById(R.id.smart_show_dialog_cancel_btn);
        setBtnStyle(cancelBtn, mCancelLabel, mCancelLabelTextSizeSp, mCancelLabelColor, mCancelLabelBold);
        setBtnListener(dialog, cancelBtn, DialogBtnClickListener.BTN_CANCEL, mOnCancelClickListener);
    }

    @Override
    protected int provideFooterView() {
        return R.layout.smart_show_default_double_btn;
    }

}
