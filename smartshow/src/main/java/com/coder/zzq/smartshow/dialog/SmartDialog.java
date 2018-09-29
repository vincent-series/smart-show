package com.coder.zzq.smartshow.dialog;

import android.support.annotation.StringRes;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.dialog.type.DialogBuilder;
import com.coder.zzq.smartshow.dialog.type.IMessageDialog;

public class SmartDialog {
    public static void loading(String msg) {
        DialogDelegate.get().loading(msg);
    }

    public static void loading() {
        DialogDelegate.get().loading("加载中...");
    }




    public static IMessageDialog message(String msg) {
        return DialogDelegate.get().message(msg);
    }

    public static IMessageDialog message(@StringRes int msgRes) {
        return message(SmartShow.getContext().getString(msgRes));
    }


}
