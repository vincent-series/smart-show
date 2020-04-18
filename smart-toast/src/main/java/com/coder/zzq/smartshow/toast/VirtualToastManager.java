package com.coder.zzq.smartshow.toast;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.toolkit.lifecycle.ActivityStack;
import com.coder.zzq.toolkit.log.EasyLogger;

final class VirtualToastManager implements Runnable {
    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_LONG = 3500;

    protected Dialog mVirtualToastDialog;
    protected Handler mHandler = new Handler();
    protected Toast mToast;


    public void show(Toast toast, boolean delay) {
        mToast = toast;
        mHandler.removeCallbacks(this);
        if (delay) {
            mHandler.postDelayed(this, 400);
        } else {
            run();
        }
    }


    public void recreateVirtualToastDialog(Activity activity) {
        if (mVirtualToastDialog == null || mVirtualToastDialog.getOwnerActivity() != activity) {
            mVirtualToastDialog = new AppCompatDialog(activity, R.style.smart_show_virtual_toast_dialog);
            mVirtualToastDialog.setOwnerActivity(activity);
            EasyLogger.d("virtual toast dialog" + Utils.getObjectDesc(mVirtualToastDialog) + "has created");
        }
    }

    @Override
    public void run() {
        Activity activity = ActivityStack.getTop();
        if (!Utils.isUpdateActivityUIPermitted(activity)) {
            EasyLogger.d("activity is can not show virtual toast dialog ,so do nothing but return.");
            return;
        }

        recreateVirtualToastDialog(activity);

        mVirtualToastDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mVirtualToastDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        mVirtualToastDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mVirtualToastDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        WindowManager.LayoutParams lp = mVirtualToastDialog.getWindow().getAttributes();

        lp.windowAnimations = android.R.style.Animation_Toast;
        lp.gravity = mToast.getGravity();
        lp.x = mToast.getXOffset();
        lp.y = mToast.getYOffset();
        ViewGroup content = mVirtualToastDialog.findViewById(android.R.id.content);
        if (mToast.getView().getParent() != content) {
            if (mToast.getView().getParent() != null) {
                ViewGroup parent = (ViewGroup) mToast.getView().getParent();
                parent.removeView(mToast.getView());
            }
            content.removeAllViews();
            mVirtualToastDialog.setContentView(mToast.getView());
        }
        try {
            mVirtualToastDialog.show();
        } catch (WindowManager.BadTokenException e) {
            EasyLogger.e("bad token has happened when show virtual toast!");
        }
        mHandler.removeCallbacks(mDismissToastRunnable);
        mHandler.postDelayed(mDismissToastRunnable, mToast.getDuration() == Toast.LENGTH_SHORT ?
                DURATION_SHORT : DURATION_LONG);
    }


    private Runnable mDismissToastRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };


    public void dismiss() {
        if (isShowing()) {
            mVirtualToastDialog.dismiss();
        }
    }

    public boolean isShowing() {
        return mVirtualToastDialog != null && mVirtualToastDialog.isShowing();
    }

    public void destroy(Activity activity) {
        if (mVirtualToastDialog != null && mVirtualToastDialog.getOwnerActivity() == activity) {
            EasyLogger.d("recycle resource when host activity" + Utils.getObjectDesc(activity) + "of virtual toast destroyed");
            dismiss();
            mVirtualToastDialog = null;
        }
    }

    private static VirtualToastManager sVirtualToastManager;

    public static VirtualToastManager get() {
        if (sVirtualToastManager == null) {
            sVirtualToastManager = new VirtualToastManager();
            EasyLogger.d("create virtual toast manager");
        }
        return sVirtualToastManager;
    }

    private VirtualToastManager() {

    }

    public static boolean hasCreated() {
        return sVirtualToastManager != null;
    }
}
