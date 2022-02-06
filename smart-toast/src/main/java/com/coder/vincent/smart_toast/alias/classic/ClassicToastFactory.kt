package com.coder.vincent.smart_toast.alias.classic

import android.view.View
import com.coder.vincent.smart_toast.CompactToast
import com.coder.vincent.smart_toast.ToastConfig
import com.coder.vincent.smart_toast.factory.CompactToastCreator
import com.coder.vincent.smart_toast.factory.ToastFactory

internal object ClassicToastFactory : ToastFactory {
    private val definition: ClassicToast = ClassicToast()
    private val compactToastCreator = CompactToastCreator()
    override fun produceToast(config: ToastConfig): CompactToast =
        compactToastCreator.create(
            definition.provideToastView(null, config as ClassicToast.Config),
            config,
        )

    override fun applyNewConfig(toastView: View, config: ToastConfig) {
        compactToastCreator.create(
            definition.provideToastView(toastView, config as ClassicToast.Config),
            config,
        )
    }
}
