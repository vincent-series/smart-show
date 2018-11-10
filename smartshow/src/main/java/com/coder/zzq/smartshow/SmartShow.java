package com.coder.zzq.smartshow;

import android.app.Activity;
import android.app.Application;

import com.coder.zzq.smartshow.lifecycle.ActivityLifecycleCallback;
import com.coder.zzq.smartshow.snackbar.SnackbarDeligate;
import com.coder.zzq.smartshow.toast.ToastDelegate;
import com.coder.zzq.smartshow.topbar.TopbarDelegate;

/**
 * Created by 朱志强 on 2018/08/19.
 */
public final class SmartShow {

    private static Application sApplication;

    public static void init(Application application) {
        if (application == null) {
            throw new NullPointerException("初始化SmartShow的application不可为null！");
        }
        sApplication = application;
        sApplication.registerActivityLifecycleCallbacks(new ActivityLifecycleCallback() {

            @Override
            public void onActivityPaused(Activity activity) {
                super.onActivityPaused(activity);
                if (ToastDelegate.hasCreated() && ToastDelegate.get().isDismissOnLeave()) {
                    ToastDelegate.get().dismiss();
                }
            }


            @Override
            public void onActivityStopped(Activity activity) {
                super.onActivityStopped(activity);
                if (SnackbarDeligate.hasCreated() && SnackbarDeligate.get().isDismissOnLeave()) {
                    SnackbarDeligate.get().onLeave(activity);
                }
                if (TopbarDelegate.hasCreated() && TopbarDelegate.get().isDismissOnLeave()) {
                    TopbarDelegate.get().onLeave(activity);
                }
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                if (SnackbarDeligate.hasCreated()) {
                    SnackbarDeligate.get().destroy(activity);
                }

                if (TopbarDelegate.hasCreated()) {
                    TopbarDelegate.get().destroy(activity);
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

}
