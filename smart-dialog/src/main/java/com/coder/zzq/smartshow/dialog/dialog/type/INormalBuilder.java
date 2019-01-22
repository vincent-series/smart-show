package com.coder.zzq.smartshow.dialog.dialog.type;

import android.app.Activity;
import android.support.annotation.DrawableRes;

import com.coder.zzq.smartshow.dialog.dialog.DialogCreator;

public interface INormalBuilder<B> {
    B darkAroundWhenShow(boolean dim);

    B windowBackground(@DrawableRes int bgRes);

    B cancelable(boolean b);

    B cancelableOnTouchOutside(boolean b);

    boolean createAndShow(Activity activity, DialogCreator dialogCreator);
}
