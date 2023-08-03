package com.coder.vincent.smart_toast.factory

import com.coder.vincent.smart_toast.compact.CompactToast

interface ToastFactory {
    fun produceToast(config: ToastConfig): CompactToast
}