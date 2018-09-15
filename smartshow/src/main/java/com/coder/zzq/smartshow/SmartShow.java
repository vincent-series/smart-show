package com.coder.zzq.smartshow;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.coder.zzq.smartshow.lifecycle.ActivityLifecycleCallback;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;
import com.coder.zzq.smartshow.snackbar.SmartSnackbarDeligate;
import com.coder.zzq.smartshow.toast.SmartToastDelegate;
import com.coder.zzq.smartshow.topbar.SmartTopbarDelegate;

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
                if (SmartToastDelegate.hasCreated() && SmartToastDelegate.get().isDismissOnLeave()) {
                    SmartToastDelegate.get().dismiss();
                }
                if (SmartSnackbarDeligate.hasCreated() && SmartSnackbarDeligate.get().isDismissOnLeave()) {
                    SmartSnackbarDeligate.get().dismiss();
                }
                if (SmartTopbarDelegate.hasCreated() && SmartTopbarDelegate.get().isDismissOnLeave()) {
                    SmartTopbarDelegate.get().dismiss();
                }

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                super.onActivityDestroyed(activity);
                ActivityStack.pop(activity);


                if (SmartSnackbarDeligate.hasCreated()) {
                    SmartSnackbarDeligate.get().destroy(activity);
                }
                if (SmartTopbarDelegate.hasCreated()) {
                    SmartTopbarDelegate.get().destroy(activity);
                }

                if (ActivityStack.isEmpty()) {
                    SmartToastDelegate.destroyDelegate();
                    SmartSnackbarDeligate.destroyDelegate();
                    SmartTopbarDelegate.destroyDelegate();
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
