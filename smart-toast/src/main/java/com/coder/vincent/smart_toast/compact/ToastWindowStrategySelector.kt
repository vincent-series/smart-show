package com.coder.vincent.smart_toast.compact

import android.view.View
import com.coder.vincent.series.common_lib.Toolkit
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

            Toolkit.isNotificationPermitted() -> OriginalToast(
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