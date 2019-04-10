package com.coder.zzq.smartshow.dialog;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;

public abstract class MessageDialog<D extends SmartDialog> extends BranchDialog<D> {
    public static final int DISABLE_COLOR = Color.parseColor("#bbbbbb");
    protected CharSequence mTitle;
    protected float mTitleTextSizeSp;
    @ColorInt
    protected int mTitleColor;
    protected boolean mTitleBold;

    protected CharSequence mConfirmLabel;
    protected DialogBtnClickListener<D> mOnConfirmClickListener;
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

    protected TextView mConfirmBtn;
    private TextView mMsgView;
    private TextView mTitleView;

    public MessageDialog() {
        mConfirmLabel = "确定";
        mConfirmLabelColor = Utils.getColorFromRes(R.color.colorPrimary);
    }

    public D message(CharSequence msg) {
        if (Utils.isEmpty(msg)) {
            return (D) this;
        }
        mMessage = msg;
        applyMsg(mNestedDialog);
        return (D) this;
    }

    protected void applyMsg(AppCompatDialog dialog) {
        if (dialog == null) {
            return;
        }
        mMsgView.setText(mMessage);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mMsgView.getLayoutParams();
        lp.topMargin = Utils.isEmpty(mTitle) ? Utils.dpToPx(7) : 0;
        mMsgView.setLayoutParams(lp);
    }

    public D messageStyle(int color, float textSizeSp, boolean bold) {
        mMessageColor = color;
        mMessageTextSizeSp = textSizeSp;
        mMessageBold = bold;
        applyMsgStyle(mNestedDialog);
        return (D) this;
    }

    protected void applyMsgStyle(AppCompatDialog dialog) {
        if (dialog == null) {
            return;
        }
        if (mMessageColor != 0) {
            mMsgView.setTextColor(mMessageColor);
        }
        if (mMessageTextSizeSp > 0) {
            mMsgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mMessageTextSizeSp);
        }
        mMsgView.getPaint().setFakeBoldText(mMessageBold);
    }

    public D confirmBtn(CharSequence label) {
        mConfirmLabel = label;
        applyBtnLabel(mNestedDialog, mConfirmBtn, mConfirmLabel);
        return (D) this;
    }

    public D confirmBtn(CharSequence label, DialogBtnClickListener clickListener) {
        confirmBtn(label);
        mOnConfirmClickListener = clickListener;
        return (D) this;
    }

    public D confirmBtn(CharSequence label, int color) {
        confirmBtn(label);
        confirmBtnTextStyle(color, mConfirmLabelTextSizeSp, mConfirmLabelBold);
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
        applyBtnStyle(mNestedDialog, mConfirmBtn, mConfirmLabelTextSizeSp, mConfirmLabelColor, mConfirmLabelBold);
        return (D) this;
    }

    public D secondsDelayConfirm(int seconds) {
        mSecondsDelayConfirm = seconds >= 0 ? seconds : 0;
        return (D) this;
    }

    public D title(CharSequence title) {
        mTitle = title;
        applyTitle(mNestedDialog);
        return (D) this;
    }

    protected void applyTitle(AppCompatDialog dialog) {
        if (dialog != null) {
            mHeaderViewWrapper.setVisibility(Utils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);
            mTitleView.setText(mTitle);
        }
    }

    public D titleStyle(int color, float textSizeSp, boolean bold) {
        mTitleColor = color;
        mTitleTextSizeSp = textSizeSp;
        mTitleBold = bold;
        applyTitleStyle(mNestedDialog);
        return (D) this;
    }

    protected void applyTitleStyle(AppCompatDialog dialog) {
        if (dialog == null) {
            return;
        }
        if (mTitleColor != 0) {
            mTitleView.setTextColor(mTitleColor);
        }
        if (mTitleTextSizeSp > 0) {
            mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSizeSp);
        }
        mTitleView.getPaint().setFakeBoldText(mTitleBold);
    }

    @Override
    protected int provideHeaderLayout() {
        return R.layout.smart_show_message_title;
    }

    protected void initHeader(AppCompatDialog dialog, FrameLayout headerViewWrapper) {
        super.initHeader(dialog, headerViewWrapper);
        mTitleView = mHeaderViewWrapper.findViewById(R.id.smart_show_dialog_title_view);
    }

    @Override
    protected void applyHeader(AppCompatDialog dialog) {
        super.applyHeader(dialog);
        applyTitle(dialog);
        applyTitleStyle(dialog);
    }


    @Override
    protected int provideBodyLayout() {
        return R.layout.smart_show_message_content;
    }

    @Override
    protected void initBody(AppCompatDialog dialog, FrameLayout bodyViewWrapper) {
        super.initBody(dialog, bodyViewWrapper);
        mMsgView = mBodyViewWrapper.findViewById(R.id.smart_show_dialog_message_view);
        mMsgView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
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
    }

    @Override
    protected void applyBody(AppCompatDialog dialog) {
        super.applyBody(dialog);
        applyMsg(dialog);
        applyMsgStyle(dialog);
    }


    @Override
    protected int provideFooterLayout() {
        return R.layout.smart_show_default_single_button;
    }

    @Override
    protected void initFooter(AppCompatDialog dialog, FrameLayout footerViewWrapper) {
        super.initFooter(dialog, footerViewWrapper);
        mConfirmBtn = mFooterViewWrapper.findViewById(R.id.smart_show_dialog_confirm_btn);
        mConfirmBtn.setOnClickListener(mOnClickListener);
        mConfirmBtn.addOnAttachStateChangeListener(new ConfirmDelayCallback() {
            private int mSecondsDelayConfirmCopy;
            private int mSecondsDelayInitValue = mSecondsDelayConfirmCopy;
            private StringBuilder mConfirmLabelWhenDelay = new StringBuilder();

            @Override
            public void reset() {
                mConfirmBtn.removeCallbacks(this);
                mSecondsDelayConfirmCopy = mSecondsDelayConfirm;
                mSecondsDelayInitValue = mSecondsDelayConfirmCopy;
            }

            @Override
            public void onViewAttachedToWindow(View v) {
                reset();
                if (mSecondsDelayConfirmCopy > 0) {
                    mConfirmBtn.setEnabled(false);
                    mConfirmBtn.setTextColor(DISABLE_COLOR);
                    mConfirmBtn.post(this);
                }
            }

            @Override
            public void run() {
                if (mSecondsDelayConfirmCopy > 0) {
                    mConfirmLabelWhenDelay.delete(0, mConfirmLabelWhenDelay.length());
                    mConfirmLabelWhenDelay.append(mConfirmLabel)
                            .append("(")
                            .append(mSecondsDelayConfirmCopy)
                            .append("s)");
                    mConfirmBtn.setText(mConfirmLabelWhenDelay.toString());
                    mSecondsDelayConfirmCopy--;
                    mConfirmBtn.postDelayed(this, 1000);
                } else {
                    mConfirmBtn.setEnabled(true);
                    mConfirmBtn.setText(mConfirmLabel);
                    mConfirmBtn.setTextColor(mConfirmLabelColor);
                    mConfirmBtn.removeCallbacks(this);
                }
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                mConfirmBtn.setText(mConfirmLabel);
                mConfirmBtn.setTextColor(mConfirmLabelColor);
                mConfirmBtn.removeCallbacks(this);
                mConfirmBtn.setEnabled(true);
                mSecondsDelayConfirmCopy = mSecondsDelayInitValue;
            }
        });
    }

    @Override
    protected void applyFooter(AppCompatDialog dialog) {
        super.applyFooter(dialog);
        applyBtnLabel(dialog, mConfirmBtn, mConfirmLabel);
        applyBtnStyle(dialog, mConfirmBtn, mConfirmLabelTextSizeSp, mConfirmLabelColor, mConfirmLabelBold);
    }

    protected void applyBtnLabel(AppCompatDialog dialog, TextView btn, CharSequence btnLabel) {
        if (dialog != null && !Utils.isEmpty(btnLabel)) {
            btn.setText(btnLabel);
        }
    }

    protected void applyBtnStyle(AppCompatDialog dialog, TextView btn, float labelSizeSp, @ColorInt int labelColor, boolean labelBold) {
        if (dialog == null) {
            return;
        }
        if (labelColor != 0) {
            btn.setTextColor(labelColor);
        }
        if (labelSizeSp > 0) {
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, labelSizeSp);
        }
        btn.getPaint().setFakeBoldText(labelBold);
    }

    protected View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBtnClick(v);
        }
    };

    protected void onBtnClick(View v) {
        if (v.getId() == R.id.smart_show_dialog_confirm_btn) {
            if (mOnConfirmClickListener == null) {
                dismiss();
            } else {
                onConfirmBtnClick();
            }
            return;
        }
    }

    protected void onConfirmBtnClick() {
        mOnConfirmClickListener.onBtnClick((D) this, DialogBtnClickListener.BTN_CONFIRM, null);
    }

}
