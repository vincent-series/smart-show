package com.coder.zzq.smartshow.snackbar.base;

import android.view.ViewGroup;
import android.widget.TextView;

public interface IProcessBarCallback {
    void processBarView(ViewGroup layout, TextView msgView, TextView actionView);
}
