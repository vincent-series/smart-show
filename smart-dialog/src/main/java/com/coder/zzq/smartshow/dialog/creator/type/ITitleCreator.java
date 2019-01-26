package com.coder.zzq.smartshow.dialog.creator.type;

import android.support.annotation.ColorInt;

public interface ITitleCreator<B> {
    B title(CharSequence title);

    B titleStyle(@ColorInt int color, float textSizeSp, boolean bold);
}
