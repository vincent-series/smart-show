package com.coder.zzq.smartshow.dialog.creator.type;

import android.app.Activity;
import android.support.annotation.DrawableRes;

public interface INormalDialogCreator<B> {
    B darkAroundWhenShow(boolean dim);

    B windowBackground(@DrawableRes int bgRes);

    B cancelable(boolean b);

    B cancelableOnTouchOutside(boolean b);

    boolean createAndShow(Activity activity);
}
