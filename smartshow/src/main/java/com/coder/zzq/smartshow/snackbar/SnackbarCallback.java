package com.coder.zzq.smartshow.snackbar;

import android.app.Activity;

import com.coder.zzq.smartshow.lifecycle.IBarCallback;

public class SnackbarCallback implements IBarCallback {
    @Override
    public void dismissOnLeave(Activity activity) {
        if (SnackbarDeligate.hasCreated() && SnackbarDeligate.get().isDismissOnLeave()) {
            SnackbarDeligate.get().onLeave(activity);
        }
    }

    @Override
    public void recycleOnDestroy(Activity activity) {
        if (SnackbarDeligate.hasCreated()) {
            SnackbarDeligate.get().destroy(activity);
        }
    }
}
