package com.coder.zzq.smartshow.toast.compact;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialog;

import com.coder.zzq.smartshow.toast.R;
import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.toolkit.lifecycle.ActivityStack;
import com.coder.zzq.toolkit.log.EasyLogger;

import java.lang.ref.WeakReference;

public final class VirtualToastManager {

    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_LONG = 3500;
    protected CompactToast mCompactToast;
    protected WeakReference<Dialog> mVirtualToastDialogReference;
    protected Handler mHandler = new Handler(Looper.getMainLooper());


    public void show(CompactToast compactToast) {
        mCompactToast = compactToast;
        Activity activity = ActivityStack.getTop();
        if (!Utils.isUpdateActivityUIPermitted(activity)) {
            EasyLogger.d("activity is can not show virtual toast dialog ,so do nothing but return.");
            return;
        }
        prepareDialog(activity);

        mVirtualToastDialogReference.get().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mVirtualToastDialogReference.get().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        mVirtualToastDialogReference.get().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        mVirtualToastDialogReference.get().getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        WindowManager.LayoutParams lp = mVirtualToastDialogReference.get().getWindow().getAttributes();

        lp.windowAnimations = android.R.style.Animation_Toast;
        lp.gravity = mCompactToast.getToast().getGravity();
        lp.x = mCompactToast.getToast().getXOffset();
        lp.y = mCompactToast.getToast().getYOffset();
        ViewGroup content = mVirtualToastDialogReference.get().findViewById(android.R.id.content);
        if (mCompactToast.getToast().getView().getParent() != content) {
            if (mCompactToast.getToast().getView().getParent() != null) {
                ViewGroup parent = (ViewGroup) mCompactToast.getToast().getView().getParent();
                parent.removeView(mCompactToast.getToast().getView());
            }
            content.removeAllViews();
            mVirtualToastDialogReference.get().setContentView(mCompactToast.getToast().getView());
        }
        try {
            mVirtualToastDialogReference.get().show();
        } catch (WindowManager.BadTokenException e) {
            EasyLogger.e("bad token has happened when show virtual toast!");
        }
        mHandler.removeCallbacks(mDismissToastRunnable);
        mHandler.postDelayed(mDismissToastRunnable, mCompactToast.getToast().getDuration() == Toast.LENGTH_SHORT ?
                DURATION_SHORT : DURATION_LONG);
    }


    public void prepareDialog(Activity activity) {
        if (mVirtualToastDialogReference == null || mVirtualToastDialogReference.get() == null || mVirtualToastDialogReference.get().getOwnerActivity() != activity) {
            mVirtualToastDialogReference = new WeakReference<>(new AppCompatDialog(activity, R.style.smart_show_virtual_toast_dialog));
            mVirtualToastDialogReference.get().setOwnerActivity(activity);
            EasyLogger.d("virtual toast dialog" + Utils.getObjectDesc(mVirtualToastDialogReference) + "has created");
        }
    }


    private final Runnable mDismissToastRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };


    public static void dismiss() {
        if (isShowing()) {
            get().mVirtualToastDialogReference.get().dismiss();
        }
    }

    public static boolean isShowing() {
        return hasCreated() && get().mVirtualToastDialogReference != null && get().mVirtualToastDialogReference.get() != null && get().mVirtualToastDialogReference.get().isShowing();
    }

    public static void destroy(Activity activity) {
        if (hasCreated()
                && get().mVirtualToastDialogReference != null
                && get().mVirtualToastDialogReference.get() != null
                && get().mVirtualToastDialogReference.get().getOwnerActivity() == activity) {
            EasyLogger.d("recycle resource when host activity" + Utils.getObjectDesc(activity) + "of virtual toast destroyed");
            dismiss();
            if (get().mVirtualToastDialogReference != null) {
                get().mVirtualToastDialogReference.clear();
            }
            get().mVirtualToastDialogReference = null;
            get().mHandler.removeCallbacks(get().mDismissToastRunnable);
            get().mCompactToast.discard();
            get().mCompactToast = null;
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

    public static CompactToast getWrappedToast() {
        if (!hasCreated()) {
            return null;
        }

        return get().mCompactToast;
    }
}
