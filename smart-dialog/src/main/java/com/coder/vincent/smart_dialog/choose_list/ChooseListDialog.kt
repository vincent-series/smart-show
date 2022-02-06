package com.coder.vincent.smart_dialog.choose_list

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.core.util.forEach
import com.coder.vincent.series.annotations.smart_dialog.DialogConfig
import com.coder.vincent.series.annotations.smart_dialog.DialogCreator
import com.coder.vincent.series.annotations.smart_dialog.DialogDefinition
import com.coder.vincent.series.common_lib.bean.DataItem
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.isScreenPortrait
import com.coder.vincent.series.common_lib.screenWidth
import com.coder.vincent.smart_dialog.CancelBtnListener
import com.coder.vincent.smart_dialog.DefaultCancelBtnListener
import com.coder.vincent.smart_dialog.ItemChosenListener
import com.coder.zzq.smartshow.dialog.R
import com.coder.zzq.smartshow.dialog.databinding.SmartShowChooseListDialogBinding
import kotlin.math.min

@DialogDefinition(alias = "chooseList")
class ChooseListDialog {
    @DialogConfig
    class Config {
        val title = DataItem<String>()
        val titleStyle = DataItem<TextStyle>()
        val items = DataItem<List<String>>()
        val iconStyle = DataItem<Int>()
        val itemCenter = DataItem<Boolean>()
        val itemLabelStyle = DataItem<TextStyle>()
        val iconColor = DataItem<Int>()
        val iconPosition = DataItem<Int>()
        val choiceMode = DataItem(CHOICE_MODE_SINGLE)
        val defaultChoosePos = DataItem(listOf(0))
        val confirmBtnLabel = DataItem<String>()
        val confirmBtnLabelStyle = DataItem<TextStyle>()
        val confirmBtnListener = DataItem<ItemChosenListener>()
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
            val contentViewBinding =
                SmartShowChooseListDialogBinding.inflate(com.coder.vincent.series.common_lib.layoutInflater)
                    .apply {
                        config.title.applyOnChange {
                            smartShowDialogTitleView.visibility =
                                if (it.isBlank()) View.GONE else View.VISIBLE
                            smartShowDialogTitleView.text = it
                        }
                        config.titleStyle.applyOnChange {
                            smartShowDialogTitleView.apply {
                                setTextColor(it.color)
                                paint.isFakeBoldText = it.bold
                                textSize = it.size
                            }
                        }
                        val adapter = ChooseListAdapter()
                        config.items.currentValue {
                            adapter.setItems(it, false)
                        }
                        config.items.applyOnChange {
                            adapter.setItems(it)
                        }
                        config.iconStyle.currentValue {
                            adapter.setIconStyle(it, false)
                        }
                        config.iconStyle.applyOnChange {
                            adapter.setIconStyle(it)
                        }

                        config.itemCenter.currentValue {
                            adapter.setItemCenter(it, false)
                        }
                        config.itemCenter.applyOnChange {
                            adapter.setItemCenter(it)
                        }
                        config.itemLabelStyle.currentValue {
                            adapter.setItemLabelStyle(it, false)
                        }

                        config.itemLabelStyle.applyOnChange {
                            adapter.setItemLabelStyle(it)
                        }

                        config.iconColor.currentValue {
                            adapter.setIconColor(it, false)
                        }

                        config.iconColor.applyOnChange {
                            adapter.setIconColor(it)
                        }

                        config.iconPosition.currentValue {
                            adapter.setIconPosition(it, false)
                        }

                        config.iconPosition.applyOnChange {
                            adapter.setIconPosition(it)
                        }


                        config.choiceMode.applyOnChange {
                            smartShowListView.choiceMode =
                                if (it == CHOICE_MODE_SINGLE) ListView.CHOICE_MODE_SINGLE else ListView.CHOICE_MODE_MULTIPLE
                            if (!config.iconStyle.haveData()) {
                                config.iconStyle.update(if (it == CHOICE_MODE_SINGLE) ICON_STYLE_CIRCLE else ICON_STYLE_CUBE)
                            }
                        }
                        smartShowListView.selector = ColorDrawable(Color.TRANSPARENT)
                        smartShowListView.selector = ColorDrawable(Color.TRANSPARENT)
                        smartShowListView.divider = ColorDrawable(Color.parseColor("#cccccc"))
                        smartShowListView.dividerHeight = 0.5f.dpToPx()
                        smartShowListView.layoutParams = smartShowListView.layoutParams.apply {
                            smartShowListView.layoutParams = smartShowListView.layoutParams.apply {
                                val maxVisibleItems = if (isScreenPortrait()) 6 else 2
                                height = (50 * visibleItems(adapter.count, maxVisibleItems)
                                        + additionalPadding(adapter.count, maxVisibleItems))
                                    .dpToPx()
                            }
                        }
                        smartShowListView.adapter = adapter
                        config.defaultChoosePos.applyOnChange {
                            for (pos in 0..adapter.count) {
                                smartShowListView.setItemChecked(pos, it.contains(pos))
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
                                val chosenItems = mutableListOf<ChosenItem>()
                                smartShowListView.checkedItemPositions.forEach { key, value ->
                                    if (value) chosenItems.add(
                                        ChosenItem(
                                            key,
                                            smartShowListView.getItemAtPosition(key).toString()
                                        )
                                    )
                                }
                                listener.invoke(dialog, chosenItems)
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
        }
}

private fun visibleItems(totalItems: Int, maxItems: Int): Int = min(totalItems, maxItems)
private fun additionalPadding(totalItems: Int, maxItems: Int) = if (totalItems > maxItems) 25 else 0

const val CHOICE_MODE_SINGLE = 0
const val CHOICE_MODE_MULTIPLE = 1
const val ICON_STYLE_CIRCLE = 0
const val ICON_STYLE_CUBE = 1
const val ICON_POSITION_LEFT = 0
const val ICON_POSITION_RIGHT = 1

data class ChosenItem(val position: Int, val value: String)
