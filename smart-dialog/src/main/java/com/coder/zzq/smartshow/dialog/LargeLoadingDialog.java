package com.coder.zzq.smartshow.dialog;

public class LargeLoadingDialog extends LoadingDialog<LargeLoadingDialog> {
    @Override
    protected int provideContentLayout() {
        return mWithoutMsgTip ? R.layout.smart_show_loading_large_no_tip : R.layout.smart_show_loading_large;
    }
}
