package com.coder.zzq.smartshow.dialog;

import android.app.Activity;

import com.coder.zzq.smartshow.core.lifecycle.IDialogCallback;
import com.coder.zzq.smartshow.dialog.cache.DialogReuseContainer;

public class DialogCallback implements IDialogCallback {
    @Override
    public void recycleOnDestroy(Activity activity) {
        DialogReuseContainer.clearAllCachedDialogOfActivity(activity);
    }
}
