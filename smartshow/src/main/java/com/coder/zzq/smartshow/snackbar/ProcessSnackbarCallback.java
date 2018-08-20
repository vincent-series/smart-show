package com.coder.zzq.smartshow.snackbar;

import android.widget.TextView;

import com.coder.zzq.smartshow.snackbar.custom.Snackbar;

/**
 * Created by 朱志强 on 2017/11/15.
 */

public interface ProcessSnackbarCallback {
    void processSnackbarView(Snackbar.SnackbarLayout layout, TextView msgView, TextView actionView);
}
