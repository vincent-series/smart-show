package com.coder.zzq.smartshow.toast.dummy;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;
/**
 * Created by 朱志强 on 2018/08/19.
 */
public final class DummyToast {

    private DummyToast() {

    }

    public static final long SHORT_SHOW = 2000;
    public static final long LONG_SHOW = 35000;
    public static final int ACTION_DISMISS_WINDOW = 0;

    private static int sAnimationStyle;
    private static long sDuration = SHORT_SHOW;
    private static PopupWindow sPopupWindow;
    private static DismissHandler sDismissHandler = new DismissHandler();
    private static long sStartTime;


    public static void show(View viewForToken, View contentView, int gravity, int xOffset, int yOffset, int duration) {
        sStartTime = System.currentTimeMillis();
        sDuration = (duration == Toast.LENGTH_SHORT) ? SHORT_SHOW : LONG_SHOW;
        if (getPopupWindow().getContentView() != contentView){
            getPopupWindow().setContentView(contentView);
        }
        sPopupWindow.showAtLocation(viewForToken,gravity,xOffset,yOffset);
        sDismissHandler.sendEmptyMessageDelayed(ACTION_DISMISS_WINDOW,sDuration);
    }


    public static PopupWindow getPopupWindow() {
        if (sPopupWindow == null) {
            sPopupWindow = new PopupWindow(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            sPopupWindow.setOutsideTouchable(false);
            sPopupWindow.setTouchable(false);
            sPopupWindow.setFocusable(false);
            sPopupWindow.setAnimationStyle(sAnimationStyle);
        }
        return sPopupWindow;
    }


    public static boolean isShowing() {
        return getPopupWindow().isShowing();
    }

    public static void dismiss() {
        getPopupWindow().dismiss();
    }


    public static void setWindowAnimationStyle(int res) {
        sAnimationStyle = res;
    }

    public static class DismissHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            long endTime = System.currentTimeMillis();
            if (endTime - sStartTime >= sDuration){
                sPopupWindow.dismiss();
            }
        }
    }

}
