package com.coder.zzq.smartshow.toast;

import androidx.annotation.NonNull;


public interface IToast<ToastType extends IToast, ShowApi> {
    ToastType leave();

    ToastType addArg(@NonNull String argName, Object argValue);


    ShowApi apply();
}
