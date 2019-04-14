package com.coder.zzq.smartshow.toast;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

import com.coder.zzq.smartshow.core.Utils;

public class PlainToastSetting implements IPlainToastSetting {
    @DrawableRes
    private int mBgDrawableRes;
    @ColorInt
    private int mBgColor;
    @ColorInt
    private int mTextColor;
    private float mTextSizeSp;
    private boolean mTextBold;

    private int mBgMode = BG_MODE_SRC;


    private CustomViewCallback mCustomViewCallback;

    @Override
    public IPlainToastSetting customView(CustomViewCallback callback) {
        mCustomViewCallback = callback;
        return this;
    }


    public CustomViewCallback getCustomViewCallback() {
        return mCustomViewCallback;
    }

    @Override
    public IPlainToastSetting backgroundColor(int color) {
        mBgColor = color;
        mBgMode = BG_MODE_COLOR;
        return this;
    }

    @Override
    public IPlainToastSetting backgroundColorRes(int colorRes) {
        return backgroundColor(Utils.getColorFromRes(colorRes));
    }

    public int getBgColor() {
        return mBgColor;
    }

    @Override
    public IPlainToastSetting backgroundDrawableRes(int drawableRes) {
        mBgDrawableRes = drawableRes;
        mBgMode = BG_MODE_DRAWABLE;
        return this;
    }

    public int getBgDrawableRes() {
        return mBgDrawableRes;
    }

    @Override
    public IPlainToastSetting srcBackground() {
        mBgMode = BG_MODE_SRC;
        return this;
    }

    public int getBgMode() {
        return mBgMode;
    }

    @Override
    public IPlainToastSetting textColor(int color) {
        mTextColor = color;
        return this;
    }

    @Override
    public IPlainToastSetting textColorRes(int colorRes) {
        return textColor(Utils.getColorFromRes(colorRes));
    }

    @ColorInt
    public int getTextColor() {
        return mTextColor;
    }

    @Override
    public IPlainToastSetting textSizeSp(float sp) {
        mTextSizeSp = sp;
        return this;
    }

    public float getTextSizeSp() {
        return mTextSizeSp;
    }

    @Override
    public IPlainToastSetting textBold(boolean bold) {
        mTextBold = bold;
        return this;
    }

    public boolean isTextBold() {
        return mTextBold;
    }
}
