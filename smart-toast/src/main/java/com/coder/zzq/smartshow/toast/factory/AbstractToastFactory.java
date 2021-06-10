package com.coder.zzq.smartshow.toast.factory;

import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.Constants;
import com.coder.zzq.smartshow.toast.compact.SafeHandler;
import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.toolkit.log.EasyLogger;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

@SuppressWarnings("deprecation")
public abstract class AbstractToastFactory<TOAST_CONFIG extends BaseToastConfig> implements ToastFactory<TOAST_CONFIG> {
    protected int mDefaultYOffsetWhenBottom;
    protected WeakReference<Toast> mCachedToastReference;

    @Override
    public final Toast produceToast(TOAST_CONFIG newConfig) {

        Toast toast = mCachedToastReference == null ? null : mCachedToastReference.get();

        if (toast != null && !Utils.equals(toast.getView().getTag(), Utils.isNotificationPermitted())) {
            toast = null;
            EasyLogger.d("clear cache because of alias changed");
        }

        if (toast != null && needCancel(toast, newConfig)) {
            toast.cancel();
            toast = null;
            EasyLogger.d("clear cache because of position changed");
        }

        boolean rebuild = (toast == null);
        if (rebuild) {
            EasyLogger.d("create a new toast instance");
            toast = createToastInstance();
            mCachedToastReference = new WeakReference<>(toast);
        }

        toast.setGravity(
                newConfig.mGravity,
                newConfig.mXOffset,
                newConfig.mYOffset == Constants.DEFAULT_VALUE ? mDefaultYOffsetWhenBottom : newConfig.mYOffset
        );

        if (toast.getView() == null) {
            toast.setView(setupConfig(null, newConfig));
        } else {
            setupConfig(toast.getView(), newConfig);
        }

        toast.getView().setTag(Utils.isNotificationPermitted());

        if (rebuild && (Build.VERSION.SDK_INT == Build.VERSION_CODES.N
                || Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1)) {
            injectSafeHandler(toast);
        }

        return toast;
    }

    private boolean needCancel(Toast toast, BaseToastConfig newConfig) {
        return toast.getView().getWindowVisibility() == View.VISIBLE
                && isToastPositionChanged(toast, newConfig);
    }

    protected View setupConfig(View rootView, TOAST_CONFIG toastConfig) {
        return rootView;
    }


    private boolean isToastPositionChanged(Toast toast, BaseToastConfig newConfig) {
        return toast.getGravity() != newConfig.mGravity
                || toast.getXOffset() != newConfig.mXOffset
                || toast.getYOffset() != (newConfig.mYOffset == Constants.DEFAULT_VALUE ? mDefaultYOffsetWhenBottom : newConfig.mYOffset);
    }

    protected Toast createToastInstance() {
        Toast toast = new Toast(Toolkit.getContext());
        mDefaultYOffsetWhenBottom = toast.getYOffset();
        return toast;
    }

    private void injectSafeHandler(Toast toast) {
        EasyLogger.d("inject safe handler ->" + this);
        try {
            Field tnField = Toast.class.getDeclaredField("mTN");
            tnField.setAccessible(true);
            Object tn = tnField.get(toast);
            Field handlerField = tn.getClass().getDeclaredField("mHandler");
            handlerField.setAccessible(true);
            Handler handlerOfTn = (Handler) handlerField.get(tn);
            handlerField.set(tn, new SafeHandler(handlerOfTn, toast.getView()));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
        if (mCachedToastReference != null) {
            if (mCachedToastReference.get() != null) {
                mCachedToastReference.get().cancel();
                mCachedToastReference.clear();
            }
            mCachedToastReference = null;
            EasyLogger.d("the toast factory called ->" + Utils.getObjectDesc(this));
        }
    }
}
