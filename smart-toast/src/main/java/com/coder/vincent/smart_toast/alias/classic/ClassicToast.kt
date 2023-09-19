package com.coder.vincent.smart_toast.alias.classic

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorInt
import com.coder.vincent.series.annotations.CustomizedConfig
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType
import com.coder.vincent.series.annotations.smart_toast.CustomizedToast
import com.coder.vincent.series.common_lib.bean.IconPosition
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.smart_toast.R
import com.coder.vincent.smart_toast.TOAST_ALIAS_CLASSIC
import com.coder.vincent.smart_toast.alias.ToastDefinition
import com.coder.vincent.smart_toast.databinding.SmartShowClassicToastBinding
import com.coder.vincent.smart_toast.factory.ToastConfig

@CustomizedToast(alias = TOAST_ALIAS_CLASSIC)
internal class ClassicToast : ToastDefinition<ClassicToast.Config> {

    @CustomizedConfig
    class Config : ToastConfig() {
        @ColorInt
        @DataItem(supportedResource = ResourceType.COLOR)
        var backgroundColor: Int = Color.parseColor("#cc000000")

        @DataItem
        var iconPosition = IconPosition.LEFT
    }


    @SuppressLint("InflateParams")
    override fun toastView(inflater: LayoutInflater): View =
        inflater.inflate(R.layout.smart_show_classic_toast, null)

    override fun applyConfig(toastView: View, config: Config) {
        SmartShowClassicToastBinding.bind(toastView).apply {
            val background = config.backgroundDrawable.mutate()
            if (background is GradientDrawable) {
                background.setColor(config.backgroundColor)
            }
            root.background = background
            smartToastMessage.text = config.message
            config.messageStyle.applyToView(smartToastMessage)
            val icon = config.iconDrawable?.apply {
                val iconSize = config.iconSize ?: (config.messageStyle.size + 3)
                setBounds(0, 0, iconSize.dpToPx(), iconSize.dpToPx())
            }
            val iconLeft = if (config.iconPosition == IconPosition.LEFT) icon else null
            val iconRight = if (config.iconPosition == IconPosition.RIGHT) icon else null
            smartToastMessage.setCompoundDrawables(iconLeft, null, iconRight, null)
            smartToastMessage.compoundDrawablePadding = config.marginBetweenIconAndMsg.dpToPx()
        }
    }
}