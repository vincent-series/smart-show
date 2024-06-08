package com.coder.vincent.smart_toast.alias.classic

import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.smart_toast.compact.CompactToast
import com.coder.vincent.smart_toast.compact.ToastWindowStrategySelector
import com.coder.vincent.smart_toast.factory.ToastConfig
import com.coder.vincent.smart_toast.factory.ToastFactory

internal object ClassicToastFactory : ToastFactory {
    private val definition: ClassicToast = ClassicToast()

    override fun produceToast(config: ToastConfig): CompactToast =
        ToastWindowStrategySelector().select(
            definition.toastView(Toolkit.layoutInflater()),
            config
        ) { toastView, toastConfig ->
            definition.applyConfig(toastView, toastConfig as ClassicToast.Config)
        }
}
