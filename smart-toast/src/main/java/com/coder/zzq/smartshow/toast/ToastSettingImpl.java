package com.coder.zzq.smartshow.toast;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.coder.zzq.smartshow.core.SmartShow;


public class ToastSettingImpl implements IToastSetting {
    private View mCustomView;
    @DrawableRes
    private int mBgDrawableRes;
    @ColorInt
    private int mBgColor;
    @ColorInt
    private int mTextColor;
    private float mTextSizeSp;
    private boolean mTextBold;
    private IProcessToastCallback mIProcessToastCallback;
    private boolean mDismissOnleave;
    @ColorInt
    private int mTypeInfoToastThemeColor;


    public ToastSettingImpl() {
        mBgColor = -1;
        mTextColor = -1;
        mTextSizeSp = -1;
        mTypeInfoToastThemeColor = -1;
    }

    @Override
    public IToastSetting view(View view) {
        mCustomView = view;
        return this;
    }

    @Override
    public IToastSetting view(@LayoutRes int layout) {
        return view(LayoutInflater.from(SmartShow.getContext()).inflate(layout, null));
    }

    @Override
    public IToastSetting backgroundColor(@ColorInt int color) {
        mBgColor = color;
        return this;
    }

    @Override
    public IToastSetting backgroundColorRes(@ColorRes int colorRes) {
        return backgroundColor(ContextCompat.getColor(SmartShow.getContext(), colorRes));
    }

    @Override
    public IToastSetting backgroundDrawableRes(int drawableRes) {
        mBgDrawableRes = drawableRes;
        return this;
    }

    @Override
    public IToastSetting textColor(@ColorInt int color) {
        mTextColor = color;
        return this;
    }

    @Override
    public IToastSetting textColorRes(@ColorRes int colorRes) {
        return textColor(ContextCompat.getColor(SmartShow.getContext(), colorRes));
    }

    @Override
    public IToastSetting textSizeSp(float sp) {
        mTextSizeSp = sp;
        return this;
    }

    @Override
    public IToastSetting textBold(boolean bold) {
        mTextBold = bold;
        return this;
    }

    @Override
    public IToastSetting dismissOnLeave(boolean b) {
        mDismissOnleave = b;
        return this;
    }

    public boolean isDismissOnleave() {
        return mDismissOnleave;
    }

    @Override
    public IToastSetting processView(IProcessToastCallback callback) {
        mIProcessToastCallback = callback;
        return this;
    }

    @Override
    public IToastSetting typeInfoToastThemeColor(int color) {
        mTypeInfoToastThemeColor = color;
        return this;
    }

    @Override
    public IToastSetting typeInfoToastThemeColorRes(int colorRes) {
        return typeInfoToastThemeColor(ContextCompat.getColor(SmartShow.getContext(), colorRes));
    }


    public View getCustomView() {
        return mCustomView;
    }

    public int getBgColor() {
        return mBgColor;
    }

    public boolean isBgColorSetup() {
        return mBgColor != -1;
    }

    public int getBgDrawableRes() {
        return mBgDrawableRes;
    }

    public boolean isBgDrawableSetup(){
        return mBgDrawableRes != 0;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public boolean isTextColorSetup() {
        return mTextColor != -1;
    }

    public float getTextSizeSp() {
        return mTextSizeSp;
    }

    public boolean isTextSizeSetup() {
        return mTextSizeSp != -1;
    }

    public boolean isTextBold() {
        return mTextBold;
    }

    public IProcessToastCallback getIProcessToastCallback() {
        return mIProcessToastCallback;
    }

    public boolean isCustom() {
        return mCustomView != null;
    }


    public boolean isViewCallbackSetup() {
        return mIProcessToastCallback != null;
    }

    public boolean isTypeInfoThemeColorSetup() {
        return mTypeInfoToastThemeColor != -1;
    }

    public int getTypeInfoThemeColor() {
        return mTypeInfoToastThemeColor;
    }
}
