package com.coder.vincent.smart_toast

import androidx.annotation.StringRes

interface ShowToast {
    // short text toast
    fun show(msg: CharSequence)
    fun show(@StringRes msg: Int)
    fun showAtTop(msg: CharSequence)
    fun showAtTop(@StringRes msg: Int)
    fun showInCenter(msg: CharSequence)
    fun showInCenter(@StringRes msg: Int)
    fun showAtLocation(msg: CharSequence, gravity: Int, xOffsetDp: Float, yOffsetDp: Float)
    fun showAtLocation(@StringRes msg: Int, gravity: Int, xOffsetDp: Float, yOffsetDp: Float)

    // long text toast
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun showLong(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun showLong(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun showLongAtTop(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun showLongAtTop(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun showLongInCenter(msg: CharSequence)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun showLongInCenter(@StringRes msg: Int)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun showLongAtLocation(msg: CharSequence, gravity: Int, xOffsetDp: Float, yOffsetDp: Float)
    @Deprecated("use config().duration(Duration.LONG) instead.")
    fun showLongAtLocation(@StringRes msg: Int, gravity: Int, xOffsetDp: Float, yOffsetDp: Float)
}