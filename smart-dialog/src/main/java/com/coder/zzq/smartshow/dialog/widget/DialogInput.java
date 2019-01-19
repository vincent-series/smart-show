package com.coder.zzq.smartshow.dialog.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;

public class DialogInput extends android.support.v7.widget.AppCompatEditText {
    private int mAtMostInput = 70;
    private TextView mInputNumView;
    @ColorInt
    private int mMarkColor = Utils.getColorFromRes(R.color.colorPrimary);

    public DialogInput(Context context) {
        super(context);
        init(context);
    }

    public DialogInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DialogInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > mAtMostInput) {
                    mInputNumView.setTextColor(Color.RED);
                    mInputNumView.setText(String.valueOf(s.length() - mAtMostInput));
                } else {
                    mInputNumView.setTextColor(mMarkColor);
                    mInputNumView.setText(String.valueOf(mAtMostInput - s.length()));
                }
            }
        });
    }

    public void setMarkColor(int markColor) {
        if (markColor >= 0) {
            mMarkColor = markColor;
        }
    }

    public void setInputNumView(TextView inputNumView) {
        mInputNumView = inputNumView;
    }

    public void setAtMostInput(int atMostInput) {
        if (atMostInput > 0) {
            mAtMostInput = atMostInput;
        }
    }
}
