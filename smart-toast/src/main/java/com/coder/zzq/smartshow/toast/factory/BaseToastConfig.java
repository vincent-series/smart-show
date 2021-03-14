package com.coder.zzq.smartshow.toast.factory;

import android.widget.Toast;

public class BaseToastConfig {
    //要显示的message
    public CharSequence mMsg = "";
    public int mDuration = Toast.LENGTH_SHORT;
    public int mGravity;
    public int mXOffset;
    public int mYOffset;
    //退出当前页面时是否隐藏当前正在显示的Toast
    public boolean mCancelOnActivityExit;
}
