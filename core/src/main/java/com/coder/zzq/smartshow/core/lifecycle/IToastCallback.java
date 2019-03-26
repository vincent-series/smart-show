package com.coder.zzq.smartshow.core.lifecycle;

import android.app.Activity;

public interface IToastCallback {
    void dismissOnLeave();

    void recycleOnDestroy(Activity activity);
}
