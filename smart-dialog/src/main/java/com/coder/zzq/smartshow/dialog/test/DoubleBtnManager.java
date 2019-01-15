package com.coder.zzq.smartshow.dialog.test;

import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.R;

public class DoubleBtnManager<M extends DoubleBtnManager> extends SingleBtnManage<M> {
    private TextView mCancelBtn;
    private CharSequence mCancelLabel;
    private DialogBtnClickListener mOnCancelListener;
    @ColorInt
    private int mCancelTextColor;
    private float mCancelTextSizeSp;
    private boolean mCancelTextBold;

    public DoubleBtnManager(int layoutRes, ViewGroup parent) {
        super(layoutRes, parent);
        mCancelBtn = mView.findViewById(R.id.dialog_cancel_btn);
        mCancelLabel = "取消";
        mCancelTextColor = -1;
    }

    public M cancelBtn(CharSequence label, DialogBtnClickListener listener) {
        mCancelLabel = label;
        mOnCancelListener = listener;
        return (M) this;
    }

    public M cancelBtnTextStyle(@ColorInt int textColor, float textSizeSp, boolean textBold) {
        mCancelTextColor = textColor;
        mCancelTextSizeSp = textSizeSp;
        mCancelTextBold = textBold;
        return (M) this;
    }

    public void apply() {
        super.apply();
        mCancelBtn.setText(mCancelLabel);
        if (mCancelTextColor >= 0) {
            mCancelBtn.setTextColor(mCancelTextColor);
        }
        if (mCancelTextSizeSp > 0) {
            mCancelBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCancelTextSizeSp);
        }
        mCancelBtn.getPaint().setFakeBoldText(mCancelTextBold);
    }
}
