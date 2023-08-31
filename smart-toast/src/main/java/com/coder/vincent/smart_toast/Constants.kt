package com.coder.vincent.smart_toast

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.coder.vincent.series.common_lib.Toolkit

internal const val TOAST_ALIAS_CLASSIC = "classic"
internal const val TOAST_ALIAS_EMOTION = "emotion"
val DEFAULT_TOAST_BACKGROUND_COLOR = Color.parseColor("#cc000000")
val DEFAULT_TOAST_Y_OFFSET = Toast(Toolkit.context()).yOffset
internal val handler = Handler(Looper.getMainLooper())