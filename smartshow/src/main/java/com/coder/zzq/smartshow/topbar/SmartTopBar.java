package com.coder.zzq.smartshow.topbar;


import com.coder.zzq.smartshow.basebar.IBarShow;

public final class SmartTopBar {

    private SmartTopBar() {

    }


    public static IBarShow get() {
        return SmartTopbarDelegate.get().nestedDecorView();
    }

    public static boolean isShowing() {
        return SmartTopbarDelegate.get().isShowing();
    }

    public static void dismiss() {
        SmartTopbarDelegate.get().dismiss();
    }


    public static ITopbarSetting setting() {
        return SmartTopbarDelegate.get().barSetting();
    }

}
