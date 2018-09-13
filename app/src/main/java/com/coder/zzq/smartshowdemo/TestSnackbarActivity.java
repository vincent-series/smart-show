package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.topbar.SmartTopBar;


public class TestSnackbarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_snackbar);
        SmartTopBar.get().show("爱你");

    }


    public void onShowClick(View view) {
        SmartTopBar.get().showIndefinite("香蕉");
    }

    public void onShowAtTopClick(View view) {
        SmartSnackbar.get().showIndefinite("苹果");
    }


    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestSnackbarActivity.class));
    }
}
