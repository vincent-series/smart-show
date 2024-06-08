package com.coder.vincent.smart_toast.compact

import android.view.View
import androidx.viewbinding.ViewBinding
import com.coder.vincent.smart_toast.factory.ToastConfig

internal abstract class AbsCompactToast(
    private val toastView: ViewBinding,
    private var config: ToastConfig,
    private var configApplyCallback: (ViewBinding, ToastConfig) -> Unit
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

    override fun view() = toastView.root

    override fun isShowing() = toastView.root.windowVisibility == View.VISIBLE

    override fun setVisibilityObserver(visibilityObserver: ToastVisibilityObserver) {
        viewAttachListener?.let {
            toastView.root.removeOnAttachStateChangeListener(viewAttachListener)
        }
        viewAttachListener = ViewAttachListener(visibilityObserver)
        toastView.root.addOnAttachStateChangeListener(viewAttachListener)
    }

    override fun removeVisibilityObserver(visibilityObserver: ToastVisibilityObserver) {
        toastView.root.removeOnAttachStateChangeListener(viewAttachListener)
    }
}

interface ToastVisibilityObserver {
    fun onToastVisibilityChanged(view: View, visible: Boolean)
}
