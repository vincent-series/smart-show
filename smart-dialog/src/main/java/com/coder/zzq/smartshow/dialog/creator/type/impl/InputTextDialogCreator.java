package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.creator.type.IInputTextDialogCreator;

class InputTextDialogCreator extends SimpleBranchCreator<IInputTextDialogCreator> implements IInputTextDialogCreator, TextWatcher {
    protected CharSequence mHint;
    protected EditText mInputEdt;
    private TextView mInputNumView;
    private int mAtMostInputNum;
    @ColorInt
    private int mInputNumMarkColor;

    public InputTextDialogCreator() {
        super();
        mTitle = "输入";
        mAtMostInputNum = 20;
        mInputNumMarkColor = Utils.getColorFromRes(R.color.colorPrimary);
    }

    @Override
    public InputTextDialogCreator hint(CharSequence hintMsg) {
        mHint = hintMsg;
        return this;
    }

    @Override
    public IInputTextDialogCreator inputAtMost(int num) {
        mAtMostInputNum = num;
        return this;
    }

    @Override
    public IInputTextDialogCreator inputCountMarkColor(int color) {
        if (color > 0) {
            mInputNumMarkColor = color;
        }
        return this;
    }

    @Override
    protected void initBody(FrameLayout bodyViewWrapper) {
        super.initBody(bodyViewWrapper);
        mInputEdt = bodyViewWrapper.findViewById(R.id.smart_show_input_edt);
        mInputEdt.addTextChangedListener(this);
        if (!Utils.isEmpty(mHint)) {
            mInputEdt.setHint(mHint);
        }
        mInputNumView = bodyViewWrapper.findViewById(R.id.smart_show_input_count_mark);
        mInputNumView.setTextColor(mInputNumMarkColor);
        mInputNumView.setText(String.valueOf(mAtMostInputNum));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    protected StringBuilder mStringBuilder = new StringBuilder();

    @Override
    public void afterTextChanged(Editable s) {
        mStringBuilder.delete(0, mStringBuilder.length());
        if (s.length() > mAtMostInputNum) {
            mInputNumView.setTextColor(Color.RED);
            mStringBuilder.append("-")
                    .append(s.length() - mAtMostInputNum);
            mInputNumView.setText(mStringBuilder);
        } else {
            mInputNumView.setTextColor(Utils.getColorFromRes(R.color.colorPrimary));
            mStringBuilder.append(mAtMostInputNum - s.length());
            mInputNumView.setText(mStringBuilder);
        }

    }

    @Override
    protected void onConfirmBtnClick(View v) {
        if (v.getId() == R.id.smart_show_dialog_confirm_btn) {
            if (mOnConfirmClickListener == null) {
                mDialog.dismiss();
            } else {
                mOnConfirmClickListener.onBtnClick(mDialog, DialogBtnClickListener.BTN_CONFIRM, mInputEdt.getText().toString());
            }
        }
    }

    @Override
    protected int provideBodyView() {
        return R.layout.smart_show_input_content;
    }
}
