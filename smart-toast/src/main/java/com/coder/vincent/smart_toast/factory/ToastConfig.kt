package com.coder.vincent.smart_toast.factory

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.Toast
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.resourceToDrawable
import com.coder.vincent.smart_toast.DEFAULT_TOAST_Y_OFFSET
import com.coder.vincent.smart_toast.R

open class ToastConfig {
    var alias: String = ""
    var backgroundDrawable: Drawable =
        R.drawable.vincent_series_smart_toast_default_bg.resourceToDrawable()!!
    var message: CharSequence = ""
    var messageStyle: TextStyle = TextStyle(Color.WHITE, 14f, false)
    var iconDrawable: Drawable? = null
    var duration: Int = Toast.LENGTH_SHORT
    var location: Location =
        Location(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, DEFAULT_TOAST_Y_OFFSET)
}

data class Location(
    val gravity: Int,
    val xOffset: Int,
    val yOffset: Int,
)