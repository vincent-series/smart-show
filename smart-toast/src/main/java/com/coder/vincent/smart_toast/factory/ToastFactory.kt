package com.coder.vincent.smart_toast.factory

import android.view.View
import com.coder.vincent.smart_toast.CompactToast
import com.coder.vincent.smart_toast.ToastConfig

internal interface ToastFactory {
    fun produceToast(config: ToastConfig): CompactToast?
    fun applyNewConfig(toastView: View, config: ToastConfig)
}