package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.support.annotation.StringRes;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.lifecycle.ActivityStack;
import com.coder.zzq.smartshow.dialog.type.IEnsureDelayDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.IEnsureDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.IInputTextDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.ILoadingDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.INotificationDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.impl.EnsureDelayDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.impl.EnsureDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.impl.InputTextDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.impl.LoadingDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.impl.NotificationDialogBuilder;

public class SmartDialog {

    public static INotificationDialogBuilder notification(CharSequence msg) {
        return new NotificationDialogBuilder()
                .message(msg);

    }

    public static INotificationDialogBuilder notification(@StringRes int msgRes) {
        return notification(SmartShow.getContext().getString(msgRes));
    }


    public static IEnsureDialogBuilder ensure(CharSequence msg) {
        return new EnsureDialogBuilder()
                .message(msg);
    }

    public static IEnsureDialogBuilder ensure(@StringRes int msgRes) {
        return ensure(SmartShow.getContext().getString(msgRes));
    }


    public static IEnsureDelayDialogBuilder ensureDelay(CharSequence msg) {
        return new EnsureDelayDialogBuilder()
                .message(msg).cancelableOnTouchOutside(false);
    }


    public static IInputTextDialogBuilder inputText() {
        return new InputTextDialogBuilder().title("输入框").cancelableOnTouchOutside(false);
    }


    public static ILoadingDialogBuilder loading(CharSequence msg) {
        return new LoadingDialogBuilder()
                .msg(msg);
    }


    public static void show(Activity activity, DialogCreator dialogCreator, int bizTag) {
        if (activity == null || dialogCreator == null
                || activity.isFinishing() || isActivityDestroyed(activity)) {
            return;
        }
        Dialog dialog = dialogCreator.createDialog(bizTag);

        if (dialog != null) {
            try {
                dialog.show();
            } catch (WindowManager.BadTokenException e) {
                EasyLogger.e("BadTokenException has happened !" + e.toString());
            }
        }

    }

    private static boolean isActivityDestroyed(Activity activity) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                activity.isDestroyed() : !ActivityStack.isInStack(activity);
    }

}
