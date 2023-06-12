package com.coder.vincent.smart_toast.factory

import android.view.View
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.smart_toast.CompactToast
import com.coder.vincent.smart_toast.ToastConfig
import com.coder.vincent.smart_toast.compact.DialogToast
import com.coder.vincent.smart_toast.compact.RealToast
import com.coder.vincent.smart_toast.compact.SystemWindowToast

internal class CompactToastCreator {
    fun create(toastView: View, config: ToastConfig): CompactToast =
        when {
            Toolkit.isSystemAlertWindowEnabled() -> SystemWindowToast(toastView, config)
            Toolkit.isNotificationPermitted() -> RealToast(toastView, config)
            else -> DialogToast(toastView, config)
        }
}