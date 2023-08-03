package com.coder.vincent.smart_toast.alias.emotion

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.BoolRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.coder.vincent.series.common_lib.resourceToBool
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToDrawable
import com.coder.vincent.series.common_lib.resourceToString
import com.coder.vincent.smart_toast.factory.Location
import com.coder.vincent.smart_toast.schedule.ToastScheduler

public class EmotionToastInvoker : EmotionToastFacade.Overall, EmotionToastFacade.ConfigSetter {
    private val config: EmotionToast.Config = EmotionToast.Config()

    override fun config(): EmotionToastFacade.ConfigSetter = this

    override fun info(msg: CharSequence) {
        showHelper(EMOTION_INFO, msg, Toast.LENGTH_SHORT)
    }

    override fun info(msg: Int) {
        info(msg.resourceToString())
    }

    override fun infoLong(msg: CharSequence) {
        showHelper(EMOTION_INFO, msg, Toast.LENGTH_LONG)
    }

    override fun infoLong(msg: Int) {
        infoLong(msg.resourceToString())
    }

    override fun warning(msg: CharSequence) {
        showHelper(EMOTION_WARNING, msg, Toast.LENGTH_SHORT)
    }

    override fun warning(msg: Int) {
        warning(msg.resourceToString())
    }

    override fun warningLong(msg: CharSequence) {
        showHelper(EMOTION_WARNING, msg, Toast.LENGTH_LONG)
    }

    override fun warningLong(msg: Int) {
        warningLong(msg)
    }

    override fun success(msg: CharSequence) {
        showHelper(EMOTION_SUCCESS, msg, Toast.LENGTH_SHORT)
    }

    override fun success(msg: Int) {
        success(msg.resourceToString())
    }

    override fun successLong(msg: CharSequence) {
        showHelper(EMOTION_SUCCESS, msg, Toast.LENGTH_LONG)
    }

    override fun successLong(msg: Int) {
        successLong(msg.resourceToString())
    }

    override fun error(msg: CharSequence) {
        showHelper(EMOTION_ERROR, msg, Toast.LENGTH_SHORT)
    }

    override fun error(msg: Int) {
        error(msg.resourceToString())
    }

    override fun errorLong(msg: CharSequence) {
        showHelper(EMOTION_ERROR, msg, Toast.LENGTH_LONG)
    }

    override fun errorLong(msg: Int) {
        errorLong(msg.resourceToString())
    }

    override fun fail(msg: CharSequence) {
        showHelper(EMOTION_FAIL, msg, Toast.LENGTH_SHORT)
    }

    override fun fail(msg: Int) {
        fail(msg.resourceToString())
    }

    override fun failLong(msg: CharSequence) {
        showHelper(EMOTION_FAIL, msg, Toast.LENGTH_LONG)
    }

    override fun failLong(msg: Int) {
        failLong(msg.resourceToString())
    }

    override fun complete(msg: CharSequence) {
        showHelper(EMOTION_COMPLETE, msg, Toast.LENGTH_SHORT)
    }

    override fun complete(msg: Int) {
        complete(msg.resourceToString())
    }

    override fun completeLong(msg: CharSequence) {
        showHelper(EMOTION_COMPLETE, msg, Toast.LENGTH_LONG)
    }

    override fun completeLong(msg: Int) {
        completeLong(msg.resourceToString())
    }

    override fun forbid(msg: CharSequence) {
        showHelper(EMOTION_FORBID, msg, Toast.LENGTH_SHORT)
    }

    override fun forbid(msg: Int) {
        forbid(msg.resourceToString())
    }

    override fun forbidLong(msg: CharSequence) {
        showHelper(EMOTION_FORBID, msg, Toast.LENGTH_LONG)
    }

    override fun forbidLong(msg: Int) {
        forbidLong(msg.resourceToString())
    }

    override fun waiting(msg: CharSequence) {
        showHelper(EMOTION_WAITING, msg, Toast.LENGTH_SHORT)
    }

    override fun waiting(msg: Int) {
        waiting(msg.resourceToString())
    }

    override fun waitingLong(msg: CharSequence) {
        showHelper(EMOTION_WAITING, msg, Toast.LENGTH_LONG)
    }

    override fun waitingLong(msg: Int) {
        waitingLong(msg.resourceToString())
    }

    private fun showHelper(@Emotion emotion: Int, msg: CharSequence, duration: Int) {
        config.emotion = emotion
        config.message = msg
        config.duration = duration
        config.location = Location(Gravity.CENTER, 0, 0)
        ToastScheduler.schedule(config, EmotionToastFactory)
    }

    override fun backgroundDrawable(bgDrawable: Drawable): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.backgroundDrawable = bgDrawable
        }

    override fun backgroundResource(@DrawableRes bgResource: Int): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.backgroundDrawable = bgResource.resourceToDrawable()!!
        }

    override fun messageColor(@ColorInt msgColor: Int): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.messageStyle.color = msgColor
        }

    override fun messageColorResource(@ColorRes msgColorResource: Int):
            EmotionToastFacade.ConfigSetter = this.apply {
        config.messageStyle.color = msgColorResource.resourceToColor()
    }

    override fun messageSize(msgSize: Float): EmotionToastFacade.ConfigSetter = this.apply {
        config.messageStyle.size = msgSize
    }

    override fun messageBold(msgBold: Boolean): EmotionToastFacade.ConfigSetter = this.apply {
        config.messageStyle.bold = msgBold
    }

    override fun messageBoldResource(@BoolRes msgBoldResource: Int): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.messageStyle.bold = msgBoldResource.resourceToBool()
        }

    override fun iconDrawable(iconDrawable: Drawable?): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.iconDrawable = iconDrawable
        }

    override fun iconResource(@DrawableRes iconResource: Int): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.iconDrawable = iconResource.resourceToDrawable()
        }

    override fun backgroundColor(backgroundColor: Int): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.backgroundColor = backgroundColor
        }

    override fun backgroundColorResource(@ColorRes backgroundColorResource: Int):
            EmotionToastFacade.ConfigSetter = this.apply {
        config.backgroundColor = backgroundColorResource.resourceToColor()
    }

    override fun iconSizeDp(iconSize: Float): EmotionToastFacade.ConfigSetter = this.apply {
        config.iconSizeDp = iconSize
    }

    override fun iconPaddingDp(iconPadding: Float): EmotionToastFacade.ConfigSetter = this.apply {
        config.iconPaddingDp = iconPadding
    }

    override fun commit(): ShowEmotionToastApi = this
}
