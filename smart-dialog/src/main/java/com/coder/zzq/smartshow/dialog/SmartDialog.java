package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.dialog.dialog.DialogCreator;


public class SmartDialog {
//    public static INotificationDialogBuilder notification(CharSequence msg) {
//        return NotificationDialogBuilder.getInstance().message(msg);
//    }
//
//    public static INotificationDialogBuilder notification(@StringRes int msgRes) {
//        return notification(SmartShow.getContext().getString(msgRes));
//    }
//
//
//    public static IEnsureDialogBuilder ensure(CharSequence msg) {
//        return EnsureDialogBuilder.getInstance().message(msg);
//    }
//
//    public static IEnsureDialogBuilder ensure(@StringRes int msgRes) {
//        return ensure(SmartShow.getContext().getString(msgRes));
//    }
//
//
//    public static IEnsureDialogBuilder ensureDelay(CharSequence msg) {
//        return EnsureDialogBuilder.getInstance()
//                .delaySeconds(10)
//                .message(msg).cancelableOnTouchOutside(false);
//    }
//
//
//    public static IInputTextDialogBuilder inputText() {
//        return InputTextDialogBuilder.getInstance().title("输入框").cancelableOnTouchOutside(false);
//    }
//
//
//    public static ILoadingDialogBuilder loading(CharSequence msg) {
//        return new LoadingDialogBuilder()
//                .msg(msg);
//    }


    public static boolean show(Activity activity, DialogCreator dialogCreator) {
        if (dialogCreator == null) {
            return false;
        }

        Dialog dialog = dialogCreator.getDialog(activity);

        if (dialog != null) {
            try {
                dialog.show();
                return true;
            } catch (WindowManager.BadTokenException e) {
                EasyLogger.e("BadToken has happened when show dialog" + dialog.getClass().getSimpleName());
            }
        }

        return false;
    }

}
