package com.coder.zzq.smartshowdemo

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.coder.vincent.series.annotations.smart_toast.CustomizedToast
import com.coder.vincent.smart_toast.alias.SimpleToastDefinition
import com.coder.vincent.smart_toast.databinding.SmartShowClassicToastBinding
import com.coder.vincent.smart_toast.factory.ToastConfig

@CustomizedToast(alias = "sample")
class SampleToast : SimpleToastDefinition {
    override fun toastView(inflater: LayoutInflater): ViewBinding =
        SmartShowClassicToastBinding.inflate(inflater)

    override fun applyConfig(viewBinding: ViewBinding, config: ToastConfig) {
        (viewBinding as SmartShowClassicToastBinding).apply {
            root.background = config.backgroundDrawable
            smartToastMessage.text = config.message
        }
    }
}