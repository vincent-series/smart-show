package com.coder.zzq.smartshowdemo;

import android.app.Application;
import android.graphics.Color;

import com.coder.zzq.smartshow.snackbar.SmartSnackbar;


/**
 * Created by 朱志强 on 2017/11/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
//        SmartToast.plainToast(this)
//                .backgroundColorRes(R.color.colorPrimary)
//                .textColorRes(R.color.colorAccent)
//                .textSizeSp(17)
//                .textBold(true)
//                .processPlainView(new ProcessViewCallback() {
//                    @Override
//                    public void processPlainView(LinearLayout outParent, TextView msgView) {
//                        msgView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//                    }
//                });

//        SmartToast.customToast(this)
//                .view(R.layout.custom_toast)
//                .processCustomView(new ProcessViewCallback() {
//                    @Override
//                    public void processCustomView(View view) {
//
//                    }
//                });

        SmartSnackbar.init(this)
                .backgroundColorRes(R.color.colorPrimary)
                .msgTextColorRes(R.color.colorAccent)
                .actionColor(Color.GREEN)
                .msgTextSizeSp(18)
                .actionSizeSp(20);

    }
}
