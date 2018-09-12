package com.coder.zzq.smartshow.snackbar.base;

public interface IBarShowCallback<Bar> {
    void onShown(Bar sb);
    void onDismissed(Bar sb, int event);
}
