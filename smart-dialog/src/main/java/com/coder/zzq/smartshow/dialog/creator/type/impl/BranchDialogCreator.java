package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.support.annotation.LayoutRes;
import android.widget.FrameLayout;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.DialogBtnClickListener;
import com.coder.zzq.smartshow.dialog.R;

abstract class BranchDialogCreator<B> extends NormalDialogCreator<B> {
    @Override
    protected int provideDialogRootView() {
        return R.layout.smart_show_branch_dialog;
    }

    @Override
    protected void initView() {
        super.initView();
        FrameLayout headerViewWrapper = mDialogRootView.findViewById(R.id.smart_show_dialog_header_wrapper);
        FrameLayout bodyViewWrapper = mDialogRootView.findViewById(R.id.smart_show_dialog_body_wrapper);
        FrameLayout footerViewWrapper = mDialogRootView.findViewById(R.id.smart_show_dialog_foot_wrapper);
        Utils.inflate(provideHeaderView(), headerViewWrapper, true);
        Utils.inflate(provideBodyView(), bodyViewWrapper, true);
        Utils.inflate(provideFooterView(), footerViewWrapper, true);
        initHeader(headerViewWrapper);
        initBody(bodyViewWrapper);
        initFooter(footerViewWrapper);
    }

    protected void initHeader(FrameLayout headerViewWrapper) {

    }

    @LayoutRes
    protected abstract int provideHeaderView();

    protected void initBody(FrameLayout bodyViewWrapper) {

    }

    @LayoutRes
    protected abstract int provideBodyView();

    protected void initFooter(FrameLayout footerViewWrapper) {

    }

    @LayoutRes
    protected abstract int provideFooterView();

    protected void onBtnClick(DialogBtnClickListener clickListener, @DialogBtnClickListener.DialogBtn int which, Object data) {
        if (clickListener == null) {
            mDialog.dismiss();
        } else {
            clickListener.onBtnClick(mDialog, which, data);
        }
    }
}
