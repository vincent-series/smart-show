package com.coder.vincent.smart_toast.alias.emotion

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.coder.vincent.smart_toast.ShowEmotionToast
import com.coder.vincent.smart_toast.bean.Duration

interface EmotionToastFacade {
    interface Overall : ShowEmotionToast {
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

        fun duration(duration: Duration): ConfigSetter

        fun commit(): ShowEmotionToast
    }
}
