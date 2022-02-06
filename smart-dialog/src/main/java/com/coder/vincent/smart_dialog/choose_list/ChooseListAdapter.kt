package com.coder.vincent.smart_dialog.choose_list

import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import androidx.core.graphics.drawable.DrawableCompat
import com.coder.vincent.series.common_lib.bean.TextStyle
import com.coder.vincent.series.common_lib.dpToPx
import com.coder.vincent.series.common_lib.layoutInflater
import com.coder.vincent.series.common_lib.resourceToColor
import com.coder.vincent.series.common_lib.resourceToDrawable
import com.coder.zzq.smartshow.dialog.R
import com.coder.zzq.smartshow.dialog.databinding.ListItemChoiceBinding

class ChooseListAdapter : BaseAdapter() {
    private var items: List<String> = listOf()
    private var iconStyle: Int = ICON_STYLE_CIRCLE
    private var itemCenter: Boolean = true
    private var itemLabelStyle: TextStyle? = null
    private var iconColor: Int = R.color.colorPrimary.resourceToColor()
    private var iconPosition: Int = ICON_POSITION_LEFT

    fun setItems(items: List<String>, notify: Boolean = true) {
        this.items = items
        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun setIconStyle(style: Int, notify: Boolean = true) {
        this.iconStyle = style
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

    fun setItemLabelStyle(textStyle: TextStyle, notify: Boolean = true) {
        this.itemLabelStyle = textStyle
        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun setIconColor(iconColor: Int, notify: Boolean = true) {
        this.iconColor = iconColor
        if (notify) {
            notifyDataSetChanged()
        }
    }

    fun setIconPosition(position: Int, notify: Boolean = true) {
        this.iconPosition = position
        if (notify) {
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
            layoutInflater,
            parent,
            false
        ).root
        return (itemView as CheckedTextView).apply {
            gravity = if (itemCenter) Gravity.CENTER else Gravity.LEFT or Gravity.CENTER_VERTICAL
            text = items[position]
            itemLabelStyle?.let {
                setTextColor(it.color)
                paint.isFakeBoldText = it.bold
                textSize = it.size
            }
            val stateListDrawable = StateListDrawable()
            val checkedDrawable =
                if (iconStyle == ICON_STYLE_CIRCLE)
                    R.drawable.smart_show_circle_choose.resourceToDrawable()
                else
                    R.drawable.smart_show_cube_choose.resourceToDrawable()
            DrawableCompat.setTint(checkedDrawable!!, iconColor)
            stateListDrawable.addState(intArrayOf(android.R.attr.state_checked), checkedDrawable)
            val uncheckedDrawable =
                if (iconStyle == ICON_STYLE_CIRCLE)
                    R.drawable.smart_show_circle_unchoose.resourceToDrawable()
                else
                    R.drawable.smart_show_cube_unchoose.resourceToDrawable()
            stateListDrawable.addState(intArrayOf(0), uncheckedDrawable)
            stateListDrawable.setBounds(0, 0, 17f.dpToPx(), 17f.dpToPx())
            var leftDrawable: Drawable? = null
            var rightDrawable: Drawable? = null
            if (iconPosition == ICON_POSITION_LEFT) {
                leftDrawable = stateListDrawable
            } else {
                rightDrawable = stateListDrawable
            }
            setCompoundDrawables(leftDrawable, null, rightDrawable, null)
        }
    }
}