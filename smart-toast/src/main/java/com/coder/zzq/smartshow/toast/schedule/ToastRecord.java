package com.coder.zzq.smartshow.toast.schedule;

import com.coder.zzq.smartshow.toast.factory.BaseToastConfig;

public class ToastRecord {
    private String mToastAlias;
    private BaseToastConfig mToastConfig;

    public String getToastAlias() {
        return mToastAlias;
    }

    public ToastRecord setToastAlias(String toastAlias) {
        mToastAlias = toastAlias;
        return this;
    }

    public BaseToastConfig getToastConfig() {
        return mToastConfig;
    }

    public ToastRecord setToastConfig(BaseToastConfig toastConfig) {
        mToastConfig = toastConfig;
        return this;
    }
}
