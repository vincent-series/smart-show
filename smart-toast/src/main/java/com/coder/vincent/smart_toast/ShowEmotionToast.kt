package com.coder.vincent.smart_toast

import androidx.annotation.StringRes

interface ShowEmotionToast {
    fun info(msg: CharSequence)
    fun info(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun infoLong(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun infoLong(@StringRes msg: Int)

    fun warning(msg: CharSequence)
    fun warning(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun warningLong(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun warningLong(@StringRes msg: Int)

    fun success(msg: CharSequence)
    fun success(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun successLong(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun successLong(@StringRes msg: Int)

    fun error(msg: CharSequence)
    fun error(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun errorLong(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun errorLong(@StringRes msg: Int)

    fun fail(msg: CharSequence)
    fun fail(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun failLong(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun failLong(@StringRes msg: Int)

    fun complete(msg: CharSequence)
    fun complete(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun completeLong(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun completeLong(@StringRes msg: Int)

    fun forbid(msg: CharSequence)
    fun forbid(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun forbidLong(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun forbidLong(@StringRes msg: Int)
    
    fun waiting(msg: CharSequence)
    fun waiting(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun waitingLong(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun waitingLong(@StringRes msg: Int)
}

const val EMOTION_INFO = 0
const val EMOTION_WARNING = 1
const val EMOTION_SUCCESS = 2
const val EMOTION_ERROR = 3
const val EMOTION_FAIL = 4
const val EMOTION_COMPLETE = 5
const val EMOTION_FORBID = 6
const val EMOTION_WAITING = 7