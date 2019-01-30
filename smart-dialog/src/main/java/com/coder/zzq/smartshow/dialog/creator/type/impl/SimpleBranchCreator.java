package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.app.Dialog;
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
        ICancelBtnCreator<B> {
    protected CharSequence mTitle;
    protected float mTitleTextSizeSp;
    @ColorInt
    protected int mTitleColor;
    protected boolean mTitleBold;

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
    public B titleStyle(int color, float textSizeSp, boolean bold) {
        mTitleColor = color;
        mTitleTextSizeSp = textSizeSp;
        mTitleBold = bold;
        return (B) this;
    }

    @Override
    public B confirmBtn(CharSequence label, DialogBtnClickListener clickListener) {
        mConfirmLabel = label;
        mOnConfirmClickListener = clickListener;
        return (B) this;
    }

    @Override
    public B confirmBtn(CharSequence label) {
        return confirmBtn(label, null);
    }

    @Override
    public B confirmBtn(CharSequence label, int color) {
        mConfirmLabel = label;
        mConfirmLabelColor = color;
        return (B) this;
    }

    @Override
    public B confirmBtn(CharSequence label, int color, DialogBtnClickListener clickListener) {
        confirmBtn(label, color);
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
    public B cancelBtn(CharSequence label) {
        return cancelBtn(label, null);
    }

    @Override
    public B cancelBtn(CharSequence label, int color) {
        mCancelLabel = label;
        mCancelLabelColor = color;
        return (B) this;
    }

    @Override
    public B cancelBtn(CharSequence label, int color, DialogBtnClickListener clickListener) {
        cancelBtn(label, color);
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
    protected void initHeader(Dialog dialog, FrameLayout headerViewWrapper) {
        super.initHeader(dialog, headerViewWrapper);
        headerViewWrapper.setVisibility(Utils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);
        if (!Utils.isEmpty(mTitle)) {
            headerViewWrapper.setVisibility(View.VISIBLE);
            TextView titleView = headerViewWrapper.findViewById(R.id.smart_show_dialog_title_view);
            titleView.setText(mTitle);
            if (mTitleColor != 0) {
                titleView.setTextColor(mTitleColor);
            }
            if (mTitleTextSizeSp > 0) {
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSizeSp);
            }
            if (mTitleBold) {
                titleView.getPaint().setFakeBoldText(mTitleBold);
            }
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
    protected void initFooter(Dialog dialog, FrameLayout footerViewWrapper) {
        super.initFooter(dialog, footerViewWrapper);
        setBtn(dialog, footerViewWrapper, R.id.smart_show_dialog_confirm_btn, mConfirmLabel, mConfirmLabelColor, mConfirmLabelTextSizeSp, mConfirmLabelBold,
                mOnConfirmClickListener);
        setBtn(dialog, footerViewWrapper, R.id.smart_show_dialog_cancel_btn, mCancelLabel, mConfirmLabelColor, mCancelLabelTextSizeSp, mCancelLabelBold,
                mOnCancelClickListener);
    }

    protected void setBtn(final Dialog dialog, FrameLayout footerViewWrapper, int btnId, CharSequence label, int labelColor, float labelSize, boolean labelBold,
                          final DialogBtnClickListener clickListener) {
        final TextView btn = footerViewWrapper.findViewById(btnId);
        if (!Utils.isEmpty(label)) {
            btn.setText(label);
        }
        if (labelColor != 0) {
            btn.setTextColor(labelColor);
        }
        if (labelSize > 0) {
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, labelSize);
        }
        btn.getPaint().setFakeBoldText(labelBold);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener == null) {
                    dialog.dismiss();
                } else if (btn.getId() == R.id.smart_show_dialog_confirm_btn) {
                    onConfirmBtnClick(dialog, btn, clickListener);
                } else if (btn.getId() == R.id.smart_show_dialog_cancel_btn) {
                    onCancelBtnClick(dialog, btn, clickListener);
                }
            }
        });
    }

    protected void onCancelBtnClick(Dialog dialog, TextView btn, DialogBtnClickListener clickListener) {
        onBtnClick(dialog, btn, DialogBtnClickListener.BTN_CONFIRM, clickListener);
    }

    protected void onConfirmBtnClick(Dialog dialog, TextView btn, DialogBtnClickListener clickListener) {
        onBtnClick(dialog, btn, DialogBtnClickListener.BTN_CANCEL, clickListener);
    }

    protected void onBtnClick(Dialog dialog, TextView btn, int which, DialogBtnClickListener clickListener) {
        if (clickListener == null) {
            dialog.dismiss();
        } else {
            clickListener.onBtnClick(dialog, which, clickListener);
        }
    }

    @Override
    protected int provideFooterView() {
        return R.layout.smart_show_default_double_btn;
    }

}
