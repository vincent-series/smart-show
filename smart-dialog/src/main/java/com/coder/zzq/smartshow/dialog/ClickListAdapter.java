package com.coder.zzq.smartshow.dialog;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClickListAdapter extends BaseAdapter {
    private List mItems = new ArrayList();
    private boolean mItemCenter = true;

    public void setItems(List items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void setItemCenter(boolean itemCenter) {
        if (mItemCenter != itemCenter) {
            mItemCenter = itemCenter;
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
        TextView itemView = (TextView) convertView;
        if (itemView == null) {
            itemView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_click, parent, false);
        }
        itemView.setGravity(mItemCenter ? Gravity.CENTER : Gravity.LEFT | Gravity.CENTER_VERTICAL);
        Object item = mItems.get(position);
        itemView.setText(item instanceof StringItem ? ((StringItem) item).getString() : item.toString());
        return itemView;
    }
}
