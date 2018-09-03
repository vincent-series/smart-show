package com.coder.zzq.smartshow.toast;

import android.content.Context;
import android.support.annotation.RestrictTo;

/**
 * Created by 朱志强 on 2017/11/14.
 */

public final class SmartToast{
    private static Context sAppContext;
    private static ToastSettingImpl sToastSetting;
    private static PlainToastManager sPlainToastManager;
    private static TypeToastManager sTypeToastManager;

    private SmartToast() {

    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static IToastSetting init(Context context) {
        if (context == null) {
            throw new NullPointerException("初始化SmartToast的context不可谓null！");
        }
         sAppContext = context.getApplicationContext();
        return createToastSetting();
    }


    private static IToastSetting createToastSetting() {
        if (sToastSetting == null){
            sToastSetting = new ToastSettingImpl();
        }
        return sToastSetting;
    }

    private static ToastSettingImpl getToastSetting(){
        if (sToastSetting == null){
            throw new NullPointerException("尚未初始化SmartToast:SmartToast.init(context)!");
        }

        return sToastSetting;
    }


    public static boolean isDismissOnLeave() {
        return getToastSetting().isDismissOnleave();
    }


    public static Context getContext() {
        if (sAppContext == null){
            throw new IllegalStateException("尚未初始化SmartToast:SmartToast.init(context)!");
        }

        return sAppContext;
    }

    public static boolean isShowing(){
        return isPlainShowing() || isTypeShowing();
    }

    public static void dismiss(){
        if (isPlainShowing()){
            getPlainShowManager().dismiss();
        }

        if (isTypeShowing()){
            getTypeShowManager().dismiss();
        }
    }

    private static boolean isTypeShowing() {
        return sTypeToastManager != null && sTypeToastManager.isShowing();
    }

    private static boolean isPlainShowing() {
        return sPlainToastManager != null && sPlainToastManager.isShowing();
    }


    private static PlainToastManager getPlainShowManager() {
        if (sPlainToastManager == null){
            sPlainToastManager = new PlainToastManager(getToastSetting());
        }

        return sPlainToastManager;
    }

    private static TypeToastManager getTypeShowManager(){
        if (sTypeToastManager == null){
            sTypeToastManager = new TypeToastManager(getToastSetting());
        }
        return sTypeToastManager;
    }


    public static void show(CharSequence msg) {

        getPlainShowManager().show(msg);
    }


    public static void showAtTop(CharSequence msg) {

        getPlainShowManager().showAtTop(msg);
    }


    public static void showInCenter(CharSequence msg) {

        getPlainShowManager().showInCenter(msg);
    }


    public static void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {

        getPlainShowManager().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public static void showLong(CharSequence msg) {

        getPlainShowManager().showLong(msg);
    }


    public static void showLongAtTop(CharSequence msg) {

        getPlainShowManager().showLongAtTop(msg);
    }


    public static void showLongInCenter(CharSequence msg) {

        getPlainShowManager().showLongInCenter(msg);
    }


    public static void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {

        getPlainShowManager().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }

    protected static void dismissTypeShowIfNeed() {
        if (sTypeToastManager != null){
            sTypeToastManager.cancel();
        }
    }



    public static void normal(String msg) {

        getTypeShowManager().normal(msg);
    }


    public static void normalLong(String msg) {
        getTypeShowManager().normalLong(msg);
    }


    public static void warning(String msg) {
        getTypeShowManager().warning(msg);
    }


    public static void warningLong(String msg) {
        getTypeShowManager().warningLong(msg);
    }


    public static void fail(String msg) {
        getTypeShowManager().fail(msg);
    }


    public static void failLong(String msg) {
        getTypeShowManager().failLong(msg);
    }


    public static void success(String msg) {
        getTypeShowManager().success(msg);
    }


    public static void successLong(String msg) {
        getTypeShowManager().successLong(msg);
    }

    public static void error(String msg) {
        getTypeShowManager().error(msg);
    }


    public static void errorLong(String msg) {
        getTypeShowManager().errorLong(msg);
    }

    protected static void dismissPlainShowIfNeed(){
        if (sPlainToastManager != null){
            sPlainToastManager.cancel();
        }
    }
}
