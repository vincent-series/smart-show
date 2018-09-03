package com.coder.zzq.smartshowdemo;

import android.app.Application;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.snackbar.ProcessSnackbarCallback;
import com.coder.zzq.smartshow.toast.IProcessToastCallback;
import com.coder.zzq.smartshow.toast.IToastSetting;
import com.coder.zzq.smartshow.toast.SmartToast;


/**
 * Created by 朱志强 on 2017/11/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SmartShow.init(this);
        SmartShow.toastSetting()
////                .view(R.layout.custom_toast)
////                自定义布局，参数可以是布局资源，也可以View。
////                 在自定义布局中，一定要设置显示文本提示的
////                TextView的Id为android:id="@id/custom_toast_msg"。
////                设置背景颜色
//                .backgroundColorRes(R.color.colorPrimary)
////                文本颜色
//                .textColorRes(R.color.colorAccent)
//                //设置文本字体大小
//                .textSizeSp(18)
//                //设置文本是否加粗
//                .textBold(true)
//                //设置离开当前页面时，该页面的Toast是否立即消失
                .dismissOnLeave(true)
//                //当Toast内容重复时的处理策略：ACTION_IGNORE为传统模式，即不重复弹出；
//                //ACTION_REPEAT_SHOW_LIKE_SNACKBAR为立即隐藏前一个，再次显示一个，类似Snackbar
//                //默认策略为不重复弹出
                .actionWhenDuplicate(IToastSetting.ACTION_REPEAT)
//                .typeInfoToastThemeColorRes(R.color.colorPrimary)
//                //对布局进一步处理
                .processView(new IProcessToastCallback() {
                    @Override
                    public void processView(boolean isCustom, View rootView, TextView msgView) {

                    }
                });

        SmartShow.snackbarSetting()
                //设置背景颜色
//                .backgroundColor()
                //设置消息文本颜色
                .msgTextColor(Color.WHITE)
                //设置消息文本大小
                .msgTextSizeSp(18)
                //设置动作文本颜色
//                .actionColorRes(R.color.colorAccent)
                //设置动作文本大小
                .actionSizeSp(18)
                .defaultActionTextForIndefinite("ok")
                // //设置进入新的页面时，该页面的Snackbar是否消失（主要是Indefinite Snackbar而言）
                .dismissOnLeave(false)
                //对布局进一步处理
                .processView(new ProcessSnackbarCallback() {
                    @Override
                    public void processSnackbarView(com.coder.zzq.smartshow.snackbar.custom.Snackbar.SnackbarLayout layout, TextView msgView, TextView actionView) {

                    }
                });
    }
}
