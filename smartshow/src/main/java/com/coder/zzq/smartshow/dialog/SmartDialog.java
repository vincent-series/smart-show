package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.Utils;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;

public class SmartDialog {
    public static void loading() {
        new LoadingDialog.Builder().create().show();
    }
}
