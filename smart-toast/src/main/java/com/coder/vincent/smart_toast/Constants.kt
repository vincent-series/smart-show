package com.coder.vincent.smart_toast

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.IntDef
import com.coder.vincent.series.common_lib.Toolkit

internal const val TOAST_ALIAS_CLASSIC = "classic"
internal const val TOAST_ALIAS_EMOTION = "emotion"
val DEFAULT_TOAST_BACKGROUND_COLOR = Color.parseColor("#cc000000")
val DEFAULT_TOAST_Y_OFFSET = Toast(Toolkit.context()).yOffset
const val TOAST_ICON_POSITION_LEFT = 0
const val TOAST_ICON_POSITION_RIGHT = 1

@Retention(AnnotationRetention.BINARY)
@IntDef(TOAST_ICON_POSITION_LEFT, TOAST_ICON_POSITION_RIGHT)
annotation class IconPosition

internal val handler = Handler(Looper.getMainLooper())