package com.coder.zzq.smartshow.toast;

import androidx.annotation.StringRes;

import com.coder.zzq.smartshow.core.SmartShow;

/**
 * Created by 朱志强 on 2017/11/14.
 */

public final class SmartToast {

    static {
        SmartShow.setToastCallback(new ToastCallback());
    }


    private SmartToast() {

    }


    public static ICustomToast toastUI(ToastUI toastUI) {
        return (ICustomToast) ToastCache.provideCustomToast().toastUI(toastUI);
    }

    public static IOriginalToast original() {
        return ToastCache.provideOriginalToast();
    }

    public static IEmotionToast emotion() {
        return ToastCache.provideEmotionToast();
    }


    public static void show(CharSequence msg) {
        original().apply().show(msg);
    }

    public static void show(@StringRes int msg) {
        original().apply().show(msg);
    }

    public static void showAtTop(CharSequence msg) {
        original().apply().showAtTop(msg);
    }

    public static void showAtTop(@StringRes int msg) {
        original().apply().showAtTop(msg);
    }


    public static void showInCenter(CharSequence msg) {
        original().apply().showInCenter(msg);
    }


    public static void showInCenter(@StringRes int msg) {
        original().apply().showInCenter(msg);
    }


    public static void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        original().apply().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void showAtLocation(@StringRes int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        original().apply().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void showLong(CharSequence msg) {
        original().apply().showLong(msg);
    }


    public static void showLong(@StringRes int msg) {
        original().apply().showLong(msg);
    }


    public static void showLongAtTop(CharSequence msg) {
        original().apply().showLongAtTop(msg);
    }


    public static void showLongAtTop(@StringRes int msg) {
        original().apply().showLongAtTop(msg);
    }


    public static void showLongInCenter(CharSequence msg) {
        original().apply().showLongInCenter(msg);
    }


    public static void showLongInCenter(@StringRes int msg) {
        original().apply().showLongInCenter(msg);
    }


    public static void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        original().apply().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }

    public static void showLongAtLocation(@StringRes int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        original().apply().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void info(CharSequence msg) {
        emotion().apply().info(msg);
    }


    public static void info(@StringRes int msg) {
        emotion().apply().info(msg);
    }


    public static void infoLong(CharSequence msg) {
        emotion().apply().infoLong(msg);
    }


    public static void infoLong(@StringRes int msg) {
        emotion().apply().infoLong(msg);
    }


    public static void warning(CharSequence msg) {
        emotion().apply().warning(msg);
    }


    public static void warning(@StringRes int msg) {
        emotion().apply().warning(msg);
    }


    public static void warningLong(CharSequence msg) {
        emotion().apply().warningLong(msg);
    }


    public static void warningLong(@StringRes int msg) {
        emotion().apply().warningLong(msg);
    }


    public static void success(CharSequence msg) {
        emotion().apply().success(msg);
    }


    public static void success(@StringRes int msg) {
        emotion().apply().success(msg);
    }


    public static void successLong(CharSequence msg) {
        emotion().apply().successLong(msg);
    }


    public static void successLong(@StringRes int msg) {
        emotion().apply().successLong(msg);
    }


    public static void error(CharSequence msg) {
        emotion().apply().error(msg);
    }


    public static void error(@StringRes int msg) {
        emotion().apply().error(msg);
    }


    public static void errorLong(CharSequence msg) {
        emotion().apply().errorLong(msg);
    }


    public static void errorLong(@StringRes int msg) {
        emotion().apply().errorLong(msg);
    }


    public static void fail(CharSequence msg) {
        emotion().apply().fail(msg);
    }


    public static void fail(@StringRes int msg) {
        emotion().apply().fail(msg);
    }


    public static void failLong(CharSequence msg) {
        emotion().apply().failLong(msg);
    }


    public static void failLong(@StringRes int msg) {
        emotion().apply().failLong(msg);
    }


    public static void complete(CharSequence msg) {
        emotion().apply().complete(msg);
    }


    public static void complete(@StringRes int msg) {
        emotion().apply().complete(msg);
    }

    public static void completeLong(CharSequence msg) {
        emotion().apply().completeLong(msg);
    }


    public static void completeLong(@StringRes int msg) {
        emotion().apply().completeLong(msg);
    }


    public static void forbid(CharSequence msg) {
        emotion().apply().forbid(msg);
    }


    public static void forbid(@StringRes int msg) {
        emotion().apply().forbid(msg);
    }

    public static void forbidLong(CharSequence msg) {
        emotion().apply().forbidLong(msg);
    }


    public static void forbidLong(@StringRes int msg) {
        emotion().apply().forbidLong(msg);
    }


    public static void waiting(CharSequence msg) {
        emotion().apply().waiting(msg);
    }


    public static void waiting(@StringRes int msg) {
        emotion().apply().waiting(msg);
    }


    public static void waitingLong(CharSequence msg) {
        emotion().apply().waitingLong(msg);
    }


    public static void waitingLong(@StringRes int msg) {
        emotion().apply().waitingLong(msg);
    }


    public static boolean isShowing() {
        return ToastManager.hasCreated() && ToastManager.get().isShowing();
    }

    public static void dismiss() {
        if (ToastManager.hasCreated()) {
            ToastManager.get().dismiss();
        }
    }
}
