package com.coder.zzq.smartshow.toast;


import android.app.Activity;

import com.coder.zzq.smartshow.core.lifecycle.IToastCallback;

final class ToastCallback implements IToastCallback {
    @Override
    public void dismissOnLeave() {
        if (ToastManager.hasCreated() && ToastManager.get().isDismissOnLeave() && !ToastManager.get().goForAnotherPage()) {
            ToastManager.get().dismiss();
        }
    }

    @Override
    public void recycleOnDestroy(Activity activity) {
        if (VirtualToastManager.hasCreated()) {
            VirtualToastManager.get().destroy(activity);
        }
    }
}
