package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.snackbar.SnackbarCallback;

public class SnackbarActivity extends BaseActivity implements SnackbarCallback {

    @Override
    protected int contentLayout() {
        return R.layout.activity_smart_show;
    }


    public void onAppleClick(View view) {
        SmartSnackbar.get(this).show("苹果");
    }

    public void onBananaClick(View view) {
        SmartSnackbar.get(this).showIndefinite("香蕉","好吃");
    }

    public void onNextPageClick(View view) {
        startActivity(new Intent(this,SnackbarActivity.class));
    }


    @Override
    public void onSnackbarShown(Snackbar sb) {
        Log.d("Main", "shown --> " + sb.toString());
    }

    @Override
    public void onSnackbarDismissed(Snackbar sb, int event) {
        Log.d("Main", "dismiss --> " + sb.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SmartSnackbar.destroy(this);
    }
}
