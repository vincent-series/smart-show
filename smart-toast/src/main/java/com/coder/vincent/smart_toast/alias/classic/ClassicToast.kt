package com.coder.vincent.smart_toast.alias.classic

import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.coder.vincent.series.annotations.smart_toast.ToastConfig
import com.coder.vincent.series.annotations.smart_toast.ToastDefinition
import com.coder.vincent.series.annotations.smart_toast.ToastView
import com.coder.vincent.series.common_lib.application
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.layoutInflater
import com.coder.vincent.smart_toast.*
import com.coder.vincent.smart_toast.databinding.SmartShowClassicToastBinding
import com.coder.vincent.smart_toast.factory.DefaultToastConfig
import kotlin.math.max

@ToastDefinition(alias = TOAST_TYPE_CLASSIC)
internal class ClassicToast {

    @ToastConfig
    class Config : DefaultToastConfig(TOAST_TYPE_CLASSIC) {
        @JvmField
        @DrawableRes
        var backgroundResource: Int = R.drawable.smart_show_classic_toast_bg

        @JvmField
        @ColorInt
        var backgroundColor: Int = Color.parseColor("#cc000000")

        @JvmField
        var messageSize: Float = 14f

        @JvmField
        @ColorInt
        var messageColor: Int = Color.WHITE

        @JvmField
        var messageBold: Boolean = false

        @JvmField
        @DrawableRes
        var icon: Int? = null

        @JvmField
        @IconPosition
        var iconPosition = TOAST_ICON_POSITION_LEFT

        @JvmField
        var iconSizeDp: Float? = null

        @JvmField
        var iconPaddingDp: Float = 10f
    }

    @ToastView
    fun provideToastView(cachedView: View?, config: Config): View =
        SmartShowClassicToastBinding.bind(
            cachedView ?: layoutInflater.inflate(R.layout.smart_show_classic_toast, null)
        ).apply {
            root.setBackgroundResource(config.backgroundResource)
            DrawableCompat.setTint(root.background.mutate(), config.backgroundColor)
            smartToastMessage.text = config.message
            smartToastMessage.setTextColor(config.messageColor)
            smartToastMessage.textSize = config.messageSize
            smartToastMessage.paint.isFakeBoldText = config.messageBold
            val icon = config.icon?.let {
                ContextCompat.getDrawable(application, it)?.apply {
                    val finalIntrinsicWidth =
                        if (intrinsicWidth == -1) config.messageSize.dpToPx() else intrinsicWidth
                    val finalIntrinsicHeight =
                        if (intrinsicHeight == -1) config.messageSize.dpToPx() else intrinsicHeight
                    val iconSize = config.iconSizeDp?.dpToPx() ?: max(
                        finalIntrinsicWidth,
                        finalIntrinsicHeight
                    )
                    setBounds(0, 0, iconSize, iconSize)
                }
            }
            smartToastMessage.setCompoundDrawables(
                if (config.iconPosition == TOAST_ICON_POSITION_LEFT) icon else null,
                null,
                if (config.iconPosition == TOAST_ICON_POSITION_RIGHT) icon else null,
                null
            )
            smartToastMessage.compoundDrawablePadding = config.iconPaddingDp.dpToPx()
        }.root
}