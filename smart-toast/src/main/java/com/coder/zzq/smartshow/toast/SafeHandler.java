package com.coder.zzq.smartshow.toast;


import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

final class SafeHandler extends Handler {
    private Handler mNestedHandler;
    private View mToastView;

    public SafeHandler(Handler nestedHandler, View toastView) {
        mNestedHandler = nestedHandler;
        mToastView = toastView;
    }

    @Override
    public void dispatchMessage(Message msg) {
        try {

            if (mToastView.getParent() != null && (mToastView.getParent() instanceof ViewGroup)) {
                ((ViewGroup) mToastView.getParent()).removeView(mToastView);
            }

            super.dispatchMessage(msg);

        } catch (WindowManager.BadTokenException | IllegalStateException ex) {
            //handle BadTokenException for android system bug on 7.1.x
            //handle IllegalStateException for some devices
        }
    }

    @Override
    public void handleMessage(Message msg) {
        mNestedHandler.handleMessage(msg);
    }
}
