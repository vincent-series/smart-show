package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.app.Dialog;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.creator.type.ILoadingDialogCreator;

class LoadingDialogCreator extends NormalDialogCreator<ILoadingDialogCreator> implements ILoadingDialogCreator {
    private CharSequence mMsg = "加载中";
    public static final int BOX_SIZE_LARGE = 0;
    public static final int BOX_SIZE_MIDDLE = 1;
    public static final int BOX_SIZE_SMALL = 2;
    private int mBoxSize = BOX_SIZE_LARGE;
    private boolean mWithNoMsgTip;
    @DrawableRes
    private int mDrawablRes = R.drawable.smart_show_loading_img;
    @LayoutRes
    private int mLayoutRes = R.layout.smart_show_loading_large;

    public LoadingDialogCreator() {
        mCancelableOnTouchOutside = false;
        mDarkAroundWhenShow = false;
        mWindowBackground = 0;
    }

    @Override
    protected int provideDialogRootView() {
        return R.layout.smart_show_loading;
    }


    @Override
    public ILoadingDialogCreator message(CharSequence msg) {
        mMsg = Utils.isEmpty(msg) ? "加载中" : msg;
        return this;
    }

    @Override
    public ILoadingDialogCreator withNoMsgTip() {
        mWithNoMsgTip = true;
        return this;
    }

    @Override
    public ILoadingDialogCreator large() {
        mBoxSize = BOX_SIZE_LARGE;
        return this;
    }


    @Override
    public ILoadingDialogCreator middle() {
        mBoxSize = BOX_SIZE_MIDDLE;
        return this;
    }

    @Override
    public ILoadingDialogCreator small() {
        mBoxSize = BOX_SIZE_SMALL;
        withNoMsgTip();
        mLayoutRes = R.layout.smart_show_loading_small;
        return this;
    }

    @Override
    protected void initView(Dialog dialog, View dialogRootView) {
        super.initView(dialog, dialogRootView);
        switch (mBoxSize) {
            case BOX_SIZE_LARGE:
                mLayoutRes = mWithNoMsgTip ? R.layout.smart_show_loading_large_no_tip : R.layout.smart_show_loading_large;
                break;
            case BOX_SIZE_MIDDLE:
                mLayoutRes = mWithNoMsgTip ? R.layout.smart_show_loading_middle_no_tip : R.layout.smart_show_loading_middle;
                break;
        }
        Utils.inflate(mLayoutRes, (ViewGroup) dialogRootView, true);
        TextView msgView = dialogRootView.findViewById(R.id.smart_show_loading_message_view);
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
