package com.coder.zzq.smartshow.dialog;

import android.view.View;

public abstract class ConfirmDelayCallback implements View.OnAttachStateChangeListener, Runnable {
    public abstract void reset();
}
