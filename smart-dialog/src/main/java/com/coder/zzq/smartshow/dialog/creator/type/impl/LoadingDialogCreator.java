package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.creator.type.ILoadingDialogCreator;

public class LoadingDialogCreator extends NormalDialogCreator<ILoadingDialogCreator> implements ILoadingDialogCreator {
    private CharSequence mMsg = "加载中...";
    @DrawableRes
    private int mDrawablRes = R.drawable.smart_show_loading_img;
    @LayoutRes
    private int mLayoutRes = R.layout.smart_show_loading_large;

    public LoadingDialogCreator() {
        mCancelableOnTouchOutside = false;
        mDarkAroundWhenShow = false;
    }

    @Override
    protected int provideDialogRootView() {
        return R.layout.smart_show_loading;
    }


    @Override
    public ILoadingDialogCreator message(CharSequence msg) {
        mMsg = Utils.isEmpty(msg) ? "加载中..." : msg;
        return this;
    }

    @Override
    public ILoadingDialogCreator large() {
        mLayoutRes = R.layout.smart_show_loading_large;
        return this;
    }


    @Override
    public ILoadingDialogCreator middle() {
        mLayoutRes = R.layout.smart_show_loading_middle;
        return this;
    }

    @Override
    public ILoadingDialogCreator small() {
        mLayoutRes = R.layout.smart_show_loading_small;
        return this;
    }

    @Override
    protected void initView() {
        super.initView();
        Utils.inflate(mLayoutRes, (ViewGroup) mDialogRootView, true);
        TextView msgView = mDialogRootView.findViewById(R.id.smart_show_loading_message_view);
        if (msgView != null) {
            msgView.setText(mMsg);
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
