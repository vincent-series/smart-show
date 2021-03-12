package com.coder.zzq.smartshow.toast;

import androidx.annotation.StringRes;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.toast.classic.ClassicToastInvoker;
import com.coder.zzq.smartshow.toast.classic.ClassicToastView;
import com.coder.zzq.smartshow.toast.emotion.EmotionToastInvoker;
import com.coder.zzq.smartshow.toast.emotion.EmotionToastView;


/**
 * Created by 朱志强 on 2017/11/14.
 */

public final class SmartToast {

    static {
        SmartShow.setToastCallback(new ToastCallback());
    }


    private SmartToast() {

    }

    public static ClassicToastView.Overall classic() {
        return new ClassicToastInvoker();
    }

    public static EmotionToastView.Overall emotion() {
        return new EmotionToastInvoker();
    }

    public static void show(CharSequence msg) {
        classic().show(msg);
    }

    public static void show(@StringRes int msg) {
        classic().show(msg);
    }

    public static void showAtTop(CharSequence msg) {
        classic().showAtTop(msg);
    }

    public static void showAtTop(@StringRes int msg) {
        classic().showAtTop(msg);
    }


    public static void showInCenter(CharSequence msg) {
        classic().showInCenter(msg);
    }


    public static void showInCenter(@StringRes int msg) {
        classic().showInCenter(msg);
    }


    public static void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        classic().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void showAtLocation(@StringRes int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        classic().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void showLong(CharSequence msg) {
        classic().showLong(msg);
    }


    public static void showLong(@StringRes int msg) {
        classic().showLong(msg);
    }


    public static void showLongAtTop(CharSequence msg) {
        classic().showLongAtTop(msg);
    }


    public static void showLongAtTop(@StringRes int msg) {
        classic().showLongAtTop(msg);
    }


    public static void showLongInCenter(CharSequence msg) {
        classic().showLongInCenter(msg);
    }


    public static void showLongInCenter(@StringRes int msg) {
        classic().showLongInCenter(msg);
    }


    public static void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {
        classic().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }

    public static void showLongAtLocation(@StringRes int msg, int gravity, float xOffsetDp, float yOffsetDp) {
        classic().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void info(CharSequence msg) {
        emotion().info(msg);
    }


    public static void info(@StringRes int msg) {
        emotion().info(msg);
    }


    public static void infoLong(CharSequence msg) {
        emotion().infoLong(msg);
    }


    public static void infoLong(@StringRes int msg) {
        emotion().infoLong(msg);
    }


    public static void warning(CharSequence msg) {
        emotion().warning(msg);
    }


    public static void warning(@StringRes int msg) {
        emotion().warning(msg);
    }


    public static void warningLong(CharSequence msg) {
        emotion().warningLong(msg);
    }


    public static void warningLong(@StringRes int msg) {
        emotion().warningLong(msg);
    }


    public static void success(CharSequence msg) {
        emotion().success(msg);
    }


    public static void success(@StringRes int msg) {
        emotion().success(msg);
    }


    public static void successLong(CharSequence msg) {
        emotion().successLong(msg);
    }


    public static void successLong(@StringRes int msg) {
        emotion().successLong(msg);
    }


    public static void error(CharSequence msg) {
        emotion().error(msg);
    }


    public static void error(@StringRes int msg) {
        emotion().error(msg);
    }


    public static void errorLong(CharSequence msg) {
        emotion().errorLong(msg);
    }


    public static void errorLong(@StringRes int msg) {
        emotion().errorLong(msg);
    }


    public static void fail(CharSequence msg) {
        emotion().fail(msg);
    }


    public static void fail(@StringRes int msg) {
        emotion().fail(msg);
    }


    public static void failLong(CharSequence msg) {
        emotion().failLong(msg);
    }


    public static void failLong(@StringRes int msg) {
        emotion().failLong(msg);
    }


    public static void complete(CharSequence msg) {
        emotion().complete(msg);
    }


    public static void complete(@StringRes int msg) {
        emotion().complete(msg);
    }

    public static void completeLong(CharSequence msg) {
        emotion().completeLong(msg);
    }


    public static void completeLong(@StringRes int msg) {
        emotion().completeLong(msg);
    }


    public static void forbid(CharSequence msg) {
        emotion().forbid(msg);
    }


    public static void forbid(@StringRes int msg) {
        emotion().forbid(msg);
    }

    public static void forbidLong(CharSequence msg) {
        emotion().forbidLong(msg);
    }


    public static void forbidLong(@StringRes int msg) {
        emotion().forbidLong(msg);
    }


    public static void waiting(CharSequence msg) {
        emotion().waiting(msg);
    }


    public static void waiting(@StringRes int msg) {
        emotion().waiting(msg);
    }


    public static void waitingLong(CharSequence msg) {
        emotion().waitingLong(msg);
    }


    public static void waitingLong(@StringRes int msg) {
        emotion().waitingLong(msg);
    }
}
