package com.coder.zzq.smartshow.dialog;

import android.app.Activity;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.core.lifecycle.IDialogCallback;

public class DialogCallback implements IDialogCallback {
    @Override
    public void recycleOnDestroy(Activity activity) {
        EasyLogger.d("start recycling dialog of activity: " + Utils.getObjectDesc(activity));
        DialogRecycleManager.recycleDialogsOfActivity(activity);
        EasyLogger.d("complete recycling dialog of activity: " + Utils.getObjectDesc(activity));
    }
}
