package com.coder.zzq.smartshow.toast;

import android.app.Application;
import android.support.annotation.RestrictTo;

import com.coder.zzq.smartshow.SmartShow;

/**
 * Created by 朱志强 on 2018/9/8.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class SmartToastDelegate {
    private static ToastSettingImpl sToastSetting;
    private PlainToastManager mPlainToastManager;
    private TypeToastManager mTypeToastManager;


    private SmartToastDelegate(Application context) {

    }

    public ToastSettingImpl toastSetting() {
        if (sToastSetting == null) {
            sToastSetting = new ToastSettingImpl();
        }
        return sToastSetting;
    }


    protected boolean hasToastSetting() {
        return sToastSetting != null;
    }


    public boolean isDismissOnLeave() {
        return sToastSetting != null && sToastSetting.isDismissOnleave();
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


    public void info(String msg) {

        getTypeShowManager().info(msg);
    }


    public void infoLong(String msg) {
        getTypeShowManager().infoLong(msg);
    }


    public void warning(String msg) {
        getTypeShowManager().warning(msg);
    }


    public void warningLong(String msg) {
        getTypeShowManager().warningLong(msg);
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

        if (sSmartToastDelegate == null) {
            sSmartToastDelegate = new SmartToastDelegate(SmartShow.getContext());
        }

        return sSmartToastDelegate;
    }

    public ToastSettingImpl getsToastSetting() {
        return sToastSetting;
    }

    public static boolean hasCreated() {
        return sSmartToastDelegate != null;
    }


    public static void destroyDelegate() {

        if (hasCreated()) {
            if (sSmartToastDelegate.mPlainToastManager != null) {
                sSmartToastDelegate.mPlainToastManager.destroy();
                sSmartToastDelegate.mPlainToastManager = null;
            }

            if (sSmartToastDelegate.mTypeToastManager != null) {
                sSmartToastDelegate.mTypeToastManager.destroy();
                sSmartToastDelegate.mTypeToastManager = null;
            }

            sSmartToastDelegate = null;
        }
    }
}
