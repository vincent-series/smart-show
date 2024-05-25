package com.coder.vincent_series.smart_snackbar

import android.animation.TimeInterpolator
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

@JvmField
val LINEAR_INTERPOLATOR: TimeInterpolator = LinearInterpolator()

@JvmField
val FAST_OUT_SLOW_IN_INTERPOLATOR: TimeInterpolator = FastOutSlowInInterpolator()

@JvmField
val LINEAR_OUT_SLOW_IN_INTERPOLATOR: TimeInterpolator = LinearOutSlowInInterpolator()
