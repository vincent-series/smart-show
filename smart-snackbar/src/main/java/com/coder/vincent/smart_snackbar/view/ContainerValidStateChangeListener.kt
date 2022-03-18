package com.coder.vincent.smart_snackbar.view

import android.view.ViewGroup

internal interface ContainerValidStateChangeListener {
    fun onContainerValidStateChanged(targetParent: ViewGroup, valid: Boolean)
}