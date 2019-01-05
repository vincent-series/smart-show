package com.coder.zzq.smartshow.toast;

import android.view.View;
import android.widget.TextView;

/**
 * Created by 朱志强 on 2017/11/13.
 */

public interface IProcessToastCallback {
    /**
     * @param rootView 根View
     * @param msgView  文本View
     */
    void processView(View rootView, TextView msgView);
}
