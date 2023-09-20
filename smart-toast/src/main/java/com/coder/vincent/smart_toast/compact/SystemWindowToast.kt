package com.coder.vincent.smart_toast.compact

import android.graphics.PixelFormat
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
import android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
import android.widget.Toast
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.smart_toast.factory.ToastConfig
import com.coder.vincent.smart_toast.handler

@Suppress("DEPRECATION")
internal class SystemWindowToast(
    toastView: View,
    config: ToastConfig,
    configApplyCallback: (View, ToastConfig) -> Unit
) : AbsCompactToast(toastView, config, configApplyCallback) {
    override fun show() {
        kotlin.runCatching {
            handler.removeCallbacksAndMessages(null)
            val lp = WindowManager.LayoutParams().apply {
                flags =
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                windowAnimations = android.R.style.Animation_Toast
                gravity = config().location.gravity
                x = config().location.xOffset
                y = config().location.yOffset
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
                format = PixelFormat.TRANSPARENT
                type =
                    if (Toolkit.sdkVersionBelow(Build.VERSION_CODES.O)) TYPE_SYSTEM_ALERT else TYPE_APPLICATION_OVERLAY
            }
            Toolkit.windowManager().addView(view(), lp)
            handler.postDelayed(
                { cancel() },
                if (config().duration == Toast.LENGTH_SHORT) TOAST_DURATION_SHORT else TOAST_DURATION_LONG
            )
        }
    }

    override fun cancel() {
        kotlin.runCatching { Toolkit.windowManager().removeView(view()) }
    }
}