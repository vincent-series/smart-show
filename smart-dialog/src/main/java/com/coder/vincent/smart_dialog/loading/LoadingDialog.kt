package com.coder.vincent.smart_dialog.loading

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.setPadding
import com.coder.vincent.series.annotations.CustomizedConfig
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType
import com.coder.vincent.series.annotations.smart_dialog.CustomizedDialog
import com.coder.vincent.series.common_lib.bean.KData
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.smart_dialog.DialogConfig
import com.coder.vincent.smart_dialog.DialogDefinition
import com.coder.vincent.smart_dialog.R
import com.coder.vincent.smart_dialog.databinding.SmartShowLoadingDialogBinding

@CustomizedDialog(alias = "loading")
class LoadingDialog : DialogDefinition<LoadingDialog.Config> {
    @CustomizedConfig
    class Config : DialogConfig() {
        init {
            dimBehind.update(value = false, employ = false)
        }

        @DataItem(supportedResource = ResourceType.STRING)
        val message = KData("正在加载")

        @DataItem
        val messageStyle = KData<TextStyle>()

        @DataItem
        val boxSize = KData(BoxSize.LARGE)
    }

    override fun dialogStyle(): Int = R.style.smart_show_no_dim_dialog
    override fun setupRootViewLayoutParams(lp: FrameLayout.LayoutParams) {
        lp.width = FrameLayout.LayoutParams.WRAP_CONTENT
        lp.height = FrameLayout.LayoutParams.WRAP_CONTENT
        56.dpToPx().let {
            lp.topMargin = it
            lp.bottomMargin = it
        }
    }

    override fun dialogView(
        inflater: LayoutInflater,
        config: Config,
        dialog: DialogInterface
    ): View = SmartShowLoadingDialogBinding.inflate(inflater).apply {
        config.boxSize.dataProcessor {
            var loadPartPadding = 0
            var progressBarSize = 0
            var showMsg = true
            var msgViewSidePadding = 0
            var msgViewTextSize = 0
            when (it) {
                BoxSize.LARGE -> {
                    loadPartPadding = 15.dpToPx()
                    progressBarSize = 32.dpToPx()
                    showMsg = true
                    msgViewSidePadding = 8.dpToPx()
                    msgViewTextSize = 15
                }

                BoxSize.MIDDLE -> {
                    loadPartPadding = 15.dpToPx()
                    progressBarSize = 24.dpToPx()
                    showMsg = true
                    msgViewSidePadding = 4.dpToPx()
                    msgViewTextSize = 12
                }

                BoxSize.SMALL -> {
                    loadPartPadding = 8.dpToPx()
                    progressBarSize = 20.dpToPx()
                    showMsg = false
                }
            }
            smartShowLoadingPart.setPadding(loadPartPadding)
            smartShowLoadingProgressBar.layoutParams.apply {
                width = progressBarSize
                height = progressBarSize
            }
            if (!showMsg) {
                smartShowLoadingMessageView.visibility = View.GONE
            } else {
                smartShowLoadingMessageView.visibility = View.VISIBLE
                smartShowLoadingMessageView.setPadding(
                    msgViewSidePadding,
                    10.dpToPx(),
                    msgViewSidePadding,
                    0
                )
                smartShowLoadingMessageView.textSize = msgViewTextSize.toFloat()
            }
        }
        config.message.dataProcessor {
            if (it.isBlank()) {
                smartShowLoadingMessageView.visibility = View.VISIBLE
            } else {
                smartShowLoadingMessageView.visibility = View.GONE
            }
            smartShowLoadingMessageView.text = it
        }
        config.messageStyle.dataProcessor {
            it.applyToView(smartShowLoadingMessageView)
        }
    }.root
}
