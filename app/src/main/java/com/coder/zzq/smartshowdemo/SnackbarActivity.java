package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.coder.zzq.smartshow.SmartSnackbar;
import com.coder.zzq.smartshow.snackbar.SnackbarCallback;
import com.coder.zzq.smartshow.SmartToast;


public class SnackbarActivity extends BaseActivity implements SnackbarCallback {

    CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.root);
    }

    @Override
    protected int contentLayout() {
        return R.layout.activity_smart_show;

    }


    public void onAppleClick(View view) {
        SmartSnackbar.get(mCoordinatorLayout).show("苹果");
    }

    public void onBananaClick(View view) {
        SmartSnackbar.get().showLong("香蕉");
    }


    public void onOrangeClick(View view) {
//        SmartSnackbar.get(mCoordinatorLayout).showIndefinite("桔子","好吃");
        startActivity(new Intent(this,SnackbarActivity.class));
    }

    public void onNameClick(View view) {
        Snackbar s;

        SmartSnackbar.get().showIndefinite("我是朱志强", "打赏", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartToast.showInCenter("Thank you ！");
            }
        });


    }


    @Override
    public void onSnackbarShown(Snackbar sb) {
        Log.d("Main", "shown" + sb.toString());
    }

    @Override
    public void onSnackbarDismissed(Snackbar sb, int event) {
        Log.d("Main", "dismiss" + sb.toString());
    }

}
