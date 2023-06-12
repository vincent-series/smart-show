package com.coder.vincent.smart_dialog

import android.content.Context
import androidx.startup.Initializer
import com.coder.vincent.series.common_lib.CommonLibInitializer
import com.coder.vincent.series.common_lib.vincentActivityLifecycleCallbacks

class SmartDialogInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        vincentActivityLifecycleCallbacks.addSubCallbacks(SmartDialogActivityLifecycleCallbacks())
    }

    override fun dependencies() =
        listOf<Class<out Initializer<*>>>(CommonLibInitializer::class.java)
}