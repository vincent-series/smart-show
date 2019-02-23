package com.coder.zzq.smartshow.core.lifecycle;

import android.app.Activity;

public interface ITopbarCallback extends IBarCallback {
    void adjustTopbarContainerIfNecessary(final Activity activity);
}
