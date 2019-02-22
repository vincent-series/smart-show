package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;


public class TestToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toast);
    }

    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestToastActivity.class));
    }


    public void onShowClick(View view) {
        SmartToast.show(R.string.apple);
    }

    public void onShowAtTopClick(View view) {
        SmartToast.showAtTop(R.string.banana);
    }

    public void onShowInCenterClick(View view) {
        SmartToast.showInCenter(R.string.orange);
    }

    public void onShowAtLocationClick(View view) {
        SmartToast.showAtLocation(R.string.litchi, Gravity.LEFT | Gravity.TOP, 10, 90);
    }

    public void onShowAnotherClick(View view) {
        SmartToast.show(R.string.mango);
    }
}
