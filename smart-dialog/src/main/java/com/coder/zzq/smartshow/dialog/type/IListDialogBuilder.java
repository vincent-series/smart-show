package com.coder.zzq.smartshow.dialog.type;

import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public interface IListDialogBuilder extends INormalDialogBuilder<IListDialogBuilder> {
    int SINGLE_CHOOSE = 0;
    int MULTI_CHOOSE = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SINGLE_CHOOSE, MULTI_CHOOSE})
    @interface ChooseMode {
    }

    IListDialogBuilder chooseMode(@ChooseMode int chooseMode);

    IListDialogBuilder items(String[] items);

    IListDialogBuilder items(List<String> item);

    IListDialogBuilder chooseMark(@DrawableRes int chooseMark);

    IListDialogBuilder withSeparatorBetweenItem(boolean with);
}
