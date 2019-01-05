package com.coder.zzq.smartshow.toast;


import com.coder.zzq.smartshow.core.lifecycle.IToastCallback;

public class ToastCallback implements IToastCallback {
    @Override
    public void dismissOnLeave() {
        if (ToastDelegate.hasCreated() && ToastDelegate.get().isDismissOnLeave()) {
            ToastDelegate.get().dismiss();
        }
    }
}
