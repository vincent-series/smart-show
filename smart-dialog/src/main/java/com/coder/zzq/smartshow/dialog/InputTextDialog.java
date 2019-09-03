package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.toolkit.Utils;

public class InputTextDialog extends SimpleBranchDialog<InputTextDialog> {
    public static int INPUT_NO_LIMIT = -1;

    protected CharSequence mDefaultText;

    protected CharSequence mHint;

    protected int mAtMostInputNum;
    @ColorInt
    protected int mInputNumMarkColor;

    protected boolean mClearWhenShowAgain;

    protected EditText mInputEdt;
    protected TextView mInputCountView;

    public InputTextDialog() {
        super();
        mTitle = "输入";
        mAtMostInputNum = 20;
        mInputNumMarkColor = Utils.getColorFromRes(R.color.colorPrimary);
    }

    public InputTextDialog textOfDefaultFill(CharSequence defaultText) {
        mDefaultText = defaultText;
        return this;
    }

    public InputTextDialog hint(CharSequence hintMsg) {
        mHint = hintMsg;
        applyHint(mNestedDialog);
        return this;
    }

    protected void applyHint(AppCompatDialog dialog) {
        if (dialog != null) {
            mInputEdt.setHint(mHint);
        }
    }

    public InputTextDialog inputAtMost(int num) {
        if (num > 0 || num == INPUT_NO_LIMIT) {
            mAtMostInputNum = num;
        } else {
            mAtMostInputNum = INPUT_NO_LIMIT;
        }
        applyInputCount(mNestedDialog, mInputEdt == null ? "" : mInputEdt.getText().toString());
        return this;
    }

    protected void applyInputCount(AppCompatDialog dialog, String s) {
        if (dialog == null) {
            return;
        }
        if (mAtMostInputNum == INPUT_NO_LIMIT) {
            mInputCountView.setText(String.valueOf(s.length()));
        } else {
            mInputCountView.setText(String.valueOf(s.length()) + "/" + mAtMostInputNum);
        }

    }

    public InputTextDialog inputCountMarkColor(@ColorInt int color) {
        mInputNumMarkColor = color;
        applyInputNumMarkColor(mNestedDialog);
        return this;
    }

    protected void applyInputNumMarkColor(AppCompatDialog dialog) {
        if (dialog != null) {
            mInputCountView.setTextColor(mInputNumMarkColor);
        }
    }

    public InputTextDialog clearInputPerShow(boolean clear) {
        mClearWhenShowAgain = clear;
        return this;
    }

    public AppCompatDialog createDialog(Activity activity) {
        AppCompatDialog dialog = super.createDialog(activity);
        Utils.popKeyboardWhenDialogShow(dialog);
        return dialog;
    }

    @Override
    public void resetDialogWhenShowAgain(AppCompatDialog dialog) {
        super.resetDialogWhenShowAgain(dialog);
        if (mClearWhenShowAgain) {
            mInputEdt.setText(mDefaultText);
        }
        mInputEdt.setSelection(mInputEdt.getText().length());
    }

    @Override
    protected int provideBodyLayout() {
        return R.layout.smart_show_input_content;
    }

    @Override
    protected void initBody(final AppCompatDialog dialog, FrameLayout bodyViewWrapper) {
        super.initBody(dialog, bodyViewWrapper);
        bodyViewWrapper.setPadding(bodyViewWrapper.getPaddingLeft(), bodyViewWrapper.getPaddingTop(),
                bodyViewWrapper.getPaddingRight(), 0);
        mInputEdt = bodyViewWrapper.findViewById(R.id.smart_show_input_edt);
        mInputCountView = bodyViewWrapper.findViewById(R.id.smart_show_input_count_mark);
        mInputEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                applyInputCount(dialog, s.toString());
                if (s.length() > mAtMostInputNum) {
                    mInputEdt.setText(s.subSequence(0, mAtMostInputNum));
                    mInputEdt.setSelection(mInputEdt.getText().length());
                }
            }
        });
    }

    @Override
    protected void applyBody(AppCompatDialog dialog) {
        super.applyBody(dialog);
        applyInputNumMarkColor(dialog);
        applyHint(dialog);
        applyInputNumMarkColor(dialog);
        mInputEdt.setText(mDefaultText);
        mInputEdt.setSelection(mInputEdt.getText().length());
    }

    protected StringBuilder mStringBuilder = new StringBuilder();

//    private void processWhenInputLimit(String s, TextView inputNumView, StringBuilder stringBuilder) {
//        stringBuilder.delete(0, stringBuilder.length());
//        if (s.length() > mAtMostInputNum) {
//            inputNumView.setTextColor(Color.RED);
//            stringBuilder.append("-")
//                    .append(s.length() - mAtMostInputNum);
//            inputNumView.setText(stringBuilder);
//        } else {
//            inputNumView.setTextColor(mInputNumMarkColor);
//            stringBuilder.append(mAtMostInputNum - s.length());
//            inputNumView.setText(stringBuilder);
//        }
//    }

    @Override
    protected void onConfirmBtnClick() {
        mOnConfirmClickListener.onBtnClick(this, DialogBtnClickListener.BTN_CONFIRM, mInputEdt.getText().toString());
    }
}
