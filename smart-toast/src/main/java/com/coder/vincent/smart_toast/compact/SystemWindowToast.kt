package com.coder.vincent.smart_toast.compact

import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
import android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
import android.widget.Toast
import com.coder.vincent.series.common_lib.sdkVersionBelow
import com.coder.vincent.series.common_lib.windowManager
import com.coder.vincent.smart_toast.ToastConfig

@Suppress("DEPRECATION")
internal class SystemWindowToast(toastView: View, config: ToastConfig) :
    BaseCompactToast(toastView, config) {
    private val handler = Handler(Looper.getMainLooper())
    override fun show() {
        kotlin.runCatching {
            val lp = WindowManager.LayoutParams().apply {
                flags =
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                windowAnimations = android.R.style.Animation_Toast
                gravity = config.location.gravity
                x = config.location.xOffset
                y = config.location.yOffset
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
                format = PixelFormat.TRANSPARENT
                type =
                    if (sdkVersionBelow(Build.VERSION_CODES.O)) TYPE_SYSTEM_ALERT else TYPE_APPLICATION_OVERLAY
            }
            windowManager.addView(toastView, lp)
            handler.postDelayed(
                { kotlin.runCatching { windowManager.removeView(toastView) } },
                if (config().duration == Toast.LENGTH_SHORT) DURATION_SHORT else DURATION_LONG
            )
        }
    }

    override fun cancel() {
        kotlin.runCatching { windowManager.removeView(toastView) }
    }
}