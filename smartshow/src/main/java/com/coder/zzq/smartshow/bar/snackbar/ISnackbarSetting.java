package com.coder.zzq.smartshow.bar.snackbar;

import android.support.design.widget.Snackbar;
import com.coder.zzq.smartshow.bar.base.IBarSetting;

public interface ISnackbarSetting extends IBarSetting<Snackbar.SnackbarLayout,ISnackbarSetting> {
    SnackbarSettingImpl backgroundColor(int color);
    SnackbarSettingImpl backgroundColorRes(int colorRes);
}
