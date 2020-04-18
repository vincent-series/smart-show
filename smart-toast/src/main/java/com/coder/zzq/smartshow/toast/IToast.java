package com.coder.zzq.smartshow.toast;

import androidx.annotation.NonNull;


public interface IToast<ToastType extends IToast, ShowApi> {
    ToastType goForAnotherPage();

    ToastType forceDismissWhenLeave(boolean forceDismiss);

    ToastType addArg(@NonNull String argName, Object argValue);


    ShowApi apply();
}
