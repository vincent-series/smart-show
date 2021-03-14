package com.coder.zzq.smartshow.toast.compact;

import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.factory.BaseToastConfig;
import com.coder.zzq.toolkit.Utils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class CompactToast {
    private String mToastAlias;
    private WeakReference<Toast> mToastReference;
    private boolean mDiscard;

    private BaseToastConfig mConfig;

    public CompactToast(Toast toast, String alias, BaseToastConfig config) {
        mToastReference = new WeakReference<>(Utils.requireNonNull(toast, "the toast instance can not null!"));
        if (Utils.isEmpty(alias)) {
            throw new IllegalStateException("the toast alias can not null or empty!");
        }

        mToastAlias = alias;
        mConfig = config;

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
            injectSafeHandler(mToastReference.get());
        }

    }


    public Toast getToast() {
        return mToastReference.get();
    }

    @SuppressWarnings("deprecation")
    public boolean isToastShowing() {
        return mToastReference != null
                && mToastReference.get() != null
                && mToastReference.get().getView() != null
                && mToastReference.get().getView().getWindowVisibility() == View.VISIBLE;
    }


    public String getToastAlias() {
        return mToastAlias;
    }

    public BaseToastConfig getConfig() {
        return mConfig;
    }

    public void updateConfig(BaseToastConfig config) {
        mConfig = config;
    }

    public boolean isDiscard() {
        return mToastReference == null || mToastReference.get() == null || mDiscard;
    }

    public void discard() {
        mDiscard = true;
        if (mToastReference != null) {
            if (mToastReference.get() != null) {
                mToastReference.get().cancel();
            }
            mToastReference.clear();
        }

        mToastReference = null;

        if (!Utils.isNotificationPermitted()) {
            VirtualToastManager.dismiss();
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

    public void show() {
        if (Utils.isNotificationPermitted()) {

            if (mToastReference.get() != null && mToastReference.get().getView().getParent() != null && mToastReference.get().getView().getParent() instanceof ViewGroup) {
                ((ViewGroup) mToastReference.get().getView().getParent()).removeAllViews();
            }

            if (mToastReference.get() != null){
                mToastReference.get().show();
            }
        } else {
            VirtualToastManager.get().show(this);
        }
    }
}
