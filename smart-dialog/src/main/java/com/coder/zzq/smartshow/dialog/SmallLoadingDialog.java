package com.coder.zzq.smartshow.dialog;

import android.view.ViewGroup;

public class SmallLoadingDialog extends NormalDialog<SmallLoadingDialog> {

    public SmallLoadingDialog() {
        mCancelableOnTouchOutside = false;
        mDarkAroundWhenShow = false;
        mWindowBackground = 0;
    }

    @Override
    protected int provideContentLayout() {
        return R.layout.smart_show_loading_small;
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
