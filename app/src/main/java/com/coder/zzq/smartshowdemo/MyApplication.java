package com.coder.zzq.smartshowdemo;

import android.app.Application;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.toast.ProcessViewCallback;
import com.coder.zzq.smartshow.toast.SmartToast;


/**
 * Created by 朱志强 on 2017/11/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SmartToast.plainToast(this)
                .backgroundColorRes(R.color.colorPrimary)
                .textColorRes(R.color.colorAccent)
                .textSizeSp(18)
                .textBold(true)
                .processPlainView(new ProcessViewCallback() {
                    @Override
                    public void processPlainView(LinearLayout outParent, TextView msgView) {

                    }
                });
        Utils.init(this);
                /*
                设置背景颜色，有可选方法，可直接以颜色值为参数

                Toast的默认背景是一个圆角图片，当你设置了背景颜色时，原有背景失效

                我们内部用ShapeDrawable实现背景，可以保证大小与你手机系统Toast一致，

                但是不同品牌手机的Toast的圆角半径不尽相同，我们统一使用2.5dp
                */
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
//                //下面的方法不是必须调用的
//                .processCustomView(new ProcessViewCallback() {
//                    @Override
//                    public void processCustomView(View view) {
//                        ((TextView) view.findViewById(R.id.custom_toast_msg)).setTextColor(Color.WHITE);
//                    }
//                });
//        //隐藏当前Snackbar
//        SmartSnackbar.dismiss();
//        SmartSnackbar.init(this)
//                .backgroundColorRes(R.color.colorPrimary)
//                .msgTextColorRes(R.color.white)
//                .actionColorRes(R.color.colorAccent)
//                .msgTextSizeSp(18)
//                .actionSizeSp(18)
//                .processView(new ProcessViewCallback() {
//                    @Override
//                    public void processSnackbarView(Snackbar.SnackbarLayout layout, TextView msgView, TextView actionView) {
//
//                    }
//                });
//
        SmartSnackbar.init(this)
                .backgroundColorRes(R.color.colorPrimary)
                .actionColorRes(R.color.colorAccent)
                .msgTextSizeSp(16)
                .actionSizeSp(16);
    }
}
