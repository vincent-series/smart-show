package com.coder.zzq.smartshowdemo;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.smartshow.topbar.SmartTopbar;
import com.coder.zzq.toolkit.Toolkit;


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
        Toolkit.setEnablePrintLog(true);

        SmartSnackbar.setting()
                .backgroundColorRes(R.color.colorPrimary)
                .dismissOnLeave(true);

        SmartTopbar.setting()
                .msgTextColorRes(R.color.white)
                .actionColorRes(R.color.colorAccent)
                .dismissOnLeave(true);

    }
}
