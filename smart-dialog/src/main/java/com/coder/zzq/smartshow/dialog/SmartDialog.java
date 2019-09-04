package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.toolkit.log.EasyLogger;

public abstract class SmartDialog<NestedDialog extends Dialog> {
    static {
        SmartShow.setDialogCallback(new DialogCallback());
    }

    protected NestedDialog mNestedDialog;

    public SmartDialog() {
        DialogRecycleManager.saveDialog(this);
    }

    public boolean showInActivity(Activity activity) {
        return show(activity, Utils.isUpdateActivityUIPermitted(activity));
    }

    public boolean showInFragment(Fragment fragment) {
        return show(fragment == null ? null : fragment.getActivity(), Utils.isCanShowDialogInFragment(fragment));
    }


    private boolean show(Activity activity, boolean canUpdateUI) {
        if (!canUpdateUI) {
            EasyLogger.d("do nothing when the condition is not met!");
            return false;
        }


        if (mNestedDialog == null || mNestedDialog.getOwnerActivity() != activity) {
            mNestedDialog = Utils.requireNonNull(createDialog(activity), "the method createDialog must return a non-null dialog!");
            mNestedDialog.setOwnerActivity(activity);
            EasyLogger.d(logForCreated());
        } else {
            resetDialogWhenShowAgain(mNestedDialog);
            EasyLogger.d(logForReuse());
        }


        try {
            mNestedDialog.show();
            return true;
        } catch (WindowManager.BadTokenException e) {
            EasyLogger.e("BadToken has happened when show dialog: \n" + Utils.getObjectDesc(mNestedDialog));
            return false;
        }

    }

    @NonNull
    protected abstract NestedDialog createDialog(Activity activity);

    protected void resetDialogWhenShowAgain(NestedDialog dialog) {

    }

    protected boolean recycle(Activity activity) {
        if (mNestedDialog != null && mNestedDialog.getOwnerActivity() == activity) {
            EasyLogger.d(logForRecycle());
            mNestedDialog = null;
            return true;
        }


        return false;
    }

    public boolean dismiss() {
        if (mNestedDialog == null || !mNestedDialog.isShowing()) {
            EasyLogger.d("do nothing but recycle when conditions not available!");
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

    public boolean isShowing() {
        return mNestedDialog != null && mNestedDialog.isShowing();
    }



    public void onScreenOrientationChanged() {

    }


    private String logForCreated() {
        return "create a dialog" + Utils.getObjectDesc(mNestedDialog) + "of" + Utils.getObjectDesc(this);
    }

    private String logForReuse() {
        return "the dialog" + Utils.getObjectDesc(mNestedDialog) + "of" + Utils.getObjectDesc(this) + "reused again";
    }

    private String logForRecycle() {
        return "the dialog" + Utils.getObjectDesc(mNestedDialog) + "of" + Utils.getObjectDesc(this) + "has recycled";
    }
}
