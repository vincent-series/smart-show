package com.coder.zzq.smartshowdemo;


import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.SmartToast;


public class ToastActivity extends BaseActivity {
    private Toast mToast;

    @Override
    protected int contentLayout() {
        return R.layout.activity_smart_show;
    }

    public void onAppleClick(View view) {
        SmartToast.show("苹果");
    }

    public void onBananaClick(View view) {
        SmartToast.showInCenter("香蕉");
    }

    public void onNextPageClick(View view) {
            startActivity(new Intent(this,TestToastActivity.class));
    }


    public void onNameClick(View view) {
        SmartToast.showLongInCenter("hello!");
    }




    public void onOrangeClick(View view) {
    }
}
