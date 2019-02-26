package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.creator.type.IInputTextDialogCreator;

class InputTextDialogCreator extends SimpleBranchCreator<IInputTextDialogCreator> implements IInputTextDialogCreator {

    protected CharSequence mDefaultText;

    protected CharSequence mHint;

    protected int mAtMostInputNum;
    @ColorInt
    protected int mInputNumMarkColor;

    protected boolean mClearPerShow;

    public InputTextDialogCreator() {
        super();
        mTitle = "输入";
        mAtMostInputNum = 20;
        mInputNumMarkColor = Utils.getColorFromRes(R.color.colorPrimary);
    }

    @Override
    public IInputTextDialogCreator textOfDefaultFill(CharSequence defaultText) {
        mDefaultText = defaultText;
        return this;
    }

    @Override
    public InputTextDialogCreator hint(CharSequence hintMsg) {
        mHint = hintMsg;
        return this;
    }

    @Override
    public IInputTextDialogCreator inputAtMost(int num) {
        if (num > 0 || num == INPUT_NO_LIMIT) {
            mAtMostInputNum = num;
        }
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
    public IInputTextDialogCreator clearInputPerShow(boolean clear) {
        mClearPerShow = clear;
        return this;
    }

    @Override
    public Dialog createDialog(Activity activity) {
        Dialog dialog = super.createDialog(activity);
        Utils.popKeyboardWhenDialogShow(dialog);
        return dialog;
    }

    @Override
    public void resetDialogPerShow(Dialog dialog) {
        super.resetDialogPerShow(dialog);
        if (mClearPerShow) {
            EditText editText = dialog.findViewById(R.id.smart_show_input_edt);
            editText.setText(mDefaultText);
        }
    }

    @Override
    protected void initBody(Dialog dialog, FrameLayout bodyViewWrapper) {
        super.initBody(dialog, bodyViewWrapper);
        EditText inputEdt = bodyViewWrapper.findViewById(R.id.smart_show_input_edt);
        final TextView inputNumView = bodyViewWrapper.findViewById(R.id.smart_show_input_count_mark);
        inputNumView.setTextColor(mInputNumMarkColor);
        inputNumView.setText(String.valueOf(mAtMostInputNum));
        inputEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            protected StringBuilder mStringBuilder = new StringBuilder();

            @Override
            public void afterTextChanged(Editable s) {
                if (mAtMostInputNum == INPUT_NO_LIMIT) {
                    inputNumView.setTextColor(Utils.getColorFromRes(R.color.colorPrimary));
                    inputNumView.setText(String.valueOf(s.length()));
                } else {
                    processWhenInputLimit(s, inputNumView, mStringBuilder);
                }
            }
        });
        if (!Utils.isEmpty(mHint)) {
            inputEdt.setHint(mHint);
        }

        if (!Utils.isEmpty(mDefaultText)) {
            inputEdt.setText(mDefaultText);
        }
    }

    private void processWhenInputLimit(Editable s, TextView inputNumView, StringBuilder stringBuilder) {
        stringBuilder.delete(0, stringBuilder.length());
        if (s.length() > mAtMostInputNum) {
            inputNumView.setTextColor(Color.RED);
            stringBuilder.append("-")
                    .append(s.length() - mAtMostInputNum);
            inputNumView.setText(stringBuilder);
        } else {
            inputNumView.setTextColor(Utils.getColorFromRes(R.color.colorPrimary));
            stringBuilder.append(mAtMostInputNum - s.length());
            inputNumView.setText(stringBuilder);
        }
    }

    @Override
    protected void onConfirmBtnClick(Dialog dialog, TextView btn, DialogBtnClickListener clickListener) {
        if (clickListener == null) {
            dialog.dismiss();
        } else {
            EditText editText = dialog.getWindow().getDecorView().findViewById(
                    R.id.smart_show_input_edt
            );
            clickListener.onBtnClick(dialog, DialogBtnClickListener.BTN_CONFIRM, editText.getText().toString());
        }
    }


    @Override
    protected int provideBodyView() {
        return R.layout.smart_show_input_content;
    }
}
