package com.coder.zzq.smartshow.dialog.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class DialogBtn extends android.support.v7.widget.AppCompatTextView implements View.OnAttachStateChangeListener, Runnable {

    protected onDialogBtnClickListener mOnDialogBtnClickListener;
    protected DataProvider mDataProvider;
    protected int mSeconds;
    protected CharSequence mLabel;
    protected StringBuilder mLabelWhenDisable;


    public DialogBtn(Context context) {
        super(context);
    }

    public DialogBtn(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogBtn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setLabelAndClickListener(CharSequence label, onDialogBtnClickListener onDialogBtnClickListener) {
        mLabel = label;
        setText(label);
        mOnDialogBtnClickListener = onDialogBtnClickListener;
    }

    public void setDataProvider(DataProvider dataProvider) {
        mDataProvider = dataProvider;
    }

    public onDialogBtnClickListener getOnDialogBtnClickListener() {
        return mOnDialogBtnClickListener;
    }

    public DataProvider getDataProvider() {
        return mDataProvider;
    }

    public void setTextStyle(@ColorInt int color, float textSizeSp, boolean bold) {
        if (color >= 0) {
            setTextColor(color);
        }
        if (textSizeSp > 0) {
            setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        }

        getPaint().setFakeBoldText(bold);
    }

    public void delayEnable(int seconds) {
        if (seconds > 0) {
            mSeconds = seconds;
            addOnAttachStateChangeListener(this);
        } else {
            mSeconds = 0;
            removeOnAttachStateChangeListener(this);
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        if (mSeconds > 0) {
            ensureCreateStringBuilder();
            setEnabled(false);
            post(this);
        }
    }

    private void ensureCreateStringBuilder() {
        if (mLabelWhenDisable == null) {
            mLabelWhenDisable = new StringBuilder();
        }
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        mSeconds = 0;
        removeCallbacks(this);
        removeOnAttachStateChangeListener(this);
    }

    @Override
    public void run() {
        if (mSeconds > 0) {
            mLabelWhenDisable.delete(0, mLabelWhenDisable.length());
            mLabelWhenDisable.append(mLabel)
                    .append("(")
                    .append(mSeconds)
                    .append("s)");
            setText(mLabelWhenDisable.toString());
            mSeconds--;
            postDelayed(this, 1000);
        } else {
            setEnabled(true);
            setText(mLabel);
            removeCallbacks(this);
            removeOnAttachStateChangeListener(this);
        }

    }


    public interface onDialogBtnClickListener {
        void onDialogBtnClick(Dialog dialog, DialogBtn btn, Object data);
    }

    public interface DataProvider {
        Object getDataWhenClick();
    }
}
