package com.coder.vincent.smart_dialog.input_num

import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
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
import com.coder.vincent.smart_dialog.databinding.SmartShowInputNumBinding
import kotlin.math.min

@DialogDefinition(alias = "inputNumber")
class InputNumberDialog {
    @DialogConfig
    class Config {
        val title = DataItem<String>()
        val titleStyle = DataItem<TextStyle>()
        val defaultFilledNumber = DataItem<String>()
        val hint = DataItem<String>()
        val numberType = DataItem(NUMBER_TYPE_INT)
        val numberSigned = DataItem(false)
        val numberUnit = DataItem<String>()
        val confirmBtnLabel = DataItem<String>()
        val confirmBtnLabelStyle = DataItem<TextStyle>()
        val confirmBtnListener = DataItem<InputNumberConfirmBtnListener>()
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
            val contentViewBinding = SmartShowInputNumBinding.inflate(layoutInflater).apply {
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
                config.hint.applyOnChange {
                    smartShowInputEdt.hint = it
                }
                config.defaultFilledNumber.applyOnChange {
                    smartShowInputEdt.setText(it)
                    smartShowInputEdt.setSelection(it.length)
                }
                config.numberType.applyOnChange {
                    when (it) {
                        NUMBER_TYPE_INT -> {
                            smartShowInputEdt.inputType = EditorInfo.TYPE_CLASS_NUMBER
                            if (!config.defaultFilledNumber.haveData()) {
                                config.defaultFilledNumber.update("0")
                            }
                        }
                        NUMBER_TYPE_DECIMAL -> {
                            smartShowInputEdt.inputType =
                                EditorInfo.TYPE_CLASS_NUMBER or EditorInfo.TYPE_NUMBER_FLAG_DECIMAL
                            if (!config.defaultFilledNumber.haveData()) {
                                config.defaultFilledNumber.update("0.00")
                            }
                        }
                    }
                }
                config.numberSigned.applyOnChange {
                    smartShowInputEdt.inputType =
                        smartShowInputEdt.inputType or EditorInfo.TYPE_NUMBER_FLAG_SIGNED
                }

                smartShowInputEdt.requestFocus()

                config.numberUnit.applyOnChange {
                    smartShowNumUnit.text = it
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
            val width = min(screenWidth() - 70.dpToPx(), 290.dpToPx())
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            val lp = ViewGroup.MarginLayoutParams(width, height)
            dialog.setContentView(contentViewBinding.root, lp)
            dialog.popKeyboardWhenDialogShow()
        }
}

const val NUMBER_TYPE_INT = 0

const val NUMBER_TYPE_DECIMAL = 1

typealias InputNumberConfirmBtnListener = (dialog: AppCompatDialog, number: String) -> Unit