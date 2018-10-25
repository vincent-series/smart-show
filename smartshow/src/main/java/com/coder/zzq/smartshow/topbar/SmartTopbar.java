package com.coder.zzq.smartshow.topbar;


import android.app.Activity;

import com.coder.zzq.smartshow.basebar.IBarShow;

public final class SmartTopbar {

    private SmartTopbar() {

    }


    public static IBarShow get(Activity activity) {
        return TopbarDelegate.get().nestedDecorView(activity);
    }

    public static boolean isShowing() {
        return TopbarDelegate.get().isShowing();
    }

    public static void dismiss() {
        TopbarDelegate.get().dismiss();
    }


    public static ITopbarSetting setting() {
        return TopbarDelegate.get().createBarSetting();
    }

}
