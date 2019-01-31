package com.coder.zzq.smartshowdemo;

import android.view.Gravity;
import android.widget.Toast;

public final class ToastUtil {
    private ToastUtil() {

    }

    //toast 单例
    private static Toast sToast;
    //默认的xOffset
    private static int sDefaultXOffset;
    //默认的yOffset
    private static int sDefaultYOffset;


    private static void createToast(CharSequence msg, int duration) {
        if (sToast == null) {
            //创建Toast单例，并保存默认的xOffset和yOffset
            sToast = Toast.makeText(MyApplication.sContext, "", Toast.LENGTH_SHORT);
            sDefaultXOffset = sToast.getXOffset();
            sDefaultYOffset = sToast.getYOffset();
        }
        //修改message、duration
        sToast.setText(msg);
        sToast.setDuration(duration);
    }

    //默认位置显示Toast
    public static void showToast(CharSequence msg, int duration) {
        createToast(msg, duration);
        sToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, sDefaultXOffset, sDefaultYOffset);
        sToast.show();
    }

    //居中显示Toast
    public static void showCenterToast(CharSequence msg, int duration) {
        createToast(msg, duration);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        sToast.show();
    }
}
