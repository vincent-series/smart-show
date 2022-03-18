package com.coder.vincent.smart_snackbar.view

import android.view.ViewGroup

interface ContainerValidStateChangeListener {
    fun onContainerValidStateChanged(targetParent: ViewGroup, valid: Boolean)
}