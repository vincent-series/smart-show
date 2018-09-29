package com.coder.zzq.smartshow.toast;


import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

class SafeHandler extends Handler {
    private Handler mNestedHandler;

    public SafeHandler(Handler nestedHandler) {
        mNestedHandler = nestedHandler;
    }

    @Override
    public void dispatchMessage(Message msg) {
        try {
            super.dispatchMessage(msg);
        } catch (WindowManager.BadTokenException e) {
        }
    }

    @Override
    public void handleMessage(Message msg) {
        mNestedHandler.handleMessage(msg);
    }
}
