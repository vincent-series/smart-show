package com.coder.zzq.smartshow;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.coder.zzq.smartshow.lifecycle.ActivityLifecycleCallback;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;
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
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                ActivityStack.push(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                super.onActivityPaused(activity);
                if (ToastDelegate.hasCreated() && ToastDelegate.get().isDismissOnLeave()) {
                    ToastDelegate.get().dismiss();
                }
                if (SnackbarDeligate.hasCreated() && SnackbarDeligate.get().isDismissOnLeave()) {
                    SnackbarDeligate.get().dismiss();
                }
                if (TopbarDelegate.hasCreated() && TopbarDelegate.get().isDismissOnLeave()) {
                    TopbarDelegate.get().dismiss();
                }

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                if (activity == ActivityStack.getTop()) {
                    ActivityStack.pop();
                }

                if (SnackbarDeligate.hasCreated()) {
                    SnackbarDeligate.get().destroy(activity);
                }
                if (TopbarDelegate.hasCreated()) {
                    TopbarDelegate.get().destroy(activity);
                }

                if (ActivityStack.isEmpty()) {
                    ToastDelegate.destroyDelegate();
                    SnackbarDeligate.destroyDelegate();
                    TopbarDelegate.destroyDelegate();
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
