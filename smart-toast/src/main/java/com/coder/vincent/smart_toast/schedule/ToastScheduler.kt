package com.coder.vincent.smart_toast.schedule

import android.view.View
import com.coder.vincent.series.common_lib.logD
import com.coder.vincent.smart_toast.CompactToast
import com.coder.vincent.smart_toast.ToastConfig
import com.coder.vincent.smart_toast.compact.DialogToast
import com.coder.vincent.smart_toast.compact.ToastVisibilityChangedListener
import com.coder.vincent.smart_toast.factory.ToastFactory

internal object ToastScheduler : ToastVisibilityChangedListener {
    private var currentToast: CompactToast? = null

    @JvmStatic
    @Synchronized
    fun schedule(newToastConfig: ToastConfig, toastFactory: ToastFactory) {
        logD(newToastConfig.toString())
        val isSameAlias = currentToast?.config()?.alias == newToastConfig.alias
        val isSameLocation = newToastConfig.isSameLocation(currentToast?.config())
        val isShowing = currentToast?.isShowing() ?: false

        logD("isSameAlias:$isSameAlias#isSameLocation:$isSameLocation#isShowing:$isShowing")

        if (isShowing && isSameAlias && isSameLocation) {
            toastFactory.applyNewConfig(currentToast!!.view(), newToastConfig)
            currentToast!!.updateConfig(newToastConfig)
            logD("just update toast config info:$currentToast.")
            return
        }

        if (isShowing) {
            currentToast?.cancel()
            logD("cancel current toast:$currentToast.")
        }

        toastFactory.produceToast(newToastConfig)?.apply {
            currentToast?.removeVisibilityObserver(ToastScheduler)
            setVisibilityObserver(ToastScheduler)
            currentToast = this
            logD("create new toast and show it:$currentToast.")
        }?.let { compactToast ->
            if (newToastConfig.boundPageId.isEmpty() || currentToast !is DialogToast) {
                compactToast.show()
                return
            }
            logD("suppress toast temporarily because of bound id(${newToastConfig.boundPageId}):$currentToast")
        }

    }

    @JvmStatic
    @Synchronized
    fun schedule(boundPageId: String) {
        currentToast?.let {
            if (boundPageId == it.config().boundPageId && it is DialogToast && !it.isShowing()) {
                it.show()
                logD("show bounded toast($boundPageId):$it")
            }
        }
    }

    @Synchronized
    override fun onToastVisibilityChanged(view: View, visible: Boolean) {
        if (!visible && view == currentToast?.view()) {
            logD("release current toast because of natural dismiss:$currentToast")
            currentToast = null
        }
    }

    fun dismiss() {
        currentToast?.cancel()
    }

    fun isShowing() = currentToast?.isShowing() == true
}