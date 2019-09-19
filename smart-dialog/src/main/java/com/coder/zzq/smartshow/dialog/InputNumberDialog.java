package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatDialog;

import com.coder.zzq.toolkit.Utils;

public class InputNumberDialog extends SimpleBranchDialog<InputNumberDialog> {

    public static final int NUMBER_TYPE_INT = 0;

    public static final int NUMBER_TYPE_DECIMAL = 1;


    protected CharSequence mDefaultNum;

    protected CharSequence mHint;


    protected int mNumberType = NUMBER_TYPE_INT;

    protected boolean mHasSigned = false;

    protected boolean mClearWhenShowAgain;

    protected CharSequence mNumUnit;

    protected EditText mInputEdt;
    protected LinearLayout mErrorTipLine;
    protected TextView mErrorTipTv;
    protected TextView mNumUnitTv;

    public InputNumberDialog() {
        super();
        mTitle = "输入";
    }

    public InputNumberDialog numberType(int numberType, boolean hasSigned) {
        mNumberType = numberType;
        mHasSigned = hasSigned;
        applyNumType(mNestedDialog);
        return this;
    }

    public InputNumberDialog numberType(int numberType) {
        return numberType(numberType, false);
    }

    protected void applyNumType(AppCompatDialog dialog) {
        if (dialog != null) {
            int type = EditorInfo.TYPE_CLASS_NUMBER;
            switch (mNumberType) {
                case NUMBER_TYPE_INT:
                    type = EditorInfo.TYPE_CLASS_NUMBER;
                    break;
                case NUMBER_TYPE_DECIMAL:
                    type |= EditorInfo.TYPE_NUMBER_FLAG_DECIMAL;
                    break;
            }
            if (mHasSigned) {
                type |= EditorInfo.TYPE_NUMBER_FLAG_SIGNED;
            }
            mInputEdt.setInputType(type);
        }
    }

    public InputNumberDialog numberOfDefaultShow(CharSequence defaultText) {
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

    public InputNumberDialog numUnit(String numUnit) {
        mNumUnit = numUnit;
        applyNumUnit(mNestedDialog);
        return this;
    }

    protected void applyNumUnit(AppCompatDialog dialog) {
        if (dialog != null) {
            mNumUnitTv.setText(mNumUnit);
        }
    }

    public void showErrorTip(CharSequence errorTip) {
        mErrorTipLine.setVisibility(Utils.isEmpty(errorTip) ? View.GONE : View.VISIBLE);
        mErrorTipTv.setText(errorTip);
    }

    public void showErrorTip(@StringRes int errorTip) {
        showErrorTip(Utils.getStringFromRes(errorTip));
    }

    @NonNull
    @Override
    protected AppCompatDialog createDialog(Activity activity) {
        AppCompatDialog dialog = super.createDialog(activity);
        Utils.popKeyboardWhenDialogShow(dialog);
        return dialog;
    }

    @Override
    protected int provideBodyLayout() {
        return R.layout.smart_show_input_num;
    }

    @Override
    protected void initBody(AppCompatDialog dialog, FrameLayout bodyViewWrapper) {
        super.initBody(dialog, bodyViewWrapper);
        mInputEdt = bodyViewWrapper.findViewById(R.id.smart_show_input_edt);
        mInputEdt.getLayoutParams().width = provideDialogWidth() / 3;
        mInputEdt.requestFocus();
        mErrorTipLine = bodyViewWrapper.findViewById(R.id.smart_show_error_tip_line);
        mErrorTipTv = bodyViewWrapper.findViewById(R.id.smart_show_error_tip);
        mNumUnitTv = bodyViewWrapper.findViewById(R.id.smart_show_num_unit);
        mNumUnitTv.setMaxWidth(provideDialogWidth() / 3 - Utils.dpToPx(40));
    }

    @Override
    protected void applyBody(AppCompatDialog dialog) {
        super.applyBody(dialog);
        applyNumType(dialog);
        applyHint(dialog);
        mInputEdt.setText(Utils.isEmpty(mDefaultNum) ? (mNumberType == NUMBER_TYPE_INT ? "0" : "0.00") : mDefaultNum);
        mInputEdt.setSelection(mInputEdt.getText().length());
        mNumUnitTv.setText(mNumUnit);
    }


    @Override
    protected void onConfirmBtnClick() {
        mOnConfirmClickListener.onBtnClick(this, DialogBtnClickListener.BTN_CONFIRM, mInputEdt.getText().toString());
    }

    @Override
    protected void resetDialogWhenShowAgain(AppCompatDialog dialog) {
        super.resetDialogWhenShowAgain(dialog);
        showErrorTip(null);
        if (mClearWhenShowAgain) {
            mInputEdt.setText(Utils.isEmpty(mDefaultNum) ? (mNumberType == NUMBER_TYPE_INT ? "0" : "0.00") : mDefaultNum);
        }
        mInputEdt.setSelection(mInputEdt.getText().length());
    }

}
