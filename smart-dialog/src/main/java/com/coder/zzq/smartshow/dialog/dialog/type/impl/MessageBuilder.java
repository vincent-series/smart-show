package com.coder.zzq.smartshow.dialog.dialog.type.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.dialog.IMessage;


public class MessageBuilder<B> extends NormalBuilder<B> implements IMessage<B>{
    protected TextView mTitleView;
    protected TextView mMessageView;
    protected int mSecondsDelayConfirm;
    protected CharSequence mConfirmLabel = "确定";
    protected int mButtonLayoutRes = R.layout.smart_show_default_single_button;

    public MessageBuilder(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    public B message(CharSequence msg) {
        if (Utils.isEmpty(msg)) {
            return (B) this;
        }
        if (mMessageView == null) {
            View root = Utils.inflate(R.layout.default_message, null);
            mMessageView = root.findViewById(R.id.smart_show_default_message);
            setView(root);
        }
        mMessageView.setText(msg);
        return (B) this;
    }

    @Override
    public B confirmBtn(CharSequence label, DialogInterface.OnClickListener clickListener) {
        setPositiveButton(label, clickListener);
        return (B) this;
    }

    @Override
    public B confirmBtnTextStyle(int color, float textSizeSp, boolean bold) {

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
        if (mTitleView == null) {
            mTitleView = (TextView) Utils.inflate(R.layout.default_title, null);
            setCustomTitle(mTitleView);
        }
        mTitleView.setText(title);
        return (B) this;
    }

    @Override
    protected void adjustDialogLayout(AlertDialog dialog) {
        super.adjustDialogLayout(dialog);
        if (mMessageView != null) {
            mMessageView.setTranslationY(mTitleView == null ? Utils.dpToPx(7) : 0);
        }
        ViewGroup buttonPanel = dialog.getWindow().getDecorView().findViewById(android.support.v7.appcompat.R.id.buttonPanel);
        ButtonBarLayout buttonBarLayout = (ButtonBarLayout) buttonPanel.getChildAt(0);
        customButtonLayout(dialog,buttonBarLayout);
    }

    protected void customButtonLayout(AlertDialog dialog,ButtonBarLayout buttonBarLayout) {

        buttonBarLayout.setPadding(0, 0, 0, 0);

        for (int index = 0; index < buttonBarLayout.getChildCount(); index++) {
            buttonBarLayout.getChildAt(index).setVisibility(View.GONE);
        }
        Utils.inflate(mButtonLayoutRes, buttonBarLayout);
        setupButton(dialog,DialogInterface.BUTTON_POSITIVE,buttonBarLayout, (ViewGroup) buttonBarLayout.findViewById(R.id.smart_show_dialog_confirm_btn));
    }


    protected void setupButton(AlertDialog dialog, int which, ButtonBarLayout srcParent, ViewGroup desParent) {
        Button btn = dialog.getButton(which);
        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundResource(R.drawable.smart_show_dialog_btn_bg);
        srcParent.removeView(btn);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        btn.setLayoutParams(lp);
        desParent.addView(btn);
    }
    protected StringBuilder mConfirmLabelWhenDelay;


}
