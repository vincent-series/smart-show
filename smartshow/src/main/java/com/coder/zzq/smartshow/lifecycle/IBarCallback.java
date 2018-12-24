package com.coder.zzq.smartshow.lifecycle;

import android.app.Activity;

public interface IBarCallback {
    void dismissOnLeave(Activity activity);

    void recycleOnDestroy(Activity activity);
}
