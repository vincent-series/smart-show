package com.coder.zzq.smartshowdemo;

import android.app.Application;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.basebar.IBarSetting;
import com.coder.zzq.smartshow.basebar.IProcessBarCallback;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.snackbar.SmartSnackbarDeligate;
import com.coder.zzq.smartshow.toast.IProcessToastCallback;
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
        SmartSnackbar.get().show("你好");
        SmartTopBar.get().show("你好");
        SmartSnackbar.get().showIndefinite("我是朱志强");

        SmartSnackbar.get().show("为SmartShow Star一下可以么", "好的");
        SmartToast.setting()
                .dismissOnLeave(true)
                .backgroundColorRes(R.color.colorPrimary)

                .textSizeSp(18)
                .textBold(true)
                .processView(new IProcessToastCallback() {
                    @Override
                    //root 为布局根View，msgView为显示消息的TextView
                    public void processView(View rootView, TextView msgView) {

                    }
                });


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


        SmartSnackbar.setting()

                .backgroundColorRes(R.color.colorPrimary)

                .msgTextColorRes(R.color.white)

                .actionColorRes(R.color.colorAccent)

                .dismissOnLeave(true);


    }
}
