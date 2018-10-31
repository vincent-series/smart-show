package com.coder.zzq.smartshow.toast;

import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class BaseToastManager implements View.OnAttachStateChangeListener {

    protected Toast mToast;
    protected CharSequence mCurMsg;
    protected int mDuration;
    protected View mView;
    protected TextView mMsgView;
    protected Object mTn;
    protected Method mHideMethod;
    protected WindowManager.LayoutParams mWindowParams;


    public BaseToastManager() {
        mToast = createToast();
        setupReflectInfo();
        setupToast();
    }


    protected abstract Toast createToast();

    protected void rebuildToast(){
        mToast = createToast();
        setupReflectInfo();
        setupToast();
    }
    protected void updateToast(){
        mMsgView.setText(mCurMsg);
    }

    protected void setupReflectInfo() {
        try {
            Field tnField = Toast.class.getDeclaredField("mTN");
            tnField.setAccessible(true);
            mTn = tnField.get(mToast);
            mHideMethod = mTn.getClass().getDeclaredMethod("handleHide");
            mHideMethod.setAccessible(true);
            Field windowParamsField = mTn.getClass().getDeclaredField("mParams");
            windowParamsField.setAccessible(true);
            mWindowParams = (WindowManager.LayoutParams) windowParamsField.get(mTn);
            if (isSdk25()) {
                Field handlerField = mTn.getClass().getDeclaredField("mHandler");
                handlerField.setAccessible(true);
                Handler handler = (Handler) handlerField.get(mTn);
                handlerField.set(mTn, new SafeHandler(handler));
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected void setupToast(){
        mCurMsg = "";
        mDuration = Toast.LENGTH_SHORT;
        mView.addOnAttachStateChangeListener(this);
    }

    protected boolean isShowing(){
        return ViewCompat.isAttachedToWindow(mView);
    }




    public void dismiss() {
        try {
            mHideMethod.invoke(mTn);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onViewAttachedToWindow(View v) {

    }


    @Override
    public void onViewDetachedFromWindow(View v) {
        updateToast();
    }


    protected boolean isSdk25() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1;
    }

    public void destroy() {
        mToast = null;
        mCurMsg = "";
        mDuration = Toast.LENGTH_SHORT;
        mView = null;
        mMsgView = null;
        mTn = null;
        mHideMethod = null;
        mWindowParams = null;
    }
}
