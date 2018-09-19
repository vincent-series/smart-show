package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public static class Builder {
        private String mMsg;

        public LoadingDialog create() {
            Activity activity = ActivityStack.getTop();
            if (activity != null) {
                View contentView = LayoutInflater.from(SmartShow.getContext()).inflate(R.layout.loading, null);
                LoadingDialog loadingDialog = new LoadingDialog(activity,R.style.loading_dialog);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingDialog.setContentView(contentView,lp);
                loadingDialog.setCancelable(true);
                loadingDialog.setCanceledOnTouchOutside(false);

                return loadingDialog;
            }
            return null;
        }

    }
}
