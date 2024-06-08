package com.coder.vincent.smart_toast.schedule

import android.view.View
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.smart_toast.compact.CompactToast
import com.coder.vincent.smart_toast.compact.IdleToast
import com.coder.vincent.smart_toast.compact.ToastVisibilityObserver
import com.coder.vincent.smart_toast.factory.ToastConfig
import com.coder.vincent.smart_toast.factory.ToastFactory

object ToastScheduler : ToastVisibilityObserver {
    private val idleToast = IdleToast()
    private var currentToast: CompactToast = idleToast

    private fun canReuseToast(toastConfig: ToastConfig): Boolean {
        val isShowing = currentToast.isShowing()
        val isSameAlias = currentToast.config().alias == toastConfig.alias
        val isSameLocation = currentToast.config().location == toastConfig.location
        Toolkit.logD("isSameAlias:$isSameAlias#isSameLocation:$isSameLocation#isShowing:$isShowing")
        return isSameAlias && isSameLocation && isShowing
    }

    @JvmStatic
    @Synchronized
    fun schedule(toastConfig: ToastConfig, toastFactory: ToastFactory) {
        Toolkit.logD(toastConfig.toString())
        if (canReuseToast(toastConfig)) {
            currentToast.updateConfig(toastConfig)
            Toolkit.logD("just update toast config info:$currentToast.")
            return
        }

        if (currentToast.isShowing()) {
            currentToast.cancel()
            Toolkit.logD("cancel current toast:$currentToast.")
        }

        toastFactory.produceToast(toastConfig).apply {
            currentToast.removeVisibilityObserver(ToastScheduler)
            currentToast = this
            currentToast.setVisibilityObserver(ToastScheduler)
            Toolkit.logD("create new toast and show it:$currentToast.")
        }.show()
    }

    @Synchronized
    override fun onToastVisibilityChanged(view: View, visible: Boolean) {
        if (!visible && view == currentToast.view()) {
            Toolkit.logD("release current toast because of natural dismiss:$currentToast")
            currentToast = IdleToast()
        }
    }

    fun dismiss() {
        currentToast.cancel()
    }

    fun isShowing() = currentToast.isShowing()
}