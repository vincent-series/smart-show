package com.coder.vincent.smart_toast.compact

import android.view.View
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.smart_toast.bean.Duration
import com.coder.vincent.smart_toast.factory.ToastConfig

class ToastWindowStrategySelector {
    fun select(
        toastView: View,
        config: ToastConfig,
        configApplyCallback: (View, ToastConfig) -> Unit
    ): CompactToast =
        when {
            Toolkit.isSystemAlertWindowEnabled() -> SystemWindowToast(
                toastView,
                config,
                configApplyCallback
            )

            !isCustomDuration(config.duration) && Toolkit.isNotificationPermitted() -> OriginalToast(
                toastView,
                config,
                configApplyCallback
            )

            else -> DialogWindowToast(
                toastView,
                config,
                configApplyCallback
            )
        }
}

fun isCustomDuration(duration: Duration): Boolean =
    duration.value.toLong().let { (it != TOAST_DURATION_SHORT) and (it != TOAST_DURATION_LONG) }