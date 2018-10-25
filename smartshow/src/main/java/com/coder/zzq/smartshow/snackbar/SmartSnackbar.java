package com.coder.zzq.smartshow.snackbar;


import android.app.Activity;
import android.support.design.widget.CoordinatorLayout;

import com.coder.zzq.smartshow.basebar.IBarShow;

/**
 * Created by 朱志强 on 2017/11/15.
 */

public final class SmartSnackbar {

    private SmartSnackbar() {

    }


    public static IBarShow get(Activity activity) {

        return SnackbarDeligate.get().nestedContentView(activity);
    }

    public static IBarShow get(CoordinatorLayout view) {
        return SnackbarDeligate.get().nestedCoordinatorLayout(view);
    }


    public static boolean isShowing() {
        return SnackbarDeligate.get().isShowing();
    }

    public static void dismiss() {
        SnackbarDeligate.get().dismiss();
    }

    public static ISnackbarSetting setting() {
        return SnackbarDeligate.get().createBarSetting();
    }

}
