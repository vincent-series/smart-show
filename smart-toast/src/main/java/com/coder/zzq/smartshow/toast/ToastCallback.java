package com.coder.zzq.smartshow.toast;


import android.app.Activity;

import com.coder.zzq.smartshow.core.lifecycle.IToastCallback;

class ToastCallback implements IToastCallback {
    @Override
    public void dismissOnLeave() {
        if (ToastDelegate.hasCreated() && ToastDelegate.get().isDismissOnLeave()) {
            ToastDelegate.get().dismiss();
        }
    }

    @Override
    public void recycleOnDestroy(Activity activity) {
        if (VirtualToastManager.hasCreated()){
            VirtualToastManager.get().destroy(activity);
        }
    }
}
