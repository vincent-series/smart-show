package com.coder.zzq.smartshow.basebar;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.Utils;


public class BarSettingImpl<View,BarSetting> implements IBarSetting<View,BarSetting> {
    @ColorInt
    private int mBgColor;
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
        mBgColor = IBarSetting.SNACK_BAR_COLOR;
        mMsgColor = Color.WHITE;
        mMsgTextSizeSp = 14;
        mActionColor = Utils.getColorFromRes(R.color.colorAccent);
        mActionSizeSp = 14;
        mDefaultActionTextForIndefinite = "确定";
    }

    @Override
    public BarSetting backgroundColor(int color) {
        mBgColor = color;
        return (BarSetting) this;
    }

    @Override
    public BarSetting backgroundColorRes(int colorRes) {
        return backgroundColor(Utils.getColorFromRes(colorRes));
    }



    @ColorInt
    public int getBackgroundColor() {
        return mBgColor;
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



    @ColorInt
    public int getMsgColor() {
        return mMsgColor;
    }


    @Override
    public BarSetting msgTextSizeSp(float textSizeSp) {
        mMsgTextSizeSp = textSizeSp;
        return (BarSetting) this;
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


    @ColorInt
    public int getActionColor(){
       return mActionColor;
    }

    @Override
    public BarSetting actionSizeSp(float textSizeSp) {
        mActionSizeSp = textSizeSp;
        return (BarSetting) this;
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


    public IProcessBarCallback getProcessBarCallback() {
        return mProcessBarCallback;
    }
}
