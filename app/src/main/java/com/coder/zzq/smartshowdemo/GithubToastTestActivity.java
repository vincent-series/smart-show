package com.coder.zzq.smartshowdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.coder.zzq.smartshow.SmartToast;

/**
 * Created by Administrator on 2017/11/16.
 */

public class GithubToastTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_toast);
    }

    public void onDefaultClick(View view) {
        //在默认位置显示
        SmartToast.show("荔枝");
    }

    public void onTopClick(View view) {
        //在屏幕顶部显示，距离顶部位置为Toast在Y方向默认的偏移距离
        SmartToast.showAtTop("苹果");
    }

    public void onCenterClick(View view) {
        //在屏幕中央显示
        SmartToast.showInCenter("香蕉");
    }

    public void onCustomClick(View view) {
        //在指定位置显示，x,y方向偏移量单位为dp
        SmartToast.showAtLocation("芒果", Gravity.LEFT | Gravity.TOP,10,10);
    }


    public void onContentChangedClick(View view) {
        SmartToast.show("桔子");
    }
}
