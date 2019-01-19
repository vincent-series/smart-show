package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.ColorInt;
import android.widget.EditText;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.type.IInputTextDialogBuilder;
import com.coder.zzq.smartshow.dialog.widget.DialogBtn;
import com.coder.zzq.smartshow.dialog.widget.DialogInput;

public class InputTextDialogBuilder extends NormalDialogBuilder<IInputTextDialogBuilder> implements IInputTextDialogBuilder {
    private static InputTextDialogBuilder sInputTextDialogBuilder;

    @Override
    protected int getContentPartLayoutRes() {
        return R.layout.smart_show_input_content;
    }

    private DialogInput mInputEdt;
    private TextView mInputNumView;
    private CharSequence mHint;
    private int mAtMostInputNum = 70;
    @ColorInt
    private int mInputNumMarkColor = Utils.getColorFromRes(R.color.colorPrimary);

    public InputTextDialogBuilder() {
        super();
    }

    @Override
    public IInputTextDialogBuilder hint(CharSequence hintMsg) {
        mHint = hintMsg;
        return this;
    }

    @Override
    public IInputTextDialogBuilder inputAtMost(int num) {
        mAtMostInputNum = num;
        return this;
    }

    @Override
    public IInputTextDialogBuilder negativeBtn(CharSequence label, DialogBtn.onDialogBtnClickListener clickListener) {
        mNegativeLabel = label;
        mOnNegativeBtnClickListener = clickListener;
        return this;
    }

    @Override
    public IInputTextDialogBuilder negativeBtnTextStyle(int color, float textSizeSp, boolean bold) {
        mNegativeLabelColor = color;
        mNegativeTextSizeSp = textSizeSp;
        mNegativeTextBold = bold;
        return this;
    }

    @Override
    public IInputTextDialogBuilder inputNumIndicatorColor(int color) {
        mInputNumMarkColor = color;
        return this;
    }


    @Override
    public Dialog createDialog(Activity activity) {
        Dialog dialog = super.createDialog(activity);
        mInputNumView = mDialogRootView.findViewById(R.id.inpu_num);
        mInputNumView.setText(String.valueOf(mAtMostInputNum));
        mInputEdt = mDialogRootView.findViewById(R.id.input_edt);
        final EditText editText = mInputEdt;
        mPositiveBtn.setDataProvider(new DialogBtn.DataProvider() {
            @Override
            public Object getDataWhenClick() {
                return editText.getText().toString().trim();
            }
        });
        mInputEdt.setInputNumView(mInputNumView);

        return dialog;
    }

    @Override
    protected void initViews(Dialog dialog) {
        super.initViews(dialog);
        mInputEdt = mDialogRootView.findViewById(R.id.input_edt);
        mInputNumView = mDialogRootView.findViewById(R.id.inpu_num);
        mInputNumView.setText(String.valueOf(mAtMostInputNum));
        mInputEdt.setHint(mHint);
        mInputEdt.setMarkColor(mInputNumMarkColor);
    }


    @Override
    public void resetDialog(Dialog dialog) {
        super.resetDialog(dialog);
    }

    @Override
    public void reset() {
        super.reset();
        mHint = null;
        mAtMostInputNum = 70;
        mInputNumView = null;
        mInputEdt = null;
    }

    public static InputTextDialogBuilder getInstance() {
        if (sInputTextDialogBuilder == null) {
            sInputTextDialogBuilder = new InputTextDialogBuilder();
        }
        return sInputTextDialogBuilder;
    }
}
