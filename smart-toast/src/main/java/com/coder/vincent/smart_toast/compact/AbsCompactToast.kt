package com.coder.vincent.smart_toast.compact

import android.view.View
import com.coder.vincent.smart_toast.factory.ToastConfig

internal abstract class AbsCompactToast(
    private val toastView: View,
    private var config: ToastConfig,
    private var configApplyCallback: (View, ToastConfig) -> Unit
) : CompactToast {
    init {
        configApplyCallback.invoke(this.toastView, this.config)
    }

    private var viewAttachListener: ViewAttachListener? = null

    override fun config() = config

    override fun updateConfig(config: ToastConfig) {
        this.config = config
        configApplyCallback.invoke(this.toastView, this.config)
    }

    override fun view() = toastView

    override fun isShowing() = toastView.windowVisibility == View.VISIBLE

    override fun setVisibilityObserver(visibilityObserver: ToastVisibilityObserver) {
        viewAttachListener?.let {
            toastView.removeOnAttachStateChangeListener(viewAttachListener)
        }
        viewAttachListener = ViewAttachListener(visibilityObserver)
        toastView.addOnAttachStateChangeListener(viewAttachListener)
    }

    override fun removeVisibilityObserver(visibilityObserver: ToastVisibilityObserver) {
        toastView.removeOnAttachStateChangeListener(viewAttachListener)
    }
}

interface ToastVisibilityObserver {
    fun onToastVisibilityChanged(view: View, visible: Boolean)
}
