package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.dialog.creator.type.impl.DialogCreator;


public class SmartDialog {

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
