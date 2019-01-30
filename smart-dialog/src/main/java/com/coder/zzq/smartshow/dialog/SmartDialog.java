package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.creator.type.IDialogCreator;

public class SmartDialog {
    private Dialog mNestedDialog;
    private IDialogCreator mDialogCreator;
    private boolean mReuseDialog = true;

    public boolean show(Activity activity) {
        if (!Utils.isUpdateActivityUIPermitted(activity) ||
                (mNestedDialog == null && mDialogCreator == null)) {
            return false;
        }

        if (mNestedDialog == null || !mReuseDialog) {
            mNestedDialog = mDialogCreator.createDialog(activity);
            EasyLogger.d("create a new dialog:\n " + mNestedDialog);
        } else {
            EasyLogger.d("reuse dialog:\n " + mNestedDialog);
        }

        if (mNestedDialog != null) {
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


    public SmartDialog reuse(boolean reuseDialog) {
        mReuseDialog = reuseDialog;
        return this;
    }

    public SmartDialog dialogCreator(IDialogCreator dialogCreator) {
        if (mDialogCreator != dialogCreator) {
            mDialogCreator = dialogCreator;
            mNestedDialog = null;
        }
        return this;
    }

}
