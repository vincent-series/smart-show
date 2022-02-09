package com.coder.vincent.smart_dialog.loading

import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.viewbinding.ViewBinding
import com.coder.vincent.series.annotations.smart_dialog.DialogConfig
import com.coder.vincent.series.annotations.smart_dialog.DialogCreator
import com.coder.vincent.series.annotations.smart_dialog.DialogDefinition
import com.coder.vincent.series.common_lib.bean.DataItem
import com.coder.vincent.series.common_lib.layoutInflater
import com.coder.vincent.smart_dialog.R
import com.coder.vincent.smart_dialog.databinding.SmartShowLargeLoadingDialogBinding
import com.coder.vincent.smart_dialog.databinding.SmartShowMiddleLoadingDialogBinding
import com.coder.vincent.smart_dialog.databinding.SmartShowSmallLoadingDialogBinding

@DialogDefinition(alias = "loading")
class LoadingDialog {
    @DialogConfig
    class Config {
        val message = DataItem("正在加载")
        val boxSize = DataItem(BOX_SIZE_LARGE)
        val dimBehind = DataItem(false)
        val cancelable = DataItem(true)
        val cancelOnTouchOutside = DataItem(false)
        val dialogShowListener = DataItem<DialogInterface.OnShowListener>()
        val dialogDismissListener = DataItem<DialogInterface.OnDismissListener>()
        val dialogCancelListener = DataItem<DialogInterface.OnCancelListener>()
    }

    @DialogCreator
    fun createDialog(activity: AppCompatActivity, config: Config): AppCompatDialog =
        AppCompatDialog(activity, R.style.smart_show_dialog).also { dialog ->

            config.boxSize.applyOnChange { boxSize ->
                val contentViewBinding: ViewBinding =
                    when (boxSize) {
                        BOX_SIZE_LARGE -> SmartShowLargeLoadingDialogBinding
                            .inflate(layoutInflater).apply {
                                config.message.applyOnChange { message ->
                                    smartShowLoadingMessageView.text = message
                                    smartShowLoadingMessageView.visibility =
                                        if (message.isBlank()) View.GONE else View.VISIBLE
                                }
                            }
                        BOX_SIZE_MIDDLE -> SmartShowMiddleLoadingDialogBinding
                            .inflate(layoutInflater).apply {
                                config.message.applyOnChange { message ->
                                    smartShowLoadingMessageView.text = message
                                    smartShowLoadingMessageView.visibility =
                                        if (message.isBlank()) View.GONE else View.VISIBLE
                                }
                            }
                        BOX_SIZE_SMALL -> SmartShowSmallLoadingDialogBinding.inflate(layoutInflater)
                        else -> throw IllegalArgumentException("invalid box size:$boxSize")
                    }
                config.dimBehind.applyOnChange {
                    if (it) {
                        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                    } else {
                        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                    }
                }

                config.cancelable.applyOnChange {
                    dialog.setCancelable(it)
                }

                config.cancelOnTouchOutside.applyOnChange {
                    dialog.setCanceledOnTouchOutside(it)
                }

                config.dialogShowListener.applyOnChange {
                    dialog.setOnShowListener(it)
                }
                config.dialogDismissListener.applyOnChange {
                    dialog.setOnDismissListener(it)
                }

                config.dialogCancelListener.applyOnChange {
                    dialog.setOnCancelListener(it)
                }
                val lp = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                dialog.setContentView(contentViewBinding.root, lp)
            }
        }

}

const val BOX_SIZE_LARGE = 0
const val BOX_SIZE_MIDDLE = 1
const val BOX_SIZE_SMALL = 2
