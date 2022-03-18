package com.coder.vincent.smart_snackbar.view

import android.view.View
import android.view.ViewGroup

internal class ViewAttachListener(private val containerValidStateChangeListener: ContainerValidStateChangeListener) :
    View.OnAttachStateChangeListener {
    override fun onViewAttachedToWindow(v: View) {
        containerValidStateChangeListener.onContainerValidStateChanged(v as ViewGroup, true)
    }

    override fun onViewDetachedFromWindow(v: View) {
        containerValidStateChangeListener.onContainerValidStateChanged(v as ViewGroup, false)
    }
}