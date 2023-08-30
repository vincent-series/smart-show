package com.coder.vincent.smart_dialog.notification

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import com.coder.vincent.series.annotations.CustomizedConfig
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType
import com.coder.vincent.series.annotations.smart_dialog.CustomizedDialog
import com.coder.vincent.series.common_lib.bean.KData
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.smart_dialog.ConfirmBtnListener
import com.coder.vincent.smart_dialog.DefaultConfirmBtnListener
import com.coder.vincent.smart_dialog.DialogConfig
import com.coder.vincent.smart_dialog.DialogDefinition
import com.coder.vincent.smart_dialog.databinding.SmartShowNotificationDialogBinding

@CustomizedDialog(alias = "notification")
class NotificationDialog : DialogDefinition<NotificationDialog.Config> {
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
    }

    override fun dialogView(
        inflater: LayoutInflater,
        config: Config,
        dialog: DialogInterface
    ): View =
        SmartShowNotificationDialogBinding.inflate(inflater).apply {
            config.title.dataProcessor {
                smartShowDialogTitleView.text = it
                smartShowDialogTitleView.visibility =
                    if (it.isBlank()) View.INVISIBLE else View.VISIBLE
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
        }.root
}
