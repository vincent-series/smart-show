package com.coder.zzq.smartshow.dialog.test;

import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.R;

public class SingleBtnManage<M extends SingleBtnManage> {
    protected View mView;
    protected TextView mConfirmBtn;
    protected CharSequence mConfirmLabel;
    protected DialogBtnClickListener mOnConfirmListener;
    @ColorInt
    protected int mConfirmTextColor;
    protected float mConfirmTextSizeSp;
    protected boolean mConfirmTextBold;

    public SingleBtnManage(@LayoutRes int layoutRes, ViewGroup parent) {
        mView = LayoutInflater.from(SmartShow.getContext()).inflate(layoutRes, parent, true);
        mConfirmBtn = mView.findViewById(R.id.dialog_confirm_btn);
        mConfirmLabel = "确定";
        mConfirmTextColor = Utils.getColorFromRes(R.color.colorPrimary);
    }

    public M confirmBtn(CharSequence label, DialogBtnClickListener listener) {
        mConfirmLabel = label;
        mOnConfirmListener = listener;
        return (M) this;
    }

    public M confirmBtnTextStyle(@ColorInt int textColor, float textSizeSp, boolean textBold) {
        mConfirmTextColor = textColor;
        mConfirmTextSizeSp = textSizeSp;
        mConfirmTextBold = textBold;
        return (M) this;
    }

    public void apply() {
        mConfirmBtn.setText(mConfirmLabel);
        mConfirmBtn.setTextColor(mConfirmTextColor);
        if (mConfirmTextSizeSp > 0) {
            mConfirmBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, mConfirmTextSizeSp);
        }
        mConfirmBtn.getPaint().setFakeBoldText(mConfirmTextBold);
    }
}
