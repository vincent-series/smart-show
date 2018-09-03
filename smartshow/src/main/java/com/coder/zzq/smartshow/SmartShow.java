package com.coder.zzq.smartshow;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.coder.zzq.smartshow.lifecycle.ActivityLifecycleCallback;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.snackbar.SnackbarSetting;
import com.coder.zzq.smartshow.toast.IToastSetting;
import com.coder.zzq.smartshow.toast.SmartToast;

/**
 * Created by 朱志强 on 2018/08/19.
 */
public final class SmartShow {

    private static Application sApplication;

    public static void init(Application application) {
        if (application == null){
            throw new NullPointerException("初始化SmartShow的context不可为null！");
        }
        sApplication = application;
        sApplication.registerActivityLifecycleCallbacks(new ActivityLifecycleCallback() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                ActivityStack.push(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                super.onActivityPaused(activity);
                setupDismissOnLeave();
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                ActivityStack.pop(activity);
                SmartSnackbar.destroy(activity);
            }
        });
        SmartToast.init(getContext());
        SmartSnackbar.init(getContext());
    }

    private static void setupDismissOnLeave() {
        if (SmartToast.isDismissOnLeave()) {
            SmartToast.dismiss();
        }
        if (SmartSnackbar.isDismissOnLeave()) {
            SmartSnackbar.dismiss();
        }
    }


    public static IToastSetting toastSetting() {
        return SmartToast.init(getContext());
    }


    public static SnackbarSetting snackbarSetting() {
        return SmartSnackbar.init(getContext());
    }


    public static Application getContext() {
        if (sApplication == null) {
            throw new NullPointerException("尚未初始化SmartShow:SmartShow.init(applicationContext)");
        }
        return sApplication;
    }
}
