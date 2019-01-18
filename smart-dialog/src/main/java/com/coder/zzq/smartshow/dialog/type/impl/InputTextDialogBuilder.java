package com.coder.zzq.smartshow.dialog.type.impl;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.type.IInputTextDialogBuilder;
import com.coder.zzq.smartshow.toast.SmartToast;

public class InputTextDialogBuilder extends NormalDialogBuilder<IInputTextDialogBuilder> implements IInputTextDialogBuilder, TextWatcher {
    @Override
    protected int getContentPartLayoutRes() {
        return R.layout.smart_show_input_content;
    }

    private EditText mInputEdt;
    private TextView mInputNumView;
    private int mAtMostInputNum = 70;

    public InputTextDialogBuilder() {
        super();
        mInputEdt = mContentPartView.findViewById(R.id.input_edt);
        mInputNumView = mContentPartView.findViewById(R.id.inpu_num);
        mInputNumView.setText(String.valueOf(mAtMostInputNum));
        mInputEdt.addTextChangedListener(this);
    }

    @Override
    public IInputTextDialogBuilder hint(CharSequence hintMsg) {
        mInputEdt.setHint(hintMsg);
        return this;
    }

    @Override
    public IInputTextDialogBuilder inputAtMost(int num) {
        mAtMostInputNum = num;
        mInputNumView.setText(String.valueOf(num));
        return this;
    }

    @Override
    public IInputTextDialogBuilder negativeBtn(CharSequence label, DialogBtnClickListener clickListener) {
        return null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > mAtMostInputNum) {
            mInputNumView.setTextColor(Color.RED);
            mInputNumView.setText(String.valueOf(s.length() - mAtMostInputNum));
        } else {
            mInputNumView.setTextColor(Utils.getColorFromRes(R.color.colorPrimary));
            mInputNumView.setText(String.valueOf(mAtMostInputNum - s.length()));
        }

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_positive_btn && mInputEdt.getText().length() > mAtMostInputNum) {
            SmartToast.showAtTop("输入超出上限（" + mAtMostInputNum + ")");
            return;
        }
        mDialog.dismiss();
        if (mOnPositiveBtnClickListener != null && v.getId() == R.id.dialog_positive_btn) {
            mOnPositiveBtnClickListener.onBtnClick((TextView) v, mInputEdt.getText().toString());
            return;
        }

        if (mOnNegativeBtnClickListener != null && v.getId() == R.id.dialog_negative_btn) {
            mOnNegativeBtnClickListener.onBtnClick((TextView) v, mInputEdt.getText().toString());
        }

    }

}
