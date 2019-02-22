package com.coder.zzq.smartshow.toast;

import android.support.annotation.StringRes;

import com.coder.zzq.smartshow.core.Utils;

/**
 * Created by 朱志强 on 2017/11/14.
 */

public final class SmartToast {

    private SmartToast() {

    }

    public static void show(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().show(msg);
    }

    public static void show(@StringRes int msg) {
        show(Utils.getStringFromRes(msg));
    }


    public static void showAtTop(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().showAtTop(msg);
    }

    public static void showAtTop(@StringRes int msg) {
        showAtTop(Utils.getStringFromRes(msg));
    }


    public static void showInCenter(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().showInCenter(msg);
    }

    public static void showInCenter(@StringRes int msg) {

        showInCenter(Utils.getStringFromRes(msg));
    }


    public static void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {

        ToastDelegate.get().getPlainShowManager().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }

    public static void showAtLocation(@StringRes int msg, int gravity, float xOffsetDp, float yOffsetDp) {

        showAtLocation(Utils.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp);
    }

    public static void showLong(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().showLong(msg);
    }

    public static void showLong(@StringRes int msg) {

        showLong(Utils.getStringFromRes(msg));
    }


    public static void showLongAtTop(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().showLongAtTop(msg);
    }

    public static void showLongAtTop(@StringRes int msg) {
        showLongAtTop(Utils.getStringFromRes(msg));
    }


    public static void showLongInCenter(CharSequence msg) {

        ToastDelegate.get().getPlainShowManager().showLongInCenter(msg);
    }

    public static void showLongInCenter(@StringRes int msg) {

        showLongInCenter(Utils.getStringFromRes(msg));
    }

    public static void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {

        ToastDelegate.get().getPlainShowManager().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }

    public static void showLongAtLocation(@StringRes int msg, int gravity, float xOffsetDp, float yOffsetDp) {

        showLongAtLocation(Utils.getStringFromRes(msg), gravity, xOffsetDp, yOffsetDp);
    }

    public static void info(CharSequence msg) {

        ToastDelegate.get().getTypeShowManager().info(msg);
    }

    public static void info(@StringRes int msg) {

        info(Utils.getStringFromRes(msg));
    }


    public static void infoLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().infoLong(msg);
    }

    public static void infoLong(@StringRes int msg) {
        infoLong(Utils.getStringFromRes(msg));
    }

    public static void warning(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().warning(msg);
    }

    public static void warning(@StringRes int msg) {
        warning(Utils.getStringFromRes(msg));
    }

    public static void warningLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().warningLong(msg);
    }

    public static void warningLong(@StringRes int msg) {
        waitingLong(Utils.getStringFromRes(msg));
    }


    public static void success(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().success(msg);
    }

    public static void success(@StringRes int msg) {
        success(Utils.getStringFromRes(msg));
    }


    public static void successLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().successLong(msg);
    }

    public static void successLong(@StringRes int msg) {
        successLong(Utils.getStringFromRes(msg));
    }

    public static void error(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().error(msg);
    }

    public static void error(@StringRes int msg) {
        error(Utils.getStringFromRes(msg));
    }


    public static void errorLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().errorLong(msg);
    }

    public static void errorLong(@StringRes int msg) {
        errorLong(Utils.getStringFromRes(msg));
    }

    public static void fail(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().fail(msg);
    }

    public static void fail(@StringRes int msg) {
        fail(Utils.getStringFromRes(msg));
    }

    public static void failLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().failLong(msg);
    }

    public static void failLong(@StringRes int msg) {
        failLong(Utils.getStringFromRes(msg));
    }

    public static void complete(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().complete(msg);
    }

    public static void complete(@StringRes int msg) {
        complete(Utils.getStringFromRes(msg));
    }

    public static void completeLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().completeLong(msg);
    }

    public static void completeLong(@StringRes int msg) {
        completeLong(Utils.getStringFromRes(msg));
    }


    public static void forbid(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().forbid(msg);
    }

    public static void forbid(@StringRes int msg) {
        forbid(Utils.getStringFromRes(msg));
    }

    public static void forbidLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().forbidLong(msg);
    }

    public static void forbidLong(@StringRes int msg) {
        forbidLong(Utils.getStringFromRes(msg));
    }


    public static void waiting(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().waiting(msg);
    }

    public static void waiting(@StringRes int msg) {
        waiting(Utils.getStringFromRes(msg));
    }

    public static void waitingLong(CharSequence msg) {
        ToastDelegate.get().getTypeShowManager().waitingLong(msg);
    }

    public static void waitingLong(@StringRes int msg) {
        waitingLong(Utils.getStringFromRes(msg));
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
