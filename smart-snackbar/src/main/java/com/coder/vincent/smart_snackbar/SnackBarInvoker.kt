package com.coder.vincent.smart_snackbar

import android.view.View
import android.view.ViewGroup
import com.coder.vincent.series.common_lib.bean.IconPosition
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToString
import com.coder.vincent.series.common_lib.spToPx
import com.coder.vincent.smart_snackbar.bean.Duration
import com.coder.vincent.smart_snackbar.bean.SnackBarStyle
import com.coder.vincent.smart_snackbar.schedule.SnackBarScheduler

internal class SnackBarInvoker(
    private val container: ViewGroup,
    @SnackBarPosition val position: Int,
) : SnackBarFacade.Overall, SnackBarFacade.ConfigSetter {

    private var config: SnackBarConfig = SnackBarConfig()

    override fun config(): SnackBarFacade.ConfigSetter = this
    override fun themeStyle(style: SnackBarStyle): SnackBarFacade.ConfigSetter =
        this.apply {
            config.style = style
        }

    override fun backgroundColor(color: Int): SnackBarFacade.ConfigSetter =
        this.apply {
            config.backgroundColor = color
        }

    override fun backgroundColorResource(colorResId: Int): SnackBarFacade.ConfigSetter =
        backgroundColor(colorResId.resourceToColor())

    override fun icon(iconResId: Int): SnackBarFacade.ConfigSetter = this.apply {
        config.icon = iconResId
    }

    override fun iconPosition(iconPosition: IconPosition): SnackBarFacade.ConfigSetter =
        this.apply {
            config.iconPosition = iconPosition
        }

    override fun iconSizeDp(size: Float) =
        this.apply {
            config.iconSize = size.dpToPx()
        }

    override fun iconPaddingDp(padding: Float): SnackBarFacade.ConfigSetter =
        this.apply {
            config.iconPadding = padding.dpToPx()
        }


    override fun messageColor(color: Int): SnackBarFacade.ConfigSetter =
        this.apply {
            config.messageColor = color
        }

    override fun messageColorResource(colorResId: Int): SnackBarFacade.ConfigSetter =
        messageColor(colorResId.resourceToColor())

    override fun messageBold(bold: Boolean): SnackBarFacade.ConfigSetter =
        this.apply {
            config.messageBold = bold
        }

    override fun messageSizeSp(size: Float): SnackBarFacade.ConfigSetter =
        this.apply {
            config.messageSize = size.spToPx()
        }

    override fun actionLabelColor(color: Int): SnackBarFacade.ConfigSetter =
        this.apply {
            config.actionLabelColor = color
        }

    override fun actionLabelColorResource(colorResId: Int): SnackBarFacade.ConfigSetter =
        actionLabelColor(colorResId.resourceToColor())

    override fun actionLabelBold(bold: Boolean): SnackBarFacade.ConfigSetter =
        this.apply {
            config.actionLabelBold = bold
        }

    override fun actionLabelSizeSp(size: Float): SnackBarFacade.ConfigSetter =
        this.apply {
            config.actionLabelSize = size.spToPx()
        }

    override fun commit(): SnackBarFacade.ShowApi = this

    override fun show(msg: String) {
        showHelper(msg, "", emptyActionReaction, Duration.SHORT)
    }

    override fun show(msg: String, actionLabel: String) {
        showHelper(msg, actionLabel, emptyActionReaction, Duration.SHORT)
    }

    override fun show(msg: String, actionLabel: String, actionReaction: (View) -> Unit) {
        showHelper(
            msg,
            actionLabel,
            actionReaction,
            Duration.SHORT,
        )
    }

    override fun show(msg: Int) {
        showHelper(msg.resourceToString(), "", emptyActionReaction, Duration.SHORT)
    }

    override fun show(msg: Int, actionLabel: Int) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            emptyActionReaction,
            Duration.SHORT,
        )
    }

    override fun show(msg: Int, actionLabel: Int, actionReaction: (View) -> Unit) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            actionReaction,
            Duration.SHORT,
        )
    }

    override fun showLong(msg: String) {
        showHelper(msg, "", emptyActionReaction, Duration.LONG)
    }

    override fun showLong(msg: String, actionLabel: String) {
        showHelper(msg, actionLabel, emptyActionReaction, Duration.LONG)
    }

    override fun showLong(
        msg: String,
        actionLabel: String,
        actionReaction: (View) -> Unit
    ) {
        showHelper(
            msg,
            actionLabel,
            actionReaction,
            Duration.LONG,
        )
    }

    override fun showLong(msg: Int) {
        showHelper(msg.resourceToString(), "", emptyActionReaction, Duration.LONG)
    }

    override fun showLong(msg: Int, actionLabel: Int) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            emptyActionReaction,
            Duration.LONG,
        )
    }

    override fun showLong(msg: Int, actionLabel: Int, actionReaction: (View) -> Unit) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            actionReaction,
            Duration.LONG,
        )
    }

    override fun showIndefinite(msg: String) {
        showHelper(msg, "", emptyActionReaction, Duration.INDEFINITE)
    }

    override fun showIndefinite(msg: String, actionLabel: String) {
        showHelper(msg, actionLabel, emptyActionReaction, Duration.INDEFINITE)
    }

    override fun showIndefinite(
        msg: String,
        actionLabel: String,
        actionReaction: (View) -> Unit
    ) {
        showHelper(
            msg,
            actionLabel,
            actionReaction,
            Duration.INDEFINITE,
        )
    }

    override fun showIndefinite(msg: Int) {
        showHelper(msg.resourceToString(), "", emptyActionReaction, Duration.INDEFINITE)
    }

    override fun showIndefinite(msg: Int, actionLabel: Int) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            emptyActionReaction,
            Duration.INDEFINITE,
        )
    }

    override fun showIndefinite(
        msg: Int,
        actionLabel: Int,
        actionReaction: (View) -> Unit
    ) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            actionReaction,
            Duration.INDEFINITE,
        )
    }

    private fun showHelper(
        msg: String,
        actionLabel: String,
        actionReaction: (View) -> Unit,
        duration: Duration,
    ) {
        config.message = msg
        config.actionLabel = actionLabel
        config.actionReaction = actionReaction
        config.duration = duration
        config.position = position
        config.targetParent = container
        SnackBarScheduler.schedule(config)
    }
}

private val emptyActionReaction: (View) -> Unit = {}