package com.coder.zzq.smartshow.toast;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;

public class SrcToastDecorator {
    private static final int BG_MODE_SRC = 0;
    private static final int BG_MODE_COLOR = 1;
    private static final int BG_MODE_DRAWABLE = 2;

    private int mBgMode = BG_MODE_SRC;
    @DrawableRes
    private int mBgDrawableRes;
    @ColorInt
    private int mBgColor;
    @ColorInt
    private int mTextColor;
    private float mTextSizeSp;
    private boolean mTextBold;


    public static SrcToastDecorator create() {
        return new SrcToastDecorator();
    }

    public SrcToastDecorator backgroundColor(@ColorInt int color) {
        mBgColor = color;
        mBgMode = BG_MODE_COLOR;
        return this;
    }

    public SrcToastDecorator backgroundColorRes(@ColorRes int colorRes) {
        return backgroundColor(Utils.getColorFromRes(colorRes));
    }

    public SrcToastDecorator backgroundDrawableRes(@DrawableRes int drawableRes) {
        mBgDrawableRes = drawableRes;
        mBgMode = BG_MODE_DRAWABLE;
        return this;
    }

    public SrcToastDecorator textColor(@ColorInt int color) {
        mTextColor = color;
        return this;
    }

    public SrcToastDecorator textColorRes(@ColorRes int colorRes) {
        return textColor(Utils.getColorFromRes(colorRes));
    }


    public SrcToastDecorator textSizeSp(float sp) {
        mTextSizeSp = sp;
        return this;
    }

    public SrcToastDecorator textBold(boolean bold) {
        mTextBold = bold;
        return this;
    }


    public Toast apply() {
        Toast toast = Toast.makeText(SmartShow.getContext(), "", Toast.LENGTH_SHORT);

        switch (mBgMode) {
            case BG_MODE_COLOR:
                Drawable bg = Utils.getDrawableFromRes(android.R.drawable.toast_frame);
                bg.mutate();
                if (bg instanceof GradientDrawable) {
                    ((GradientDrawable) bg).setColor(mBgColor);
                } else {
                    DrawableCompat.setTint(bg, mBgColor);
                }
                ViewCompat.setBackground(toast.getView(), bg);
                break;
            case BG_MODE_DRAWABLE:
                toast.getView().setBackgroundResource(mBgDrawableRes);
                break;
        }

        TextView msgView = toast.getView().findViewById(android.R.id.message);
        if (mTextColor != 0) {
            msgView.setTextColor(mTextColor);
        }
        if (mTextSizeSp > 0) {
            msgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSizeSp);
        }

        msgView.getPaint().setFakeBoldText(mTextBold);
        msgView.setGravity(Gravity.CENTER);

        return toast;
    }


}
