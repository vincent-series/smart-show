package com.coder.zzq.smartshow.bar.core;

import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

public interface IBarShow {
    void show(CharSequence msg);

    void show(@StringRes int msg);

    void show(CharSequence msg, CharSequence actionText);

    void show(@StringRes int msg, @StringRes int actionText);

    void show(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);

    void show(@StringRes int msg, @StringRes int actionText, View.OnClickListener onActionClickListener);


    void showLong(CharSequence msg);

    void showLong(@StringRes int msg);

    void showLong(CharSequence msg, CharSequence actionText);

    void showLong(@StringRes int msg, @StringRes int actionText);

    void showLong(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);

    void showLong(@StringRes int msg, @StringRes int actionText, View.OnClickListener onActionClickListener);




    void showIndefinite(CharSequence msg);

    void showIndefinite(@StringRes int msg);

    void showIndefinite(CharSequence msg, CharSequence actionText);

    void showIndefinite(@StringRes int msg, @StringRes int actionText);

    void showIndefinite(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);

    void showIndefinite(@StringRes int msg, @StringRes int actionText, View.OnClickListener onActionClickListener);


    IBarShow icon(@DrawableRes int icon);
}
