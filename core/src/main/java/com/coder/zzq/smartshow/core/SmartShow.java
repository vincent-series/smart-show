package com.coder.zzq.smartshow.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.coder.zzq.smartshow.core.lifecycle.ActivityLifecycleCallback;
import com.coder.zzq.smartshow.core.lifecycle.ActivityStack;
import com.coder.zzq.smartshow.core.lifecycle.IBarCallback;
import com.coder.zzq.smartshow.core.lifecycle.IToastCallback;

/**
 * Created by 朱志强 on 2018/08/19.
 */
public final class SmartShow {

    private static Application sApplication;
    private static IToastCallback sToastCallback;
    private static IBarCallback sSnackbarCallback;
    private static IBarCallback sTopbarCallback;

    public static void init(Application application) {
        if (application == null) {
            throw new NullPointerException("初始化SmartShow的application不可为null！");
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
                if (sToastCallback != null) {
                    sToastCallback.dismissOnLeave();
                }
            }


            @Override
            public void onActivityStopped(Activity activity) {
                super.onActivityStopped(activity);
                ActivityStack.pop(activity);
                if (sSnackbarCallback != null) {
                    sSnackbarCallback.dismissOnLeave(activity);
                }
                if (sTopbarCallback != null) {
                    sTopbarCallback.dismissOnLeave(activity);
                }
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                if (sSnackbarCallback != null) {
                    sSnackbarCallback.recycleOnDestroy(activity);
                }
                if (sTopbarCallback != null) {
                    sTopbarCallback.recycleOnDestroy(activity);
                }
            }

        });

    }

    public static Application getContext() {
        if (sApplication == null) {
            throw new NullPointerException("尚未初始化SmartShow:SmartShow.init(applicationContext)");
        }
        return sApplication;
    }

    public static void setToastCallback(IToastCallback toastCallback) {
        sToastCallback = toastCallback;
    }

    public static void setSnackbarCallback(IBarCallback snackbarCallback) {
        sSnackbarCallback = snackbarCallback;
    }

    public static void setTopbarCallback(IBarCallback topbarCallback) {
        sTopbarCallback = topbarCallback;
    }

}
