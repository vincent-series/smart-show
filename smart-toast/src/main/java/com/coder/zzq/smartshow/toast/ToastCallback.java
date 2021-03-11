package com.coder.zzq.smartshow.toast;


import android.app.Activity;

import com.coder.zzq.smartshow.core.lifecycle.IToastCallback;
import com.coder.zzq.smartshow.toast.compact.VirtualToastManager;
import com.coder.zzq.smartshow.toast.schedule.ToastScheduler;

final class ToastCallback implements IToastCallback {
    @Override
    public void dismissOnLeave() {
        ToastScheduler.get().cancelCurrentIfNecessary();
    }

    @Override
    public void recycleOnDestroy(Activity activity) {
        VirtualToastManager.destroy(activity);
    }
}
