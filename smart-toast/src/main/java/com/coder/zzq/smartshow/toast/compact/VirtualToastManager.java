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

@SuppressWarnings({"deprecation", "rawtypes"})
public final class VirtualToastManager {

    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_LONG = 3500;
    protected CompactToast mCompactToast;
    protected WeakReference<Dialog> mToastDialogReference;
    protected Handler mHandler = new Handler(Looper.getMainLooper());


    public void show(CompactToast newCompactToast) {
        mHandler.removeCallbacks(mShowToastRunnable);
        if (newCompactToast.getConfig().mTransition) {
            mShowToastRunnable.setNewCompactToast(newCompactToast);
            mHandler.postDelayed(mShowToastRunnable, 400);
        } else {
            showHelper(newCompactToast);
        }
    }

    private void showHelper(CompactToast newCompactToast) {
        Activity activity = ActivityStack.get(Utils::isUpdateActivityUIPermitted);

        if (activity == null) {
            EasyLogger.d("activity is can not show virtual toast dialog ,so do nothing but return.");
            return;
        }

        boolean positionChanged = mCompactToast != null && isPositionChanged(mCompactToast, newCompactToast);
        boolean aliasChanged = mCompactToast != null && !mCompactToast.getToastAlias().equals(newCompactToast.getToastAlias());
        mCompactToast = newCompactToast;
        Toast toast = mCompactToast.generateRealToast();
        Dialog dialog;
        if (mToastDialogReference == null || mToastDialogReference.get() == null || mToastDialogReference.get().getOwnerActivity() != activity) {
            dismiss();
            dialog = createDialog(activity, toast);
        } else if (mToastDialogReference.get().isShowing() && (aliasChanged || positionChanged)) {
            mToastDialogReference.get().dismiss();
            dialog = createDialog(activity, toast);
        } else {
            dialog = mToastDialogReference.get();
        }


        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.windowAnimations = android.R.style.Animation_Toast;
        lp.gravity = mCompactToast.getConfig().mGravity;
        lp.x = mCompactToast.getConfig().mXOffset;
        lp.y = toast.getYOffset();

        ViewGroup content = dialog.findViewById(android.R.id.content);
        if (toast.getView().getParent() != content) {
            if (toast.getView().getParent() != null) {
                ViewGroup parent = (ViewGroup) toast.getView().getParent();
                parent.removeView(toast.getView());
            }
            content.removeAllViews();
            dialog.setContentView(toast.getView());
        }


        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {
            EasyLogger.e("bad token has happened when show virtual toast!");
        }
        mHandler.removeCallbacks(mDismissToastRunnable);
        mHandler.postDelayed(mDismissToastRunnable, mCompactToast.getConfig().mDuration == Toast.LENGTH_SHORT ?
                DURATION_SHORT : DURATION_LONG);
    }

    private Dialog createDialog(Activity activity, Toast toast) {
        Dialog dialog;
        dialog = new AppCompatDialog(activity, R.style.smart_show_virtual_toast_dialog);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        dialog.setOwnerActivity(activity);
        dialog.setContentView(toast.getView());
        mToastDialogReference = new WeakReference<>(dialog);
        EasyLogger.d("virtual toast dialog" + Utils.getObjectDesc(mToastDialogReference) + "has created");
        return dialog;
    }

    private boolean isPositionChanged(CompactToast compactToast, CompactToast newCompactToast) {
        return compactToast.getConfig().mGravity != newCompactToast.getConfig().mGravity
                || compactToast.getConfig().mXOffset != newCompactToast.getConfig().mXOffset
                || compactToast.getConfig().mYOffset != newCompactToast.getConfig().mYOffset;
    }


    private final Runnable mDismissToastRunnable = VirtualToastManager::dismiss;


    private static class ShowRunnable implements Runnable {
        private CompactToast mNewCompactToast;

        public void setNewCompactToast(CompactToast newCompactToast) {
            mNewCompactToast = newCompactToast;
        }

        @Override
        public void run() {
            VirtualToastManager.get().showHelper(mNewCompactToast);
        }
    }

    private final ShowRunnable mShowToastRunnable = new ShowRunnable();

    public static void dismiss() {
        if (isShowing()) {
            get().mToastDialogReference.get().dismiss();
        }
    }

    public static boolean isShowing() {
        return hasCreated() && get().mToastDialogReference != null && get().mToastDialogReference.get() != null && get().mToastDialogReference.get().isShowing();
    }

    public static void destroy(Activity activity) {
        if (hasCreated()
                && get().mToastDialogReference != null
                && get().mToastDialogReference.get() != null
                && get().mToastDialogReference.get().getOwnerActivity() == activity) {
            EasyLogger.d("recycle resource when host activity" + Utils.getObjectDesc(activity) + "of virtual toast destroyed");
            dismiss();
            if (get().mToastDialogReference != null) {
                get().mToastDialogReference.clear();
            }
            get().mToastDialogReference = null;
            get().mHandler.removeCallbacks(get().mDismissToastRunnable);
            get().mHandler.removeCallbacks(get().mShowToastRunnable);
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

    public static void reset() {
        if (hasCreated()) {
            if (get().mToastDialogReference != null) {
                dismiss();
                get().mToastDialogReference.clear();
                get().mToastDialogReference = null;
                get().mCompactToast = null;
            }
        }
    }
}
