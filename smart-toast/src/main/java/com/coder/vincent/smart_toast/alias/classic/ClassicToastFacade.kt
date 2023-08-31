package com.coder.vincent.smart_toast.alias.classic

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.coder.vincent.series.common_lib.bean.IconPosition
import com.coder.vincent.smart_toast.ShowToast

interface ClassicToastFacade {
    interface Overall : ShowToast {
        fun config(): ConfigSetter
    }

    interface ConfigSetter {
        fun backgroundDrawable(bgDrawable: Drawable): ConfigSetter

        fun backgroundResource(@DrawableRes bgResource: Int): ConfigSetter

        fun messageStyle(
            @ColorInt color: Int,
            size: Float,
            bold: Boolean,
        ): ConfigSetter

        fun iconDrawable(iconDrawable: Drawable?): ConfigSetter

        fun iconResource(@DrawableRes iconResource: Int): ConfigSetter

        fun iconSize(size: Float): ConfigSetter

        fun marginBetweenIconAndMsg(margin: Float): ConfigSetter

        fun backgroundColor(@ColorInt backgroundColor: Int): ConfigSetter

        fun backgroundColorResource(@ColorRes backgroundColorResource: Int): ConfigSetter

        fun iconPosition(iconPosition: IconPosition): ConfigSetter

        fun commit(): ShowToast
    }
}
