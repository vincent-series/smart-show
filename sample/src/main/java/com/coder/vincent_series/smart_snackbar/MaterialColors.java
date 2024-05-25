package com.coder.vincent_series.smart_snackbar;


import android.graphics.Color;

import androidx.core.graphics.ColorUtils;

public class MaterialColors {
    private MaterialColors() {
    }
    @ColorInt
    public static int layer(
            @ColorInt int backgroundColor,
            @ColorInt int overlayColor,
            @FloatRange(from = 0.0, to = 1.0) float overlayAlpha) {
        int computedAlpha = Math.round(Color.alpha(overlayColor) * overlayAlpha);
        int computedOverlayColor = ColorUtils.setAlphaComponent(overlayColor, computedAlpha);
        return layer(backgroundColor, computedOverlayColor);
    }

    @ColorInt
    public static int layer(@ColorInt int backgroundColor, @ColorInt int overlayColor) {
        return ColorUtils.compositeColors(overlayColor, backgroundColor);
    }
}
