package com.coder.vincent.smart_toast

import android.graphics.Color
import android.widget.Toast
import com.coder.vincent.series.common_lib.application

internal const val DEFAULT_VALUE = -1
internal const val TOAST_TYPE_CLASSIC = "classic"
internal const val TOAST_TYPE_EMOTION = "emotion"
const val INTENT_KEY_BOUND_PAGE_ID = "startPageId"
const val INTENT_KEY_PENDING_BOUND_PAGE_ID = "pendingPageId"
internal val DEFAULT_TOAST_BACKGROUND_COLOR = Color.parseColor("#cc000000")
internal val DEFAULT_TOAST_Y_OFFSET = Toast(application).yOffset