package com.coder.vincent.smart_toast

import android.view.View
import com.coder.vincent.smart_toast.compact.ToastVisibilityChangedListener

interface CompactToast {
    fun config(): ToastConfig
    fun updateConfig(newToastConfig: ToastConfig)
    fun view(): View
    fun show()
    fun isShowing(): Boolean
    fun cancel()
    fun setVisibilityObserver(visibilityChangedListener: ToastVisibilityChangedListener)
    fun removeVisibilityObserver(visibilityChangedListener: ToastVisibilityChangedListener)
}

