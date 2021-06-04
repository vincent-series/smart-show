package com.coder.zzq.smartshow.toast.compact;

import android.view.ViewGroup;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.factory.BaseToastConfig;
import com.coder.zzq.smartshow.toast.factory.ToastFactory;
import com.coder.zzq.toolkit.Utils;

@SuppressWarnings("deprecation")
public class CompactToast<CONFIG_TYPE extends BaseToastConfig> {
    private final ToastFactory<CONFIG_TYPE> mToastFactory;
    private CONFIG_TYPE mConfig;

    public CompactToast(ToastFactory<CONFIG_TYPE> toastFactory, CONFIG_TYPE config) {
        mToastFactory = toastFactory;
        mConfig = config;
    }

    public String getToastAlias() {
        return mToastFactory.toastAlias();
    }

    public CONFIG_TYPE getConfig() {
        return mConfig;
    }

    public ToastFactory<CONFIG_TYPE> getToastFactory() {
        return mToastFactory;
    }

    public void show() {
        if (Utils.isNotificationPermitted()) {
            generateRealToast().show();
        } else {
            VirtualToastManager.get().show(this);
        }
    }

    public Toast generateRealToast() {
        Toast toast = mToastFactory.produceToast(mConfig);
        if (toast.getView().getParent() != null && toast.getView().getParent() instanceof ViewGroup) {
            ((ViewGroup) toast.getView().getParent()).removeAllViews();
        }
        return toast;
    }

    public void reset() {
        mToastFactory.reset();
        VirtualToastManager.reset();
    }

    public int tag() {
        return hashCode();
    }
}
