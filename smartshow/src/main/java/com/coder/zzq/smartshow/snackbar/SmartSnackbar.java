package com.coder.zzq.smartshow.snackbar;


import android.support.design.widget.CoordinatorLayout;

import com.coder.zzq.smartshow.basebar.IBarSetting;
import com.coder.zzq.smartshow.basebar.IBarShow;

/**
 * Created by 朱志强 on 2017/11/15.
 */

public final class SmartSnackbar{

    private SmartSnackbar() {

    }


    public static IBarShow get() {

        return SmartSnackbarDeligate.get().nestedContentView();
    }

    public static IBarShow get(CoordinatorLayout view) {
        return SmartSnackbarDeligate.get().nestedCoordinatorLayout(view);
    }


    public static boolean isShowing() {
        return SmartSnackbarDeligate.get().isShowing();
    }

    public static void dismiss() {
        SmartSnackbarDeligate.get().dismiss();
    }

    public static IBarSetting setting(){
        return SmartSnackbarDeligate.get().barSetting();
    }

}
