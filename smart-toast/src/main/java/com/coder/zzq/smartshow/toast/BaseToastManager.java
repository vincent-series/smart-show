package com.coder.zzq.smartshow.toast;

import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.Utils;

import java.lang.reflect.Field;

public abstract class BaseToastManager {
    public static final int PLAIN_TOAST = 1;
    public static final int TYPE_TOAST = 2;
    protected Toast mToast;
    protected CharSequence mCurMsg;
    protected int mDuration;
    protected View mView;
    protected TextView mMsgView;
    protected Object mTn;
    protected WindowManager.LayoutParams mWindowParams;
    protected long mLastShowTime;


    public BaseToastManager() {
        mToast = createToast();
        setupReflectInfo();
        initSetup();
    }


    protected abstract Toast createToast();

    protected void rebuildToast() {
        mToast = createToast();
        EasyLogger.d("toast has rebuild : " + getToastType());
        setupReflectInfo();
        initSetup();
    }

    protected void updateToast() {
        if (mMsgView != null) {
            mMsgView.setText(mCurMsg);
        }
    }

    protected void setupReflectInfo() {
        try {
            Field tnField = Toast.class.getDeclaredField("mTN");
            tnField.setAccessible(true);
            mTn = tnField.get(mToast);

            Field windowParamsField = mTn.getClass().getDeclaredField("mParams");
            windowParamsField.setAccessible(true);
            mWindowParams = (WindowManager.LayoutParams) windowParamsField.get(mTn);

            if (isSdk25()) {
                Field handlerField = mTn.getClass().getDeclaredField("mHandler");
                handlerField.setAccessible(true);
                Handler handlerOfTn = (Handler) handlerField.get(mTn);
                handlerField.set(mTn, new SafeHandler(handlerOfTn));
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected void initSetup() {
        mCurMsg = "";
        mDuration = Toast.LENGTH_SHORT;
    }

    protected boolean isShowing() {
        if (Utils.isNotificationPermitted()) {
            return ViewCompat.isAttachedToWindow(mView) || mView.getParent() != null;
        } else {
            return VirtualToastManager.get().isShowing(getToastType());
        }
    }

    protected abstract int getToastType();


    public void dismiss() {
        if (!isShowing()) {
            return;
        }
        mToast.cancel();
        rebuildToast();
        if (!Utils.isNotificationPermitted()) {
            VirtualToastManager.get().dismiss(getToastType());
        }
    }

    protected void applySetting() {

    }

    public void showToast() {
        applySetting();
        ViewParent parent = mView.getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).removeView(mView);
        }
        if (Utils.isNotificationPermitted()) {
            mToast.show();
        } else {
            VirtualToastManager.get().show(getToastType(), mToast, mWindowParams);
        }
        mLastShowTime = System.currentTimeMillis();
    }

    protected boolean isSdk25() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1;
    }

    protected boolean isShortInterval(){
        return System.currentTimeMillis() - mLastShowTime < 100;
    }
}
