package com.coder.vincent.smart_toast.alias

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.coder.vincent.smart_toast.factory.ToastConfig

interface ToastDefinition<Config : ToastConfig> {
    fun toastView(inflater: LayoutInflater): ViewBinding
    fun applyConfig(viewBinding: ViewBinding, config: Config)
}