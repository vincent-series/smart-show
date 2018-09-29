package com.coder.zzq.smartshow.dialog.type;

import android.support.annotation.StringRes;

public interface IMessageDialog extends IDialogShow<IMessageDialog> {
    IMessageDialog setMessage(@StringRes int msgRes);

    IMessageDialog setMessage(CharSequence msg);
}
