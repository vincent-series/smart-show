package com.coder.zzq.smartshow.dialog;

import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;

abstract class LoadingDialog<D extends SmartDialog> extends NormalDialog<D> {
    protected CharSequence mMsg = "加载中";
    protected boolean mWithoutMsgTip;
    protected TextView mMsgView;
    protected View mWithTipView;
    protected View mWithoutTipView;

    public LoadingDialog() {
        mCancelableOnTouchOutside = false;
        mDarkAroundWhenShow = false;
        mWindowBackground = 0;
    }

    public D message(CharSequence msg) {
        mMsg = Utils.isEmpty(msg) ? "加载中" : msg;
        return (D) this;
    }

    public D withoutMsgTip(boolean withoutMsgTip) {
        mWithoutMsgTip = withoutMsgTip;
        return (D) this;
    }


    @Override
    protected int provideContentLayout() {
        return R.layout.smart_show_loading_dialog;
    }

    @Override
    protected void initView(AppCompatDialog dialog, View contentView) {
        super.initView(dialog, contentView);
    }

    @Override
    protected void applyNewSetting(AppCompatDialog dialog) {
        super.applyNewSetting(dialog);
        if (mWithoutMsgTip && mContentView != mWithoutTipView) {
            ((FrameLayout) mContentView).removeAllViews();

        }
        mMsgView = mContentView.findViewById(R.id.smart_show_loading_message_view);
        if (mMsgView != null) {
            mMsgView.setText(mMsg);
        }
    }

    @Override
    protected int provideDialogStyle() {
        return R.style.smart_show_dialog;
    }

    @Override
    protected int provideDialogWidth() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}
