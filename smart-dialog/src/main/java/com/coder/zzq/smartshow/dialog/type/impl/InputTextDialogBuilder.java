package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.zzq.smartshow.dialog.InputCheckListener;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.type.IInputTextDialogBuilder;

public class InputTextDialogBuilder extends NormalDialogBuilder<IInputTextDialogBuilder> implements IInputTextDialogBuilder, TextWatcher {
    @Override
    protected int getContentPartLayoutRes() {
        return R.layout.smart_show_input_content;
    }

    private EditText mInputEdt;
    private TextView mInputNumView;
    private int mAtMostInputNum = 70;
    private InputCheckListener mInputCheckListener;

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
    public IInputTextDialogBuilder inputCheck(InputCheckListener listener) {
        mInputCheckListener = listener;
        return this;
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
            mInputNumView.setText("-" + String.valueOf(s.length() - mAtMostInputNum));
        } else {
            mInputNumView.setTextColor(mConfirmBtn.getTextColors());
            mInputNumView.setText(String.valueOf(mAtMostInputNum - s.length()));
        }

    }

    @Override
    public boolean createAndShow(Activity activity, int tag) {
        boolean showSucc = super.createAndShow(activity, tag);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_positive_btn && mInputCheckListener != null
                && !mInputCheckListener.check(mInputEdt.getText().toString())) {
            return;
        }
        mDialog.dismiss();
        if (mOnConfirmBtnClickListener != null && v.getId() == R.id.dialog_positive_btn) {
            mOnConfirmBtnClickListener.onBtnClick((TextView) v, mInputEdt.getText().toString());
            return;
        }

        if (mOnCancelBtnClickListener != null && v.getId() == R.id.dialog_negative_btn) {
            mOnCancelBtnClickListener.onBtnClick((TextView) v, mInputEdt.getText().toString());
        }

    }

}
