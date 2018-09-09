package com.coder.zzq.smartshow.bar.base;

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
}
