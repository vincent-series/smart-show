package com.coder.zzq.smartshow.toast;

import android.app.Application;
import android.support.annotation.RestrictTo;

import com.coder.zzq.smartshow.SmartShow;

/**
 * Created by 朱志强 on 2018/9/8.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class SmartToastDelegate {
    private ToastSettingImpl mToastSetting;
    private PlainToastManager mPlainToastManager;
    private TypeToastManager mTypeToastManager;



    private SmartToastDelegate(Application context) {

    }

    public ToastSettingImpl toastSetting() {
        if (mToastSetting == null) {
            mToastSetting = new ToastSettingImpl();
        }
        return mToastSetting;
    }


    protected boolean hasToastSetting(){
        return mToastSetting != null;
    }


    public boolean isDismissOnLeave() {
        return mToastSetting != null && mToastSetting.isDismissOnleave();
    }

    public boolean isShowing() {
        return isPlainShowing() || isTypeShowing();
    }

    public void dismiss() {
        if (isPlainShowing()) {
            getPlainShowManager().dismiss();
        }

        if (isTypeShowing()) {
            getTypeShowManager().dismiss();
        }
    }

    protected boolean isTypeShowing() {
        return mTypeToastManager != null && mTypeToastManager.isShowing();
    }

    protected boolean isPlainShowing() {
        return mPlainToastManager != null && mPlainToastManager.isShowing();
    }


    protected PlainToastManager getPlainShowManager() {
        if (mPlainToastManager == null) {
            mPlainToastManager = new PlainToastManager();
        }

        return mPlainToastManager;
    }

    protected TypeToastManager getTypeShowManager() {
        if (mTypeToastManager == null) {
            mTypeToastManager = new TypeToastManager();
        }
        return mTypeToastManager;
    }


    public void show(CharSequence msg) {

        getPlainShowManager().show(msg);
    }


    public void showAtTop(CharSequence msg) {

        getPlainShowManager().showAtTop(msg);
    }


    public void showInCenter(CharSequence msg) {

        getPlainShowManager().showInCenter(msg);
    }


    public void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {

        getPlainShowManager().showAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }


    public void showLong(CharSequence msg) {

        getPlainShowManager().showLong(msg);
    }


    public void showLongAtTop(CharSequence msg) {

        getPlainShowManager().showLongAtTop(msg);
    }


    public void showLongInCenter(CharSequence msg) {

        getPlainShowManager().showLongInCenter(msg);
    }


    public void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp) {

        getPlainShowManager().showLongAtLocation(msg, gravity, xOffsetDp, yOffsetDp);
    }

    protected void dismissTypeShowIfNeed() {
        if (mTypeToastManager != null) {
            mTypeToastManager.cancel();
        }
    }


    public void normal(String msg) {

        getTypeShowManager().normal(msg);
    }


    public void normalLong(String msg) {
        getTypeShowManager().normalLong(msg);
    }


    public void warning(String msg) {
        getTypeShowManager().warning(msg);
    }


    public void warningLong(String msg) {
        getTypeShowManager().warningLong(msg);
    }


    public void fail(String msg) {
        getTypeShowManager().fail(msg);
    }


    public void failLong(String msg) {
        getTypeShowManager().failLong(msg);
    }


    public void success(String msg) {
        getTypeShowManager().success(msg);
    }


    public void successLong(String msg) {
        getTypeShowManager().successLong(msg);
    }

    public void error(String msg) {
        getTypeShowManager().error(msg);
    }


    public void errorLong(String msg) {
        getTypeShowManager().errorLong(msg);
    }

    public void dismissPlainShowIfNeed() {
        if (mPlainToastManager != null) {
            mPlainToastManager.cancel();
        }
    }


    private static SmartToastDelegate sSmartToastDelegate;

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static SmartToastDelegate get() {

        if (sSmartToastDelegate == null){
            sSmartToastDelegate = new SmartToastDelegate(SmartShow.getContext());
        }

        return sSmartToastDelegate;
    }

    public ToastSettingImpl getToastSetting() {
        return mToastSetting;
    }

    public static boolean hasCreated() {
        return sSmartToastDelegate != null;
    }
}
