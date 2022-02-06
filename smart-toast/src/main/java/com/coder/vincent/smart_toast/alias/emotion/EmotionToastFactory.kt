package com.coder.vincent.smart_toast.alias.emotion

import android.view.View
import com.coder.vincent.smart_toast.CompactToast
import com.coder.vincent.smart_toast.ToastConfig
import com.coder.vincent.smart_toast.factory.CompactToastCreator
import com.coder.vincent.smart_toast.factory.ToastFactory

internal object EmotionToastFactory : ToastFactory {
    private val definition = EmotionToast()
    private val compactToastCreator = CompactToastCreator()
    override fun produceToast(config: ToastConfig): CompactToast =
        compactToastCreator.create(
            definition.provideToastView(null, config as EmotionToast.Config),
            config,
        )

    override fun applyNewConfig(toastView: View, config: ToastConfig) {
        definition.provideToastView(toastView, config as EmotionToast.Config)
    }
}