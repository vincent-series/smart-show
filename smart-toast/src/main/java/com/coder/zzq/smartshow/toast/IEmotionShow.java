package com.coder.zzq.smartshow.toast;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public interface IEmotionShow {

    void info(CharSequence msg);

    void info(@StringRes int msg);

    void info(@StringRes int msg, @DrawableRes int iconRes);

    void info(CharSequence msg, @DrawableRes int iconRes);


    void infoLong(CharSequence msg);

    void infoLong(@StringRes int msg);

    void infoLong(@StringRes int msg, @DrawableRes int iconRes);

    void infoLong(CharSequence msg, @DrawableRes int iconRes);


    void warning(CharSequence msg);

    void warning(@StringRes int msg);

    void warning(@StringRes int msg, @DrawableRes int iconRes);

    void warning(CharSequence msg, @DrawableRes int iconRes);


    void warningLong(CharSequence msg);

    void warningLong(@StringRes int msg);

    void warningLong(@StringRes int msg, @DrawableRes int iconRes);

    void warningLong(CharSequence msg, @DrawableRes int iconRes);


    void success(CharSequence msg);

    void success(@StringRes int msg);

    void success(@StringRes int msg, @DrawableRes int iconRes);

    void success(CharSequence msg, @DrawableRes int iconRes);


    void successLong(CharSequence msg);

    void successLong(@StringRes int msg);

    void successLong(@StringRes int msg, @DrawableRes int iconRes);

    void successLong(CharSequence msg, @DrawableRes int iconRes);


    void error(CharSequence msg);

    void error(@StringRes int msg);

    void error(@StringRes int msg, @DrawableRes int iconRes);

    void error(CharSequence msg, @DrawableRes int iconRes);


    void errorLong(CharSequence msg);

    void errorLong(@StringRes int msg);

    void errorLong(@StringRes int msg, @DrawableRes int iconRes);

    void errorLong(CharSequence msg, @DrawableRes int iconRes);


    void fail(CharSequence msg);

    void fail(@StringRes int msg);

    void fail(@StringRes int msg, @DrawableRes int iconRes);

    void fail(CharSequence msg, @DrawableRes int iconRes);


    void failLong(CharSequence msg);

    void failLong(@StringRes int msg);

    void failLong(@StringRes int msg, @DrawableRes int iconRes);

    void failLong(CharSequence msg, @DrawableRes int iconRes);


    void complete(CharSequence msg);

    void complete(@StringRes int msg);

    void complete(@StringRes int msg, @DrawableRes int iconRes);

    void complete(CharSequence msg, @DrawableRes int iconRes);


    void completeLong(CharSequence msg);

    void completeLong(@StringRes int msg);

    void completeLong(@StringRes int msg, @DrawableRes int iconRes);

    void completeLong(CharSequence msg, @DrawableRes int iconRes);


    void forbid(CharSequence msg);

    void forbid(@StringRes int msg);

    void forbid(@StringRes int msg, @DrawableRes int iconRes);

    void forbid(CharSequence msg, @DrawableRes int iconRes);


    void forbidLong(CharSequence msg);

    void forbidLong(@StringRes int msg);

    void forbidLong(@StringRes int msg, @DrawableRes int iconRes);

    void forbidLong(CharSequence msg, @DrawableRes int iconRes);


    void waiting(CharSequence msg);

    void waiting(@StringRes int msg);

    void waiting(@StringRes int msg, @DrawableRes int iconRes);

    void waiting(CharSequence msg, @DrawableRes int iconRes);


    void waitingLong(CharSequence msg);

    void waitingLong(@StringRes int msg);

    void waitingLong(@StringRes int msg, @DrawableRes int iconRes);

    void waitingLong(CharSequence msg, @DrawableRes int iconRes);

}
