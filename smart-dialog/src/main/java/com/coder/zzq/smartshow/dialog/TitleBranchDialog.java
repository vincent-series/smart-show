package com.coder.zzq.smartshow.dialog;

import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.toolkit.Utils;

public abstract class TitleBranchDialog<D extends NormalDialog> extends BranchDialog<D> {
    protected CharSequence mTitle;
    protected float mTitleTextSizeSp;
    @ColorInt
    protected int mTitleColor;
    protected boolean mTitleBold;

    protected TextView mTitleView;

    public D title(CharSequence title) {
        mTitle = title;
        applyTitle(mNestedDialog);
        return (D) this;
    }

    protected void applyTitle(AppCompatDialog dialog) {
        if (dialog != null) {
            mHeaderViewWrapper.setVisibility(Utils.isEmpty(mTitle) ? View.GONE : View.VISIBLE);
            mTitleView.setText(mTitle);
        }
    }

    public D titleStyle(int color, float textSizeSp, boolean bold) {
        mTitleColor = color;
        mTitleTextSizeSp = textSizeSp;
        mTitleBold = bold;
        applyTitleStyle(mNestedDialog);
        return (D) this;
    }

    protected void applyTitleStyle(AppCompatDialog dialog) {
        if (dialog == null) {
            return;
        }
        if (mTitleColor != 0) {
            mTitleView.setTextColor(mTitleColor);
        }
        if (mTitleTextSizeSp > 0) {
            mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTitleTextSizeSp);
        }
        mTitleView.getPaint().setFakeBoldText(mTitleBold);
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
    protected void applyHeader(AppCompatDialog dialog) {
        super.applyHeader(dialog);
        applyTitle(dialog);
        applyTitleStyle(dialog);
    }


    @Override
    protected abstract int provideBodyLayout();

    @Override
    protected void initBody(AppCompatDialog dialog, FrameLayout bodyViewWrapper) {

    }


    @Override
    protected abstract int provideFooterLayout();
}
