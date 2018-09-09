package com.coder.zzq.smartshow.bar.base;

public interface IBarShowCallback<Bar> {
    void onShown(Bar sb);
    void onDismissed(Bar sb, int event);
}
