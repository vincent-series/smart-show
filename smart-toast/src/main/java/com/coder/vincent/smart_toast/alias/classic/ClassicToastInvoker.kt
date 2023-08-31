package com.coder.vincent.smart_toast.alias.classic

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.bean.IconPosition
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToDrawable
import com.coder.vincent.series.common_lib.resourceToString
import com.coder.vincent.smart_toast.DEFAULT_TOAST_Y_OFFSET
import com.coder.vincent.smart_toast.ShowToast
import com.coder.vincent.smart_toast.factory.Location
import com.coder.vincent.smart_toast.schedule.ToastScheduler

class ClassicToastInvoker : ClassicToastFacade.Overall, ClassicToastFacade.ConfigSetter {
    private val config: ClassicToast.Config = ClassicToast.Config()

    override fun config(): ClassicToastFacade.ConfigSetter = this

    override fun show(msg: CharSequence) {
        showHelper(
            msg, Toast.LENGTH_SHORT, Location(
                Gravity.BOTTOM or
                        Gravity.CENTER_HORIZONTAL, 0, DEFAULT_TOAST_Y_OFFSET
            )
        )
    }

    override fun show(@StringRes msg: Int) {
        show(msg.resourceToString())
    }

    override fun showAtTop(msg: CharSequence) {
        showHelper(
            msg, Toast.LENGTH_SHORT,
            Location(
                Gravity.TOP or
                        Gravity.CENTER_HORIZONTAL, 0, Toolkit.getToolbarHeight() + 40f.dpToPx()
            ),
        )
    }

    override fun showAtTop(@StringRes msg: Int) {
        showAtTop(msg.resourceToString())
    }

    override fun showInCenter(msg: CharSequence) {
        showHelper(msg, Toast.LENGTH_SHORT, Location(Gravity.CENTER, 0, 0))
    }

    override fun showInCenter(@StringRes msg: Int) {
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
            Location(gravity, xOffsetDp.dpToPx(), yOffsetDp.dpToPx())
        )
    }

    override fun showAtLocation(
        @StringRes msg: Int,
        gravity: Int,
        xOffsetDp: Float,
        yOffsetDp: Float,
    ) {
        showAtLocation(msg.resourceToString(), gravity, xOffsetDp, yOffsetDp)
    }

    override fun showLong(msg: CharSequence) {
        showHelper(
            msg, Toast.LENGTH_LONG,
            Location(
                Gravity.BOTTOM or
                        Gravity.CENTER_HORIZONTAL, 0, DEFAULT_TOAST_Y_OFFSET
            ),
        )
    }

    override fun showLong(@StringRes msg: Int) {
        showLong(msg.resourceToString())
    }

    override fun showLongAtTop(msg: CharSequence) {
        showHelper(
            msg, Toast.LENGTH_LONG, Location(
                Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0,
                Toolkit.getToolbarHeight() + 40f.dpToPx()
            )
        )
    }

    override fun showLongAtTop(@StringRes msg: Int) {
        showLongAtTop(msg.resourceToString())
    }

    override fun showLongInCenter(msg: CharSequence) {
        showHelper(msg, Toast.LENGTH_LONG, Location(Gravity.CENTER, 0, 0))
    }

    override fun showLongInCenter(@StringRes msg: Int) {
        showLongInCenter(msg.resourceToString())
    }

    override fun showLongAtLocation(
        msg: CharSequence,
        gravity: Int,
        xOffsetDp: Float,
        yOffsetDp: Float,
    ) {
        showHelper(
            msg,
            Toast.LENGTH_LONG,
            Location(gravity, xOffsetDp.dpToPx(), yOffsetDp.dpToPx())
        )
    }

    override fun showLongAtLocation(
        @StringRes msg: Int,
        gravity: Int,
        xOffsetDp: Float,
        yOffsetDp: Float,
    ) {
        showLongAtLocation(msg.resourceToString(), gravity, xOffsetDp, yOffsetDp)
    }

    private fun showHelper(
        msg: CharSequence,
        duration: Int,
        location: Location,
    ) {
        config.message = msg
        config.location = location
        config.duration = duration
        ToastScheduler.schedule(config, ClassicToastFactory)
    }

    override fun backgroundDrawable(bgDrawable: Drawable): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.backgroundDrawable = bgDrawable
        }

    override fun backgroundResource(@DrawableRes bgResource: Int): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.backgroundDrawable = bgResource.resourceToDrawable()!!
        }

    override fun messageStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): ClassicToastFacade.ConfigSetter = this.apply {
        config.messageStyle = TextStyle(color, size, bold)
    }

    override fun iconDrawable(iconDrawable: Drawable?): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.iconDrawable = iconDrawable
        }

    override fun iconResource(@DrawableRes iconResource: Int): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.iconDrawable = iconResource.resourceToDrawable()
        }

    override fun iconSize(size: Float): ClassicToastFacade.ConfigSetter = this.apply {
        config.iconSize = size
    }

    override fun marginBetweenIconAndMsg(margin: Float): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.marginBetweenIconAndMsg = margin
        }

    override fun backgroundColor(backgroundColor: Int): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.backgroundColor = backgroundColor
        }

    override fun backgroundColorResource(@ColorRes backgroundColorResource: Int):
            ClassicToastFacade.ConfigSetter = this.apply {
        config.backgroundColor = backgroundColorResource.resourceToColor()
    }

    override fun iconPosition(iconPosition: IconPosition): ClassicToastFacade.ConfigSetter =
        this.apply {
            config.iconPosition = iconPosition
        }

    override fun commit(): ShowToast = this
}
