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
        scheduledSnackBar: ScheduledSnackBar?,
        config: SnackBarConfig
    ): ScheduledSnackBar {
        if (scheduledSnackBar == null) {
            return barProvider.provideSnackBar(config)
        }
        val isShowing = scheduledSnackBar.isShowing()
        val messageChanged = scheduledSnackBar.config().message != config.message
        val actionLabelChanged = scheduledSnackBar.config().actionLabel != config.actionLabel
        return if (isShowing && (messageChanged || actionLabelChanged)) {
            barProvider.provideSnackBar(config)
        } else {
            scheduledSnackBar.applyNewConfig(config)
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

}