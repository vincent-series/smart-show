package com.coder.zzq.smartshowdemo;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.toast.IPlainToastSetting;
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
        SmartToast.globalSetting()
                .dismissOnLeave(true);
        SmartToast.typeSetting()
                .themeColorRes(R.color.colorPrimary);
        SmartToast.plainSetting()
                .customView(new IPlainToastSetting.CustomViewCallback() {
                    @Override
                    public View createToastView(LayoutInflater inflater) {
                        View view = inflater.inflate(R.layout.custom_toast, null);
                        return view;
                    }
                });
        SmartSnackbar.setting()
                .backgroundColorRes(R.color.colorPrimary)
                .dismissOnLeave(true);

        SmartTopbar.setting()
                .backgroundColorRes(R.color.colorPrimary)
                .msgTextColorRes(R.color.white)
                .actionColorRes(R.color.colorAccent)
                .dismissOnLeave(true);

    }
}
