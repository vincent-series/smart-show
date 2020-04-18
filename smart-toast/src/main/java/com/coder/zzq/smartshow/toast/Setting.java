package com.coder.zzq.smartshow.toast;

final class Setting implements ISetting {
    private boolean mDismissOnLeave;

    @Override
    public ISetting dismissOnLeave(boolean b) {
        mDismissOnLeave = b;
        return this;
    }

    public boolean isDismissOnLeave() {
        return mDismissOnLeave;
    }
}
