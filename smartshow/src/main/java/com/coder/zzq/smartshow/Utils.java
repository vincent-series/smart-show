package com.coder.zzq.smartshow;

import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

public class Utils {


    public static int dpToPx(float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, SmartShow.getContext().getResources().getDisplayMetrics()));
    }

    @ColorInt
    public static int getColorFromRes(@ColorRes int colorRes) {
        return ContextCompat.getColor(SmartShow.getContext(), colorRes);
    }

    public static <T> T requireNonNull(T obj, String tip) {
        if (obj == null) {
            throw new NullPointerException(tip);
        }

        return obj;
    }



}
