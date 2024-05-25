package com.coder.vincent_series.smart_snackbar

object ShapeModelDetector {

    init {
        Class.forName("com.google.android.material.shape.ShapeAppearanceModel").newInstance()
    }
}