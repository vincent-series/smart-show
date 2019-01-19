package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.StringRes;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.dialog.DialogCreator;
import com.coder.zzq.smartshow.dialog.dialog.DialogWrapper;


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


    public static boolean show(Activity activity, DialogCreator dialogCreator, DialogWrapper dialogWrapper) {
        if (activity == null || dialogCreator == null || activity.isFinishing() || Utils.isActivityDestroyed(activity)) {
            return false;
        }

        Dialog dialog = getDialog(activity, dialogCreator, dialogWrapper);

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

    private static Dialog getDialog(Activity activity, DialogCreator dialogCreator, DialogWrapper dialogWrapper) {
        if (dialogWrapper == null) {
            EasyLogger.d("dialog wrapper == null and create dialog from dialogCreator");
            return dialogCreator.createDialog(activity);
        } else if (dialogWrapper.getDialog() == null) {
            EasyLogger.d("dialog wrapper != null but not contain dialog,so create dialog from dialogCreator");
            dialogWrapper.setDialog(dialogCreator.createDialog(activity));
            return dialogWrapper.getDialog();
        } else {
            EasyLogger.d("get dialog form wrapper: dialog -> " + (dialogWrapper.getDialog() == null ? "null" : dialogWrapper.getDialog().toString()));
            dialogCreator.resetDialog(dialogWrapper.getDialog());
            return dialogWrapper.getDialog();
        }
    }

}
