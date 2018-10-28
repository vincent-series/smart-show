package com.coder.zzq.smartshowdemo.show;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.smartshow.toast.ToastDelegate;
import com.coder.zzq.smartshowdemo.R;
import com.coder.zzq.smartshowdemo.TestToastActivity;
import com.coder.zzq.smartshowdemo.TestTypeToastActivity;

public class ToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }

    public void onTypeToastClick(View view) {
        SmartToast.setting().typeInfoToastThemeColor(-1);
        startActivity(new Intent(this, TestTypeToastActivity.class));
    }

    public void onColorTypeToastClick(View view) {
        SmartToast.setting().typeInfoToastThemeColorRes(R.color.colorPrimary);
        startActivity(new Intent(this, TestTypeToastActivity.class));
    }


    public void onToastClick(View view) {
        SmartToast.setting().backgroundColor(-1);
        startActivity(new Intent(this, TestToastActivity.class));
    }


    public void onColorToastClick(View view) {
        SmartToast.setting().backgroundColorRes(R.color.colorPrimary);
        startActivity(new Intent(this, TestToastActivity.class));
    }
}
