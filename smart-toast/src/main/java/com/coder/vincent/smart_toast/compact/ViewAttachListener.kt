package com.coder.vincent.smart_toast.compact

import android.view.View

internal class ViewAttachListener(private val visibilityObserver: ToastVisibilityObserver) :
    View.OnAttachStateChangeListener {
    override fun onViewAttachedToWindow(v: View) {
        visibilityObserver.onToastVisibilityChanged(v, true)
    }

    override fun onViewDetachedFromWindow(v: View) {
        visibilityObserver.onToastVisibilityChanged(v, false)
    }
}