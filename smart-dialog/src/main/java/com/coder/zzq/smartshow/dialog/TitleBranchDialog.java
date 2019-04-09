package com.coder.zzq.smartshow.dialog;

import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;

public abstract class TitleBranchDialog<D extends SmartDialog> extends BranchDialog<D> {
    protected CharSequence mTitle;
    protected float mTitleTextSizeSp;
    @ColorInt
    protected int mTitleColor;
    protected boolean mTitleBold;

    private TextView mTitleView;

    public D title(CharSequence title) {
        mTitle = title;
        return (D) this;
    }

    public D titleStyle(int color, float textSizeSp, boolean bold) {
        mTitleColor = color;
        mTitleTextSizeSp = textSizeSp;
        mTitleBold = bold;
        return (D) this;
    }

    @Override
    protected int provideHeaderLayout() {
        return R.layout.smart_show_message_title;
    }

    @Override
    protected void initHeader(AppCompatDialog dialog, FrameLayout headerViewWrapper) {
        super.initHeader(dialog, headerViewWrapper);
        mTitleView = mHeaderViewWrapper.findViewById(R.id.smart_show_dialog_title_view);
    }

    @Override
    protected void applyHeader() {
        mHeaderViewWrapper.setVisibility(Utils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);
        if (!Utils.isEmpty(mTitle)) {
            mTitleView.setText(mTitle);
            if (mTitleColor != 0) {
                mTitleView.setTextColor(mTitleColor);
            }
            if (mTitleTextSizeSp > 0) {
                mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSizeSp);
            }
            if (mTitleBold) {
                mTitleView.getPaint().setFakeBoldText(mTitleBold);
            }
        }
    }


    @Override
    protected abstract int provideBodyLayout();

    @Override
    protected void initBody(AppCompatDialog dialog, FrameLayout bodyViewWrapper) {

    }


    @Override
    protected abstract int provideFooterLayout();
}
