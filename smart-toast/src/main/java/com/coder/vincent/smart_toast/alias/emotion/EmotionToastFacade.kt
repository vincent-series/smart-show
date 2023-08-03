package com.coder.vincent.smart_toast.alias.emotion

import android.graphics.drawable.Drawable
import androidx.annotation.BoolRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

interface EmotionToastFacade {
    interface Overall : ShowEmotionToastApi {
        fun config(): ConfigSetter
    }

    interface ConfigSetter {
        fun backgroundDrawable(bgDrawable: Drawable): ConfigSetter

        fun backgroundResource(@DrawableRes bgResource: Int): ConfigSetter

        fun messageColor(@ColorInt msgColor: Int): ConfigSetter

        fun messageColorResource(@ColorRes msgColorResource: Int): ConfigSetter

        fun messageSize(msgSize: Float): ConfigSetter

        fun messageBold(msgBold: Boolean): ConfigSetter

        fun messageBoldResource(@BoolRes msgBoldResource: Int): ConfigSetter

        fun iconDrawable(iconDrawable: Drawable?): ConfigSetter

        fun iconResource(@DrawableRes iconResource: Int): ConfigSetter

        fun backgroundColor(backgroundColor: Int): ConfigSetter

        fun backgroundColorResource(@ColorRes backgroundColorResource: Int):
                ConfigSetter

        fun iconSizeDp(iconSize: Float): ConfigSetter

        fun iconPaddingDp(iconPadding: Float): ConfigSetter

        fun commit(): ShowEmotionToastApi
    }
}
