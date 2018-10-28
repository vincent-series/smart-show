package com.coder.zzq.smartshowdemo;

import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.NinePatchDrawable;
import android.view.View;
import android.widget.TextView;

import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.toast.IProcessToastCallback;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.smartshow.topbar.SmartTopbar;


/**
 * Created by 朱志强 on 2017/11/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SmartShow.init(this);

        SmartToast.setting().dismissOnLeave(true);

        SmartSnackbar.setting()
                .backgroundColorRes(R.color.colorPrimary)
                .msgTextColorRes(R.color.white)
                .actionColorRes(R.color.colorAccent)
                .dismissOnLeave(true);

        SmartTopbar.setting()
                .msgTextColorRes(R.color.white)
                .actionColorRes(R.color.colorAccent)
                .dismissOnLeave(true);

    }
}
