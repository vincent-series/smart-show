package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.util.SparseArray;

import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.toolkit.log.EasyLogger;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

class DialogRecycleManager {

    private static SparseArray<Set<SmartDialog>> sDialogContainer;

    public static void recycleDialogsOfActivity(Activity activity) {
        if (activity == null || isEmpty()) {
            return;
        }

        Set<SmartDialog> dialogs = getDialogContainer().get(activity.hashCode());

        if (dialogs == null || dialogs.isEmpty()) {
            return;
        }

        Iterator<SmartDialog> iterator = dialogs.iterator();
        while (iterator.hasNext()) {
            SmartDialog dialog = iterator.next();
            dialog.recycle();
            iterator.remove();
            EasyLogger.d("the dialog:" + Utils.getObjectDesc(dialog) + "has removed from the dialog collection of the activity:" + activity);
        }

        getDialogContainer().remove(activity.hashCode());
        EasyLogger.d("the dialog collection of the activity(" + Utils.getObjectDesc(activity) + ")is removed.");
        EasyLogger.d("the collections of the activity(" + Utils.getObjectDesc(activity) + ")is now:" + getDialogContainer().size());
    }


    public static void putDialog(SmartDialog dialog, Activity activity) {
        if (dialog == null || activity == null) {
            return;
        }

        Set<SmartDialog> dialogs = getDialogContainer().get(activity.hashCode());
        if (dialogs == null) {
            dialogs = new HashSet<>();
            getDialogContainer().put(activity.hashCode(), dialogs);
        }
        dialogs.add(dialog);
    }

    public static boolean isEmpty() {
        return sDialogContainer == null || sDialogContainer.size() == 0;
    }


    private static SparseArray<Set<SmartDialog>> getDialogContainer() {
        if (sDialogContainer == null) {
            sDialogContainer = new SparseArray<>();
        }
        return sDialogContainer;
    }

}
