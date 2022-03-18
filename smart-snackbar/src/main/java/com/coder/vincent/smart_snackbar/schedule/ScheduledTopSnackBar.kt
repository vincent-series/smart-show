package com.coder.vincent.smart_snackbar.schedule

import com.coder.vincent.smart_snackbar.SnackBarConfig
import com.coder.vincent.smart_snackbar.TopSnackBar

class ScheduledTopSnackBar(private var config: SnackBarConfig) : ScheduledSnackBar {
    private val bar: TopSnackBar =
        TopSnackBar.make(config.targetParent, config.message, config.duration.value)

    init {
        bar.setAction(config.actionLabel, config.actionReaction)
        bar.animationMode = config.animationMode.value
    }

    override fun config(): SnackBarConfig = config

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