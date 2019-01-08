package com.coder.zzq.smartshow.dialog.cache;

import android.app.Activity;
import android.app.Dialog;
import android.util.SparseArray;

public final class DialogReuseContainer {
    private static SparseArray<SparseArray<Dialog>> sCachedDialogs;

    public static Dialog retrieveCachedDialog(Activity activity, int bizTag) {
        return isEmpty() ? null : getCachedDialogs().get(activity.hashCode()).get(bizTag);
    }


    private static SparseArray<SparseArray<Dialog>> getCachedDialogs() {
        if (sCachedDialogs == null) {
            sCachedDialogs = new SparseArray<>();
        }
        return sCachedDialogs;
    }

    public static boolean isEmpty() {
        return sCachedDialogs == null || getCachedDialogs().size() == 0;
    }

    public static void clearAllCachedDialogOfActivity(Activity activity) {
        if (!isEmpty()) {
            getCachedDialogs().get(activity.hashCode()).clear();
            getCachedDialogs().remove(activity.hashCode());
        }
    }
}
