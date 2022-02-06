package com.coder.vincent.smart_toast.compact

import android.view.View
import com.coder.vincent.smart_toast.CompactToast
import com.coder.vincent.smart_toast.ToastConfig

internal abstract class BaseCompactToast(val toastView: View, var config: ToastConfig) :
    CompactToast {

    private var viewAttachListener: ViewAttachListener? = null

    override fun config() = config

    override fun updateConfig(newToastConfig: ToastConfig) {
        config = newToastConfig
    }

    override fun view() = toastView

    override fun isShowing() = toastView.windowVisibility == View.VISIBLE

    override fun setVisibilityObserver(visibilityChangedListener: ToastVisibilityChangedListener) {
        viewAttachListener?.let {
            toastView.removeOnAttachStateChangeListener(viewAttachListener)
        }
        viewAttachListener = ViewAttachListener(visibilityChangedListener)
        toastView.addOnAttachStateChangeListener(viewAttachListener)
    }

    override fun removeVisibilityObserver(visibilityChangedListener: ToastVisibilityChangedListener) {
        toastView.removeOnAttachStateChangeListener(viewAttachListener)
    }
}

interface ToastVisibilityChangedListener {
    fun onToastVisibilityChanged(view: View, visible: Boolean)
}

const val DURATION_SHORT = 2000L
const val DURATION_LONG = 3500L
