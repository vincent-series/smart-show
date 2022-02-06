package com.coder.vincent.smart_toast.alias.classic

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.coder.vincent.smart_toast.PlainToastApi
import com.coder.vincent.smart_toast.compact.ToastTransitionIntent

interface ClassicToastFacade {
    interface Overall : PlainToastApi {
        fun config(): ConfigSetter
    }

    interface ConfigSetter {
        fun backgroundResource(@DrawableRes backgroundResource: Int): ConfigSetter

        fun backgroundColor(@ColorInt backgroundColor: Int): ConfigSetter

        fun messageSize(messageSize: Float): ConfigSetter

        fun messageColor(@ColorInt messageColor: Int): ConfigSetter

        fun messageBold(messageBold: Boolean): ConfigSetter

        fun iconResource(@DrawableRes icon: Int): ConfigSetter

        fun iconPosition(@IconPosition iconPosition: Int): ConfigSetter

        fun iconSizeDp(iconSizeDp: Float): ConfigSetter

        fun iconPaddingDp(iconPaddingDp: Float): ConfigSetter

        fun targetPage(transitionIntent: ToastTransitionIntent): ConfigSetter

        fun apply(): PlainToastApi
    }
}
