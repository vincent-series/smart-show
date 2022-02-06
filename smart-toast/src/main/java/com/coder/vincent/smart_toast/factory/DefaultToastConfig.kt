package com.coder.vincent.smart_toast.factory

import android.widget.Toast
import com.coder.vincent.smart_toast.Location
import com.coder.vincent.smart_toast.ToastConfig

abstract class DefaultToastConfig(override val alias: String) : ToastConfig {
    override var boundPageId: String = ""
    override var duration: Int = Toast.LENGTH_SHORT
    override lateinit var message: CharSequence
    override lateinit var location: Location

    override fun isSameContent(config: ToastConfig?) = message == config?.message
    override fun isSameLocation(config: ToastConfig?) = location == config?.location
}