package com.coder.zzq.smartshow.snackbar.base;

import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.Utils;

public class BarSettingImpl<View,BarSetting> implements IBarSetting<View,BarSetting> {

    @ColorInt
    private int mMsgColor;
    private float mMsgTextSizeSp;

    @ColorInt
    private int mActionColor;
    private float mActionSizeSp;

    private boolean mDismissOnLeave;
    private String mDefaultActionTextForIndefinite;


    private IProcessBarCallback mProcessBarCallback;

    public BarSettingImpl() {
        mMsgColor = -1;
        mMsgTextSizeSp = -1f;
        mActionColor = -1;
        mActionSizeSp = -1f;
        mDefaultActionTextForIndefinite = "确定";
    }

    @Override
    public BarSetting msgTextColor(int color) {
        mMsgColor = color;
        return (BarSetting) this;
    }

    @Override
    public BarSetting msgTextColorRes(int colorRes) {
        return msgTextColor(Utils.getColorFromRes(colorRes));
    }

    public boolean msgColorHasSetup() {
        return mMsgColor != -1;
    }

    @ColorInt
    public int getMsgColor() {
        return mMsgColor;
    }


    @Override
    public BarSetting msgTextSizeSp(float textSizeSp) {
        mMsgTextSizeSp = textSizeSp;
        return (BarSetting) this;
    }

    public boolean msgTextSizeHasSetup() {
        return mMsgTextSizeSp != -1f;
    }

    public float getMsgTextSizeSp(){
        return mMsgTextSizeSp;
    }


    @Override
    public BarSetting actionColor(int color) {
        mActionColor = color;
        return (BarSetting) this;
    }

    @Override
    public BarSetting actionColorRes(int colorRes) {
        return actionColor(Utils.getColorFromRes(colorRes));
    }

    public boolean actionColorHasSetup(){
        return mActionColor != -1;
    }

    @ColorInt
    public int getActionColor(){
       return mActionColor;
    }

    @Override
    public BarSetting actionSizeSp(float textSizeSp) {
        mActionSizeSp = textSizeSp;
        return (BarSetting) this;
    }

    public boolean actionSizeHasSetup(){
        return mActionSizeSp != -1f;
    }

    public float getActionSizeSp(){
        return mActionSizeSp;
    }


    @Override
    public BarSetting defaultActionTextForIndefinite(String actionText) {
        mDefaultActionTextForIndefinite = actionText;
        return (BarSetting) this;
    }

    public CharSequence getDefaultActionTextForIndefinite(){
       return mDefaultActionTextForIndefinite;
    }

    @Override
    public BarSetting dismissOnLeave(boolean b) {
        mDismissOnLeave = b;
        return (BarSetting) this;
    }

    public boolean isDismissOnLeave(){
        return mDismissOnLeave;
    }

    @Override
    public BarSetting processView(IProcessBarCallback callback) {
        mProcessBarCallback = callback;
        return (BarSetting) this;
    }

    public boolean processBarCallbackHasSetup(){
        return mProcessBarCallback != null;
    }

    public IProcessBarCallback getProcessBarCallback() {
        return mProcessBarCallback;
    }
}
