package com.coder.zzq.smartshow.toast;

import android.graphics.Color;

import com.coder.zzq.toolkit.Utils;

public interface Constants {

    int DEFAULT_VALUE = -1;

    String TOAST_TYPE_CLASSIC = "classic";
    String TOAST_TYPE_EMOTION = "emotion";

    int DEFAULT_TOAST_BACKGROUND_COLOR = Color.parseColor("#cc000000");
    int DEFAULT_ICON_PADDING = Utils.dpToPx(10);

    String DELAY_TOAST_TAG_KEY = "delay_toast";
}
