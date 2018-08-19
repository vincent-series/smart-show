package com.coder.zzq.smartshowdemo;

import android.app.Application;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.SmartSnackbar;
import com.coder.zzq.smartshow.SmartToast;
import com.coder.zzq.smartshow.toast.ProcessViewCallback;


/**
 * Created by 朱志强 on 2017/11/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SmartShow.init(this);
        SmartShow.toastSetting()
                .dismissOnLeave(false)
                .backgroundColorRes(R.color.colorPrimary)
                .textColorRes(R.color.colorAccent)
                .textSizeSp(18)
                .textBold(true);
        SmartShow.snackbarSetting()
                .backgroundColorRes(R.color.colorPrimary)
                .actionColorRes(R.color.colorAccent)
                .msgTextSizeSp(18)
                .actionSizeSp(18);
    }
}
