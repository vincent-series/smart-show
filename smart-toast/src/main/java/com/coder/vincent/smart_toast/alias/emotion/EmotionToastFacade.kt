package com.coder.vincent.smart_toast.alias.emotion

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.coder.vincent.smart_toast.compact.ToastTransitionIntent

interface EmotionToastFacade {
    interface Overall : EmotionToastApi {
        fun config(): ConfigSetter
    }

    interface ConfigSetter {
        fun backgroundColor(@ColorInt color: Int): ConfigSetter
        fun backgroundColorResource(@ColorRes colorResource: Int): ConfigSetter
        fun iconResource(@DrawableRes iconResource: Int): ConfigSetter
        fun iconSizeDp(sizeDp: Float): ConfigSetter
        fun iconPaddingDp(padding: Float): ConfigSetter
        fun messageColor(@ColorInt color: Int): ConfigSetter
        fun messageColorResource(@ColorRes colorResource: Int): ConfigSetter
        fun messageSizeSp(sizeSp: Float): ConfigSetter
        fun messageBold(bold: Boolean): ConfigSetter
        fun targetPage(transitionIntent: ToastTransitionIntent): ConfigSetter
        fun apply(): EmotionToastApi
    }
}