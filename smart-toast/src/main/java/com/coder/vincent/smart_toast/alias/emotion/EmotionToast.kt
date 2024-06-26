package com.coder.vincent.smart_toast.alias.emotion

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.viewbinding.ViewBinding
import com.coder.vincent.series.annotations.CustomizedConfig
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType
import com.coder.vincent.series.annotations.smart_toast.CustomizedToast
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.resourceToDrawable
import com.coder.vincent.smart_toast.DEFAULT_TOAST_BACKGROUND_COLOR
import com.coder.vincent.smart_toast.EMOTION_COMPLETE
import com.coder.vincent.smart_toast.EMOTION_ERROR
import com.coder.vincent.smart_toast.EMOTION_FAIL
import com.coder.vincent.smart_toast.EMOTION_FORBID
import com.coder.vincent.smart_toast.EMOTION_INFO
import com.coder.vincent.smart_toast.EMOTION_SUCCESS
import com.coder.vincent.smart_toast.EMOTION_WAITING
import com.coder.vincent.smart_toast.EMOTION_WARNING
import com.coder.vincent.smart_toast.R
import com.coder.vincent.smart_toast.ShowEmotionToast
import com.coder.vincent.smart_toast.ShowToastApiProvider
import com.coder.vincent.smart_toast.TOAST_ALIAS_EMOTION
import com.coder.vincent.smart_toast.alias.ToastDefinition
import com.coder.vincent.smart_toast.databinding.SmartShowEmotionToastBinding
import com.coder.vincent.smart_toast.factory.ToastConfig

@CustomizedToast(alias = TOAST_ALIAS_EMOTION)
internal class EmotionToast : ToastDefinition<EmotionToast.Config>,
    ShowToastApiProvider<ShowEmotionToast> {
    @CustomizedConfig
    class Config : ToastConfig() {
        var emotion = EMOTION_INFO

        @ColorInt
        @DataItem(supportedResource = ResourceType.COLOR)
        var backgroundColor = DEFAULT_TOAST_BACKGROUND_COLOR
    }

    @SuppressLint("InflateParams")
    override fun toastView(inflater: LayoutInflater): ViewBinding =
        SmartShowEmotionToastBinding.inflate(inflater)

    override fun applyConfig(viewBinding: ViewBinding, config: Config) {
        (viewBinding as SmartShowEmotionToastBinding).apply {
            val background = config.backgroundDrawable.mutate()
            if (background is GradientDrawable) {
                background.setColor(config.backgroundColor)
            }
            root.background = background
            (config.iconDrawable ?: parseDefaultIcon(config.emotion)).let {
                (config.iconSize ?: 30f).dpToPx().let { size ->
                    emotionIcon.layoutParams.height = size
                    emotionIcon.layoutParams.width = size
                    it.setBounds(0, 0, size, size)
                }
                emotionIcon.setImageDrawable(it)
            }

            (emotionIcon.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                config.marginBetweenIconAndMsg.dpToPx()
            emotionMessage.text = config.message
            config.messageStyle.applyToView(emotionMessage)
        }
    }
}

private fun parseDefaultIcon(emotion: Int): Drawable =
    when (emotion) {
        EMOTION_INFO -> R.drawable.ic_smart_toast_info
        EMOTION_WARNING -> R.drawable.ic_smart_toast_warning
        EMOTION_SUCCESS -> R.drawable.ic_smart_toast_success
        EMOTION_ERROR -> R.drawable.ic_smart_toast_error
        EMOTION_FORBID -> R.drawable.ic_smart_toast_forbid
        EMOTION_WAITING -> R.drawable.ic_smart_toast_wait
        EMOTION_COMPLETE -> R.drawable.ic_smart_toast_complete
        EMOTION_FAIL -> R.drawable.ic_smart_toast_fail
        else -> 0
    }.resourceToDrawable()!!