package com.coder.vincent.smart_toast.compact

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.coder.vincent.smart_toast.INTENT_KEY_BOUND_PAGE_ID
import com.coder.vincent.smart_toast.INTENT_KEY_PENDING_BOUND_PAGE_ID
import java.util.*

sealed class ToastTransitionIntent : Intent {
    lateinit var boundPageId: String

    constructor() : super()
    constructor(action: String) : super(action)
    constructor(action: String, uri: Uri) : super(action, uri)
    constructor(packageContext: Context, cls: Class<*>) : super(packageContext, cls)
    constructor(action: String, uri: Uri, packageContext: Context, cls: Class<*>)
            : super(action, uri, packageContext, cls)

    constructor(intent: Intent) : super(intent)
}

class PageAfterFinishingCurrent(private val currentPage: Intent) : ToastTransitionIntent() {
    init {
        UUID.randomUUID().toString().let {
            currentPage.putExtra(INTENT_KEY_PENDING_BOUND_PAGE_ID, it)
            boundPageId = it
        }
    }
}

class TargetPage : ToastTransitionIntent {
    init {
        UUID.randomUUID().toString().let {
            putExtra(INTENT_KEY_BOUND_PAGE_ID, it)
            boundPageId = it
        }
    }

    constructor() : super()
    constructor(action: String) : super(action)
    constructor(action: String, uri: Uri) : super(action, uri)
    constructor(packageContext: Context, cls: Class<*>) : super(packageContext, cls)
    constructor(action: String, uri: Uri, packageContext: Context, cls: Class<*>)
            : super(action, uri, packageContext, cls)

    constructor(intent: Intent) : super(intent)
}