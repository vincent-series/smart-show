package com.coder.vincent.smart_toast

import androidx.annotation.StringRes

interface PlainToastApi {
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
    fun showLong(msg: CharSequence)
    fun showLong(@StringRes msg: Int)
    fun showLongAtTop(msg: CharSequence)
    fun showLongAtTop(@StringRes msg: Int)
    fun showLongInCenter(msg: CharSequence)
    fun showLongInCenter(@StringRes msg: Int)
    fun showLongAtLocation(msg: CharSequence, gravity: Int, xOffsetDp: Float, yOffsetDp: Float)
    fun showLongAtLocation(@StringRes msg: Int, gravity: Int, xOffsetDp: Float, yOffsetDp: Float)
}