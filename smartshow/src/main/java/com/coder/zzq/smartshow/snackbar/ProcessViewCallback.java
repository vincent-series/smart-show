package com.coder.zzq.smartshow.snackbar;

import android.support.design.widget.Snackbar;
import android.widget.TextView;

/**
 * Created by 朱志强 on 2017/11/15.
 */

public interface ProcessViewCallback {
    void processSnackbarView(Snackbar.SnackbarLayout layout, TextView msgView,TextView actionView);
}
