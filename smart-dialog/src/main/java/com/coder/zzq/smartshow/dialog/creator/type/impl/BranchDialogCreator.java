package com.coder.zzq.smartshow.dialog.creator.type.impl;

import android.app.Dialog;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.FrameLayout;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;

abstract class BranchDialogCreator<B> extends NormalDialogCreator<B> {
    @Override
    protected int provideDialogRootView() {
        return R.layout.smart_show_branch_dialog;
    }

    @Override
    protected void initView(Dialog dialog, View dialogRootView) {
        super.initView(dialog, dialogRootView);
        FrameLayout headerViewWrapper = dialogRootView.findViewById(R.id.smart_show_dialog_header_wrapper);
        FrameLayout bodyViewWrapper = dialogRootView.findViewById(R.id.smart_show_dialog_body_wrapper);
        FrameLayout footerViewWrapper = dialogRootView.findViewById(R.id.smart_show_dialog_foot_wrapper);
        Utils.inflate(provideHeaderView(), headerViewWrapper, true);
        Utils.inflate(provideBodyView(), bodyViewWrapper, true);
        Utils.inflate(provideFooterView(), footerViewWrapper, true);
        initHeader(dialog, headerViewWrapper);
        initBody(dialog, bodyViewWrapper);
        initFooter(dialog, footerViewWrapper);
    }

    protected void initHeader(Dialog dialog, FrameLayout headerViewWrapper) {

    }

    @LayoutRes
    protected abstract int provideHeaderView();

    protected void initBody(Dialog dialog, FrameLayout bodyViewWrapper) {

    }

    @LayoutRes
    protected abstract int provideBodyView();

    protected void initFooter(Dialog dialog, FrameLayout footerViewWrapper) {

    }

    @LayoutRes
    protected abstract int provideFooterView();

}
