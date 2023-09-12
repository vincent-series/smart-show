package com.coder.vincent.smart_dialog.choose_list

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ListView
import androidx.core.util.forEach
import com.coder.vincent.series.annotations.CustomizedConfig
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType
import com.coder.vincent.series.annotations.smart_dialog.CustomizedDialog
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.bean.IconPosition
import com.coder.vincent.series.common_lib.bean.KData
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.smart_dialog.CancelBtnListener
import com.coder.vincent.smart_dialog.DefaultCancelBtnListener
import com.coder.vincent.smart_dialog.DialogConfig
import com.coder.vincent.smart_dialog.DialogDefinition
import com.coder.vincent.smart_dialog.ItemChosenListener
import com.coder.vincent.smart_dialog.databinding.SmartShowChooseListDialogBinding

@CustomizedDialog(alias = "chosenList")
class ChosenListDialog : DialogDefinition<ChosenListDialog.Config> {
    @CustomizedConfig
    class Config : DialogConfig() {
        @DataItem(supportedResource = ResourceType.STRING)
        val title = KData("")

        @DataItem
        val titleStyle = KData<TextStyle>()

        @DataItem(supportedResource = ResourceType.STRING_ARRAY)
        val items = KData<List<String>>()

        @DataItem
        val itemCenter = KData(false)

        @DataItem
        val itemLabelStyle = KData<TextStyle>()

        @DataItem(supportedResource = ResourceType.COLOR)
        val iconColor = KData<Int>()

        @DataItem
        val iconPosition = KData(IconPosition.LEFT)

        @DataItem
        val singleChoice = KData(true)

        @DataItem
        val defaultChosenPos = KData(listOf(0))

        @DataItem(supportedResource = ResourceType.STRING)
        val confirmBtnLabel = KData<String>()

        @DataItem
        val confirmBtnLabelStyle = KData<TextStyle>()

        @DataItem
        val confirmBtnListener = KData<ItemChosenListener>()

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
    ): View = SmartShowChooseListDialogBinding.inflate(inflater).apply {
        config.title.dataProcessor {
            smartShowDialogTitleView.visibility =
                if (it.isBlank()) View.GONE else View.VISIBLE
            smartShowDialogTitleView.text = it
        }
        config.titleStyle.dataProcessor {
            it.applyToView(smartShowDialogTitleView)
        }
        val adapter = ChosenListAdapter()
        config.items.dataProcessor {
            adapter.setItems(it)
        }
        config.itemCenter.dataProcessor {
            adapter.setItemCenter(it)
        }
        config.itemLabelStyle.dataProcessor {
            adapter.setItemLabelStyle(it)
        }
        config.iconColor.dataProcessor {
            adapter.setIconColor(it)
        }
        config.iconPosition.dataProcessor {
            adapter.setIconPosition(it)
        }
        config.singleChoice.dataProcessor {
            smartShowListView.choiceMode =
                if (it) ListView.CHOICE_MODE_SINGLE else ListView.CHOICE_MODE_MULTIPLE
            adapter.setIconStyle(if (it) IconStyle.CIRCLE else IconStyle.CUBE)
        }
        smartShowListView.let {
            it.selector = ColorDrawable(Color.TRANSPARENT)
            it.selector = ColorDrawable(Color.TRANSPARENT)
            it.divider = ColorDrawable(Color.parseColor("#cccccc"))
            it.dividerHeight = 0.5f.dpToPx()
            adapter.attach(it)
        }
        config.defaultChosenPos.dataProcessor {
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

    override fun setupRootViewLayoutParams(lp: FrameLayout.LayoutParams) {
        super.setupRootViewLayoutParams(lp)
        (135).dpToPx().let {
            lp.topMargin = it
            lp.bottomMargin = it
        }
    }
}

data class ChosenItem(val position: Int, val value: String)
