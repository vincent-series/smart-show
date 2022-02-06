package com.coder.vincent.smart_toast.compact

import android.os.Handler
import android.os.Looper
import android.os.Message

internal class SafeHandler(private val mNestedHandler: Handler) :
    Handler(Looper.getMainLooper()) {
    override fun dispatchMessage(msg: Message) {
        kotlin.runCatching {
            super.dispatchMessage(msg)
        }
    }

    override fun handleMessage(msg: Message) {
        mNestedHandler.handleMessage(msg)
    }
}