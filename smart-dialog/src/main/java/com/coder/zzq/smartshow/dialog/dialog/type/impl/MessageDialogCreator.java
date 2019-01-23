package com.coder.zzq.smartshow.dialog.dialog.type.impl;

import android.support.annotation.ColorInt;
import android.widget.FrameLayout;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.dialog.IMessage;


public abstract class MessageDialogCreator<B> extends NormalDialogCreator<B> implements IMessage<B> {
    protected CharSequence mTitle;
    protected CharSequence mConfirmLabel;
    protected DialogBtnClickListener mOnConfirmClickListener;
    protected float mConfirmLabelTextSizeSp;
    @ColorInt
    protected int mConfirmLabelColor;
    protected boolean mConfirmLabelBold;
    protected int mSecondsDelayConfirm;

    protected CharSequence mMessage;

    @Override
    public B message(CharSequence msg) {
        if (Utils.isEmpty(msg)) {
            return (B) this;
        }
        mMessage = msg;
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
    public B secondsDelayConfirm(int seconds) {
        if (seconds > 0) {
            mSecondsDelayConfirm = seconds;
        }
        return (B) this;
    }

    @Override
    public B title(CharSequence title) {
        if (Utils.isEmpty(title)) {
            return (B) this;
        }
        mTitle = title;
        return (B) this;
    }

    protected StringBuilder mConfirmLabelWhenDelay;

    @Override
    protected void initView() {
        initTitle((FrameLayout) mDialogRootView.findViewById(R.id.smart_show_dialog_title_wrapper));
        initBody((FrameLayout) mDialogRootView.findViewById(R.id.smart_show_dialog_body_wrapper));
        initBtn((FrameLayout) mDialogRootView.findViewById(R.id.smart_show_dialog_btn_wrapper));
    }

    protected void initTitle(FrameLayout wrapper) {
        Utils.inflate(provideTitleView(), wrapper, true);
    }

    protected abstract int provideTitleView();

    protected void initBody(FrameLayout wrapper) {
        Utils.inflate(provideBodyView(), wrapper, true);
    }

    protected abstract int provideBodyView();

    protected void initBtn(FrameLayout wrapper) {
        Utils.inflate(provideBtnView(), wrapper, true);
    }

    protected abstract int provideBtnView();

    @Override
    protected int provideDialogRootView() {
        return R.layout.smart_show_message_dialog;
    }
}
