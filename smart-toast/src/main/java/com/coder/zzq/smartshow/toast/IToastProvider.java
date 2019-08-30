package com.coder.zzq.smartshow.toast;

import android.content.Context;
import android.widget.Toast;

public interface IToastProvider {

    Toast createCustomToast(int toastTag, Context applicationContext);

}
