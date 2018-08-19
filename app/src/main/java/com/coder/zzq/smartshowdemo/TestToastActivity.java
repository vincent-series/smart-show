package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.SmartToast;

public class TestToastActivity extends AppCompatActivity {
    private Toast mToast;

    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toast);
        mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);

    }

    public void onNormalShowFirstClick(View view) {
        mToast.setText("Showing!");
        mToast.show();
    }

    public void onShowClick(View view) {
        //默认位置
        SmartToast.show("苹果");
    }

    public void onAnotherShow(View view) {
        //默认位置
        SmartToast.show("香蕉");
    }


    public void onShowInCenterClick(View view) {
        SmartToast.showInCenter("桔子");
    }

    public void onShowAtTopClick(View view) {
        SmartToast.showAtTop("芒果");
    }


    public void onShowAtSomeLocationClick(View view) {
        //左上角，x,y偏移量均为10dp
//        SmartToast.showAtLocation("荔枝！", Gravity.LEFT | Gravity.TOP,10,10);
        startActivity(new Intent(this,TestToastActivity.class));
    }


}
