package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.creator.type.IMessageTipCreator;


public abstract class MessageDialogCreator<B> extends BranchDialogCreator<B> implements IMessageTipCreator<B>, View.OnClickListener, View.OnAttachStateChangeListener, Runnable {
    public static final int DISABLE_COLOR = Color.parseColor("#bbbbbb");
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
    protected TextView mConfirmBtn;
    protected int mSecondsDelayConfirm;

    protected CharSequence mMessage;

    protected float mMessageTextSizeSp;
    @ColorInt
    protected int mMessageColor;
    protected boolean mMessageBold;

    public MessageDialogCreator() {

    }

    @Override
    public B message(CharSequence msg) {
        if (Utils.isEmpty(msg)) {
            return (B) this;
        }
        mMessage = msg;
        return (B) this;
    }

    @Override
    public B messageStyle(int color, float textSizeSp, boolean bold) {
        mMessageColor = color;
        mMessageTextSizeSp = textSizeSp;
        mMessageBold = bold;
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

    @Override
    public B titleStyle(int color, float textSizeSp, boolean bold) {
        mTitleColor = color;
        mTitleTextSizeSp = textSizeSp;
        mTitleBold = bold;
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
            if (mTitleColor > 0) {
                titleView.setTextColor(mTitleColor);
            }
            if (mTitleTextSizeSp > 0) {
                titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSizeSp);
            }
            titleView.getPaint().setFakeBoldText(mTitleBold);
        } else {
            headerViewWrapper.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initBody(FrameLayout bodyViewWrapper) {
        super.initBody(bodyViewWrapper);
        TextView messageView = bodyViewWrapper.findViewById(R.id.smart_show_dialog_message_view);
        messageView.setText(mMessage);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) messageView.getLayoutParams();
        lp.topMargin = Utils.isEmpty(mTitle) ? Utils.dpToPx(7) : 0;
        messageView.addOnAttachStateChangeListener(this);
        if (mMessageColor > 0) {
            messageView.setTextColor(mMessageColor);
        }
        if (mMessageTextSizeSp > 0) {
            messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mMessageTextSizeSp);
        }
        messageView.getPaint().setFakeBoldText(mMessageBold);
    }


    @Override
    protected void initFooter(FrameLayout footerViewWrapper) {
        super.initFooter(footerViewWrapper);
        TextView confirmBtn = setBtn(footerViewWrapper, R.id.smart_show_dialog_confirm_btn, mConfirmLabel, mConfirmLabelColor, mConfirmLabelTextSizeSp, mConfirmLabelBold);
        if (mSecondsDelayConfirm > 0) {
            mConfirmBtn = confirmBtn;
            mConfirmLabel = confirmBtn.getText();
            if (mConfirmLabelColor <= 0) {
                mConfirmLabelColor = confirmBtn.getCurrentTextColor();
            }
            mConfirmLabelWhenDelay = new StringBuilder();
            confirmBtn.addOnAttachStateChangeListener(this);
        }
    }

    protected TextView setBtn(FrameLayout footerViewWrapper, int btnId, CharSequence label, int labelColor, float labelSize, boolean labelBold) {
        TextView btn = footerViewWrapper.findViewById(btnId);
        if (!Utils.isEmpty(label)) {
            btn.setText(label);
        }
        if (labelColor > 0) {
            btn.setTextColor(labelColor);
        }
        if (labelSize > 0) {
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, labelSize);
        }
        btn.getPaint().setFakeBoldText(labelBold);
        btn.setOnClickListener(this);

        return btn;
    }

    @Override
    protected int provideHeaderView() {
        return R.layout.smart_show_message_title;
    }

    @Override
    protected int provideBodyView() {
        return R.layout.smart_show_message_content;
    }

    @Override
    protected int provideFooterView() {
        return R.layout.smart_show_default_single_button;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.smart_show_dialog_confirm_btn) {
            onBtnClick(mOnConfirmClickListener, DialogBtnClickListener.BTN_CONFIRM, null);
        }
    }

    private StringBuilder mConfirmLabelWhenDelay;

    @Override
    public void onViewAttachedToWindow(View v) {
        if (v.getId() == R.id.smart_show_dialog_message_view) {
            TextView msgView = (TextView) v;
            if (msgView.getLineCount() > 1) {
                msgView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            } else {
                msgView.setGravity(Gravity.CENTER);
            }
        } else if (v.getId() == R.id.smart_show_dialog_confirm_btn) {
            if (mSecondsDelayConfirm > 0) {
                mConfirmBtn.setEnabled(false);
                mConfirmBtn.setTextColor(DISABLE_COLOR);
                v.post(this);
            }
        }
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        if (v.getId() == R.id.smart_show_dialog_confirm_btn) {
            mConfirmBtn.setText(mConfirmLabel);
            mConfirmBtn.setTextColor(mConfirmLabelColor);
            mConfirmBtn.removeCallbacks(this);
            mConfirmBtn.setEnabled(true);
        }
    }

    @Override
    public void run() {

        if (mSecondsDelayConfirm > 0) {
            mConfirmLabelWhenDelay.delete(0, mConfirmLabelWhenDelay.length());
            mConfirmLabelWhenDelay.append(mConfirmLabel)
                    .append("(")
                    .append(mSecondsDelayConfirm)
                    .append("s)");
            mConfirmBtn.setText(mConfirmLabelWhenDelay.toString());
            mSecondsDelayConfirm--;
            mConfirmBtn.postDelayed(this, 1000);
        } else {
            mConfirmBtn.setEnabled(true);
            mConfirmBtn.setText(mConfirmLabel);
            mConfirmBtn.setTextColor(mConfirmLabelColor);
            mConfirmBtn.removeCallbacks(this);
        }
    }
}
