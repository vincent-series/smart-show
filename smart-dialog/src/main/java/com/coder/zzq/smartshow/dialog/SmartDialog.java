package com.coder.zzq.smartshow.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.SmartShow;
import com.coder.zzq.smartshow.core.Utils;

public abstract class SmartDialog<NestedDialog extends Dialog> {
    static {
        SmartShow.setDialogCallback(new DialogCallback());
    }

    protected NestedDialog mNestedDialog;
    protected Activity mOwnerActivity;

    public SmartDialog() {

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

        if (mNestedDialog == null || mOwnerActivity != activity) {
            mOwnerActivity = activity;
            mNestedDialog = Utils.requireNonNull(createDialog(mOwnerActivity), "the method createDialog must return a non-null dialog!");
            DialogRecycleManager.putDialog(this, mOwnerActivity);
            EasyLogger.d("create a new dialog:\n " + mNestedDialog);
        } else {
            resetDialogWhenShowAgain(mNestedDialog);
            EasyLogger.d("reuse dialog:\n " + mNestedDialog);
        }


        try {
            mNestedDialog.show();
            return true;
        } catch (WindowManager.BadTokenException e) {
            EasyLogger.e("BadToken has happened when show dialog: \n" + mNestedDialog.getClass().getSimpleName());
            return false;
        }

    }

    @NonNull
    protected abstract NestedDialog createDialog(Activity activity);

    protected void resetDialogWhenShowAgain(NestedDialog dialog) {

    }

    protected void recycle() {
        mOwnerActivity = null;
        mNestedDialog = null;
        EasyLogger.d("the dialog:" + Utils.getObjectDesc(this) + "has recycled.");
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
}
