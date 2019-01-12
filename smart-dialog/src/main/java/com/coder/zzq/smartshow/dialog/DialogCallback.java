package com.coder.zzq.smartshow.dialog;

import android.app.Activity;

import com.coder.zzq.smartshow.core.lifecycle.IDialogCallback;
import com.coder.zzq.smartshow.dialog.cache.DialogCacheContainer;

public class DialogCallback implements IDialogCallback {
    @Override
    public void recycleOnDestroy(Activity activity) {
        DialogCacheContainer.clearAllCachedDialogOfActivity(activity);
    }
}
