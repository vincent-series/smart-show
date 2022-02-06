package com.coder.vincent.smart_dialog.notification

import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.coder.vincent.series.annotations.smart_dialog.DialogConfig
import com.coder.vincent.series.annotations.smart_dialog.DialogCreator
import com.coder.vincent.series.annotations.smart_dialog.DialogDefinition
import com.coder.vincent.series.common_lib.bean.DataItem
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.layoutInflater
import com.coder.vincent.series.common_lib.screenWidth
import com.coder.vincent.smart_dialog.ConfirmBtnListener
import com.coder.vincent.smart_dialog.DefaultConfirmBtnListener
import com.coder.zzq.smartshow.dialog.R
import com.coder.zzq.smartshow.dialog.databinding.SmartShowNotificationDialogBinding
import kotlin.math.min

@DialogDefinition(alias = "notification")
class NotificationDialog {
    @DialogConfig
    class Config {
        val title = DataItem<String>()
        val titleStyle = DataItem<TextStyle>()
        val message = DataItem("I'm a message.")
        val messageStyle = DataItem<TextStyle>()
        val confirmBtnLabel = DataItem<String>()
        val confirmBtnLabelStyle = DataItem<TextStyle>()
        val confirmBtnListener = DataItem<ConfirmBtnListener>(DefaultConfirmBtnListener())
        val dimBehind = DataItem(true)
        val cancelable = DataItem(true)
        val cancelOnTouchOutside = DataItem(false)
        val dialogShowListener = DataItem<DialogInterface.OnShowListener>()
        val dialogDismissListener = DataItem<DialogInterface.OnDismissListener>()
        val dialogCancelListener = DataItem<DialogInterface.OnCancelListener>()
    }

    @DialogCreator
    fun createDialog(activity: AppCompatActivity, config: Config): AppCompatDialog =
        AppCompatDialog(activity, R.style.smart_show_dialog).also { dialog ->
            val contentViewBinding =
                SmartShowNotificationDialogBinding.inflate(layoutInflater).apply {
                    config.title.applyOnChange {
                        smartShowDialogTitleView.text = it
                        smartShowDialogTitleView.visibility =
                            if (it.isBlank()) View.INVISIBLE else View.VISIBLE
                    }
                    config.titleStyle.applyOnChange {
                        smartShowDialogTitleView.setTextColor(it.color)
                        smartShowDialogTitleView.textSize = it.size
                        smartShowDialogTitleView.paint.isFakeBoldText = it.bold
                    }

                    config.message.applyOnChange {
                        smartShowDialogMessageView.text = it
                    }

                    config.messageStyle.applyOnChange {
                        smartShowDialogMessageView.setTextColor(it.color)
                        smartShowDialogMessageView.textSize = it.size
                        smartShowDialogMessageView.paint.isFakeBoldText = it.bold
                    }

                    config.confirmBtnLabel.applyOnChange {
                        smartShowDialogConfirmBtn.text = it
                    }

                    config.confirmBtnLabelStyle.applyOnChange {
                        smartShowDialogConfirmBtn.setTextColor(it.color)
                        smartShowDialogConfirmBtn.textSize = it.size
                        smartShowDialogConfirmBtn.paint.isFakeBoldText = it.bold
                    }

                    config.confirmBtnListener.applyOnChange { listener ->
                        smartShowDialogConfirmBtn.setOnClickListener {
                            listener.invoke(dialog)
                        }
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
                }
            val width = min(screenWidth() - 70.dpToPx(), 290.dpToPx())
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            val lp = ViewGroup.MarginLayoutParams(width, height)
            dialog.setContentView(contentViewBinding.root, lp)
        }
}
