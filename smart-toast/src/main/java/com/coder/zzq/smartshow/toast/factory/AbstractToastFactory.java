package com.coder.zzq.smartshow.toast.factory;

import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.compact.CompactToast;
import com.coder.zzq.smartshow.toast.Constants;
import com.coder.zzq.toolkit.Toolkit;

public abstract class AbstractToastFactory<TOAST_CONFIG extends BaseToastConfig> implements ToastFactory<TOAST_CONFIG> {
    protected int mDefaultYOffsetWhenBottom;
    protected CompactToast mCachedCompactToast;

    @Override
    public final CompactToast produceToast(TOAST_CONFIG newConfig) {

        if (mCachedCompactToast == null || mCachedCompactToast.isDiscard()) {
            mCachedCompactToast = new CompactToast(createToastInstance(), provideToastAlias(), newConfig);
        } else if (mCachedCompactToast.isToastShowing() && isToastPositionChanged(mCachedCompactToast.getConfig(), newConfig)) {
            mCachedCompactToast.discard();
            mCachedCompactToast = new CompactToast(createToastInstance(), provideToastAlias(), newConfig);
        }

        mCachedCompactToast.updateConfig(newConfig);
        mCachedCompactToast.getToast().setGravity(
                newConfig.mGravity,
                newConfig.mXOffset,
                newConfig.mYOffset == Constants.DEFAULT_VALUE ? mDefaultYOffsetWhenBottom : newConfig.mYOffset
        );

        if (mCachedCompactToast.getToast().getView() == null) {
            mCachedCompactToast.getToast().setView(setupConfig(null, newConfig));
        } else {
            setupConfig(mCachedCompactToast.getToast().getView(), newConfig);
        }

        return mCachedCompactToast;
    }

    protected View setupConfig(View rootView, TOAST_CONFIG toastConfig) {
        return rootView;
    }


    protected abstract String provideToastAlias();


    private boolean isToastPositionChanged(BaseToastConfig srcConfig, BaseToastConfig newConfig) {
        return srcConfig.mGravity != newConfig.mGravity
                || srcConfig.mXOffset != newConfig.mXOffset
                || srcConfig.mYOffset != newConfig.mYOffset;
    }

    protected Toast createToastInstance() {
        Toast toast = new Toast(Toolkit.getContext());
        mDefaultYOffsetWhenBottom = toast.getYOffset();
        return toast;
    }
}
