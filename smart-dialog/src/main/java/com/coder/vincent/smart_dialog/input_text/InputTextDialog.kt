package com.coder.vincent.smart_dialog.input_text

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.coder.vincent.series.annotations.CustomizedConfig
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType
import com.coder.vincent.series.annotations.smart_dialog.CustomizedDialog
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.bean.KData
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.smart_dialog.CancelBtnListener
import com.coder.vincent.smart_dialog.DefaultCancelBtnListener
import com.coder.vincent.smart_dialog.DialogConfig
import com.coder.vincent.smart_dialog.DialogDefinition
import com.coder.vincent.smart_dialog.InputTextConfirmListener
import com.coder.vincent.smart_dialog.databinding.SmartShowInputTextDialogBinding

@CustomizedDialog(alias = "inputText")
class InputTextDialog : DialogDefinition<InputTextDialog.Config> {
    @CustomizedConfig
    class Config : DialogConfig() {
        @DataItem(supportedResource = ResourceType.STRING)
        val title = KData<String>()

        @DataItem
        val titleStyle = KData<TextStyle>()

        @DataItem
        val defaultFilledText = KData<String>()

        @DataItem(supportedResource = ResourceType.STRING)
        val hint = KData<String>()

        @DataItem
        val maxInputLength = KData(30)

        @DataItem(supportedResource = ResourceType.COLOR)
        val inputCounterColor = KData<Int>()

        @DataItem(supportedResource = ResourceType.STRING)
        val confirmBtnLabel = KData<String>()

        @DataItem
        val confirmBtnLabelStyle = KData<TextStyle>()

        @DataItem
        val confirmBtnListener = KData<InputTextConfirmListener>()

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
    ): View = SmartShowInputTextDialogBinding.inflate(Toolkit.layoutInflater()).apply {
        config.title.dataProcessor {
            smartShowDialogTitleView.visibility =
                if (it.isBlank()) View.GONE else View.VISIBLE
            smartShowDialogTitleView.text = it
        }
        config.titleStyle.dataProcessor {
            it.applyToView(smartShowDialogTitleView)
        }

        config.hint.dataProcessor {
            smartShowInputEdt.hint = it
        }

        smartShowInputEdt.addTextChangedListener(afterTextChanged = {
            (it?.length ?: 0).let { length ->
                if (config.maxInputLength.existsData()) {
                    smartShowInputCountMark.text =
                        if (config.maxInputLength.data() == INPUT_NUM_NO_LIMIT) "$length" else "$length/${config.maxInputLength.data()}"
                    if (config.maxInputLength.data() != INPUT_NUM_NO_LIMIT && config.maxInputLength.data() < length) {
                        smartShowInputEdt.setText(it?.substring(0, config.maxInputLength.data()))
                    }
                }
                smartShowInputEdt.setSelection(smartShowInputEdt.length())
            }
        })

        config.defaultFilledText.dataProcessor {
            smartShowInputEdt.setText(it)
        }

        smartShowInputEdt.requestFocus()

        config.maxInputLength.dataProcessor { mostInput ->
            smartShowInputCountMark.text = smartShowInputEdt.text.length.let {
                if (mostInput == INPUT_NUM_NO_LIMIT) "$it" else "$it/$mostInput"
            }
        }


        config.inputCounterColor.dataProcessor {
            smartShowInputCountMark.setTextColor(it)
        }

        config.confirmBtnLabel.dataProcessor {
            smartShowDialogConfirmBtn.text = it
        }

        config.confirmBtnLabelStyle.dataProcessor {
            it.applyToView(smartShowDialogConfirmBtn)
        }

        config.confirmBtnListener.dataProcessor { listener ->
            smartShowDialogConfirmBtn.setOnClickListener {
                listener.invoke(dialog, smartShowInputEdt.text.toString().trim())
            }
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

        smartShowClearInput.setOnClickListener {
            smartShowInputEdt.setText("")
        }
    }.root
}

const val INPUT_NUM_NO_LIMIT = -1