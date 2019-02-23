package com.coder.zzq.smartshow.bar.core;

import android.support.annotation.DrawableRes;
import android.view.View;

public interface IBarShow {
    void show(CharSequence msg);

    void show(CharSequence msg, CharSequence actionText);

    void show(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);

    void showLong(CharSequence msg);

    void showLong(CharSequence msg, CharSequence actionText);

    void showLong(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);

    void showIndefinite(CharSequence msg);

    void showIndefinite(CharSequence msg, CharSequence actionText);

    void showIndefinite(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);


    void show(CharSequence msg, @DrawableRes int icon);

    void show(CharSequence msg, CharSequence actionText, @DrawableRes int icon);

    void show(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener, @DrawableRes int icon);

    void showLong(CharSequence msg, @DrawableRes int icon);

    void showLong(CharSequence msg, CharSequence actionText, @DrawableRes int icon);

    void showLong(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener, @DrawableRes int icon);

    void showIndefinite(CharSequence msg, @DrawableRes int icon);

    void showIndefinite(CharSequence msg, CharSequence actionText, @DrawableRes int icon);

    void showIndefinite(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener, @DrawableRes int icon);
}
