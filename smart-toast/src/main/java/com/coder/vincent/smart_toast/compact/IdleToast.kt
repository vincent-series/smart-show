package com.coder.vincent.smart_toast.compact

import android.view.View
import com.coder.vincent.smart_toast.factory.ToastConfig

/**
 * Fake Toast When no Toast to Show
 */
class IdleToast : CompactToast {
    override fun config() = ToastConfig()

    override fun updateConfig(config: ToastConfig) {
        throw UnsupportedOperationException()
    }

    override fun view(): View {
        throw UnsupportedOperationException()
    }

    override fun show() {}

    override fun isShowing() = false

    override fun cancel() {}

    override fun setVisibilityObserver(visibilityObserver: ToastVisibilityObserver) {}

    override fun removeVisibilityObserver(visibilityObserver: ToastVisibilityObserver) {}
}