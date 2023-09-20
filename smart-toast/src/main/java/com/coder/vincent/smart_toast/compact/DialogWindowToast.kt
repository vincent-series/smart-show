package com.coder.vincent.smart_toast.compact

import android.app.Activity
import android.app.Dialog
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.bean.ActivityItem
import com.coder.vincent.series.common_lib.lifecycle.ActivityStack
import com.coder.vincent.series.common_lib.lifecycle.ActivityState
import com.coder.vincent.smart_toast.R
import com.coder.vincent.smart_toast.factory.ToastConfig
import com.coder.vincent.smart_toast.handler

internal class DialogWindowToast(
    toastView: View,
    config: ToastConfig,
    configApplyCallback: (View, ToastConfig) -> Unit
) : AbsCompactToast(toastView, config, configApplyCallback) {
    lateinit var toast: Dialog
    override fun show() {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(ShowRunnable(), 100)
    }

    override fun cancel() {
        if(!toast.isShowing){
            Toolkit.logD("no need dismiss since dialog is not showing")
            return
        }
        kotlin.runCatching { toast.dismiss() }
    }

    private inner class ShowRunnable : Runnable {
        override fun run() {
            if (ActivityStack.isEmpty()) {
                return
            }
            chooseAppropriateActivity()?.let {
                this@DialogWindowToast.showToast(it)
                return@run
            }
            Toolkit.logD("proper activity not found! delay 250ms")
            handler.postDelayed(this@ShowRunnable, 250)
        }

        private fun chooseAppropriateActivity() = ActivityStack.find {
            when (it.activityState) {
                ActivityState.CREATED, ActivityState.STARTED, ActivityState.RESUMED -> true
                ActivityState.PAUSED -> !it.activity.isFinishing && SystemClock.elapsedRealtime() - it.stateTimestamp > 500L
                ActivityState.STOPPED, ActivityState.DESTROYED -> false
            }
        }
    }

    private fun showToast(activityItem: ActivityItem) {
        Toolkit.logD("show dialog toast:${activityItem.activity}")
        kotlin.runCatching {
            toast = createToast(activityItem.activity)
            toast.show()
            handler.postDelayed(
                { cancel() },
                if (config().duration == Toast.LENGTH_SHORT) TOAST_DURATION_SHORT else TOAST_DURATION_LONG
            )
            activityItem.addStateChangeCallback {
                if (it == ActivityState.DESTROYED) {
                    cancel()
                    handler.removeCallbacksAndMessages(null)
                    Toolkit.logD("auto close dialog when activity is destroyed")
                }
            }
        }
    }

    private fun createToast(activity: Activity): Dialog {
        val dialog =
            if (activity is AppCompatActivity)
                AppCompatDialog(activity, R.style.vincent_series_dialog_toast_style)
            else
                Dialog(activity, R.style.vincent_series_dialog_toast_style)

        return dialog.apply {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
            window?.attributes?.apply {
                windowAnimations = android.R.style.Animation_Toast
                gravity = config().location.gravity
                x = config().location.xOffset
                y = config().location.yOffset
            }
            setContentView(view())
        }
    }

}

