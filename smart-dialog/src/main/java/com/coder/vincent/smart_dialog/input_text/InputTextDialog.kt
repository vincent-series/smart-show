package com.coder.vincent.smart_dialog.input_text

import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.core.widget.addTextChangedListener
import com.coder.vincent.series.annotations.smart_dialog.DialogConfig
import com.coder.vincent.series.annotations.smart_dialog.DialogCreator
import com.coder.vincent.series.annotations.smart_dialog.DialogDefinition
import com.coder.vincent.series.common_lib.bean.DataItem
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.layoutInflater
import com.coder.vincent.series.common_lib.popKeyboardWhenDialogShow
import com.coder.vincent.series.common_lib.screenWidth
import com.coder.vincent.smart_dialog.CancelBtnListener
import com.coder.vincent.smart_dialog.DefaultCancelBtnListener
import com.coder.vincent.smart_dialog.DefaultInputOnShowListener
import com.coder.vincent.smart_dialog.R
import com.coder.vincent.smart_dialog.databinding.SmartShowInputTextDialogBinding
import kotlin.math.min

@DialogDefinition(alias = "inputText")
class InputTextDialog {
    @DialogConfig
    class Config {
        val title = DataItem<String>()
        val titleStyle = DataItem<TextStyle>()
        val defaultFilledText = DataItem<String>()
        val hint = DataItem<String>()
        val mostInputNum = DataItem(30)
        val inputNumMarkColor = DataItem<Int>()
        val confirmBtnLabel = DataItem<String>()
        val confirmBtnLabelStyle = DataItem<TextStyle>()
        val confirmBtnListener = DataItem<InputTextConfirmListener>()
        val cancelBtnLabel = DataItem<String>()
        val cancelBtnLabelStyle = DataItem<TextStyle>()
        val cancelBtnListener =
            DataItem<CancelBtnListener>(DefaultCancelBtnListener())
        val dimBehind = DataItem(true)
        val cancelable = DataItem(true)
        val cancelOnTouchOutside = DataItem(false)
        val dialogShowListener =
            DataItem<DialogInterface.OnShowListener>(DefaultInputOnShowListener())
        val dialogDismissListener = DataItem<DialogInterface.OnDismissListener>()
        val dialogCancelListener = DataItem<DialogInterface.OnCancelListener>()
    }

    @DialogCreator
    fun createDialog(activity: AppCompatActivity, config: Config): AppCompatDialog =
        AppCompatDialog(activity, R.style.smart_show_dialog).also { dialog ->
            val contentViewBinding = SmartShowInputTextDialogBinding.inflate(layoutInflater).apply {
                config.title.applyOnChange {
                    smartShowDialogTitleView.visibility =
                        if (it.isBlank()) View.GONE else View.VISIBLE
                    smartShowDialogTitleView.text = it
                }
                config.titleStyle.applyOnChange {
                    smartShowDialogTitleView.setTextColor(it.color)
                    smartShowDialogTitleView.textSize = it.size
                    smartShowDialogTitleView.paint.isFakeBoldText = it.bold
                }

                config.hint.applyOnChange {
                    smartShowInputEdt.hint = it
                }

                smartShowInputEdt.addTextChangedListener(afterTextChanged = {
                    (it?.length ?: 0).let { length ->
                        config.mostInputNum.currentValue { mostInput ->
                            smartShowInputCountMark.text =
                                if (mostInput == INPUT_NUM_NO_LIMIT) "$length" else "$length/$mostInput"
                            if (mostInput != INPUT_NUM_NO_LIMIT && length > mostInput) {
                                smartShowInputEdt.setText(it?.substring(0, mostInput))
                            }
                        }
                        smartShowInputEdt.setSelection(smartShowInputEdt.length())
                    }
                })

                config.defaultFilledText.applyOnChange {
                    smartShowInputEdt.setText(it)
                }

                smartShowInputEdt.requestFocus()

                config.mostInputNum.applyOnChange { mostInput ->
                    smartShowInputCountMark.text = smartShowInputEdt.text.length.let {
                        if (mostInput == INPUT_NUM_NO_LIMIT) "$it" else "$it/$mostInput"
                    }
                }


                config.inputNumMarkColor.applyOnChange {
                    smartShowInputCountMark.setTextColor(it)
                }

                config.confirmBtnLabel.applyOnChange {
                    smartShowDialogConfirmBtn.text = it
                }

                config.confirmBtnLabelStyle.applyOnChange {
                    smartShowDialogConfirmBtn.apply {
                        setTextColor(it.color)
                        textSize = it.size
                        paint.isFakeBoldText = it.bold
                    }
                }

                config.confirmBtnListener.applyOnChange { listener ->
                    smartShowDialogConfirmBtn.setOnClickListener {
                        listener.invoke(dialog, smartShowInputEdt.text.toString().trim())
                    }
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

                smartShowClearInput.setOnClickListener {
                    smartShowInputEdt.setText("")
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
            dialog.popKeyboardWhenDialogShow()
        }
}

const val INPUT_NUM_NO_LIMIT = -1

typealias InputTextConfirmListener = (dialog: AppCompatDialog, content: String) -> Unit