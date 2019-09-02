package com.coder.zzq.smartshow.dialog;

import android.support.v7.app.AppCompatDialog;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;

public class InputNumberDialog extends SimpleBranchDialog<InputNumberDialog> {

    protected CharSequence mDefaultNum;

    protected CharSequence mHint;

    protected int mNumberType = EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_SIGNED | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL;

    protected boolean mClearWhenShowAgain;

    protected EditText mInputEdt;

    public InputNumberDialog() {
        super();
        mTitle = "输入";
    }

    public InputNumberDialog numberType(int numberType) {
        mNumberType = numberType;
        applyNumType(mNestedDialog);
        return this;
    }

    protected void applyNumType(AppCompatDialog dialog) {
        if (dialog != null) {
            mInputEdt.setInputType(mNumberType);
        }
    }

    public InputNumberDialog numberOfDefaultFill(CharSequence defaultText) {
        mDefaultNum = defaultText;
        return this;
    }

    public InputNumberDialog hint(CharSequence hintMsg) {
        mHint = hintMsg;
        applyHint(mNestedDialog);
        return this;
    }

    protected void applyHint(AppCompatDialog dialog) {
        if (dialog != null) {
            mInputEdt.setHint(mHint);
        }
    }


    public InputNumberDialog clearInputPerShow(boolean clear) {
        mClearWhenShowAgain = clear;
        return this;
    }

    @Override
    protected int provideBodyLayout() {
        return R.layout.smart_show_input_num;
    }

    @Override
    protected void initBody(AppCompatDialog dialog, FrameLayout bodyViewWrapper) {
        super.initBody(dialog, bodyViewWrapper);
        mInputEdt = bodyViewWrapper.findViewById(R.id.smart_show_input_edt);
    }

    @Override
    protected void applyBody(AppCompatDialog dialog) {
        super.applyBody(dialog);
        applyNumType(dialog);
        applyHint(dialog);
        mInputEdt.setText(mDefaultNum);
    }


    @Override
    protected void onConfirmBtnClick() {
        mOnConfirmClickListener.onBtnClick(this, DialogBtnClickListener.BTN_CONFIRM, mInputEdt.getText().toString());
    }
}
