package com.coder.zzq.smartshow.toast;

public class GlobalSetting implements IGlobalSetting {
    private boolean mDismissOnLeave;

    @Override
    public IGlobalSetting dismissOnLeave(boolean b) {
        mDismissOnLeave = b;
        return this;
    }

    public boolean isDismissOnLeave() {
        return mDismissOnLeave;
    }
}
