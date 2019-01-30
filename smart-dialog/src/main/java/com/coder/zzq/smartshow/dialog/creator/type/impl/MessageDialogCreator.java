package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.app.Dialog;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.ConfirmDelayCallback;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.creator.type.IMessageTipCreator;


public abstract class MessageDialogCreator<B> extends BranchDialogCreator<B> implements IMessageTipCreator<B> {
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
            titleView.getPaint().setFakeBoldText(mTitleBold);
        } else {
            headerViewWrapper.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initBody(Dialog dialog, FrameLayout bodyViewWrapper) {
        super.initBody(dialog, bodyViewWrapper);
        TextView messageView = bodyViewWrapper.findViewById(R.id.smart_show_dialog_message_view);
        messageView.setText(mMessage);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) messageView.getLayoutParams();
        lp.topMargin = Utils.isEmpty(mTitle) ? Utils.dpToPx(7) : 0;
        messageView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                TextView msgView = (TextView) v;
                if (msgView.getLineCount() > 1) {
                    msgView.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                } else {
                    msgView.setGravity(Gravity.CENTER);
                }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });
        if (mMessageColor != 0) {
            messageView.setTextColor(mMessageColor);
        }
        if (mMessageTextSizeSp > 0) {
            messageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mMessageTextSizeSp);
        }
        messageView.getPaint().setFakeBoldText(mMessageBold);
    }


    @Override
    protected void initFooter(Dialog dialog, FrameLayout footerViewWrapper) {
        super.initFooter(dialog, footerViewWrapper);
        TextView confirmBtn = footerViewWrapper.findViewById(R.id.smart_show_dialog_confirm_btn);
        setBtnStyle(confirmBtn, mConfirmLabel, mConfirmLabelTextSizeSp, mConfirmLabelColor, mConfirmLabelBold);
        setBtnListener(dialog, confirmBtn, DialogBtnClickListener.BTN_CONFIRM, mOnConfirmClickListener);

    }

    protected void setBtnListener(final Dialog dialog, final TextView btn, @DialogBtnClickListener.DialogBtn final int which, final DialogBtnClickListener clickListener) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener == null) {
                    dialog.dismiss();
                } else {
                    clickListener.onBtnClick(dialog, which, null);
                }
            }
        });
        if (which == DialogBtnClickListener.BTN_CONFIRM && mSecondsDelayConfirm > 0) {
            btn.addOnAttachStateChangeListener(new ConfirmDelayCallback() {
                @ColorInt
                private int mConfirmBtnSrcColor = btn.getCurrentTextColor();
                private CharSequence mConfirmBtnSrcLabel = btn.getText();
                private int mConfirmBtnSecondsDelay = mSecondsDelayConfirm;
                private int mConfirmBtnSecondsDelayInitValue = mConfirmBtnSecondsDelay;
                private StringBuilder mConfirmLabelWhenDelay = new StringBuilder();

                @Override
                public void onViewAttachedToWindow(View v) {
                    TextView textView = (TextView) v;
                    if (mConfirmBtnSecondsDelay > 0) {
                        textView.setEnabled(false);
                        textView.setTextColor(DISABLE_COLOR);
                        textView.post(this);
                    }
                }

                @Override
                public void run() {
                    if (mConfirmBtnSecondsDelay > 0) {
                        mConfirmLabelWhenDelay.delete(0, mConfirmLabelWhenDelay.length());
                        mConfirmLabelWhenDelay.append(mConfirmBtnSrcLabel)
                                .append("(")
                                .append(mConfirmBtnSecondsDelay)
                                .append("s)");
                        btn.setText(mConfirmLabelWhenDelay.toString());
                        mConfirmBtnSecondsDelay--;
                        btn.postDelayed(this, 1000);
                    } else {
                        btn.setEnabled(true);
                        btn.setText(mConfirmBtnSrcLabel);
                        btn.setTextColor(mConfirmBtnSrcColor);
                        btn.removeCallbacks(this);
                    }
                }

                @Override
                public void onViewDetachedFromWindow(View v) {
                    btn.setText(mConfirmBtnSrcLabel);
                    btn.setTextColor(mConfirmBtnSrcColor);
                    btn.removeCallbacks(this);
                    btn.setEnabled(true);
                    mConfirmBtnSecondsDelay = mConfirmBtnSecondsDelayInitValue;
                }
            });
        }
    }

    protected void setBtnStyle(TextView btn, CharSequence btnLabel, float labelSizeSp, @ColorInt int labelColor, boolean labelBold) {
        if (!Utils.isEmpty(btnLabel)) {
            btn.setText(btnLabel);
        }
        if (labelColor != 0) {
            btn.setTextColor(labelColor);
        }
        if (labelSizeSp > 0) {
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, labelSizeSp);
        }
        btn.getPaint().setFakeBoldText(labelBold);
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

}
