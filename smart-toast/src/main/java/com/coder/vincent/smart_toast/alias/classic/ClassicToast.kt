package com.coder.vincent.smart_toast.alias.classic

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType
import com.coder.vincent.series.annotations.smart_toast.CustomizedConfig
import com.coder.vincent.series.annotations.smart_toast.CustomizedToast
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.smart_toast.IconPosition
import com.coder.vincent.smart_toast.R

import com.coder.vincent.smart_toast.TOAST_ICON_POSITION_LEFT
import com.coder.vincent.smart_toast.TOAST_ICON_POSITION_RIGHT
import com.coder.vincent.smart_toast.alias.ToastDefinition
import com.coder.vincent.smart_toast.databinding.SmartShowClassicToastBinding
import com.coder.vincent.smart_toast.factory.ToastConfig
import kotlin.math.max

@CustomizedToast(alias = "abc")
internal class ClassicToast : ToastDefinition<ClassicToast.Config> {

    @CustomizedConfig
    class Config : ToastConfig() {
        @ColorInt
        @DataItem(supportResource = ResourceType.COLOR)
        var backgroundColor: Int = Color.parseColor("#cc000000")

        @IconPosition
        @DataItem
        var iconPosition = TOAST_ICON_POSITION_LEFT

        @DataItem(nullable = true)
        var iconSizeDp: Float? = null

        @DataItem
        var iconPaddingDp: Float = 10f
    }


    @SuppressLint("InflateParams")
    override fun toastView(): View =
        Toolkit.layoutInflater().inflate(R.layout.smart_show_classic_toast, null)

    override fun applyConfig(toastView: View, config: Config) {
        SmartShowClassicToastBinding.bind(toastView).apply {
            val background = config.backgroundDrawable.mutate()
            if (background is GradientDrawable) {
                background.setColor(config.backgroundColor)
            }
            root.background = background
            smartToastMessage.text = config.message
            smartToastMessage.setTextColor(config.messageStyle.color)
            smartToastMessage.textSize = config.messageStyle.size
            smartToastMessage.paint.isFakeBoldText = config.messageStyle.bold
            val icon = config.iconDrawable?.apply {
                val finalIntrinsicWidth =
                    if (intrinsicWidth == -1) config.messageStyle.size.dpToPx() else intrinsicWidth
                val finalIntrinsicHeight =
                    if (intrinsicHeight == -1) config.messageStyle.size.dpToPx() else intrinsicHeight
                val iconSize = config.iconSizeDp?.dpToPx() ?: max(
                    finalIntrinsicWidth,
                    finalIntrinsicHeight
                )
                setBounds(0, 0, iconSize, iconSize)
            }
            smartToastMessage.setCompoundDrawables(
                if (config.iconPosition == TOAST_ICON_POSITION_LEFT) icon else null,
                null,
                if (config.iconPosition == TOAST_ICON_POSITION_RIGHT) icon else null,
                null
            )
            smartToastMessage.compoundDrawablePadding = config.iconPaddingDp.dpToPx()
        }
    }
}