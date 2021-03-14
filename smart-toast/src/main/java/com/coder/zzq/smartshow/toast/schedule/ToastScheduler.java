package com.coder.zzq.smartshow.toast.schedule;

import com.coder.zzq.smartshow.toast.compact.CompactToast;
import com.coder.zzq.toolkit.Utils;

public class ToastScheduler {
    private static final ToastScheduler sScheduler = new ToastScheduler();
    private CompactToast mCurrentToast;

    public static ToastScheduler get() {
        return sScheduler;
    }

    public synchronized void schedule(CompactToast newToast) {
        if (mCurrentToast != null && (mCurrentToast.isToastShowing() && !Utils.equals(mCurrentToast.getToastAlias(), newToast.getToastAlias()))) {
            mCurrentToast.discard();
        }
        mCurrentToast = newToast;
        if (!mCurrentToast.isToastShowing()) {
            mCurrentToast.show();
        }
    }

    public synchronized void cancelCurrentIfNecessary() {
        if (mCurrentToast != null && mCurrentToast.getConfig().mCancelOnActivityExit && mCurrentToast.isToastShowing()) {
            mCurrentToast.discard();
            mCurrentToast = null;
        }
    }

    public synchronized boolean isCurrentShowing() {
        return mCurrentToast != null && mCurrentToast.isToastShowing();
    }

    public synchronized void dismiss() {
        if (mCurrentToast != null) {
            mCurrentToast.discard();
        }
    }
}
