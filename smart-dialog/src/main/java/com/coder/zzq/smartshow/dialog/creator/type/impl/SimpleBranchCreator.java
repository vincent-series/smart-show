package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.creator.type.ICancelBtnCreator;
import com.coder.zzq.smartshow.dialog.creator.type.IConfirmBtnCreator;
import com.coder.zzq.smartshow.dialog.creator.type.ITitleCreator;

public abstract class SimpleBranchCreator<B> extends BranchDialogCreator<B> implements ITitleCreator<B>, IConfirmBtnCreator<B>,
        ICancelBtnCreator<B>, View.OnClickListener {
    protected CharSequence mTitle;

    protected CharSequence mConfirmLabel;
    protected DialogBtnClickListener mOnConfirmClickListener;
    protected float mConfirmLabelTextSizeSp;
    @ColorInt
    protected int mConfirmLabelColor;
    protected boolean mConfirmLabelBold;

    protected CharSequence mCancelLabel;
    protected DialogBtnClickListener mOnCancelClickListener;
    protected float mCancelLabelTextSizeSp;
    @ColorInt
    protected int mCancelLabelColor;
    protected boolean mCancelLabelBold;


    @Override
    public B title(CharSequence title) {
        if (Utils.isEmpty(title)) {
            return (B) this;
        }
        mTitle = title;
        return (B) this;
    }

    @Override
    public B confirmBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mConfirmLabel = label;
        mOnConfirmClickListener = clickListener;
        return (B) this;
    }

    @Override
    public B confirmBtnTextStyle(int color, float textSizeSp, boolean bold) {
        mConfirmLabelColor = color;
        mConfirmLabelTextSizeSp = textSizeSp;
        mConfirmLabelBold = bold;
        return (B) this;
    }


    @Override
    public B cancelBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mCancelLabel = label;
        mOnCancelClickListener = clickListener;
        return (B) this;
    }

    @Override
    public B cancelBtnTextStyle(int color, float textSizeSp, boolean bold) {
        mCancelLabelColor = color;
        mCancelLabelTextSizeSp = textSizeSp;
        mCancelLabelBold = bold;
        return (B) this;
    }


    @Override
    protected void initHeader(FrameLayout headerViewWrapper) {
        super.initHeader(headerViewWrapper);
        headerViewWrapper.setVisibility(Utils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);
        if (!Utils.isEmpty(mTitle)) {
            headerViewWrapper.setVisibility(View.VISIBLE);
            TextView titleView = headerViewWrapper.findViewById(R.id.smart_show_dialog_title_view);
            titleView.setText(mTitle);
        } else {
            headerViewWrapper.setVisibility(View.GONE);
        }
    }

    @Override
    protected int provideHeaderView() {
        return R.layout.smart_show_message_title;
    }

    @Override
    protected abstract int provideBodyView();


    @Override
    protected void initFooter(FrameLayout footerViewWrapper) {
        super.initFooter(footerViewWrapper);
        TextView confirmBtn = footerViewWrapper.findViewById(R.id.smart_show_dialog_confirm_btn);
        if (!Utils.isEmpty(mConfirmLabel)) {
            confirmBtn.setText(mConfirmLabel);
        }
        if (mConfirmLabelColor > 0) {
            confirmBtn.setTextColor(mConfirmLabelColor);
        }
        if (mConfirmLabelTextSizeSp > 0) {
            confirmBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, mConfirmLabelTextSizeSp);
        }
        confirmBtn.getPaint().setFakeBoldText(mConfirmLabelBold);
        confirmBtn.setOnClickListener(this);

        TextView cancelBtn = footerViewWrapper.findViewById(R.id.smart_show_dialog_cancel_btn);
        if (!Utils.isEmpty(mCancelLabel)) {
            cancelBtn.setText(mCancelLabel);
        }
        if (mCancelLabelColor > 0) {
            cancelBtn.setTextColor(mCancelLabelColor);
        }
        if (mCancelLabelTextSizeSp > 0) {
            cancelBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCancelLabelTextSizeSp);
        }

        cancelBtn.getPaint().setFakeBoldText(mCancelLabelBold);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    protected int provideFooterView() {
        return R.layout.smart_show_default_double_btn;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.smart_show_dialog_confirm_btn) {
            onConfirmBtnClick(v);
        } else if (v.getId() == R.id.smart_show_dialog_cancel_btn) {
            onCancelBtnClick(v);
        }

    }

    protected void onCancelBtnClick(View v) {
        onBtnClick(mOnCancelClickListener, DialogBtnClickListener.BTN_CANCEL, null);
    }

    protected void onConfirmBtnClick(View v) {
        onBtnClick(mOnConfirmClickListener, DialogBtnClickListener.BTN_CONFIRM, null);
    }

}
