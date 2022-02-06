package com.coder.vincent.smart_toast

import com.coder.vincent.smart_toast.alias.classic.ClassicToastFacade
import com.coder.vincent.smart_toast.alias.classic.ClassicToastInvoker
import com.coder.vincent.smart_toast.alias.emotion.EmotionToastFacade
import com.coder.vincent.smart_toast.alias.emotion.EmotionToastInvoker
import com.coder.vincent.smart_toast.schedule.ToastScheduler

/**
 * Created by Vincent on 2017/11/14.
 */

object SmartToast {
    @JvmStatic
    fun classic(): ClassicToastFacade.Overall = ClassicToastInvoker()

    @JvmStatic
    fun emotion(): EmotionToastFacade.Overall = EmotionToastInvoker()

    @JvmStatic
    fun isShowing() = ToastScheduler.isShowing()

    @JvmStatic
    fun dismiss() {
        ToastScheduler.dismiss()
    }
}