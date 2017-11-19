package com.coder.zzq.smartshowdemo;

import android.support.design.widget.Snackbar;
import android.util.Log;

import com.coder.zzq.smartshow.snackbar.SnackbarCallback;


public class SnackbarActivity extends BaseActivity implements SnackbarCallback {


    @Override
    protected int contentLayout() {
        return R.layout.activity_smart_show;
    }

    @Override
    public void onSnackbarShown(Snackbar sb) {
        Log.d("Main", "shown");
    }

    @Override
    public void onSnackbarDismissed(Snackbar sb, int event) {
        Log.d("Main", "dismiss");
    }

}
