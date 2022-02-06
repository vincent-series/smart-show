package com.coder.vincent.smart_dialog.ensure

import android.content.DialogInterface
import android.graphics.Color
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
import com.coder.vincent.smart_dialog.CancelBtnListener
import com.coder.vincent.smart_dialog.ConfirmBtnListener
import com.coder.vincent.smart_dialog.DefaultCancelBtnListener
import com.coder.vincent.smart_dialog.DefaultConfirmBtnListener
import com.coder.zzq.smartshow.dialog.R
import com.coder.zzq.smartshow.dialog.databinding.SmartShowEnsureDialogBinding
import kotlin.math.min

@DialogDefinition(alias = "ensure")
class EnsureDialog {
    @DialogConfig
    class Config {
        val title = DataItem<String>()
        val titleStyle = DataItem<TextStyle>()
        val message = DataItem("I'm a message.")
        val messageStyle = DataItem<TextStyle>()
        val confirmBtnLabel = DataItem<String>()
        val confirmBtnLabelStyle = DataItem<TextStyle>()
        val confirmBtnListener = DataItem<ConfirmBtnListener>(DefaultConfirmBtnListener())
        val delayToConfirm = DataItem<Int>()
        val cancelBtnLabel = DataItem<String>()
        val cancelBtnLabelStyle = DataItem<TextStyle>()
        val cancelBtnListener =
            DataItem<CancelBtnListener>(DefaultCancelBtnListener())
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
            val contentViewBinding = SmartShowEnsureDialogBinding.inflate(layoutInflater).apply {
                config.title.applyOnChange {
                    smartShowDialogTitleView.text = it
                    smartShowDialogTitleView.visibility =
                        if (it.isBlank()) View.GONE else View.VISIBLE
                }
                config.titleStyle.applyOnChange {
                    smartShowDialogTitleView.apply {
                        setTextColor(it.color)
                        paint.isFakeBoldText = it.bold
                        textSize = it.size
                    }
                }
                config.message.applyOnChange {
                    smartShowDialogMessageView.text = it
                }
                config.messageStyle.applyOnChange {
                    smartShowDialogMessageView.apply {
                        setTextColor(it.color)
                        paint.isFakeBoldText = it.bold
                        textSize = it.size
                    }
                }

                config.confirmBtnLabel.applyOnChange {
                    smartShowDialogConfirmBtn.text = it
                }

                config.confirmBtnLabelStyle.applyOnChange {
                    smartShowDialogConfirmBtn.apply {
                        setTextColor(it.color)
                        paint.isFakeBoldText = it.bold
                        textSize = it.size
                    }
                }

                config.confirmBtnListener.applyOnChange { listener ->
                    smartShowDialogConfirmBtn.setOnClickListener {
                        listener.invoke(dialog)
                    }
                }
                config.delayToConfirm.applyOnChange {
                    smartShowDialogConfirmBtn.addOnAttachStateChangeListener(object :
                        ConfirmDelayCallback {
                        val srcConfirmBtnLabel = smartShowDialogConfirmBtn.text
                        val srcConfirmBtnColor = smartShowDialogConfirmBtn.currentTextColor
                        var delaySecondsCountDown = it
                        var confirmLabelOnDelay = StringBuilder()
                        override fun reset() {
                            smartShowDialogConfirmBtn.removeCallbacks(this)
                            delaySecondsCountDown = it
                        }

                        override fun onViewAttachedToWindow(v: View?) {
                            reset()
                            if (delaySecondsCountDown > 0) {
                                smartShowDialogConfirmBtn.isEnabled = false
                                smartShowDialogConfirmBtn.setTextColor(disableColor)
                                smartShowDialogConfirmBtn.post(this)
                            }
                        }

                        override fun onViewDetachedFromWindow(v: View?) {
                            smartShowDialogConfirmBtn.text = srcConfirmBtnLabel
                            smartShowDialogConfirmBtn.setTextColor(srcConfirmBtnColor)
                            smartShowDialogConfirmBtn.removeCallbacks(this)
                            smartShowDialogConfirmBtn.isEnabled = true
                            delaySecondsCountDown = it
                        }

                        override fun run() {
                            if (delaySecondsCountDown > 0) {
                                confirmLabelOnDelay.clear()
                                confirmLabelOnDelay.append(srcConfirmBtnLabel)
                                    .append("(")
                                    .append(delaySecondsCountDown)
                                    .append("s)")
                                smartShowDialogConfirmBtn.text = confirmLabelOnDelay
                                delaySecondsCountDown--
                                smartShowDialogConfirmBtn.postDelayed(this, 1000)
                            } else {
                                smartShowDialogConfirmBtn.isEnabled = true
                                smartShowDialogConfirmBtn.text = srcConfirmBtnLabel
                                smartShowDialogConfirmBtn.setTextColor(srcConfirmBtnColor)
                                smartShowDialogConfirmBtn.removeCallbacks(this)
                            }
                        }
                    })
                }
                config.cancelBtnLabel.applyOnChange {
                    smartShowDialogCancelBtn.text = it
                }

                config.cancelBtnLabelStyle.applyOnChange {
                    smartShowDialogCancelBtn.apply {
                        setTextColor(it.color)
                        paint.isFakeBoldText = it.bold
                        textSize = it.size
                    }
                }

                config.cancelBtnListener.applyOnChange { listener ->
                    smartShowDialogCancelBtn.setOnClickListener {
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

val disableColor = Color.parseColor("#bbbbbb")

interface ConfirmDelayCallback : View.OnAttachStateChangeListener, Runnable {
    fun reset()
}