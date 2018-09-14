package com.coder.zzq.smartshow;

import android.support.annotation.RestrictTo;

import com.coder.zzq.smartshow.snackbar.SnackbarSettingImpl;
import com.coder.zzq.smartshow.toast.ToastSettingImpl;
import com.coder.zzq.smartshow.topbar.TopbarSettingImpl;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class Config {
    private static ToastSettingImpl sToastSetting;
    private static SnackbarSettingImpl sSnackbarSetting;
    private static TopbarSettingImpl sTopbarSetting;

    public static ToastSettingImpl createToastSetting() {
        if (sToastSetting == null){
            sToastSetting = new ToastSettingImpl();
        }
        return sToastSetting;
    }

    public static boolean hasToastSetting(){
        return sToastSetting != null;
    }

    public static ToastSettingImpl getToastSetting(){
        return sToastSetting;
    }

    public static SnackbarSettingImpl buildSmartSnackbarSetting() {
        if (sSnackbarSetting == null) {
            sSnackbarSetting = new SnackbarSettingImpl();
        }
        return sSnackbarSetting;
    }

    public static boolean hasSnackbarSetting(){
        return sSnackbarSetting != null;
    }

    public static SnackbarSettingImpl getSnackbarSetting(){
        return sSnackbarSetting;
    }


    public static TopbarSettingImpl buildSmartTopBarSetting() {
        if (sTopbarSetting == null){
            sTopbarSetting = new TopbarSettingImpl();
        }
        return sTopbarSetting;
    }

    public static boolean hasTopbarSetting(){
        return sTopbarSetting != null;
    }

    public static TopbarSettingImpl getTopbarSetting(){
        return sTopbarSetting;
    }


}
