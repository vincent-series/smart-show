package com.coder.vincent.smart_dialog.choose_list

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import androidx.core.util.forEach
import com.coder.vincent.series.annotations.CustomizedConfig
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType
import com.coder.vincent.series.annotations.smart_dialog.CustomizedDialog
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.bean.KData
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.smart_dialog.CancelBtnListener
import com.coder.vincent.smart_dialog.DefaultCancelBtnListener
import com.coder.vincent.smart_dialog.DialogConfig
import com.coder.vincent.smart_dialog.DialogDefinition
import com.coder.vincent.smart_dialog.ItemChosenListener
import com.coder.vincent.smart_dialog.databinding.SmartShowChooseListDialogBinding
import kotlin.math.min

@CustomizedDialog(alias = "chooseList")
class ChooseListDialog : DialogDefinition<ChooseListDialog.Config> {
    @CustomizedConfig
    class Config : DialogConfig() {
        @DataItem
        val title = KData("")

        @DataItem
        val titleStyle = KData<TextStyle>()

        @DataItem
        val items = KData<List<String>>()

        @DataItem
        val itemCenter = KData<Boolean>()

        @DataItem
        val itemLabelStyle = KData<TextStyle>()

        @DataItem(supportedResource = ResourceType.COLOR)
        val iconColor = KData<Int>()

        @DataItem
        val iconPosition = KData<Int>()

        @DataItem
        val singleChoice = KData(true)

        @DataItem
        val defaultChoosePos = KData(listOf(0))

        @DataItem(supportedResource = ResourceType.STRING)
        val confirmBtnLabel = KData<String>()

        @DataItem
        val confirmBtnLabelStyle = KData<TextStyle>()

        @DataItem
        val confirmBtnListener = KData<ItemChosenListener>()

        @DataItem
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
    ): View = SmartShowChooseListDialogBinding.inflate(inflater).apply {
        config.title.dataProcessor {
            smartShowDialogTitleView.visibility =
                if (it.isBlank()) View.GONE else View.VISIBLE
            smartShowDialogTitleView.text = it
        }
        config.titleStyle.dataProcessor {
            it.applyToView(smartShowDialogTitleView)
        }
        val adapter = ChooseListAdapter()
        config.items.dataProcessor {
            adapter.setItems(it, true)
            smartShowListView.layoutParams = smartShowListView.layoutParams.apply {
                smartShowListView.layoutParams = smartShowListView.layoutParams.apply {
                    val maxVisibleItems = if (Toolkit.isScreenPortrait()) 6 else 2
                    height = (50 * visibleItems(adapter.count, maxVisibleItems)
                            + additionalPadding(adapter.count, maxVisibleItems))
                        .dpToPx()
                }
            }
        }
        config.itemCenter.dataProcessor {
            adapter.setItemCenter(it, true)
        }
        config.itemLabelStyle.dataProcessor {
            adapter.setItemLabelStyle(it, true)
        }
        config.iconColor.dataProcessor {
            adapter.setIconColor(it, true)
        }
        config.iconPosition.dataProcessor {
            adapter.setIconPosition(it, true)
        }
        config.singleChoice.dataProcessor {
            smartShowListView.choiceMode =
                if (it) ListView.CHOICE_MODE_SINGLE else ListView.CHOICE_MODE_MULTIPLE
            adapter.setIconStyle(if (it) LIST_ITEM_ICON_STYLE_CIRCLE else LIST_ITEM_ICON_STYLE_CUBE)
            adapter.notifyDataSetChanged()
        }
        smartShowListView.selector = ColorDrawable(Color.TRANSPARENT)
        smartShowListView.selector = ColorDrawable(Color.TRANSPARENT)
        smartShowListView.divider = ColorDrawable(Color.parseColor("#cccccc"))
        smartShowListView.dividerHeight = 0.5f.dpToPx()

        smartShowListView.adapter = adapter
        config.defaultChoosePos.dataProcessor {
            for (pos in 0..adapter.count) {
                smartShowListView.setItemChecked(pos, it.contains(pos))
            }
        }
        config.confirmBtnLabel.dataProcessor {
            smartShowDialogConfirmBtn.text = it
        }

        config.confirmBtnLabelStyle.dataProcessor {
            it.applyToView(smartShowDialogConfirmBtn)
        }

        config.confirmBtnListener.dataProcessor { listener ->
            smartShowDialogConfirmBtn.setOnClickListener {
                Toolkit.logD("confirm clicked")
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

private fun visibleItems(totalItems: Int, maxItems: Int): Int = min(totalItems, maxItems)
private fun additionalPadding(totalItems: Int, maxItems: Int) = if (totalItems > maxItems) 25 else 0

const val LIST_CHOICE_MODE_SINGLE = 0
const val LIST_CHOICE_MODE_MULTIPLE = 1
const val LIST_ITEM_ICON_STYLE_CIRCLE = 0
const val LIST_ITEM_ICON_STYLE_CUBE = 1
const val LIST_ITEM_ICON_POSITION_LEFT = 0
const val LIST_ITEM_ICON_POSITION_RIGHT = 1

data class ChosenItem(val position: Int, val value: String)
