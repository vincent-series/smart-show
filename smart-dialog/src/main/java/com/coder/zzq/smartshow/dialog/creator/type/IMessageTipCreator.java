package com.coder.zzq.smartshow.dialog.creator.type;

import android.support.annotation.ColorInt;

public interface IMessageTipCreator<B> extends ITitleCreator<B>, IConfirmBtnCreator<B>, INormalDialogCreator<B>,
        IDelayConfirmCreator<B> {
    B message(CharSequence msg);

    B messageStyle(@ColorInt int color, float textSizeSp, boolean bold);
}
