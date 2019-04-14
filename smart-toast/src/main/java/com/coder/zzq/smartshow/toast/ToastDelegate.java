package com.coder.zzq.smartshow.toast;

import android.support.annotation.RestrictTo;

import com.coder.zzq.smartshow.core.SmartShow;

/**
 * Created by 朱志强 on 2018/9/8.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class ToastDelegate {
    private GlobalSetting mGlobalSetting;
    private PlainToastSetting mPlainSetting;
    private TypeToastSetting mTypeSetting;
    private PlainToastManager mPlainToastManager;
    private TypeToastManager mTypeToastManager;


    private ToastDelegate() {

    }

    public IPlainToastSetting createPlainSetting() {
        if (mPlainSetting == null) {
            mPlainSetting = new PlainToastSetting();
        }
        return mPlainSetting;
    }


    protected boolean hasPlainSetting() {
        return mPlainSetting != null;
    }

    public PlainToastSetting getPlainSetting() {
        return mPlainSetting;
    }


    public ITypeToastSetting createTypeSetting() {
        if (mTypeSetting == null) {
            mTypeSetting = new TypeToastSetting();
        }
        return mTypeSetting;
    }


    protected boolean hasTypeSetting() {
        return mTypeSetting != null;
    }

    public TypeToastSetting getTypeSetting() {
        return mTypeSetting;
    }

    public IGlobalSetting createGlobalSetting() {
        if (mGlobalSetting == null) {
            mGlobalSetting = new GlobalSetting();
        }
        return mGlobalSetting;
    }

    public boolean hasGlobalSetting() {
        return mGlobalSetting != null;
    }

    public GlobalSetting getGlobalSetting() {
        return mGlobalSetting;
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
            mTypeToastManager.dismiss();
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
            mPlainToastManager.dismiss();
        }
    }


    private static ToastDelegate sSmartToastDelegate;

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static ToastDelegate get() {
        SmartShow.getContext();
        if (sSmartToastDelegate == null) {
            sSmartToastDelegate = new ToastDelegate();
            SmartShow.setToastCallback(new ToastCallback());
        }

        return sSmartToastDelegate;
    }

    public static boolean hasCreated() {
        return sSmartToastDelegate != null;
    }

    public boolean isDismissOnLeave() {
        return hasGlobalSetting() && getGlobalSetting().isDismissOnLeave();
    }
}
