package com.coder.vincent.smart_toast.lifecycle

import android.app.Activity
import com.coder.vincent.series.common_lib.lifecycle.ActivityStack
import com.coder.vincent.series.common_lib.lifecycle.EmptyActivityLifecycleCallbacks
import com.coder.vincent.smart_toast.INTENT_KEY_BOUND_PAGE_ID
import com.coder.vincent.smart_toast.INTENT_KEY_PENDING_BOUND_PAGE_ID
import com.coder.vincent.smart_toast.schedule.ToastScheduler

class SmartToastActivityLifecycleCallbacks : EmptyActivityLifecycleCallbacks() {


    override fun onActivityStarted(activity: Activity) {
        activity.intent?.getStringExtra(INTENT_KEY_BOUND_PAGE_ID)?.let { boundPageId ->
            if (boundPageId.isNotBlank()) {
                ToastScheduler.schedule(boundPageId)
            }
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        activity.intent?.getStringExtra(INTENT_KEY_PENDING_BOUND_PAGE_ID)?.let { boundPageId ->
            ActivityStack.currentStartedActivity()?.let {
                it.activity.intent.putExtra(INTENT_KEY_BOUND_PAGE_ID, boundPageId)
                ToastScheduler.schedule(boundPageId)
            }
        }
    }

}