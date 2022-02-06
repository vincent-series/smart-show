package com.coder.vincent.smart_toast.alias.emotion

import android.view.Gravity
import android.widget.Toast
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToString
import com.coder.vincent.smart_toast.Location
import com.coder.vincent.smart_toast.compact.ToastTransitionIntent
import com.coder.vincent.smart_toast.schedule.ToastScheduler

internal class EmotionToastInvoker : EmotionToastFacade.Overall, EmotionToastFacade.ConfigSetter {
    private val config = EmotionToast.Config()
    override fun config(): EmotionToastFacade.ConfigSetter = this

    override fun backgroundColor(color: Int): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.backgroundColor = color
        }

    override fun backgroundColorResource(colorResource: Int): EmotionToastFacade.ConfigSetter =
        backgroundColor(colorResource.resourceToColor())

    override fun iconResource(iconResource: Int): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.iconResource = iconResource
        }

    override fun iconSizeDp(sizeDp: Float): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.iconSize = sizeDp
        }

    override fun iconPaddingDp(padding: Float): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.iconPadding = padding
        }

    override fun messageColor(color: Int): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.messageColor = color
        }

    override fun messageColorResource(colorResource: Int): EmotionToastFacade.ConfigSetter =
        messageColor(colorResource.resourceToColor())

    override fun messageSizeSp(sizeSp: Float): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.messageSizeSp = sizeSp
        }

    override fun messageBold(bold: Boolean): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.messageBold = bold
        }

    override fun targetPage(transitionIntent: ToastTransitionIntent): EmotionToastFacade.ConfigSetter =
        this.apply {
            config.boundPageId = transitionIntent.boundPageId
        }

    override fun apply(): EmotionToastApi = this

    override fun info(msg: CharSequence) {
        showHelper(EMOTION_TYPE_INFO, msg, Toast.LENGTH_SHORT)
    }

    override fun info(msg: Int) {
        info(msg.resourceToString())
    }

    override fun infoLong(msg: CharSequence) {
        showHelper(EMOTION_TYPE_INFO, msg, Toast.LENGTH_LONG)
    }

    override fun infoLong(msg: Int) {
        infoLong(msg.resourceToString())
    }

    override fun warning(msg: CharSequence) {
        showHelper(EMOTION_TYPE_WARNING, msg, Toast.LENGTH_SHORT)
    }

    override fun warning(msg: Int) {
        warning(msg.resourceToString())
    }

    override fun warningLong(msg: CharSequence) {
        showHelper(EMOTION_TYPE_WARNING, msg, Toast.LENGTH_LONG)
    }

    override fun warningLong(msg: Int) {
        warningLong(msg)
    }

    override fun success(msg: CharSequence) {
        showHelper(EMOTION_TYPE_SUCCESS, msg, Toast.LENGTH_SHORT)
    }

    override fun success(msg: Int) {
        success(msg.resourceToString())
    }

    override fun successLong(msg: CharSequence) {
        showHelper(EMOTION_TYPE_SUCCESS, msg, Toast.LENGTH_LONG)
    }

    override fun successLong(msg: Int) {
        successLong(msg.resourceToString())
    }

    override fun error(msg: CharSequence) {
        showHelper(EMOTION_TYPE_ERROR, msg, Toast.LENGTH_SHORT)
    }

    override fun error(msg: Int) {
        error(msg.resourceToString())
    }

    override fun errorLong(msg: CharSequence) {
        showHelper(EMOTION_TYPE_ERROR, msg, Toast.LENGTH_LONG)
    }

    override fun errorLong(msg: Int) {
        errorLong(msg.resourceToString())
    }

    override fun fail(msg: CharSequence) {
        showHelper(EMOTION_TYPE_FAIL, msg, Toast.LENGTH_SHORT)
    }

    override fun fail(msg: Int) {
        fail(msg.resourceToString())
    }

    override fun failLong(msg: CharSequence) {
        showHelper(EMOTION_TYPE_FAIL, msg, Toast.LENGTH_LONG)
    }

    override fun failLong(msg: Int) {
        failLong(msg.resourceToString())
    }

    override fun complete(msg: CharSequence) {
        showHelper(EMOTION_TYPE_COMPLETE, msg, Toast.LENGTH_SHORT)
    }

    override fun complete(msg: Int) {
        complete(msg.resourceToString())
    }

    override fun completeLong(msg: CharSequence) {
        showHelper(EMOTION_TYPE_COMPLETE, msg, Toast.LENGTH_LONG)
    }

    override fun completeLong(msg: Int) {
        completeLong(msg.resourceToString())
    }

    override fun forbid(msg: CharSequence) {
        showHelper(EMOTION_TYPE_FORBID, msg, Toast.LENGTH_SHORT)
    }

    override fun forbid(msg: Int) {
        forbid(msg.resourceToString())
    }

    override fun forbidLong(msg: CharSequence) {
        showHelper(EMOTION_TYPE_FORBID, msg, Toast.LENGTH_LONG)
    }

    override fun forbidLong(msg: Int) {
        forbidLong(msg.resourceToString())
    }

    override fun waiting(msg: CharSequence) {
        showHelper(EMOTION_TYPE_WAITING, msg, Toast.LENGTH_SHORT)
    }

    override fun waiting(msg: Int) {
        waiting(msg.resourceToString())
    }

    override fun waitingLong(msg: CharSequence) {
        showHelper(EMOTION_TYPE_WAITING, msg, Toast.LENGTH_LONG)
    }

    override fun waitingLong(msg: Int) {
        waitingLong(msg.resourceToString())
    }

    private fun showHelper(@EmotionType emotionType: Int, msg: CharSequence, duration: Int) {
        config.emotionType = emotionType
        config.message = msg
        config.duration = duration
        config.location = Location(Gravity.CENTER, 0, 0)
        ToastScheduler.schedule(config, EmotionToastFactory)
    }
}