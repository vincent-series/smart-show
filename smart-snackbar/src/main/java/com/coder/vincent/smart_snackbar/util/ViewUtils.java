package com.coder.vincent.smart_snackbar.util;

import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ViewUtils {

    private ViewUtils() {
    }

    public static PorterDuff.Mode parseTintMode(int value, PorterDuff.Mode defaultMode) {
        switch (value) {
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return defaultMode;
        }
    }

    public static void addOnGlobalLayoutListener(
            @Nullable View view, @NonNull OnGlobalLayoutListener victim) {
        if (view != null) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(victim);
        }
    }

    public static void removeOnGlobalLayoutListener(
            @Nullable View view, @NonNull OnGlobalLayoutListener victim) {
        if (view != null) {
            removeOnGlobalLayoutListener(view.getViewTreeObserver(), victim);
        }
    }

    public static void removeOnGlobalLayoutListener(
            @NonNull ViewTreeObserver viewTreeObserver, @NonNull OnGlobalLayoutListener victim) {
        viewTreeObserver.removeOnGlobalLayoutListener(victim);
    }
}
