package com.coder.zzq.smartshow.bar.core;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public interface IProcessBarCallback {
    void processBarView(ViewGroup layout, TextView msgView, Button actionView);
}
