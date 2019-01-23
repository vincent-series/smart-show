package com.coder.zzq.smartshow.dialog.dialog.type.impl;

import android.content.DialogInterface;
import android.support.annotation.ColorInt;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.dialog.IMessage;


public class MessageDialogCreator<B> extends NormalDialogCreator<B> implements IMessage<B> {
    protected CharSequence mTitle;
    protected CharSequence mConfirmLabel;
    protected DialogBtnClickListener mOnConfirmClickListener;
    protected float mConfirmLabelTextSizeSp;
    @ColorInt
    protected int mConfirmLabelColor;
    protected boolean mConfirmLabelBold;
    protected int mSecondsDelayConfirm;

    @Override
    public B message(CharSequence msg) {
        if (Utils.isEmpty(msg)) {
            return (B) this;
        }
        mTitle = msg;
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
    protected int provideDialogRootView() {
        return R.layout.smart_show_normal_dialog;
    }
}
