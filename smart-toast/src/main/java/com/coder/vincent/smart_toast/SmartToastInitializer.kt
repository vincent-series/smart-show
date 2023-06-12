package com.coder.vincent.smart_toast

import android.content.Context
import androidx.startup.Initializer
import com.coder.vincent.series.common_lib.CommonLibInitializer
import com.coder.vincent.series.common_lib.vincentActivityLifecycleCallbacks
import com.coder.vincent.smart_toast.lifecycle.SmartToastActivityLifecycleCallbacks

class SmartToastInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        vincentActivityLifecycleCallbacks.addSubCallbacks(SmartToastActivityLifecycleCallbacks())
    }

    override fun dependencies() =
        listOf<Class<out Initializer<*>>>(CommonLibInitializer::class.java)
}