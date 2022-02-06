package com.coder.vincent.smart_toast.alias.classic

import android.view.Gravity
import android.widget.Toast
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.getToolbarHeight
import com.coder.vincent.series.common_lib.resourceToString
import com.coder.vincent.smart_toast.DEFAULT_TOAST_Y_OFFSET
import com.coder.vincent.smart_toast.Location
import com.coder.vincent.smart_toast.PlainToastApi
import com.coder.vincent.smart_toast.compact.ToastTransitionIntent
import com.coder.vincent.smart_toast.schedule.ToastScheduler

class ClassicToastInvoker : ClassicToastFacade.Overall, ClassicToastFacade.ConfigSetter {
    private val config: ClassicToast.Config = ClassicToast.Config()

    override fun config(): ClassicToastFacade.ConfigSetter = this

    override fun show(msg: CharSequence) {
        showHelper(
            msg,
            Toast.LENGTH_SHORT,
            Location(
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                0,
                DEFAULT_TOAST_Y_OFFSET,
            )
        )
    }

    override fun show(msg: Int) {
        show(msg.resourceToString())
    }

    override fun showAtTop(msg: CharSequence) {
        showHelper(
            msg,
            Toast.LENGTH_SHORT,
            Location(
                Gravity.TOP or Gravity.CENTER_HORIZONTAL,
                0,
                getToolbarHeight() + 40f.dpToPx(),
            ),
        )
    }

    override fun showAtTop(msg: Int) {
        showAtTop(msg.resourceToString())
    }

    override fun showInCenter(msg: CharSequence) {
        showHelper(msg, Toast.LENGTH_SHORT, Location(Gravity.CENTER, 0, 0))
    }

    override fun showInCenter(msg: Int) {
        showInCenter(msg.resourceToString())
    }

    override fun showAtLocation(
        msg: CharSequence,
        gravity: Int,
        xOffsetDp: Float,
        yOffsetDp: Float,
    ) {
        showHelper(
            msg,
            Toast.LENGTH_SHORT,
            Location(
                gravity,
                xOffsetDp.dpToPx(),
                yOffsetDp.dpToPx()
            ),
        )
    }

    override fun showAtLocation(
        msg: Int,
        gravity: Int,
        xOffsetDp: Float,
        yOffsetDp: Float
    ) {
        showAtLocation(msg.resourceToString(), gravity, xOffsetDp, yOffsetDp)
    }

    override fun showLong(msg: CharSequence) {
        showHelper(
            msg,
            Toast.LENGTH_LONG,
            Location(
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
                0,
                DEFAULT_TOAST_Y_OFFSET,
            ),
        )
    }

    override fun showLong(msg: Int) {
        showLong(msg.resourceToString())
    }

    override fun showLongAtTop(msg: CharSequence) {
        showHelper(
            msg,
            Toast.LENGTH_LONG,
            Location(
                Gravity.TOP or Gravity.CENTER_HORIZONTAL,
                0,
                getToolbarHeight() + 40f.dpToPx(),
            )
        )
    }

    override fun showLongAtTop(msg: Int) {
        showLongAtTop(msg.resourceToString())
    }

    override fun showLongInCenter(msg: CharSequence) {
        showHelper(msg, Toast.LENGTH_LONG, Location(Gravity.CENTER, 0, 0))
    }

    override fun showLongInCenter(msg: Int) {
        showLongInCenter(msg.resourceToString())
    }

    override fun showLongAtLocation(
        msg: CharSequence,
        gravity: Int,
        xOffsetDp: Float,
        yOffsetDp: Float
    ) {
        showHelper(
            msg,
            Toast.LENGTH_LONG,
            Location(
                gravity,
                xOffsetDp.dpToPx(),
                yOffsetDp.dpToPx(),
            )
        )
    }

    override fun showLongAtLocation(
        msg: Int,
        gravity: Int,
        xOffsetDp: Float,
        yOffsetDp: Float
    ) {
        showLongAtLocation(msg.resourceToString(), gravity, xOffsetDp, yOffsetDp)
    }

    private fun showHelper(
        msg: CharSequence,
        duration: Int,
        location: Location
    ) {
        config.message = msg
        config.location = location
        config.duration = duration
        ToastScheduler.schedule(config, ClassicToastFactory)
    }

    override fun backgroundResource(backgroundResource: Int): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.backgroundResource = backgroundResource
        }

    override fun backgroundColor(backgroundColor: Int): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.backgroundColor = backgroundColor
        }

    override fun messageSize(messageSize: Float): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.messageSize = messageSize
        }

    override fun messageColor(messageColor: Int): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.messageColor = messageColor
        }

    override fun messageBold(messageBold: Boolean): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.messageBold = messageBold
        }

    override fun iconResource(icon: Int): ClassicToastFacade.ConfigSetter = this.apply {
        config.icon = icon
    }

    override fun iconPosition(iconPosition: Int): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.iconPosition = iconPosition
        }

    override fun iconSizeDp(iconSizeDp: Float): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.iconSizeDp = iconSizeDp
        }

    override fun iconPaddingDp(iconPaddingDp: Float): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.iconPaddingDp = iconPaddingDp
        }

    override fun targetPage(transitionIntent: ToastTransitionIntent): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.boundPageId = transitionIntent.boundPageId
        }

    override fun apply(): PlainToastApi = this
}
