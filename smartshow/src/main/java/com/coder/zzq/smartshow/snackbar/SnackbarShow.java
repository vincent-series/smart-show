package com.coder.zzq.smartshow.snackbar;

import android.view.View;

/**
 * Created by 朱志强 on 2017/11/12.
 */

public interface SnackbarShow {

    void show(CharSequence msg);

    void showAtTop(CharSequence msg);

    void show(CharSequence msg, CharSequence actionText);

    void showAtTop(CharSequence msg, CharSequence actionText);

    void show(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);

    void showAtTop(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);


    void showLong(CharSequence msg);

    void showLongAtTop(CharSequence msg);

    void showLong(CharSequence msg, CharSequence actionText);

    void showLongAtTop(CharSequence msg, CharSequence actionText);

    void showLong(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);

    void showLongAtTop(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);


    void showIndefinite(CharSequence msg);

    void showIndefiniteAtTop(CharSequence msg);

    void showIndefinite(CharSequence msg, CharSequence actionText);

    void showIndefiniteAtTop(CharSequence msg, CharSequence actionText);

    void showIndefinite(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);

    void showIndefiniteAtTop(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener);

}
