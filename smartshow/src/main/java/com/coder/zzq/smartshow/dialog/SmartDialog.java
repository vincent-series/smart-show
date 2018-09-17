package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.Utils;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;

public class SmartDialog {
    public static void loading() {
        Activity activity = ActivityStack.getTop();
        if (activity != null) {
            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setView(R.layout.loading)
                    .create();
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

            dialog.getWindow().setBackgroundDrawableResource(R.drawable.ss_type_info_toast_bg);
            dialog.show();
        }

    }
}
