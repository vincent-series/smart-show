package com.coder.vincent.smart_toast.alias

import android.view.LayoutInflater
import android.view.View
import com.coder.vincent.smart_toast.factory.ToastConfig

interface ToastDefinition<Config : ToastConfig> {
    fun toastView(inflater: LayoutInflater): View
    fun applyConfig(toastView: View, config: Config)
}