package com.coder.zzq.smartshow.toast;

/**
 * Created by 朱志强 on 2017/11/14.
 */

public final class SmartToast{

    private SmartToast() {

    }

    public static void show(CharSequence msg) {

        SmartToastDelegate.get().getPlainShowManager().show(msg);
    }


    public static void showAtTop(CharSequence msg) {

        SmartToastDelegate.get().getPlainShowManager().showAtTop(msg);
    }


    public static void showInCenter(CharSequence msg) {

        SmartToastDelegate.get().getPlainShowManager().showInCenter(msg);
    }


    public static void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {

        SmartToastDelegate.get().getPlainShowManager().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void showLong(CharSequence msg) {

        SmartToastDelegate.get().getPlainShowManager().showLong(msg);
    }


    public static void showLongAtTop(CharSequence msg) {

        SmartToastDelegate.get().getPlainShowManager().showLongAtTop(msg);
    }


    public static void showLongInCenter(CharSequence msg) {

        SmartToastDelegate.get().getPlainShowManager().showLongInCenter(msg);
    }


    public static void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {

        SmartToastDelegate.get().getPlainShowManager().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void info(String msg) {

        SmartToastDelegate.get().getTypeShowManager().info(msg);
    }


    public static void infoLong(String msg) {
        SmartToastDelegate.get().getTypeShowManager().infoLong(msg);
    }


    public static void warning(String msg) {
        SmartToastDelegate.get().getTypeShowManager().warning(msg);
    }


    public static void warningLong(String msg) {
        SmartToastDelegate.get().getTypeShowManager().warningLong(msg);
    }


    public static void success(String msg) {
        SmartToastDelegate.get().getTypeShowManager().success(msg);
    }


    public static void successLong(String msg) {
        SmartToastDelegate.get().getTypeShowManager().successLong(msg);
    }

    public static void error(String msg) {
        SmartToastDelegate.get().getTypeShowManager().error(msg);
    }


    public static void errorLong(String msg) {
        SmartToastDelegate.get().getTypeShowManager().errorLong(msg);
    }



    public static boolean isShowing(){
        return SmartToastDelegate.get().isPlainShowing() || SmartToastDelegate.get().isTypeShowing();
    }

    public static void dismiss(){
        if (SmartToastDelegate.get().isPlainShowing()){
            SmartToastDelegate.get().getPlainShowManager().dismiss();
        }

        if (SmartToastDelegate.get().isTypeShowing()){
            SmartToastDelegate.get().getTypeShowManager().dismiss();
        }
    }


    public static IToastSetting setting() {
        return SmartToastDelegate.get().toastSetting();
    }
}
