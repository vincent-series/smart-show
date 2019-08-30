package com.coder.zzq.smartshow.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.coder.zzq.smartshow.core.lifecycle.IBarCallback;
import com.coder.zzq.smartshow.core.lifecycle.IDialogCallback;
import com.coder.zzq.smartshow.core.lifecycle.IToastCallback;
import com.coder.zzq.smartshow.core.lifecycle.ITopbarCallback;
import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.toolkit.lifecycle.ActivityCallback;

/**
 * Created by 朱志强 on 2018/08/19.
 */
public final class SmartShow {

    private static IToastCallback sToastCallback;
    private static IBarCallback sSnackbarCallback;
    private static ITopbarCallback sTopbarCallback;
    private static IDialogCallback sDialogCallback;

    public static void init(Application application) {
        if (application == null) {
            throw new NullPointerException("初始化SmartShow的application不可为null！");
        }

        Toolkit.init(application);

        application.registerActivityLifecycleCallbacks(new ActivityCallback() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                if (sTopbarCallback != null) {
                    sTopbarCallback.adjustTopbarContainerIfNecessary(activity);
                }
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
                if (sToastCallback != null) {
                    sToastCallback.recycleOnDestroy(activity);
                }
                if (sSnackbarCallback != null) {
                    sSnackbarCallback.recycleOnDestroy(activity);
                }
                if (sTopbarCallback != null) {
                    sTopbarCallback.recycleOnDestroy(activity);
                }
                if (sDialogCallback != null) {
                    sDialogCallback.recycleOnDestroy(activity);
                }
            }

        });

    }

    public static void setToastCallback(IToastCallback toastCallback) {
        sToastCallback = toastCallback;
    }

    public static void setSnackbarCallback(IBarCallback snackbarCallback) {
        sSnackbarCallback = snackbarCallback;
    }

    public static void setTopbarCallback(ITopbarCallback topbarCallback) {
        sTopbarCallback = topbarCallback;
    }

    public static void setDialogCallback(IDialogCallback dialogCallback) {
        sDialogCallback = dialogCallback;
    }

}
