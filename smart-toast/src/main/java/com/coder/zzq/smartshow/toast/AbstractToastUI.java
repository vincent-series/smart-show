package com.coder.zzq.smartshow.toast;

import android.widget.Toast;


abstract class AbstractToastUI {
    protected abstract Toast createToast(CharSequence msg, UIArguments uiArguments);
}
