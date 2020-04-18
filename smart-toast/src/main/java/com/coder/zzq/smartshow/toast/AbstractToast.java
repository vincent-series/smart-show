package com.coder.zzq.smartshow.toast;

import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.toolkit.log.EasyLogger;

import java.lang.reflect.Field;

class AbstractToast<ToastType extends IToast, ShowApi> implements IToast<ToastType, ShowApi> {
    protected Toast mToast;
    protected AbstractToastUI mToastUI;
    protected CharSequence mMsg = "";
    protected UIArguments mUIArguments;
    protected int mGravity;
    protected int mXOffset;
    protected int mYOffset = -1;
    protected boolean mGoForAnotherPage = false;
    protected boolean mForceDismissWhenLeave = false;


    protected AbstractToast<ToastType, ShowApi> toastUI(AbstractToastUI toastUI) {
        mToastUI = toastUI;
        return this;
    }

    protected AbstractToast<ToastType, ShowApi> message(CharSequence msg) {
        mMsg = (msg == null || msg.toString().trim().isEmpty()) ? "" : msg;
        return this;
    }

    protected AbstractToast<ToastType, ShowApi> message(@StringRes int msg) {
        return message(Utils.getStringFromRes(msg));
    }


    protected AbstractToast<ToastType, ShowApi> gravity(int gravity) {
        mGravity = gravity;
        return this;
    }


    protected AbstractToast<ToastType, ShowApi> xOffset(int xOffset) {
        mXOffset = xOffset;
        return this;
    }


    protected AbstractToast<ToastType, ShowApi> yOffset(int yOffset) {
        mYOffset = yOffset;
        return this;
    }


    private UIArguments ensureUIArgumentsCreated() {
        if (mUIArguments == null) {
            mUIArguments = new UIArguments();
        }
        return mUIArguments;
    }

    @Override
    public ToastType addArg(@NonNull String argName, Object argValue) {
        ensureUIArgumentsCreated().addArg(argName, argValue);
        return (ToastType) this;
    }

    @Override
    public ToastType goForAnotherPage() {
        mGoForAnotherPage = true;
        return (ToastType) this;
    }

    @Override
    public ToastType forceDismissWhenLeave(boolean forceDismiss) {
        mForceDismissWhenLeave = forceDismiss;
        return (ToastType) this;
    }

    public boolean isForceDismissWhenLeave() {
        return mForceDismissWhenLeave;
    }

    protected Object getArg(@NonNull String argName) {
        return mUIArguments == null ? null : mUIArguments.getArg(argName);
    }

    protected AbstractToastUI getToastUI() {
        return mToastUI;
    }

    protected UIArguments getUIArguments() {
        return mUIArguments;
    }

    protected int getGravity() {
        return mGravity;
    }

    protected int getXOffset() {
        return mXOffset;
    }

    protected int getYOffset() {
        return mYOffset;
    }


    protected boolean different(AbstractToast toast) {
        return locationChanged(toast) || uiChanged(toast);
    }

    private boolean uiChanged(AbstractToast toast) {
        return mToastUI.getClass() != toast.getToastUI().getClass()
                || !TextUtils.equals(mMsg, toast.mMsg) || !Utils.equals(getUIArguments(), toast.getUIArguments());
    }

    private boolean locationChanged(AbstractToast toast) {
        return mGravity != toast.getGravity() || mXOffset != toast.getXOffset() || mYOffset != toast.getYOffset();
    }

    @Override
    public ShowApi apply() {
        return (ShowApi) this;
    }

    protected void showUI(boolean shortDuration) {
        if (!ToastManager.get().needShow(this)) {
            return;
        }
        mToast = mToastUI.createToast(mMsg, mUIArguments);
        mToast.setGravity(getGravity(), getXOffset(), getYOffset() == -1 ? mToast.getYOffset() : getYOffset());
        mToast.setDuration(shortDuration ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            injectSafeHandler(mToast);
        }
        if (Utils.isNotificationPermitted()) {
            mToast.show();
        } else {
            VirtualToastManager.get().show(mToast, mGoForAnotherPage);
        }
    }

    protected void injectSafeHandler(Toast toast) {
        try {
            Field tnField = Toast.class.getDeclaredField("mTN");
            tnField.setAccessible(true);
            Object tn = tnField.get(toast);
            Field handlerField = tn.getClass().getDeclaredField("mHandler");
            handlerField.setAccessible(true);
            Handler handlerOfTn = (Handler) handlerField.get(tn);
            handlerField.set(tn, new SafeHandler(handlerOfTn, toast.getView()));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void hideUI() {
        if (isShowing()) {
            hideHelper();
        }
    }

    private void hideHelper() {
        if (Utils.isNotificationPermitted()) {
            mToast.cancel();
        } else {
            VirtualToastManager.get().dismiss();
        }
    }


    protected boolean isShowing() {
        if (Utils.isNotificationPermitted()) {
            return mToast != null && mToast.getView().getWindowVisibility() == View.VISIBLE;
        } else {
            return VirtualToastManager.hasCreated() && VirtualToastManager.get().isShowing();
        }
    }


    protected void reset() {
        mToast = null;
        mToastUI = null;
        mMsg = "";
        if (mUIArguments != null) {
            mUIArguments.clear();
        }
        mGravity = 0;
        mXOffset = 0;
        mYOffset = -1;
        mGoForAnotherPage = false;
        mForceDismissWhenLeave = false;

        EasyLogger.d("reset toast " + Utils.getObjectDesc(this));
    }
}
