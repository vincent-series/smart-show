package com.coder.vincent.smart_dialog.click_list

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.coder.vincent.series.annotations.smart_dialog.DialogConfig
import com.coder.vincent.series.annotations.smart_dialog.DialogCreator
import com.coder.vincent.series.annotations.smart_dialog.DialogDefinition
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.bean.DataItem
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.smart_dialog.ItemClickedListener
import com.coder.vincent.smart_dialog.R
import com.coder.vincent.smart_dialog.databinding.ListItemClickBinding
import com.coder.vincent.smart_dialog.databinding.SmartShowClickListDialogBinding
import kotlin.math.min

@DialogDefinition(alias = "clickList")
class ClickListDialog {
    @DialogConfig
    class Config {
        val title = DataItem<String>()
        val titleStyle = DataItem<TextStyle>()
        val items = DataItem<List<String>>()
        val itemCenter = DataItem(true)
        val itemStyle = DataItem<TextStyle>()
        val itemClickedListener = DataItem<ItemClickedListener>()
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
                SmartShowClickListDialogBinding.inflate(Toolkit.layoutInflater()).apply {
                    config.title.applyOnChange {
                        smartShowDialogTitleView.text = it
                        smartShowDialogTitleView.visibility =
                            if (it.isBlank()) View.GONE else View.VISIBLE
                    }
                    config.titleStyle.applyOnChange {
                        smartShowDialogTitleView.setTextColor(it.color)
                        smartShowDialogTitleView.textSize = it.size
                        smartShowDialogTitleView.paint.isFakeBoldText = it.bold
                    }
                    val adapter = ClickListAdapter()
                    config.items.currentValue {
                        adapter.setItems(it, false)
                    }
                    config.items.applyOnChange {
                        adapter.setItems(it)
                    }
                    config.itemCenter.currentValue {
                        adapter.setItemCenter(it, false)
                    }
                    config.itemCenter.applyOnChange {
                        adapter.setItemCenter(it)
                    }
                    config.itemStyle.currentValue {
                        adapter.setItemStyle(it, false)
                    }
                    config.itemStyle.applyOnChange {
                        adapter.setItemStyle(it)
                    }
                    smartShowListView.selector = ColorDrawable(Color.TRANSPARENT)
                    smartShowListView.divider = ColorDrawable(Color.parseColor("#cccccc"))
                    smartShowListView.dividerHeight = 0.5f.dpToPx()
                    smartShowListView.layoutParams = smartShowListView.layoutParams.apply {
                        val maxVisibleItems = if (Toolkit.isScreenPortrait()) 6 else 2
                        height = (50 * visibleItems(adapter.count, maxVisibleItems)
                                + additionalPadding(adapter.count, maxVisibleItems))
                            .dpToPx()
                    }
                    smartShowListView.adapter = adapter
                    config.itemClickedListener.applyOnChange { listener ->
                        smartShowListView.onItemClickListener =
                            AdapterView.OnItemClickListener { _, _, position, _ ->
                                config.items.currentValue {
                                    listener.invoke(dialog, ClickedItem(position, it[position]))
                                }
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
            val width = min(Toolkit.screenWidth() - 70.dpToPx(), 290.dpToPx())
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            val lp = ViewGroup.MarginLayoutParams(width, height)
            dialog.setContentView(contentViewBinding.root, lp)
        }
}

private fun visibleItems(totalItems: Int, maxItems: Int): Int = min(totalItems, maxItems)
private fun additionalPadding(totalItems: Int, maxItems: Int) = if (totalItems > maxItems) 25 else 0

class ClickListAdapter : BaseAdapter() {
    private var items = listOf<String>()
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
        val itemView =
            convertView ?: ListItemClickBinding.inflate(
                Toolkit.layoutInflater(),
                parent,
                false
            ).root
        return (itemView as TextView).apply {
            gravity = if (itemCenter) Gravity.CENTER else Gravity.LEFT or Gravity.CENTER_VERTICAL
            text = items[position]
            itemLabelStyle?.let {
                setTextColor(it.color)
                paint.isFakeBoldText = it.bold
                textSize = it.size
            }
        }
    }
}

data class ClickedItem(val position: Int, val value: String)