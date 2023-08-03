package com.coder.vincent.smart_toast.alias.emotion

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType

import com.coder.vincent.series.annotations.smart_toast.CustomizedConfig
import com.coder.vincent.series.annotations.smart_toast.CustomizedToast
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.resourceToDrawable
import com.coder.vincent.smart_toast.DEFAULT_TOAST_BACKGROUND_COLOR
import com.coder.vincent.smart_toast.R
import com.coder.vincent.smart_toast.alias.ToastDefinition
import com.coder.vincent.smart_toast.databinding.SmartShowEmotionToastBinding
import com.coder.vincent.smart_toast.factory.ToastConfig

@CustomizedToast(alias = "def")
internal class EmotionToast : ToastDefinition<EmotionToast.Config> {
    @CustomizedConfig
    class Config : ToastConfig() {
        @Emotion
        var emotion = EMOTION_INFO

        @ColorInt
        @DataItem(supportResource = ResourceType.COLOR)
        var backgroundColor = DEFAULT_TOAST_BACKGROUND_COLOR

        @DataItem
        var iconSizeDp: Float = 30f

        @DataItem
        var iconPaddingDp: Float = 10f
    }

    @SuppressLint("InflateParams")
    override fun toastView(): View =
        Toolkit.layoutInflater().inflate(R.layout.smart_show_emotion_toast, null)

    override fun applyConfig(toastView: View, config: Config) {
        SmartShowEmotionToastBinding.bind(toastView).apply {
            val background = config.backgroundDrawable.mutate()
            if (background is GradientDrawable) {
                background.setColor(config.backgroundColor)
            }
            root.background = background
            (config.iconDrawable
                ?: parseDefaultIconResource(config.emotion).resourceToDrawable())?.let {
                config.iconSizeDp.dpToPx().let { size ->
                    emotionIcon.layoutParams.height = size
                    emotionIcon.layoutParams.width = size
                    it.setBounds(0, 0, size, size)
                }
                emotionIcon.setImageDrawable(it)
            }
            (emotionIcon.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                config.iconPaddingDp.dpToPx()
            emotionMessage.text = config.message
            emotionMessage.textSize = config.messageStyle.size
            emotionMessage.paint.isFakeBoldText = config.messageStyle.bold
            emotionMessage.setTextColor(config.messageStyle.color)
        }
    }
}

@Retention(AnnotationRetention.BINARY)
@IntDef(
    EMOTION_INFO,
    EMOTION_WARNING,
    EMOTION_SUCCESS,
    EMOTION_ERROR,
    EMOTION_FAIL,
    EMOTION_COMPLETE,
    EMOTION_FORBID,
    EMOTION_WAITING
)
internal annotation class Emotion

internal const val EMOTION_INFO = 0
internal const val EMOTION_WARNING = 1
internal const val EMOTION_SUCCESS = 2
internal const val EMOTION_ERROR = 3
internal const val EMOTION_FAIL = 4
internal const val EMOTION_COMPLETE = 5
internal const val EMOTION_FORBID = 6
internal const val EMOTION_WAITING = 7

private fun parseDefaultIconResource(@Emotion emotion: Int): Int =
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
    }