package com.coder.vincent.smart_toast.alias.emotion

import android.graphics.drawable.Drawable
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToDrawable
import com.coder.vincent.series.common_lib.resourceToString
import com.coder.vincent.smart_toast.EMOTION_COMPLETE
import com.coder.vincent.smart_toast.EMOTION_ERROR
import com.coder.vincent.smart_toast.EMOTION_FAIL
import com.coder.vincent.smart_toast.EMOTION_FORBID
import com.coder.vincent.smart_toast.EMOTION_INFO
import com.coder.vincent.smart_toast.EMOTION_SUCCESS
import com.coder.vincent.smart_toast.EMOTION_WAITING
import com.coder.vincent.smart_toast.EMOTION_WARNING
import com.coder.vincent.smart_toast.ShowEmotionToast
import com.coder.vincent.smart_toast.factory.Location
import com.coder.vincent.smart_toast.schedule.ToastScheduler

class EmotionToastInvoker : EmotionToastFacade.Overall, EmotionToastFacade.ConfigSetter {
    private val config: EmotionToast.Config = EmotionToast.Config()

    override fun config(): EmotionToastFacade.ConfigSetter = this

    override fun info(msg: CharSequence) {
        showHelper(EMOTION_INFO, msg, Toast.LENGTH_SHORT)
    }

    override fun info(@StringRes msg: Int) {
        info(msg.resourceToString())
    }

    override fun infoLong(msg: CharSequence) {
        showHelper(EMOTION_INFO, msg, Toast.LENGTH_LONG)
    }

    override fun infoLong(@StringRes msg: Int) {
        info(msg.resourceToString())
    }

    override fun warning(msg: CharSequence) {
        showHelper(EMOTION_WARNING, msg, Toast.LENGTH_SHORT)
    }

    override fun warning(@StringRes msg: Int) {
        warning(msg.resourceToString())
    }

    override fun warningLong(msg: CharSequence) {
        showHelper(EMOTION_WARNING, msg, Toast.LENGTH_LONG)
    }

    override fun warningLong(@StringRes msg: Int) {
        warning(msg.resourceToString())
    }

    override fun success(msg: CharSequence) {
        showHelper(EMOTION_SUCCESS, msg, Toast.LENGTH_SHORT)
    }

    override fun success(@StringRes msg: Int) {
        success(msg.resourceToString())
    }

    override fun successLong(msg: CharSequence) {
        showHelper(EMOTION_SUCCESS, msg, Toast.LENGTH_LONG)
    }

    override fun successLong(@StringRes msg: Int) {
        success(msg.resourceToString())
    }

    override fun error(msg: CharSequence) {
        showHelper(EMOTION_ERROR, msg, Toast.LENGTH_SHORT)
    }

    override fun error(@StringRes msg: Int) {
        error(msg.resourceToString())
    }

    override fun errorLong(msg: CharSequence) {
        showHelper(EMOTION_ERROR, msg, Toast.LENGTH_LONG)
    }

    override fun errorLong(@StringRes msg: Int) {
        error(msg.resourceToString())
    }

    override fun fail(msg: CharSequence) {
        showHelper(EMOTION_FAIL, msg, Toast.LENGTH_SHORT)
    }

    override fun fail(@StringRes msg: Int) {
        fail(msg.resourceToString())
    }

    override fun failLong(msg: CharSequence) {
        showHelper(EMOTION_FAIL, msg, Toast.LENGTH_LONG)
    }

    override fun failLong(@StringRes msg: Int) {
        fail(msg.resourceToString())
    }

    override fun complete(msg: CharSequence) {
        showHelper(EMOTION_COMPLETE, msg, Toast.LENGTH_SHORT)
    }

    override fun complete(@StringRes msg: Int) {
        complete(msg.resourceToString())
    }

    override fun completeLong(msg: CharSequence) {
        showHelper(EMOTION_COMPLETE, msg, Toast.LENGTH_LONG)
    }

    override fun completeLong(@StringRes msg: Int) {
        complete(msg.resourceToString())
    }

    override fun forbid(msg: CharSequence) {
        showHelper(EMOTION_FORBID, msg, Toast.LENGTH_SHORT)
    }

    override fun forbid(@StringRes msg: Int) {
        forbid(msg.resourceToString())
    }

    override fun forbidLong(msg: CharSequence) {
        showHelper(EMOTION_FORBID, msg, Toast.LENGTH_LONG)
    }

    override fun forbidLong(@StringRes msg: Int) {
        forbid(msg.resourceToString())
    }

    override fun waiting(msg: CharSequence) {
        showHelper(EMOTION_WAITING, msg, Toast.LENGTH_SHORT)
    }

    override fun waiting(@StringRes msg: Int) {
        waiting(msg.resourceToString())
    }

    override fun waitingLong(msg: CharSequence) {
        showHelper(EMOTION_WAITING, msg, Toast.LENGTH_LONG)
    }

    override fun waitingLong(@StringRes msg: Int) {
        waiting(msg.resourceToString())
    }

    private fun showHelper(
        emotion: Int,
        msg: CharSequence,
        duration: Int,
    ) {
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

    override fun messageStyle(
        @ColorInt color: Int,
        size: Float,
        bold: Boolean,
    ): EmotionToastFacade.ConfigSetter = this.apply {
        config.messageStyle = TextStyle(color, size, bold)
    }

    override fun iconDrawable(iconDrawable: Drawable?): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.iconDrawable = iconDrawable
        }

    override fun iconResource(@DrawableRes iconResource: Int): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.iconDrawable = iconResource.resourceToDrawable()
        }

    override fun iconSize(size: Float): EmotionToastFacade.ConfigSetter = this.apply {
        config.iconSize = size
    }

    override fun marginBetweenIconAndMsg(margin: Float): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.marginBetweenIconAndMsg = margin
        }

    override fun backgroundColor(backgroundColor: Int): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.backgroundColor = backgroundColor
        }

    override fun backgroundColorResource(@ColorRes backgroundColorResource: Int):
            EmotionToastFacade.ConfigSetter = this.apply {
        config.backgroundColor = backgroundColorResource.resourceToColor()
    }

    override fun commit(): ShowEmotionToast = this
}
