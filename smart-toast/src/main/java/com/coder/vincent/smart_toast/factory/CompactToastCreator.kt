package com.coder.vincent.smart_toast.factory

import android.view.View
import com.coder.vincent.series.common_lib.isNotificationPermitted
import com.coder.vincent.series.common_lib.isSystemAlertWindowEnabled
import com.coder.vincent.smart_toast.CompactToast
import com.coder.vincent.smart_toast.ToastConfig
import com.coder.vincent.smart_toast.compact.DialogToast
import com.coder.vincent.smart_toast.compact.RealToast
import com.coder.vincent.smart_toast.compact.SystemWindowToast

internal class CompactToastCreator {
    fun create(toastView: View, config: ToastConfig): CompactToast =
        when {
            isSystemAlertWindowEnabled() -> SystemWindowToast(toastView, config)
            isNotificationPermitted() -> RealToast(toastView, config)
            else -> DialogToast(toastView, config)
        }
}