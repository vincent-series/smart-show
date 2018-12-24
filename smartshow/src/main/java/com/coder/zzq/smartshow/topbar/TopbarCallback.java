package com.coder.zzq.smartshow.topbar;

import android.app.Activity;

import com.coder.zzq.smartshow.lifecycle.IBarCallback;

public class TopbarCallback implements IBarCallback {
    @Override
    public void dismissOnLeave(Activity activity) {
        if (TopbarDelegate.hasCreated() && TopbarDelegate.get().isDismissOnLeave()) {
            TopbarDelegate.get().onLeave(activity);
        }
    }

    @Override
    public void recycleOnDestroy(Activity activity) {
        if (TopbarDelegate.hasCreated()) {
            TopbarDelegate.get().destroy(activity);
        }
    }
}
