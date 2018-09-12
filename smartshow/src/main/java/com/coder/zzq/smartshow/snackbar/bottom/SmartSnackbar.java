package com.coder.zzq.smartshow.snackbar.bottom;

import android.support.design.widget.CoordinatorLayout;

import com.coder.zzq.smartshow.snackbar.base.IBarShow;

/**
 * Created by 朱志强 on 2017/11/15.
 */

public final class SmartSnackbar{

    private SmartSnackbar() {

    }


    public static IBarShow get() {
        return SmartSnackbarDeligate.get().getPlain();
    }

    public static IBarShow get(CoordinatorLayout view) {
        return SmartSnackbarDeligate.get().getWithCoordinatorLayout(view);
    }


    public static boolean isShowing() {
        return SmartSnackbarDeligate.get().isShowing();
    }

    public static void dismiss() {
        SmartSnackbarDeligate.get().dismiss();
    }

    public static ISnackbarSetting setting(){
        return SmartSnackbarDeligate.get().barSetting();
    }

}
