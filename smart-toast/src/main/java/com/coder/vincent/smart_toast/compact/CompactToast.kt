package com.coder.vincent.smart_toast.compact

import android.view.View
import com.coder.vincent.smart_toast.factory.ToastConfig

const val TOAST_DURATION_SHORT = 2000L
const val TOAST_DURATION_LONG = 3500L
const val TOAST_DURATION_INDEFINITE = 0L

interface CompactToast {
    fun config(): ToastConfig
    fun updateConfig(config: ToastConfig)
    fun view(): View
    fun show()
    fun isShowing(): Boolean
    fun cancel()
    fun setVisibilityObserver(visibilityObserver: ToastVisibilityObserver)
    fun removeVisibilityObserver(visibilityObserver: ToastVisibilityObserver)
}


