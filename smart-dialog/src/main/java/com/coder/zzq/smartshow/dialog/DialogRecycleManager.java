package com.coder.zzq.smartshow.dialog;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

class DialogRecycleManager {

    private static List<SmartDialog> sDialogSet;

    public static void recycleDialogs(Activity activity) {

        if (isEmpty()) {
            return;
        }

        for (int index = sDialogSet.size() - 1; index >= 0; index--) {
            SmartDialog dialog = sDialogSet.get(index);
            if (dialog.recycle(activity)) {
                sDialogSet.remove(index);
            }
        }
    }


    public static void saveDialog(SmartDialog dialog) {
        if (dialog == null) {
            return;
        }
        getDialogSet().add(dialog);
    }

    public static boolean isEmpty() {
        return sDialogSet == null || sDialogSet.size() == 0;
    }


    private static List<SmartDialog> getDialogSet() {
        if (sDialogSet == null) {
            sDialogSet = new ArrayList<>();
        }
        return sDialogSet;
    }

}
