package com.coder.vincent.smart_toast.compact

import android.view.View

internal class ViewAttachListener(private val visibilityChangedListener: ToastVisibilityChangedListener) :
    View.OnAttachStateChangeListener {
    override fun onViewAttachedToWindow(v: View) {
        visibilityChangedListener.onToastVisibilityChanged(v, true)
    }

    override fun onViewDetachedFromWindow(v: View) {
        visibilityChangedListener.onToastVisibilityChanged(v, false)
    }
}