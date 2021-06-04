package com.coder.zzq.smartshow.toast.factory;

import android.widget.Toast;

public interface ToastFactory<TOAST_CONFIG extends BaseToastConfig> {
    Toast produceToast(TOAST_CONFIG newConfig);

    String toastAlias();

    void reset();
}