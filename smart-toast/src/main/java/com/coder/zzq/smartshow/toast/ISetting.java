package com.coder.zzq.smartshow.toast;

import android.graphics.Color;

public interface ISetting {
    int DEFAULT_EMOTION_TOAST_THEME_COLOR = Color.parseColor("#bb000000");

    ISetting dismissOnLeave(boolean b);

    ISetting emotionToastThemeColor(int emotionToastThemeColor);

    ISetting defaultToastTag(int tag);

    ISetting toastProvide(IToastProvider provider);
}
