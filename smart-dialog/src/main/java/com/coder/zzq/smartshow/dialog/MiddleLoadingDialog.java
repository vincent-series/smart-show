package com.coder.zzq.smartshow.dialog;

public class MiddleLoadingDialog extends LoadingDialog<MiddleLoadingDialog> {
    @Override
    protected int provideContentLayout() {
        return mWithoutMsgTip ? R.layout.smart_show_loading_middle_no_tip : R.layout.smart_show_loading_middle;
    }
}
