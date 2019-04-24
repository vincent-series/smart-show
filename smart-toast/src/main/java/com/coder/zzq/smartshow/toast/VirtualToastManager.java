package com.coder.zzq.smartshow.toast;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.support.v7.app.AppCompatDialog;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.core.lifecycle.ActivityStack;

public final class VirtualToastManager {
    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_LONG = 3500;

    private Dialog mVirtualPlainToastDialog;
    private Dialog mVirtualTypeToastDialog;
    private Activity mHostActivity;
    private Handler mDismissHandler = new Handler();

    public void show(int toastType, Toast toast, WindowManager.LayoutParams windowParams) {
        Activity activity = ActivityStack.getTop();
        if (!Utils.isUpdateActivityUIPermitted(activity)) {
            EasyLogger.d("activity is can not show virtual toast dialog ,so do nothing but return.");
            return;
        }
        boolean hostActivityChanged = mHostActivity != activity;
        mHostActivity = activity;
        Dialog virtualToastDialog = toastType == BaseToastManager.PLAIN_TOAST ? getVirtualPlainToastDialog(hostActivityChanged)
                : getVirtualTypeToastDialog(hostActivityChanged);
        virtualToastDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        virtualToastDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        virtualToastDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        virtualToastDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams lp = virtualToastDialog.getWindow().getAttributes();
        lp.width = windowParams.width;
        lp.height = windowParams.height;
        lp.windowAnimations = windowParams.windowAnimations;
        lp.gravity = toast.getGravity();
        lp.x = toast.getXOffset();
        lp.y = toast.getYOffset();
        ViewGroup content = virtualToastDialog.findViewById(android.R.id.content);
        if (toast.getView().getParent() != content) {
            if (toast.getView().getParent() != null) {
                ViewGroup parent = (ViewGroup) toast.getView().getParent();
                parent.removeView(toast.getView());
            }
            content.removeAllViews();
            virtualToastDialog.setContentView(toast.getView());
        }
        try {
            virtualToastDialog.show();
        } catch (WindowManager.BadTokenException e) {
            EasyLogger.e("bad token has happened when show virtual toast!");
            mHostActivity = null;
        }

        Runnable dismissRunnable = toastType == BaseToastManager.PLAIN_TOAST ? mDismissPlainToastRunnable
                : mDismissTypeToastRunnable;
        mDismissHandler.removeCallbacks(dismissRunnable);
        mDismissHandler.postDelayed(dismissRunnable, toast.getDuration() == Toast.LENGTH_SHORT ?
                DURATION_SHORT : DURATION_LONG);
    }


    public Dialog getVirtualPlainToastDialog(boolean hostActivityChanged) {
        if (mVirtualPlainToastDialog == null || hostActivityChanged) {
            mVirtualPlainToastDialog = new AppCompatDialog(mHostActivity, R.style.smart_show_virtual_toast_dialog);
        }
        return mVirtualPlainToastDialog;
    }

    public Dialog getVirtualTypeToastDialog(boolean hostActivityChanged) {
        if (mVirtualTypeToastDialog == null || hostActivityChanged) {
            mVirtualTypeToastDialog = new AppCompatDialog(mHostActivity, R.style.smart_show_virtual_toast_dialog);
        }
        return mVirtualTypeToastDialog;
    }


    private Runnable mDismissPlainToastRunnable = new Runnable() {
        @Override
        public void run() {
            dismissToastDialog(mVirtualPlainToastDialog);
        }
    };

    private Runnable mDismissTypeToastRunnable = new Runnable() {
        @Override
        public void run() {
            dismissToastDialog(mVirtualTypeToastDialog);
        }
    };

    private boolean isToastDialogShowing(Dialog toastDialog) {
        return toastDialog != null && toastDialog.isShowing();
    }

    private void dismissToastDialog(Dialog toastDialog) {
        if (isToastDialogShowing(toastDialog)) {
            toastDialog.dismiss();
        }
    }

    public void dismiss(int toastType) {
        dismissToastDialog(toastType == BaseToastManager.PLAIN_TOAST ? mVirtualPlainToastDialog : mVirtualTypeToastDialog);
    }

    public boolean isShowing(int toastType) {
        return isToastDialogShowing(toastType == BaseToastManager.PLAIN_TOAST ? mVirtualPlainToastDialog : mVirtualTypeToastDialog);
    }

    public void destroy(Activity activity) {
        if (mHostActivity == activity) {
            EasyLogger.d("recycle resource when host activity of virtual toast destroyed");
            mHostActivity = null;
            mVirtualPlainToastDialog = null;
            mVirtualTypeToastDialog = null;
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
