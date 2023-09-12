package com.coder.vincent.smart_dialog.choose_list

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.View.TEXT_ALIGNMENT_TEXT_START
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import android.widget.ListView
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat
import com.coder.vincent.series.common_lib.Toolkit
import com.coder.vincent.series.common_lib.bean.IconPosition
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToDrawable
import com.coder.vincent.smart_dialog.R
import com.coder.vincent.smart_dialog.databinding.ListItemChoiceBinding

class ChosenListAdapter : BaseAdapter() {
    private var items: List<String> = listOf()
    private var iconStyle = IconStyle.CIRCLE
    private var itemCenter: Boolean = false
    private var itemLabelStyle: TextStyle? = null
    private var iconColor: Int = R.color.colorPrimary.resourceToColor()
    private var iconPosition = IconPosition.LEFT
    private var attached = false

    fun attach(listView: ListView) {
        listView.adapter = this
        attached = true
    }

    fun setItems(items: List<String>) {
        val changed = this.items != items
        this.items = items
        if (attached && changed) {
            notifyDataSetChanged()
        }
    }

    fun setIconStyle(style: IconStyle) {
        val changed = this.iconStyle != style
        this.iconStyle = style
        if (attached && changed) {
            notifyDataSetChanged()
        }
    }

    fun setItemCenter(itemCenter: Boolean) {
        val changed = this.itemCenter != itemCenter
        this.itemCenter = itemCenter
        if (attached && changed) {
            notifyDataSetChanged()
        }
    }

    fun setItemLabelStyle(textStyle: TextStyle) {
        val changed = this.itemLabelStyle != textStyle
        this.itemLabelStyle = textStyle
        if (attached && changed) {
            notifyDataSetChanged()
        }
    }

    fun setIconColor(@ColorInt iconColor: Int) {
        val changed = this.iconColor != iconColor
        this.iconColor = iconColor
        if (attached && changed) {
            notifyDataSetChanged()
        }
    }

    fun setIconPosition(position: IconPosition) {
        val changed = this.iconPosition != position
        this.iconPosition = position
        if (attached && changed) {
            notifyDataSetChanged()
        }
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): String {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?: ListItemChoiceBinding.inflate(
            Toolkit.layoutInflater(),
            parent,
            false
        ).root
        return (itemView as CheckedTextView).apply {
            textAlignment = if (itemCenter) TEXT_ALIGNMENT_CENTER else TEXT_ALIGNMENT_TEXT_START
            text = items[position]
            itemLabelStyle?.applyToView(this)
            val stateListDrawable = StateListDrawable()
            val checkedDrawable =
                if (iconStyle == IconStyle.CIRCLE)
                    R.drawable.smart_show_circle_choose.resourceToDrawable()
                else
                    R.drawable.smart_show_cube_choose.resourceToDrawable()
            DrawableCompat.setTint(checkedDrawable!!, iconColor)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_checked), checkedDrawable)
            val uncheckedDrawable =
                if (iconStyle == IconStyle.CIRCLE)
                    R.drawable.smart_show_circle_unchoose.resourceToDrawable()
                else
                    R.drawable.smart_show_cube_unchoose.resourceToDrawable()
            stateListDrawable.addState(intArrayOf(0), uncheckedDrawable)
            stateListDrawable.setBounds(0, 0, 17f.dpToPx(), 17f.dpToPx())
            var leftDrawable: Drawable? = null
            var rightDrawable: Drawable? = null
            if (iconPosition == IconPosition.LEFT) {
                leftDrawable = stateListDrawable
            } else {
                rightDrawable = stateListDrawable
            }
            setCompoundDrawables(leftDrawable, null, rightDrawable, null)
            if (iconPosition == IconPosition.LEFT) {
                setPadding(0, paddingTop, 32.dpToPx(), paddingBottom)
            } else {
                setPadding(32.dpToPx(), paddingTop, 0, paddingBottom)
            }
        }
    }
}