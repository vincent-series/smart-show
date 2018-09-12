package com.coder.zzq.smartshow.snackbar.bottom;

import android.support.design.widget.Snackbar;
import com.coder.zzq.smartshow.snackbar.base.IBarSetting;

public interface ISnackbarSetting extends IBarSetting<Snackbar.SnackbarLayout,ISnackbarSetting> {
    SnackbarSettingImpl backgroundColor(int color);
    SnackbarSettingImpl backgroundColorRes(int colorRes);
}
