package com.coder.vincent.smart_dialog.loading

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import android.widget.FrameLayout
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.smart_dialog.createDialogInstance

internal class LoadingDialogFactory {
    private val definition: LoadingDialog = LoadingDialog()

    fun produceDialog(activity: Activity, config: LoadingDialog.Config): Dialog =
        activity.createDialogInstance(definition.dialogStyle()).apply {
            definition.setupWindowAttributes(window!!)
            config.dimBehind.dataProcessor {
                if (it) {
                    this.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                } else {
                    this.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                }
            }
            config.cancelable.dataProcessor {
                this.setCancelable(it)
            }
            config.cancelOnTouchOutside.dataProcessor {
                this.setCanceledOnTouchOutside(it)
            }
            config.dialogShowListener.dataProcessor {
                this.setOnShowListener(it)
            }
            config.dialogDismissListener.dataProcessor {
                this.setOnDismissListener(it)
            }
            config.dialogCancelListener.dataProcessor {
                this.setOnCancelListener(it)
            }
            val lp =
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
            definition.setupRootViewLayoutParams(lp)
            this.setContentView(definition.dialogView(Toolkit.layoutInflater(), config, this), lp)
        }
}
