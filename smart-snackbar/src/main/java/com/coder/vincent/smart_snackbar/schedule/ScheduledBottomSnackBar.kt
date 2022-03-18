package com.coder.vincent.smart_snackbar.schedule

import com.coder.vincent.smart_snackbar.SnackBarConfig
import com.google.android.material.snackbar.Snackbar

internal class ScheduledBottomSnackBar(private var config: SnackBarConfig) : ScheduledSnackBar {
    private val bar: Snackbar =
        Snackbar.make(config.targetParent, config.message, config.duration.value)

    init {
        bar.setAction(config.actionLabel, config.actionReaction)
        bar.animationMode = config.animationMode.value
    }

    override fun config() = config

    override fun applyNewConfig(config: SnackBarConfig): ScheduledSnackBar {
        this.config = config
        bar.setText(config.message)
        bar.duration = config.duration.value
        bar.setAction(config.actionLabel, config.actionReaction)
        bar.animationMode = config.animationMode.value
        return this
    }

    override fun show() {
        bar.show()
    }

    override fun dismiss() {
        bar.dismiss()
    }

    override fun isShowing() = bar.isShown
}