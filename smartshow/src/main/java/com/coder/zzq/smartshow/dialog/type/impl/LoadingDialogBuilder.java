package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.Utils;
import com.coder.zzq.smartshow.dialog.type.ILoadingDialogBuilder;

public class LoadingDialogBuilder implements ILoadingDialogBuilder {
    private View mContentView;
    private ProgressBar mProgressBar;
    private TextView mMsgView;
    private CharSequence mMsg = "加载中...";
    @DrawableRes
    private int mDrawablRes = R.drawable.smart_show_loading_img;
    @LayoutRes
    private int mLayoutRes = R.layout.loading_large;

    public LoadingDialogBuilder() {

    }

    @Override
    public ILoadingDialogBuilder msg(CharSequence msg) {
        mMsg = Utils.isEmpty(msg) ? "加载中..." : msg;
        return this;
    }

    @Override
    public ILoadingDialogBuilder large() {
        mLayoutRes = R.layout.loading_large;
        return this;
    }

    @Override
    public ILoadingDialogBuilder middle() {
        mLayoutRes = R.layout.loading_middle;
        return this;
    }

    @Override
    public ILoadingDialogBuilder small() {
        mLayoutRes = R.layout.loading_small;
        return this;
    }

    @Override
    public Dialog create(Activity activity) {
        mContentView = LayoutInflater.from(SmartShow.getContext()).inflate(mLayoutRes, null);
        TextView msgView = mContentView.findViewById(R.id.loading_message);
        mMsgView = msgView;
        if (mMsgView != null) {
            mMsgView.setText(mMsg);
        }
        mProgressBar = mContentView.findViewById(R.id.progress_bar);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Dialog dialog = new Dialog(activity, R.style.smart_show_loading_dialog);
        dialog.setContentView(mContentView, lp);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
