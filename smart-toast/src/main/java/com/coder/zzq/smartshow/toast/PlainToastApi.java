package com.coder.zzq.smartshow.toast;


import androidx.annotation.StringRes;

public interface PlainToastApi {

    // short text toast

    void show(CharSequence msg);

    void show(@StringRes int msg);


    void showAtTop(CharSequence msg);

    void showAtTop(@StringRes int msg);


    void showInCenter(CharSequence msg);

    void showInCenter(@StringRes int msg);


    void showAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp);

    void showAtLocation(@StringRes int msg, int gravity, float xOffsetDp, float yOffsetDp);


    // long text toast

    void showLong(CharSequence msg);

    void showLong(@StringRes int msg);


    void showLongAtTop(CharSequence msg);

    void showLongAtTop(@StringRes int msg);


    void showLongInCenter(CharSequence msg);

    void showLongInCenter(@StringRes int msg);


    void showLongAtLocation(CharSequence msg, int gravity, float xOffsetDp, float yOffsetDp);

    void showLongAtLocation(@StringRes int msg, int gravity, float xOffsetDp, float yOffsetDp);

}
