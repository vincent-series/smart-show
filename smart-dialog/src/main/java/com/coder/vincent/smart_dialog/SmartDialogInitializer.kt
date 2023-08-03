package com.coder.vincent.smart_dialog

import android.content.Context
import androidx.startup.Initializer
import com.coder.vincent.series.common_lib.CommonLibInitializer
import com.coder.vincent.series.common_lib.VincentSeriesLib

class SmartDialogInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        VincentSeriesLib.addSubActivityLifecycleCallbacks(SmartDialogActivityLifecycleCallbacks())
    }

    override fun dependencies() =
        listOf<Class<out Initializer<*>>>(CommonLibInitializer::class.java)
}