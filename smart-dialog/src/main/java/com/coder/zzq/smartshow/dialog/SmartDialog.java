package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.cache.DialogCacheContainer;
import com.coder.zzq.smartshow.dialog.type.IInputTextDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.ILoadingDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.INotificationDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.impl.InputTextDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.impl.LoadingDialogBuilder;
import com.coder.zzq.smartshow.dialog.type.impl.MessageDialogBuilder;

public class SmartDialog {
    static {
        SmartShow.setDialogCallback(new DialogCallback());
    }

    public static INotificationDialogBuilder messageDialog() {
        return new MessageDialogBuilder();
    }

    public static IInputTextDialogBuilder inputText() {
        return new InputTextDialogBuilder().title("输入框").cancelableOnTouchOutside(false);
    }


    public static ILoadingDialogBuilder loading(CharSequence msg) {
        return new LoadingDialogBuilder()
                .msg(msg);
    }


    public static <D extends Dialog> boolean show(Activity activity, DialogCreator<D> dialogCreator, int tag) {
        boolean showSucc = false;
        if (activity == null || dialogCreator == null
                || activity.isFinishing() || Utils.isActivityDestroyed(activity)) {
            return showSucc;
        }

        D dialog = (D) DialogCacheContainer.retrieveCachedDialog(activity, tag);
        if (dialog == null) {
            dialog = dialogCreator.createDialog(activity);
            DialogCacheContainer.cache(dialog, activity, tag);
            EasyLogger.d("create and cache dialog ->" + dialog.hashCode());
        } else {
            dialogCreator.resetDialog(dialog);
            EasyLogger.d("reset dialog ->" + dialog.hashCode());
        }

        if (dialog != null) {
            try {
                dialog.show();
                showSucc = true;
            } catch (WindowManager.BadTokenException e) {
                showSucc = false;
                EasyLogger.e("BadTokenException has happened !" + e.toString());
            }
        }

        return showSucc;

    }

}
