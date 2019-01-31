package com.coder.zzq.smartshowdemo;

import android.app.Application;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.smartshow.topbar.SmartTopbar;


/**
 * Created by 朱志强 on 2017/11/12.
 */

public class MyApplication extends Application {
    public static MyApplication sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        SmartShow.init(this);

        SmartToast.setting()
//                .backgroundColorRes(R.color.colorPrimary)
                .dismissOnLeave(false);

        SmartSnackbar.setting()

                .backgroundColorRes(R.color.colorPrimary)
//                .msgTextColorRes(R.color.white)
//                .actionColorRes(R.color.colorAccent)
                .dismissOnLeave(true);

        SmartTopbar.setting()
                .backgroundColorRes(R.color.colorPrimary)
                .msgTextColorRes(R.color.white)
                .actionColorRes(R.color.colorAccent)
                .dismissOnLeave(true);

    }
}
