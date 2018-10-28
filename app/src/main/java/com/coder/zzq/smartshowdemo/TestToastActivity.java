package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;

public class TestToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }

    public void onNextPageClick(View view) {
        startActivity(new Intent(this,TestToastActivity.class));
        finish();
    }





    public void onShowClick(View view) {
        SmartToast.show("苹果");
    }

    public void onShowAtTopClick(View view) {
        SmartToast.showAtTop("香蕉");
    }

    public void onShowInCenterClick(View view) {
        SmartToast.showInCenter("橘子");
    }

    public void onShowAtLocationClick(View view) {
        SmartToast.showAtLocation("荔枝", Gravity.LEFT|Gravity.TOP,10,10);
    }
}
