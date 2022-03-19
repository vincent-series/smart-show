package com.coder.vincent.smart_snackbar

import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToString
import com.coder.vincent.smart_snackbar.bean.Duration
import com.coder.vincent.smart_snackbar.schedule.SnackBarScheduler
import com.coder.vincent.smart_snackbar.util.Utils.spToPx

internal class SnackBarInvoker(var container: ViewGroup) : SnackBarFacade.Overall,
    SnackBarFacade.ConfigSetter {

    private var config: SnackBarConfig = SnackBarConfig()

    override fun config(): SnackBarFacade.ConfigSetter = this
    override fun backgroundColor(color: Int): SnackBarFacade.ConfigSetter =
        this.apply {
            config.backgroundColor = color
        }

    override fun backgroundColorResource(colorResId: Int): SnackBarFacade.ConfigSetter =
        backgroundColor(colorResId.resourceToColor())

    override fun icon(iconResId: Int): SnackBarFacade.ConfigSetter = this.apply {
        config.icon = iconResId
    }

    override fun iconPosition(iconPosition: Int): SnackBarFacade.ConfigSetter =
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

    override fun apply(): SnackBarFacade.ShowApi = this

    override fun showAtBottom(msg: String, actionLabel: String, actionReaction: (View) -> Unit) {
        showHelper(
            msg,
            actionLabel,
            actionReaction,
            Duration.SHORT,
            SNACK_BAR_POSITION_BOTTOM,
            parseTargetParent(container, SNACK_BAR_POSITION_BOTTOM)
        )
    }

    override fun showAtBottom(msg: Int, actionLabel: Int, actionReaction: (View) -> Unit) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            actionReaction,
            Duration.SHORT,
            SNACK_BAR_POSITION_BOTTOM,
            parseTargetParent(container, SNACK_BAR_POSITION_BOTTOM)
        )
    }

    override fun showLongAtBottom(
        msg: String,
        actionLabel: String,
        actionReaction: (View) -> Unit
    ) {
        showHelper(
            msg,
            actionLabel,
            actionReaction,
            Duration.LONG,
            SNACK_BAR_POSITION_BOTTOM,
            parseTargetParent(container, SNACK_BAR_POSITION_BOTTOM)
        )
    }

    override fun showLongAtBottom(msg: Int, actionLabel: Int, actionReaction: (View) -> Unit) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            actionReaction,
            Duration.LONG,
            SNACK_BAR_POSITION_BOTTOM,
            parseTargetParent(container, SNACK_BAR_POSITION_BOTTOM)
        )
    }

    override fun showIndefiniteAtBottom(
        msg: String,
        actionLabel: String,
        actionReaction: (View) -> Unit
    ) {
        showHelper(
            msg,
            actionLabel,
            actionReaction,
            Duration.INDEFINITE,
            SNACK_BAR_POSITION_BOTTOM,
            parseTargetParent(container, SNACK_BAR_POSITION_BOTTOM)
        )
    }

    override fun showIndefiniteAtBottom(
        msg: Int,
        actionLabel: Int,
        actionReaction: (View) -> Unit
    ) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            actionReaction,
            Duration.INDEFINITE,
            SNACK_BAR_POSITION_BOTTOM,
            parseTargetParent(container, SNACK_BAR_POSITION_BOTTOM)
        )
    }

    override fun showAtTop(msg: String, actionLabel: String, actionReaction: (View) -> Unit) {
        showHelper(
            msg,
            actionLabel,
            actionReaction,
            Duration.SHORT,
            SNACK_BAR_POSITION_TOP,
            parseTargetParent(container, SNACK_BAR_POSITION_TOP)
        )
    }

    override fun showAtTop(msg: Int, actionLabel: Int, actionReaction: (View) -> Unit) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            actionReaction,
            Duration.SHORT,
            SNACK_BAR_POSITION_TOP,
            parseTargetParent(container, SNACK_BAR_POSITION_TOP)
        )
    }

    override fun showLongAtTop(msg: String, actionLabel: String, actionReaction: (View) -> Unit) {
        showHelper(
            msg,
            actionLabel,
            actionReaction,
            Duration.LONG,
            SNACK_BAR_POSITION_TOP,
            parseTargetParent(container, SNACK_BAR_POSITION_TOP)
        )
    }

    override fun showLongAtTop(msg: Int, actionLabel: Int, actionReaction: (View) -> Unit) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            actionReaction,
            Duration.LONG,
            SNACK_BAR_POSITION_TOP,
            parseTargetParent(container, SNACK_BAR_POSITION_TOP)
        )
    }

    override fun showIndefiniteAtTop(
        msg: String,
        actionLabel: String,
        actionReaction: (View) -> Unit
    ) {
        showHelper(
            msg,
            actionLabel,
            actionReaction,
            Duration.INDEFINITE,
            SNACK_BAR_POSITION_TOP,
            parseTargetParent(container, SNACK_BAR_POSITION_TOP)
        )
    }

    override fun showIndefiniteAtTop(msg: Int, actionLabel: Int, actionReaction: (View) -> Unit) {
        showHelper(
            msg.resourceToString(),
            actionLabel.resourceToString(),
            actionReaction,
            Duration.INDEFINITE,
            SNACK_BAR_POSITION_TOP,
            parseTargetParent(container, SNACK_BAR_POSITION_TOP)
        )
    }

    private fun parseTargetParent(container: ViewGroup, position: Int): ViewGroup {
        return if (container is CoordinatorLayout || position == SNACK_BAR_POSITION_TOP)
            container
        else
            container.findViewById(android.R.id.content)
    }

    private fun showHelper(
        msg: String,
        actionLabel: String,
        actionReaction: (View) -> Unit,
        duration: Duration,
        position: Int,
        targetParent: ViewGroup,
    ) {
        config.message = msg
        config.actionLabel = actionLabel
        config.actionReaction = actionReaction
        config.duration = duration
        config.position = position
        config.targetParent = targetParent
        SnackBarScheduler.schedule(config)
    }
}