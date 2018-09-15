package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.SmartToast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onToastClick(View view) {
        startActivity(new Intent(this, ToastActivity.class));
    }

    public void onSnackbarClick(View view) {
        startActivity(new Intent(this,TestSnackbarActivity.class));
    }

    public void onTopBarClick(View view) {
        startActivity(new Intent(this, TestTopbarActivity.class));
    }

    public void onTypeToastClick(View view) {
        startActivity(new Intent(this, TypeToastActivity.class));
    }
}
