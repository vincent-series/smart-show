package com.coder.zzq.smartshow.toast;

import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public abstract class BaseToastManager {

    protected Toast mToast;
    protected CharSequence mCurMsg;
    protected int mDuration;
    protected View mView;
    protected TextView mMsgView;
    protected Object mTn;
    protected WindowManager.LayoutParams mWindowParams;


    public BaseToastManager() {
        mToast = createToast();
        setupReflectInfo();
        setupToast();
    }


    protected abstract Toast createToast();

    protected void rebuildToast() {
        mToast = createToast();
        setupReflectInfo();
        setupToast();
    }

    protected void updateToast() {
        mMsgView.setText(mCurMsg);
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

    protected void setupToast() {
        mCurMsg = "";
        mDuration = Toast.LENGTH_SHORT;
    }

    protected boolean isShowing() {
        return ViewCompat.isAttachedToWindow(mView);
    }


    public void dismiss() {
        mToast.cancel();
        rebuildToast();
    }

    protected boolean isSdk25() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1;
    }

}
