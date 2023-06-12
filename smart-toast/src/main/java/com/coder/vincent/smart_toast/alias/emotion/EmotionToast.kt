package com.coder.vincent.smart_toast.alias.emotion

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.core.graphics.drawable.DrawableCompat
import com.coder.vincent.series.annotations.smart_toast.ToastConfig
import com.coder.vincent.series.annotations.smart_toast.ToastDefinition
import com.coder.vincent.series.annotations.smart_toast.ToastView
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.resourceToDrawable
import com.coder.vincent.smart_toast.DEFAULT_TOAST_BACKGROUND_COLOR
import com.coder.vincent.smart_toast.DEFAULT_VALUE
import com.coder.vincent.smart_toast.R
import com.coder.vincent.smart_toast.TOAST_TYPE_EMOTION
import com.coder.vincent.smart_toast.databinding.SmartShowEmotionToastBinding
import com.coder.vincent.smart_toast.factory.DefaultToastConfig

@ToastDefinition(alias = TOAST_TYPE_EMOTION)
internal class EmotionToast {
    @ToastView
    fun provideToastView(cachedView: View?, config: Config): View =
        SmartShowEmotionToastBinding.bind(
            cachedView ?: Toolkit.layoutInflater().inflate(R.layout.smart_show_emotion_toast, null)
        ).apply {
            DrawableCompat.setTint(root.background.mutate(), config.backgroundColor)
            val icon = if (config.iconResource == DEFAULT_VALUE)
                parseDefaultIconResource(config.emotionType)
            else
                config.iconResource
            icon.resourceToDrawable()?.let {
                config.iconSize.dpToPx().let { size ->
                    emotionIcon.layoutParams.height = size
                    emotionIcon.layoutParams.width = size
                    it.setBounds(0, 0, size, size)
                }
                emotionIcon.setImageDrawable(it)
            }
            (emotionIcon.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                config.iconPadding.dpToPx()
            emotionMessage.text = config.message
            emotionMessage.textSize = config.messageSizeSp
            emotionMessage.paint.isFakeBoldText = config.messageBold
            emotionMessage.setTextColor(config.messageColor)

        }.root

    @ToastConfig
    class Config : DefaultToastConfig(TOAST_TYPE_EMOTION) {
        @JvmField
        @EmotionType
        var emotionType = EMOTION_TYPE_INFO

        @JvmField
        @ColorInt
        var backgroundColor = DEFAULT_TOAST_BACKGROUND_COLOR

        @DrawableRes
        var iconResource: Int = DEFAULT_VALUE

        @JvmField
        var iconSize: Float = 30f

        @JvmField
        var iconPadding: Float = 10f

        @JvmField
        var messageColor = Color.WHITE

        @JvmField
        var messageSizeSp = 14f

        @JvmField
        var messageBold = false
    }
}

@Retention(AnnotationRetention.BINARY)
@IntDef(
    EMOTION_TYPE_INFO,
    EMOTION_TYPE_WARNING,
    EMOTION_TYPE_SUCCESS,
    EMOTION_TYPE_ERROR,
    EMOTION_TYPE_FAIL,
    EMOTION_TYPE_COMPLETE,
    EMOTION_TYPE_FORBID,
    EMOTION_TYPE_WAITING
)
internal annotation class EmotionType

internal const val EMOTION_TYPE_INFO = 0
internal const val EMOTION_TYPE_WARNING = 1
internal const val EMOTION_TYPE_SUCCESS = 2
internal const val EMOTION_TYPE_ERROR = 3
internal const val EMOTION_TYPE_FAIL = 4
internal const val EMOTION_TYPE_COMPLETE = 5
internal const val EMOTION_TYPE_FORBID = 6
internal const val EMOTION_TYPE_WAITING = 7

private fun parseDefaultIconResource(@EmotionType emotionType: Int): Int =
    when (emotionType) {
        EMOTION_TYPE_INFO -> R.drawable.ic_smart_toast_emotion_info
        EMOTION_TYPE_WARNING -> R.drawable.ic_smart_toast_emotion_warning
        EMOTION_TYPE_SUCCESS -> R.drawable.ic_smart_toast_emotion_success
        EMOTION_TYPE_ERROR -> R.drawable.ic_smart_toast_emotion_error
        EMOTION_TYPE_FORBID -> R.drawable.ic_smart_toast_emotion_forbid
        EMOTION_TYPE_WAITING -> R.drawable.ic_smart_toast_emotion_wait
        EMOTION_TYPE_COMPLETE -> R.drawable.ic_smart_toast_emotion_complete
        EMOTION_TYPE_FAIL -> R.drawable.ic_smart_toast_emotion_fail
        else -> 0
    }