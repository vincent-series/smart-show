package com.coder.zzq.smartshow.dialog.creator.type;

import android.support.annotation.DrawableRes;

public interface INormalDialogCreator<B> extends IDialogCreator {
    B darkAroundWhenShow(boolean dim);

    B windowBackground(@DrawableRes int bgRes);

    B cancelable(boolean b);

    B cancelableOnTouchOutside(boolean b);
}
