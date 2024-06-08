package com.coder.vincent.smart_toast.compact

import android.os.Build
import androidx.viewbinding.ViewBinding
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.lifecycle.ActivityStack
import com.coder.vincent.smart_toast.bean.Duration
import com.coder.vincent.smart_toast.factory.ToastConfig

class ToastWindowStrategySelector {
    fun select(
        toastView: ViewBinding,
        config: ToastConfig,
        configApplyCallback: (ViewBinding, ToastConfig) -> Unit
    ): CompactToast =
        when {
            Toolkit.isSystemAlertWindowEnabled() -> SystemWindowToast(
                toastView,
                config,
                configApplyCallback
            )

            ActivityStack.isInBackground() && !Toolkit.sdkVersionBelow(Build.VERSION_CODES.R) -> OriginalToast(
                toastView.root,
                config
            )

            !isDurationCustomized(config.duration) && Toolkit.isNotificationPermitted() -> DecoratedToast(
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

fun isDurationCustomized(duration: Duration): Boolean =
    duration.value.toLong().let { (it != TOAST_DURATION_SHORT) and (it != TOAST_DURATION_LONG) }