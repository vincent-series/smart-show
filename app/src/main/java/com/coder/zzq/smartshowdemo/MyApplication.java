package com.coder.zzq.smartshowdemo;

import android.app.Application;
import android.graphics.Color;

import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.basebar.IBarSetting;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.smartshow.topbar.SmartTopBar;


/**
 * Created by 朱志强 on 2017/11/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SmartShow.init(this);

        SmartToast.setting()
                .typeInfoToastThemeColorRes(R.color.colorPrimary)
                .dismissOnLeave(true);


        SmartTopBar.setting()
                .dismissOnLeave(true)
                .backgroundColor(Color.WHITE)
                .msgTextColor(Color.DKGRAY)
                .actionColor(Color.RED)
                .darkStatusBarTextAndIcon()
                .defaultActionTextForIndefinite("ok");

        SmartSnackbar.setting()
                .dismissOnLeave(true)
                .backgroundColorRes(R.color.colorPrimary);


    }
}
