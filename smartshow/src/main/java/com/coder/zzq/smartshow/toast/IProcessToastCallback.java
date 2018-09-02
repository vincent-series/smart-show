package com.coder.zzq.smartshow.toast;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 朱志强 on 2017/11/13.
 */

public interface IProcessToastCallback {
    void processView(boolean isCustom,View rootView,TextView msgView);
}
