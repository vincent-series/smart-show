package com.coder.vincent.smart_snackbar.schedule

import android.view.ViewGroup
import com.coder.vincent.smart_snackbar.SNACK_BAR_POSITION_BOTTOM
import com.coder.vincent.smart_snackbar.SNACK_BAR_POSITION_TOP
import com.coder.vincent.smart_snackbar.SnackBarConfig
import com.coder.vincent.smart_snackbar.SnackBarProvider
import com.coder.vincent.smart_snackbar.view.ContainerValidStateChangeListener
import com.coder.vincent.smart_snackbar.view.ViewAttachListener

internal object SnackBarScheduler : ContainerValidStateChangeListener {
    private var bottomBar: ScheduledSnackBar? = null
    private var topBar: ScheduledSnackBar? = null
    private val barProvider = SnackBarProvider()

    fun schedule(config: SnackBarConfig) {
        config.targetParent.addOnAttachStateChangeListener(
            ViewAttachListener(this)
        )
        when (config.position) {
            SNACK_BAR_POSITION_BOTTOM -> {
                bottomBar = prepareScheduledSnackBar(bottomBar, config)
                bottomBar?.show()
            }
            SNACK_BAR_POSITION_TOP -> {
                topBar = prepareScheduledSnackBar(topBar, config)
                topBar?.show()
            }
        }
    }

    private fun prepareScheduledSnackBar(
        bar: ScheduledSnackBar?,
        config: SnackBarConfig
    ): ScheduledSnackBar {
        if (bar == null || bar.isDismissedByGesture() || bar.config().targetParent != config.targetParent) {
            return barProvider.provideSnackBar(config)
        }
        val isShowing = bar.isShowing()
        val messageChanged = bar.config().message != config.message
        val actionLabelChanged = bar.config().actionLabel != config.actionLabel
        return if (isShowing && (messageChanged || actionLabelChanged)) {
            barProvider.provideSnackBar(config)
        } else {
            bar.applyNewConfig(config)
        }
    }

    override fun onContainerValidStateChanged(targetParent: ViewGroup, valid: Boolean) {
        if (bottomBar?.config()?.targetParent == targetParent && !valid) {
            bottomBar = null
        }
        if (topBar?.config()?.targetParent == targetParent && !valid) {
            topBar = null
        }
    }

    fun dismiss() {
        topBar?.dismiss()
        bottomBar?.dismiss()
    }

    fun dismissBottom() {
        bottomBar?.dismiss()
    }

    fun dismissTop() {
        topBar?.dismiss()
    }

}