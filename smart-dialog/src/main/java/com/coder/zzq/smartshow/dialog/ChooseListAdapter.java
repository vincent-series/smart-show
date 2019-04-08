package com.coder.zzq.smartshow.dialog;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.coder.zzq.smartshow.core.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChooseListAdapter extends BaseAdapter {
    private List mItems = new ArrayList();
    private int mCheckMarkPos = Gravity.LEFT;
    @ColorInt
    private int mCheckMarkColor = Utils.getColorFromRes(R.color.colorPrimary);

    private boolean mUseCubeMark;

    public void setItems(List items, int checkMarkPos, int checkMarkColor, boolean useCubeMark) {
        boolean needNotify = !mItems.equals(items) || mCheckMarkPos != checkMarkPos || mCheckMarkColor != checkMarkColor
                || useCubeMark != mUseCubeMark;
        if (!mItems.equals(items)) {
            mItems.clear();
            mItems.addAll(items);
        }
        mCheckMarkPos = checkMarkPos;
        mCheckMarkColor = checkMarkColor;
        mUseCubeMark = useCubeMark;
        if (needNotify) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckedTextView itemView = (CheckedTextView) convertView;
        if (itemView == null) {
            itemView = (CheckedTextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single_choice, parent, false);
        }
        StateListDrawable stateListDrawable = new StateListDrawable();
        Drawable checkedDrawable = Utils.getDrawableFromRes(mUseCubeMark ? R.drawable.smart_show_multiple_choose : R.drawable.smart_show_single_choose);
        DrawableCompat.setTint(checkedDrawable, mCheckMarkColor);
        stateListDrawable.addState(
                new int[]{
                        android.R.attr.state_checked
                },
                checkedDrawable
        );
        Drawable uncheckedDrawable = Utils.getDrawableFromRes(mUseCubeMark ? R.drawable.smart_show_multiple_unchoose : R.drawable.smart_show_single_unchoose);
        uncheckedDrawable.setBounds(0, 0, Utils.dpToPx(5), Utils.dpToPx(5));
        stateListDrawable.addState(
                new int[]{
                        -android.R.attr.state_checked
                },
                uncheckedDrawable
        );
        stateListDrawable.setBounds(0, 0, Utils.dpToPx(17), Utils.dpToPx(17));
        Drawable leftDrawable = null;
        Drawable rightDrawable = null;
        if (mCheckMarkPos == Gravity.LEFT) {
            leftDrawable = stateListDrawable;
        } else {
            rightDrawable = stateListDrawable;
        }
        itemView.setCompoundDrawables(leftDrawable, null, rightDrawable, null);
        Object item = mItems.get(position);
        itemView.setText(item instanceof StringItem ? ((StringItem) item).getString() : item.toString());
        return itemView;
    }
}
