package com.coder.zzq.smartshow.toast.schedule;

import com.coder.zzq.smartshow.toast.compact.CompactToast;

@SuppressWarnings("rawtypes")
public class ToastScheduler {
    private static final ToastScheduler sScheduler = new ToastScheduler();

    public static ToastScheduler get() {
        return sScheduler;
    }

    private CompactToast mCurrentToast;

    public synchronized void schedule(CompactToast newToast) {
        if (mCurrentToast != null && !mCurrentToast.getToastAlias().equals(newToast.getToastAlias())) {
            mCurrentToast.reset();
        }
        newToast.show();
        mCurrentToast = newToast;
    }

    public synchronized void reschedule(int tag) {
        if (mCurrentToast != null && tag == mCurrentToast.tag()) {
            mCurrentToast.show();
        }
    }

    public synchronized void dismissWhenSetCancelTag() {
        if (mCurrentToast != null && mCurrentToast.getConfig().mCancelOnActivityExit && !mCurrentToast.getConfig().mTransition) {
            mCurrentToast.reset();
        }
    }


    public synchronized void dismiss() {
        if (mCurrentToast != null) {
            mCurrentToast.reset();
        }
    }
}
