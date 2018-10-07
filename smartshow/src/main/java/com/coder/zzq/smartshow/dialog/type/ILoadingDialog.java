package com.coder.zzq.smartshow.dialog.type;

import android.support.annotation.StringRes;

public interface ILoadingDialog extends IDialog {
    
    ILoadingDialog msg(String msg);

    ILoadingDialog large();

    ILoadingDialog middle();

    ILoadingDialog small();


}
