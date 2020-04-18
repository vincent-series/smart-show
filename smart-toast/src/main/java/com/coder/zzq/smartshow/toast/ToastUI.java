package com.coder.zzq.smartshow.toast;

import android.view.View;
import android.widget.Toast;

import com.coder.zzq.toolkit.Toolkit;

public abstract class ToastUI extends AbstractToastUI {
    abstract protected View createUI(CharSequence msg, UIArguments uiArguments);

    @Override
    protected Toast createToast(CharSequence msg, UIArguments uiArguments) {
        Toast toast = new Toast(Toolkit.getContext());
        toast.setView(createUI(msg, uiArguments));
        return toast;
    }
}
