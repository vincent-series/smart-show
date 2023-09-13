package com.coder.vincent.smart_dialog.input_num

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
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
import com.coder.vincent.smart_dialog.InputNumberConfirmListener
import com.coder.vincent.smart_dialog.databinding.SmartShowInputNumBinding

@CustomizedDialog(alias = "inputNumber")
class InputNumberDialog : DialogDefinition<InputNumberDialog.Config> {
    @CustomizedConfig
    class Config : DialogConfig() {
        @DataItem(supportedResource = ResourceType.STRING)
        val title = KData<String>()

        @DataItem
        val titleStyle = KData<TextStyle>()

        @DataItem
        val defaultFilledNumber = KData<String>()

        @DataItem(supportedResource = ResourceType.STRING)
        val hint = KData<String>()

        @DataItem
        val numberType = KData(NumberType.INTEGER)

        @DataItem
        val numberSigned = KData(false)

        @DataItem(supportedResource = ResourceType.STRING)
        val numberUnit = KData<String>()

        @DataItem(supportedResource = ResourceType.STRING)
        val confirmBtnLabel = KData<String>()

        @DataItem
        val confirmBtnLabelStyle = KData<TextStyle>()

        @DataItem
        val confirmBtnListener = KData<InputNumberConfirmListener>()

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
    ): View = SmartShowInputNumBinding.inflate(Toolkit.layoutInflater()).apply {
        config.title.dataProcessor {
            smartShowDialogTitleView.text = it
            smartShowDialogTitleView.visibility =
                if (it.isBlank()) View.INVISIBLE else View.VISIBLE
        }
        config.titleStyle.dataProcessor {
            it.applyToView(smartShowDialogTitleView)
        }
        config.hint.dataProcessor {
            smartShowInputEdt.hint = it
        }
        config.defaultFilledNumber.dataProcessor {
            smartShowInputEdt.setText(it)
            smartShowInputEdt.setSelection(it.length)
        }
        config.numberType.dataProcessor {
            when (it) {
                NumberType.INTEGER -> {
                    smartShowInputEdt.inputType = EditorInfo.TYPE_CLASS_NUMBER
                    val fill =
                        if (smartShowInputEdt.text.isBlank() && !config.defaultFilledNumber.existsData()) "0" else config.defaultFilledNumber.data()
                    config.defaultFilledNumber.update(fill, true)
                }

                NumberType.DECIMAL -> {
                    smartShowInputEdt.inputType =
                        EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL
                    val fill =
                        if (smartShowInputEdt.text.isBlank() && !config.defaultFilledNumber.existsData()) "0.00" else config.defaultFilledNumber.data()
                    config.defaultFilledNumber.update(fill, true)
                }
            }
        }
        config.numberSigned.dataProcessor {
            smartShowInputEdt.inputType =
                smartShowInputEdt.inputType or EditorInfo.TYPE_NUMBER_FLAG_SIGNED
        }



        config.numberUnit.dataProcessor {
            smartShowNumUnit.text = it
            smartShowNumUnitLeftParentheses.visibility =
                if (it.isBlank()) View.GONE else View.VISIBLE
            smartShowNumUnitRightParentheses.visibility =
                if (it.isBlank()) View.GONE else View.VISIBLE
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

        smartShowInputEdt.requestFocus()
    }.root

    override fun setupWindowAttributes(window: Window) {
        super.setupWindowAttributes(window)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }
}