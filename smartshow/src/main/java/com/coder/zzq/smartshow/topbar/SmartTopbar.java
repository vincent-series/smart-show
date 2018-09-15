package com.coder.zzq.smartshow.topbar;


import com.coder.zzq.smartshow.basebar.IBarShow;

public final class SmartTopbar {

    private SmartTopbar() {

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
        return SmartTopbarDelegate.get().createBarSetting();
    }

}
