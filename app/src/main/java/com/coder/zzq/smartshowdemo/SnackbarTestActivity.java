package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.coder.zzq.smartshow.SmartSnackbar;
import com.coder.zzq.smartshow.SmartToast;

public class SnackbarTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snackbar_test_activity);
    }


    public void onShowClick(View view) {
        SmartSnackbar.get().showIndefinite("香蕉");
    }

    public void onShowAtTopClick(View view) {
        SmartSnackbar.get().showAtTop("苹果");
    }



    public void onNextPageClick(View view) {
        startActivity(new Intent(this,SnackbarTestActivity.class));
    }
}
