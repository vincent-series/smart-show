package com.coder.zzq.smartshow.dialog;

import android.app.Dialog;

public interface DialogCreator {
    Dialog createDialog(int bizTag);
    Dialog resetDialog(int bizTag);
}
