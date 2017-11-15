package com.coder.zzq.smartshow.toast;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by 喜欢、陪你看风景 on 2017/11/13.
 */

public interface CustomToastSetting {
    CustomToastSetting view(View view);
    CustomToastSetting view(@LayoutRes int layout);
    CustomToastSetting processCustomView(ProcessViewCallback callback);
}
