package com.coder.zzq.smartshow.toast;

import androidx.fragment.app.DialogFragment;

public class ToastDialog {
    DialogFragment mDialogFragment;
    public void test(){
        mDialogFragment.dismiss();
        mDialogFragment.onDetach();
    }
}
