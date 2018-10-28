package com.coder.zzq.smartshowdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.snackbar.ISnackbarShowCallback;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.toast.SmartToast;


public class TestSnackbarActivity extends AppCompatActivity  implements ISnackbarShowCallback{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_snackbar);
    }

    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestSnackbarActivity.class));
    }

    public void onIndefiniteClick(View view) {
        SmartSnackbar.get(this).showIndefinite("为该库Start一下好么", "好的",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SmartToast.showInCenter("Thank you");
                    }
                });
    }

    public void onLongClick(View view) {
        SmartSnackbar.get(this).showLong("香蕉");
    }

    public void onShortClick(View view) {
        SmartSnackbar.get(this).show("苹果");
    }

    @Override
    public void onShown(Snackbar sb) {

    }

    @Override
    public void onDismissed(Snackbar sb, int event) {

    }
}
