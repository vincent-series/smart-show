package com.coder.vincent.smart_snackbar.schedule

import android.view.ViewGroup
import com.coder.vincent.smart_snackbar.SnackBarConfig
import com.coder.vincent.smart_snackbar.view.ContainerValidStateChangeListener
import com.coder.vincent.smart_snackbar.view.ViewAttachListener

internal object SnackBarScheduler : ContainerValidStateChangeListener {
    private var snackBar: ScheduledSnackBar? = null

    fun schedule(config: SnackBarConfig) {
        config.targetParent.addOnAttachStateChangeListener(
            ViewAttachListener(this)
        )
        snackBar = prepareScheduledSnackBar(snackBar, config)
        snackBar?.show()
    }

    private fun prepareScheduledSnackBar(
        bar: ScheduledSnackBar?,
        config: SnackBarConfig
    ): ScheduledSnackBar {
        if (bar == null
            || bar.isDismissedByGesture()
            || bar.config().targetParent != config.targetParent
            || bar.config().style != config.style) {
            return ScheduledSnackBarImpl(config)
        }
        val isShowing = bar.isShowing()
        val messageChanged = bar.config().message != config.message
        val actionLabelChanged = bar.config().actionLabel != config.actionLabel
        return if (isShowing && (messageChanged || actionLabelChanged)) {
            ScheduledSnackBarImpl(config)
        } else {
            bar.applyNewConfig(config)
        }
    }

    override fun onContainerValidStateChanged(targetParent: ViewGroup, valid: Boolean) {
        if (snackBar?.config()?.targetParent == targetParent && !valid) {
            snackBar = null
        }
    }

    fun dismiss() {
        snackBar?.dismiss()
    }
}