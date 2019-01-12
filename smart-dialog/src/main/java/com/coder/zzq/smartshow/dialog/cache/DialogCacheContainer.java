package com.coder.zzq.smartshow.dialog.cache;

import android.app.Activity;
import android.app.Dialog;
import android.util.SparseArray;

public final class DialogCacheContainer {
    private static SparseArray<SparseArray<Dialog>> sCacheContainer;

    public static Dialog retrieveCachedDialog(Activity activity, int bizTag) {
        return isEmpty() ? null : getCacheContainer().get(activity.hashCode()).get(bizTag);
    }


    private static SparseArray<SparseArray<Dialog>> getCacheContainer() {
        if (sCacheContainer == null) {
            sCacheContainer = new SparseArray<>();
        }
        return sCacheContainer;
    }

    public static boolean isEmpty() {
        return sCacheContainer == null || getCacheContainer().size() == 0;
    }

    public static void clearAllCachedDialogOfActivity(Activity activity) {
        if (!isEmpty()) {
            if (getCacheContainer().get(activity.hashCode()) != null){
                getCacheContainer().get(activity.hashCode()).clear();
                getCacheContainer().remove(activity.hashCode());
            }
        }
    }

    private static SparseArray<Dialog> getCacheArrayOfActivity(Activity activity) {
        SparseArray<Dialog> cache = getCacheContainer().get(activity.hashCode());
        if (cache == null) {
            cache = new SparseArray<>();
            getCacheContainer().put(activity.hashCode(), cache);
        }

        return cache;
    }

    public static void cache(Dialog dialog, Activity activity, int tag) {
        getCacheArrayOfActivity(activity).put(tag, dialog);
    }
}
