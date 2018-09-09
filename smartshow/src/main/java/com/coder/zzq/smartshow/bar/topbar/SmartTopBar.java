package com.coder.zzq.smartshow.bar.topbar;

import android.support.design.widget.CoordinatorLayout;

import com.coder.zzq.smartshow.bar.base.IBarShow;

public final class SmartTopBar {

    private SmartTopBar() {

    }


    public static IBarShow get() {
        return SmartTopbarDelegate.get().getPlain();
    }

    public static IBarShow get(CoordinatorLayout view) {
        return SmartTopbarDelegate.get().getWithCoordinatorLayout(view);
    }


    public static boolean isShowing() {
        return SmartTopbarDelegate.get().isShowing();
    }

    public static void dismiss() {
        SmartTopbarDelegate.get().dismiss();
    }


    public static ITopbarSetting topbarSetting() {
        return SmartTopbarDelegate.get().barSetting();
    }

}
