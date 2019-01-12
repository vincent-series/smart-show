package com.coder.zzq.smartshow.dialog.type.impl;

import android.app.Activity;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.type.IListDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListDialogBuilder extends NormalDialogBuilder<IListDialogBuilder> implements IListDialogBuilder {
    private List<String> mItems = new ArrayList<>();
    @ChooseMode
    private int mChooseMode = SINGLE_CHOOSE;
    @DrawableRes
    private int mChooseMark = R.drawable.smart_show_choose_mark;
    @ColorInt
    private int mChooseMarkThemeColor = Utils.getColorFromRes(R.color.colorAccent);

    private boolean mWithSeparatorBetweenItem;

    @Override
    protected int getContentPartLayoutRes() {
        return R.layout.smart_show_list_content;
    }

    @Override
    public IListDialogBuilder chooseMode(int chooseMode) {
        mChooseMode = chooseMode;
        return this;
    }

    @Override
    public IListDialogBuilder items(String[] items) {
        return items(Arrays.asList(items));
    }

    @Override
    public IListDialogBuilder items(List<String> item) {
        mItems.clear();
        mItems.addAll(item);
        return this;
    }

    @Override
    public IListDialogBuilder chooseMark(@DrawableRes int chooseMark) {
        mChooseMark = chooseMark;
        return this;
    }

    @Override
    public IListDialogBuilder withSeparatorBetweenItem(boolean with) {
        mWithSeparatorBetweenItem = with;
        return this;
    }

    @Override
    protected void initPartView(View contentPartView) {
        ListView listView = (ListView) contentPartView;
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(contentPartView.getContext(), R.layout.smart_show_item_layout, mItems);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    public boolean createAndShow(Activity activity, int tag) {
        return super.createAndShow(activity, tag);
    }
}
