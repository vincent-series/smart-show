package com.coder.zzq.smartshow.dialog;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.widget.FrameLayout;

import com.coder.zzq.smartshow.core.Utils;

public abstract class BranchDialog<D extends NormalDialog> extends NormalDialog<D> {
    protected FrameLayout mHeaderViewWrapper;
    protected FrameLayout mBodyViewWrapper;
    protected FrameLayout mFooterViewWrapper;

    @Override
    protected int provideContentLayout() {
        return R.layout.smart_show_branch_dialog;
    }

    @Override
    protected void initView(AppCompatDialog dialog, View dialogContentView) {
        super.initView(dialog, dialogContentView);
        mHeaderViewWrapper = dialogContentView.findViewById(R.id.smart_show_dialog_header_wrapper);
        mBodyViewWrapper = dialogContentView.findViewById(R.id.smart_show_dialog_body_wrapper);
        mFooterViewWrapper = dialogContentView.findViewById(R.id.smart_show_dialog_foot_wrapper);

        inflateWrappedView(provideHeaderLayout(), mHeaderViewWrapper);
        inflateWrappedView(provideBodyLayout(), mBodyViewWrapper);
        inflateWrappedView(provideFooterLayout(), mFooterViewWrapper);

        initHeader(dialog, mHeaderViewWrapper);
        initBody(dialog, mBodyViewWrapper);
        initFooter(dialog, mFooterViewWrapper);
    }

    private void inflateWrappedView(@LayoutRes int layout, FrameLayout parent) {
        if (layout != 0) {
            Utils.inflate(layout, parent, true);
        }
    }

    @Override
    protected void applySetting(AppCompatDialog dialog) {
        super.applySetting(dialog);
        applyHeader(dialog);
        applyBody(dialog);
        applyFooter(dialog);
    }

    @LayoutRes
    protected abstract int provideHeaderLayout();

    protected void initHeader(AppCompatDialog dialog, FrameLayout headerViewWrapper) {

    }

    protected void applyHeader(AppCompatDialog dialog) {

    }

    @LayoutRes
    protected abstract int provideBodyLayout();

    protected void initBody(AppCompatDialog dialog, FrameLayout bodyViewWrapper) {

    }

    protected void applyBody(AppCompatDialog dialog) {

    }

    @LayoutRes
    protected abstract int provideFooterLayout();

    protected void initFooter(AppCompatDialog dialog, FrameLayout footerViewWrapper) {

    }

    protected void applyFooter(AppCompatDialog dialog) {

    }
}
