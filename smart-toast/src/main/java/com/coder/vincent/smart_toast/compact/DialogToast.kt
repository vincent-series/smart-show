package com.coder.vincent.smart_toast.compact

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.coder.vincent.series.common_lib.lifecycle.ActivityStack
import com.coder.vincent.smart_toast.INTENT_KEY_BOUND_PAGE_ID
import com.coder.vincent.smart_toast.R
import com.coder.vincent.smart_toast.ToastConfig

internal class DialogToast(toastView: View, config: ToastConfig) :
    BaseCompactToast(toastView, config) {
    private val handler = Handler(Looper.getMainLooper())
    lateinit var toast: AppCompatDialog

    override fun show() {
        kotlin.runCatching {
            pickAppropriateActivity()?.let {
                toast = createToast(it)
                toast.show()
                handler.postDelayed(
                    { kotlin.runCatching { toast.dismiss() } },
                    if (config().duration == Toast.LENGTH_SHORT) DURATION_SHORT else DURATION_LONG
                )
            }
        }
    }

    override fun cancel() {
        kotlin.runCatching { toast.dismiss() }
    }

    private fun pickAppropriateActivity(): Activity? =
        config.boundPageId.let {
            if (it.isBlank()) {
                ActivityStack.currentStartedActivity()?.activity
            } else {
                ActivityStack.find { activityItem ->
                    activityItem.activity.intent?.getStringExtra(INTENT_KEY_BOUND_PAGE_ID)
                        ?.isNotBlank() == true
                }?.activity
            }
        }?.apply {
            intent.removeExtra(INTENT_KEY_BOUND_PAGE_ID)
        }


    private fun createToast(activity: Activity) =
        AppCompatDialog(activity, R.style.smart_show_virtual_toast_dialog).apply {
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
                gravity = config.location.gravity
                x = config.location.xOffset
                y = config.location.yOffset
            }
            setContentView(toastView)
        }
}