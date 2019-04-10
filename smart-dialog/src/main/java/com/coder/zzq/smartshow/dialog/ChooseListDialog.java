package com.coder.zzq.smartshow.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatDialog;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.coder.zzq.smartshow.core.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChooseListDialog extends SimpleBranchDialog<ChooseListDialog> {
    public static final int CHOICE_MODE_SINGLE = 0;
    public static final int CHOICE_MODE_MULTIPLE = 1;
    private List mItems = new ArrayList();
    private int mChoiceMode = ListView.CHOICE_MODE_SINGLE;
    private int mCheckMarkPos = Gravity.LEFT;
    private int[] mDefaultChoosePos;
    private boolean mKeepChosenPosByLast = true;
    private boolean mUseCubeMarkWhenMultipleChoose;
    @ColorInt
    private int mCheckMarkColor = Utils.getColorFromRes(R.color.colorPrimary);

    protected ListView mListView;

    protected ChooseListAdapter mChooseListAdapter;

    public ChooseListDialog items(List items) {
        if (!mItems.equals(items)) {
            mItems.clear();
            mItems.addAll(items);
            applyItems(mNestedDialog);
        }
        return this;
    }

    public ChooseListDialog items(Object[] items) {
        items(Arrays.asList(items));
        return this;
    }

    protected void applyItems(AppCompatDialog dialog) {
        if (dialog == null) {
            return;
        }
        ViewGroup.LayoutParams lp = mListView.getLayoutParams();
        if (mItems.size() < 4) {
            lp.height = Utils.dpToPx(50) * (mItems.size() + 2);
        } else if (mItems.size() > 5) {
            lp.height = Utils.dpToPx(50) * 5;
        } else {
            lp.height = ListView.LayoutParams.WRAP_CONTENT;
        }
        mListView.setLayoutParams(lp);
        mChooseListAdapter.setItems(mItems);
    }


    public ChooseListDialog useCubeMarkWhenMultipleChoice(boolean useCubeMarkWhenMultipleChoose) {
        mUseCubeMarkWhenMultipleChoose = useCubeMarkWhenMultipleChoose;
        applyUseCubeMark(mNestedDialog);
        return this;
    }

    protected void applyUseCubeMark(AppCompatDialog dialog) {
        if (dialog != null) {
            mChooseListAdapter.setUseCubeMark(mUseCubeMarkWhenMultipleChoose && mChoiceMode == ListView.CHOICE_MODE_MULTIPLE);
        }
    }


    public ChooseListDialog choiceMode(int choiceMode) {
        switch (choiceMode) {
            case CHOICE_MODE_SINGLE:
                mChoiceMode = ListView.CHOICE_MODE_SINGLE;
                break;
            case CHOICE_MODE_MULTIPLE:
                mChoiceMode = ListView.CHOICE_MODE_MULTIPLE;
                break;
        }
        applyChoiceMode(mNestedDialog);
        return this;
    }

    protected void applyChoiceMode(AppCompatDialog dialog) {
        if (dialog != null && mListView.getChoiceMode() != mChoiceMode) {
            mListView.clearChoices();
            mListView.setChoiceMode(mChoiceMode);
        }
    }

    public ChooseListDialog checkMarkPos(int pos) {
        mCheckMarkPos = pos;
        applyCheckMarkPos(mNestedDialog);
        return this;
    }

    protected void applyCheckMarkPos(AppCompatDialog dialog) {
        if (dialog != null) {
            mChooseListAdapter.setCheckMarkPos(mCheckMarkPos);
        }
    }

    public ChooseListDialog checkMarkColor(int color) {
        mCheckMarkColor = color;
        applyCheckMarkColor(mNestedDialog);
        return this;
    }

    protected void applyCheckMarkColor(AppCompatDialog dialog) {
        if (dialog != null) {
            mChooseListAdapter.setCheckMarkColor(mCheckMarkColor);
        }
    }

    public ChooseListDialog checkMarkColorRes(int colorRes) {
        return checkMarkColor(Utils.getColorFromRes(colorRes));
    }

    public ChooseListDialog defaultChoosePos(int... choosePos) {
        mDefaultChoosePos = choosePos;
        return this;
    }

    public ChooseListDialog keepChosenPosByLast(boolean keep) {
        mKeepChosenPosByLast = keep;
        return this;
    }

    @Override
    protected int provideBodyLayout() {
        return R.layout.smart_show_list_dialog;
    }

    @Override
    protected void initBody(AppCompatDialog dialog, FrameLayout bodyViewWrapper) {
        super.initBody(dialog, bodyViewWrapper);
        bodyViewWrapper.setPadding(bodyViewWrapper.getPaddingLeft(), bodyViewWrapper.getPaddingTop(),
                bodyViewWrapper.getPaddingRight(), 0);
        mListView = bodyViewWrapper.findViewById(R.id.smart_show_list_view);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mListView.setDivider(new ColorDrawable(Color.parseColor("#cccccc")));
        mListView.setDividerHeight(Utils.dpToPx(0.5f));
        mChooseListAdapter = new ChooseListAdapter();
        mListView.setAdapter(mChooseListAdapter);
    }

    @Override
    protected void applySetting(AppCompatDialog dialog) {
        super.applySetting(dialog);
        setDefaultCheckedItems(mListView);
    }

    @Override
    protected void applyBody(AppCompatDialog dialog) {
        super.applyBody(dialog);
        applyChoiceMode(dialog);
        applyItems(dialog);
        applyCheckMarkPos(dialog);
        applyCheckMarkColor(dialog);
        applyUseCubeMark(dialog);
    }

    @Override
    protected void onConfirmBtnClick() {
        int[] choosePositions = new int[mListView.getCheckedItemCount()];
        Object[] chooseItems = new Object[mListView.getCheckedItemCount()];
        switch (mChoiceMode) {
            case ListView.CHOICE_MODE_SINGLE:
                int singleChoosePos = mListView.getCheckedItemPosition();
                choosePositions[0] = singleChoosePos;
                chooseItems[0] = mListView.getItemAtPosition(singleChoosePos);
                break;

            case ListView.CHOICE_MODE_MULTIPLE:
                SparseBooleanArray sparseBooleanArray = mListView.getCheckedItemPositions();
                for (int index = 0, chooseIndex = 0; index < sparseBooleanArray.size(); index++) {
                    if (sparseBooleanArray.valueAt(index)) {
                        int choosePos = sparseBooleanArray.keyAt(index);
                        choosePositions[chooseIndex] = choosePos;
                        chooseItems[chooseIndex] = mListView.getItemAtPosition(choosePos);
                        chooseIndex++;
                    }
                }
                break;
        }
        ChooseResult chooseResult = new ChooseResult();
        chooseResult.setChooseItems(chooseItems);
        chooseResult.setChoosePositions(choosePositions);
        mOnConfirmClickListener.onBtnClick(this, DialogBtnClickListener.BTN_CONFIRM, chooseResult);
    }


    @Override
    public void resetDialogWhenShowAgain(AppCompatDialog dialog) {
        super.resetDialogWhenShowAgain(dialog);
        if (!mKeepChosenPosByLast) {
            mListView.clearChoices();
            setDefaultCheckedItems(mListView);
        }
    }

    private void setDefaultCheckedItems(ListView listView) {
        if (mDefaultChoosePos != null && mDefaultChoosePos.length > 0) {
            for (int index = 0; index < mDefaultChoosePos.length; index++) {
                if (mDefaultChoosePos[index] < mChooseListAdapter.getCount()) {
                    listView.setItemChecked(mDefaultChoosePos[index], true);
                }
            }
        }
    }
}
