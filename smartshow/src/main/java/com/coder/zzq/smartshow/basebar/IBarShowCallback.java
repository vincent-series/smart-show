package com.coder.zzq.smartshow.basebar;

public interface IBarShowCallback<Bar> {
    void onShown(Bar sb);
    void onDismissed(Bar sb, int event);
}
