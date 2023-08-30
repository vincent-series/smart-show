package com.coder.vincent.smart_dialog.click_list

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import com.coder.vincent.series.annotations.CustomizedConfig
import com.coder.vincent.series.annotations.DataItem
import com.coder.vincent.series.annotations.ResourceType
import com.coder.vincent.series.annotations.smart_dialog.CustomizedDialog
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.bean.KData
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.smart_dialog.DialogConfig
import com.coder.vincent.smart_dialog.DialogDefinition
import com.coder.vincent.smart_dialog.ItemClickedListener
import com.coder.vincent.smart_dialog.databinding.ListItemClickBinding
import com.coder.vincent.smart_dialog.databinding.SmartShowClickListDialogBinding
import kotlin.math.min

@CustomizedDialog(alias = "clickList")
class ClickListDialog : DialogDefinition<ClickListDialog.Config> {
    @CustomizedConfig
    class Config : DialogConfig() {
        @DataItem(supportedResource = ResourceType.STRING)
        val title = KData<String>()

        @DataItem
        val titleStyle = KData<TextStyle>()

        @DataItem
        val items = KData<List<String>>()

        @DataItem
        val itemCenter = KData(true)

        @DataItem
        val itemStyle = KData<TextStyle>()

        @DataItem
        val itemClickedListener = KData<ItemClickedListener>()
    }

    override fun dialogView(
        inflater: LayoutInflater,
        config: Config,
        dialog: DialogInterface
    ): View = SmartShowClickListDialogBinding.inflate(inflater).apply {
        config.title.dataProcessor {
            smartShowDialogTitleView.text = it
            smartShowDialogTitleView.visibility =
                if (it.isBlank()) View.GONE else View.VISIBLE
        }
        config.titleStyle.dataProcessor {
            it.applyToView(smartShowDialogTitleView)
        }
        val adapter = ClickListAdapter()
        config.items.dataProcessor {
            adapter.setItems(it, true)
            smartShowListView.layoutParams = smartShowListView.layoutParams.apply {
                val maxVisibleItems = if (Toolkit.isScreenPortrait()) 6 else 2
                height = (50 * visibleItems(it.size, maxVisibleItems)
                        + additionalPadding(it.size, maxVisibleItems))
                    .dpToPx()
            }
        }
        config.itemCenter.dataProcessor {
            adapter.setItemCenter(it, true)
        }
        config.itemStyle.dataProcessor {
            adapter.setItemStyle(it, true)
        }
        smartShowListView.selector = ColorDrawable(Color.TRANSPARENT)
        smartShowListView.divider = ColorDrawable(Color.parseColor("#cccccc"))
        smartShowListView.dividerHeight = 0.5f.dpToPx()
        smartShowListView.adapter = adapter
        config.itemClickedListener.dataProcessor { listener ->
            smartShowListView.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    listener.invoke(
                        dialog,
                        ClickedItem(position, config.items.data()[position])
                    )
                }
        }
    }.root
}

private fun visibleItems(totalItems: Int, maxItems: Int): Int = min(totalItems, maxItems)
private fun additionalPadding(totalItems: Int, maxItems: Int) = if (totalItems > maxItems) 25 else 0

class ClickListAdapter : BaseAdapter() {
    private var items = emptyList<String>()
    private var itemCenter = true
    private var itemLabelStyle: TextStyle? = null
    fun setItems(items: List<String>, notify: Boolean = true) {
        this.items = items
        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun setItemCenter(itemCenter: Boolean, notify: Boolean = true) {
        this.itemCenter = itemCenter
        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun setItemStyle(style: TextStyle, notify: Boolean = true) {
        itemLabelStyle = style
        if (notify) {
            notifyDataSetChanged()
        }
    }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): String = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: ListItemClickBinding.inflate(
            Toolkit.layoutInflater(),
            parent,
            false
        ).root
        return (itemView as TextView).apply {
            gravity = if (itemCenter) Gravity.CENTER else Gravity.LEFT or Gravity.CENTER_VERTICAL
            text = items[position]
            itemLabelStyle?.applyToView(this)
        }
    }
}

data class ClickedItem(val position: Int, val value: String)