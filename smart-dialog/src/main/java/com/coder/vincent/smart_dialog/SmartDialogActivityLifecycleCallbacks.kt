package com.coder.vincent.smart_dialog

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.coder.vincent.series.common_lib.lifecycle.EmptyActivityLifecycleCallbacks

class SmartDialogActivityLifecycleCallbacks : EmptyActivityLifecycleCallbacks() {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            ViewModelProvider(activity as FragmentActivity).get(DialogHandleManager::class.java)
                .onOwnerRecreate()
        }
    }
}