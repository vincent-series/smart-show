package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.creator.type.IDialogCreator;

public final class SmartDialog {
    private Dialog mNestedDialog;
    private IDialogCreator mDialogCreator;
    private boolean mReuseDialog = true;

    private SmartDialog() {

    }

    public static SmartDialog newInstance(IDialogCreator dialogCreator) {
        SmartDialog smartDialog = new SmartDialog();
        smartDialog.mDialogCreator = Utils.requireNonNull(dialogCreator, "dialog creator can not null!");
        return smartDialog;
    }

    public boolean show(Activity activity) {
        if (!Utils.isUpdateActivityUIPermitted(activity) ||
                (mNestedDialog == null && mDialogCreator == null)) {
            EasyLogger.d("do nothing but recycle when conditions not available!");
            recycle();
            return false;
        }

        if (mNestedDialog == null || !mReuseDialog) {
            mNestedDialog = mDialogCreator.createDialog(activity);
            EasyLogger.d("create a new dialog:\n " + mNestedDialog);
        } else {
            EasyLogger.d("reuse dialog:\n " + mNestedDialog);
        }

        if (mNestedDialog != null) {
            mDialogCreator.resetDialogPerShow(mNestedDialog);
            try {
                mNestedDialog.show();
                return true;
            } catch (WindowManager.BadTokenException e) {
                EasyLogger.e("BadToken has happened when show dialog: \n" + mNestedDialog.getClass().getSimpleName());
                return false;
            }
        }

        return false;
    }


    public boolean dismiss(Activity activity) {
        if (!Utils.isUpdateActivityUIPermitted(activity) ||
                (mNestedDialog == null || !mNestedDialog.isShowing())) {
            EasyLogger.d("do nothing but recycle when conditions not available!");
            recycle();
            return false;
        }

        try {
            mNestedDialog.dismiss();
            return true;
        } catch (IllegalStateException e) {
            EasyLogger.d("IllegalStateException has happened when show dialog:\n" + mNestedDialog);
            return false;
        }
    }

    private void recycle() {
        mNestedDialog = null;
        mDialogCreator = null;
    }

    public SmartDialog reuse(boolean reuseDialog) {
        mReuseDialog = reuseDialog;
        return this;
    }

    public boolean isShowing() {
        return mNestedDialog != null && mNestedDialog.isShowing();
    }

    public boolean doSomething(Activity activity, DoSomethingCallback callback) {
        if (!Utils.isUpdateActivityUIPermitted(activity) || mNestedDialog == null || callback == null) {
            EasyLogger.d("do nothing but recycle when conditions not available!");
            recycle();
            return false;
        }

        callback.doSomething(mNestedDialog);

        return true;
    }

    public interface DoSomethingCallback {
        void doSomething(Dialog nestedDialog);
    }
}
