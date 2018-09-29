package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.Utils;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;

class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private Activity mActivity;
    private TextView mMsgView;


    public void changeMsg(String msg) {
        mMsgView.setText(msg);
    }


    public void destroy() {
        mActivity = null;
        mMsgView = null;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public boolean isInvalid() {
        return mActivity != ActivityStack.getTop();
    }

    public static class Builder {

        public LoadingDialog create(String msg) {
            Activity activity = ActivityStack.getTop();
            if (activity != null) {
                msg = Utils.isEmpty(msg) ? "加载中..." : msg;
                LoadingDialog loadingDialog = new LoadingDialog(activity, R.style.smart_show_loading_dialog);
                loadingDialog.mActivity = activity;
                View contentView = LayoutInflater.from(SmartShow.getContext()).inflate(R.layout.loading, null);
                TextView msgView = contentView.findViewById(R.id.loading_message);
                loadingDialog.mMsgView = msgView;
                msgView.setText(msg);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingDialog.setContentView(contentView, lp);
                loadingDialog.setCancelable(true);
                loadingDialog.setCanceledOnTouchOutside(false);

                return loadingDialog;
            }
            return null;
        }

    }
}
