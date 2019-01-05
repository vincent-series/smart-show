package com.coder.zzq.smartshow.toast;

/**
 * Created by 朱志强 on 2017/11/14.
 */

public final class SmartToast {

    private SmartToast() {

    }

    public static void show(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().show(msg);
    }


    public static void showAtTop(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().showAtTop(msg);
    }


    public static void showInCenter(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().showInCenter(msg);
    }


    public static void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {

        ToastDelegate.get().getPlainShowManager().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void showLong(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().showLong(msg);
    }


    public static void showLongAtTop(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().showLongAtTop(msg);
    }


    public static void showLongInCenter(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().showLongInCenter(msg);
    }


    public static void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {

        ToastDelegate.get().getPlainShowManager().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void info(CharSequence msg) {

        ToastDelegate.get().getTypeShowManager().info(msg);
    }


    public static void infoLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().infoLong(msg);
    }


    public static void warning(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().warning(msg);
    }


    public static void warningLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().warningLong(msg);
    }


    public static void success(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().success(msg);
    }


    public static void successLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().successLong(msg);
    }

    public static void error(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().error(msg);
    }


    public static void errorLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().errorLong(msg);
    }

    public static void fail(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().fail(msg);
    }

    public static void failLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().failLong(msg);
    }

    public static void complete(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().complete(msg);
    }

    public static void completeLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().completeLong(msg);
    }


    public static void forbid(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().forbid(msg);
    }

    public static void forbidLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().forbidLong(msg);
    }


    public static void waiting(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().waiting(msg);
    }

    public static void waitingLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().waitingLong(msg);
    }

    public static boolean isShowing() {
        return ToastDelegate.get().isPlainShowing() || ToastDelegate.get().isTypeShowing();
    }

    public static void dismiss() {
        if (ToastDelegate.get().isPlainShowing()) {
            ToastDelegate.get().getPlainShowManager().dismiss();
        }

        if (ToastDelegate.get().isTypeShowing()) {
            ToastDelegate.get().getTypeShowManager().dismiss();
        }
    }


    public static IToastSetting setting() {
        return ToastDelegate.get().createToastSetting();
    }
}
