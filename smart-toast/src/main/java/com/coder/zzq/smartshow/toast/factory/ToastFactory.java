package com.coder.zzq.smartshow.toast.factory;

import com.coder.zzq.smartshow.toast.compact.CompactToast;

public interface ToastFactory<TOAST_CONFIG extends BaseToastConfig> {
    CompactToast produceToast(TOAST_CONFIG newConfig);
}