package com.coder.zzq.smartshow.toast;

import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.toolkit.log.EasyLogger;

/**
 * Created by 朱志强 on 2018/9/8.
 */
final class ToastManager {
    private AbstractToast mCurrentToast;

    private ToastManager() {

    }

    private static ToastManager sToastManager;


    static ToastManager get() {
        if (sToastManager == null) {
            sToastManager = new ToastManager();
        }

        return sToastManager;
    }

    public static boolean hasCreated() {
        return sToastManager != null;
    }


    public boolean isDismissOnLeave() {
        return !Utils.isNotificationPermitted() || (mCurrentToast != null && mCurrentToast.isForceDismissWhenLeave());
    }

    public boolean isShowing() {
        return mCurrentToast != null && mCurrentToast.isShowing();
    }

    public void dismiss() {
        if (mCurrentToast != null && mCurrentToast.isShowing()) {
            mCurrentToast.hideUI();
        }
    }


    public boolean needShow(AbstractToast toast) {
        boolean needShow = !isShowing() || mCurrentToast.different(toast);
        if (needShow) {
            EasyLogger.d("need to show a new toast " + Utils.getObjectDesc(toast));
            dismiss();
            ToastCache.cacheRecord(mCurrentToast);
            mCurrentToast = toast;
        } else {
            ToastCache.cacheRecord(toast);
            EasyLogger.d("no need to show the toast,discard and cached it " + Utils.getObjectDesc(toast));
        }

        return needShow;
    }

    public boolean goForAnotherPage() {
        return mCurrentToast != null && mCurrentToast.mGoForAnotherPage;
    }
}