package com.coder.vincent.smart_dialog.ensure

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import com.coder.vincent.series.annotations.CustomizedConfig
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType
import com.coder.vincent.series.annotations.smart_dialog.CustomizedDialog
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.bean.KData
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.smart_dialog.CancelBtnListener
import com.coder.vincent.smart_dialog.ConfirmBtnListener
import com.coder.vincent.smart_dialog.DefaultCancelBtnListener
import com.coder.vincent.smart_dialog.DefaultConfirmBtnListener
import com.coder.vincent.smart_dialog.DialogConfig
import com.coder.vincent.smart_dialog.DialogDefinition
import com.coder.vincent.smart_dialog.databinding.SmartShowEnsureDialogBinding

@CustomizedDialog(alias = "acknowledge")
class AcknowledgeDialog : DialogDefinition<AcknowledgeDialog.Config> {
    @CustomizedConfig
    class Config : DialogConfig() {
        @DataItem(supportedResource = ResourceType.STRING)
        val title = KData<String>()

        @DataItem
        val titleStyle = KData<TextStyle>()

        @DataItem(supportedResource = ResourceType.STRING)
        val message = KData("I'm a message.")

        @DataItem
        val messageStyle = KData<TextStyle>()

        @DataItem(supportedResource = ResourceType.STRING)
        val confirmBtnLabel = KData<String>()

        @DataItem
        val confirmBtnLabelStyle = KData<TextStyle>()

        @DataItem
        val confirmBtnListener = KData<ConfirmBtnListener>(DefaultConfirmBtnListener())

        @DataItem
        val delayToConfirm = KData(0)

        @DataItem(supportedResource = ResourceType.STRING)
        val cancelBtnLabel = KData<String>()

        @DataItem
        val cancelBtnLabelStyle = KData<TextStyle>()

        @DataItem
        val cancelBtnListener = KData<CancelBtnListener>(DefaultCancelBtnListener())
    }

    override fun dialogView(
        inflater: LayoutInflater,
        config: Config,
        dialog: DialogInterface
    ): View = SmartShowEnsureDialogBinding.inflate(Toolkit.layoutInflater()).apply {
        config.title.dataProcessor {
            smartShowDialogTitleView.text = it
            smartShowDialogTitleView.visibility =
                if (it.isBlank()) View.GONE else View.VISIBLE
        }
        config.titleStyle.dataProcessor {
            it.applyToView(smartShowDialogTitleView)
        }
        config.message.dataProcessor {
            smartShowDialogMessageView.text = it
        }
        config.messageStyle.dataProcessor {
            it.applyToView(smartShowDialogMessageView)
        }

        config.confirmBtnLabel.dataProcessor {
            smartShowDialogConfirmBtn.text = it
        }

        config.confirmBtnLabelStyle.dataProcessor {
            it.applyToView(smartShowDialogConfirmBtn)
        }

        config.confirmBtnListener.dataProcessor { listener ->
            smartShowDialogConfirmBtn.setOnClickListener {
                listener.invoke(dialog)
            }
        }
        object : ConfirmDelayCallback {
            var totalDelaySeconds = 0
            var countDownSeconds = 0
            var countDownLabel = StringBuilder()
            override fun restore() {
                smartShowDialogConfirmDelayBtn.removeCallbacks(this)
                smartShowDialogConfirmDelayBtn.visibility = View.GONE
                smartShowDialogConfirmBtn.visibility = View.VISIBLE
                countDownSeconds = totalDelaySeconds
                smartShowDialogConfirmBtn.isEnabled = true
            }

            override fun reset(delaySeconds: Int) {
                totalDelaySeconds = delaySeconds
                restore()
            }

            override fun onViewAttachedToWindow(v: View) {
                if (countDownSeconds > 0) {
                    smartShowDialogConfirmBtn.isEnabled = false
                    smartShowDialogConfirmBtn.visibility = View.INVISIBLE
                    smartShowDialogConfirmDelayBtn.visibility = View.VISIBLE
                    smartShowDialogConfirmDelayBtn.post(this)
                }
            }

            override fun onViewDetachedFromWindow(v: View) {
                restore()
            }

            override fun run() {
                if (countDownSeconds > 0) {
                    countDownLabel.clear()
                    countDownLabel.append(smartShowDialogConfirmBtn.text)
                        .append(" (")
                        .append(countDownSeconds)
                        .append("s)")
                    smartShowDialogConfirmDelayBtn.text = countDownLabel
                    countDownSeconds--
                    smartShowDialogConfirmDelayBtn.postDelayed(this, 1000)
                } else {
                    restore()
                }
            }
        }.let {
            smartShowDialogConfirmBtn.tag = it
            smartShowDialogConfirmBtn.addOnAttachStateChangeListener(it)
        }

        config.delayToConfirm.dataProcessor {
            (smartShowDialogConfirmBtn.tag as ConfirmDelayCallback).reset(it)
        }
        config.cancelBtnLabel.dataProcessor {
            smartShowDialogCancelBtn.text = it
        }

        config.cancelBtnLabelStyle.dataProcessor {
            it.applyToView(smartShowDialogCancelBtn)
        }

        config.cancelBtnListener.dataProcessor { listener ->
            smartShowDialogCancelBtn.setOnClickListener {
                listener.invoke(dialog)
            }
        }
    }.root
}

interface ConfirmDelayCallback : View.OnAttachStateChangeListener, Runnable {
    fun restore()
    fun reset(delaySeconds: Int)
}