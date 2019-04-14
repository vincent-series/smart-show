package com.coder.zzq.smartshow.toast;


import android.support.annotation.DrawableRes;

public interface ITypeShow {

    void info(CharSequence msg);

    void info(CharSequence msg, @DrawableRes int iconRes);

    void infoLong(CharSequence msg);

    void infoLong(CharSequence msg, @DrawableRes int iconRes);

    void warning(CharSequence msg);

    void warning(CharSequence msg, @DrawableRes int iconRes);

    void warningLong(CharSequence msg);

    void warningLong(CharSequence msg, @DrawableRes int iconRes);

    void success(CharSequence msg);

    void success(CharSequence msg, @DrawableRes int iconRes);

    void successLong(CharSequence msg);

    void successLong(CharSequence msg, @DrawableRes int iconRes);

    void error(CharSequence msg);

    void error(CharSequence msg, @DrawableRes int iconRes);

    void errorLong(CharSequence msg);

    void errorLong(CharSequence msg, @DrawableRes int iconRes);

    void fail(CharSequence msg);

    void fail(CharSequence msg, @DrawableRes int iconRes);

    void failLong(CharSequence msg);

    void failLong(CharSequence msg, @DrawableRes int iconRes);

    void complete(CharSequence msg);

    void complete(CharSequence msg, @DrawableRes int iconRes);

    void completeLong(CharSequence msg);

    void completeLong(CharSequence msg, @DrawableRes int iconRes);

    void forbid(CharSequence msg);

    void forbid(CharSequence msg, @DrawableRes int iconRes);

    void forbidLong(CharSequence msg);

    void forbidLong(CharSequence msg, @DrawableRes int iconRes);

    void waiting(CharSequence msg);

    void waiting(CharSequence msg, @DrawableRes int iconRes);

    void waitingLong(CharSequence msg);

    void waitingLong(CharSequence msg, @DrawableRes int iconRes);
}
