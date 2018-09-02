package com.coder.zzq.smartshowdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.SmartToast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private Toast mToast;
    private Field mViewFiled;
    private Object mTn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showSnackBar(View view) {
        SmartToast.showLongInCenter("香蕉");
    }

    public void onAnotherClick(View view) {
        SmartToast.showLong("苹果");
    }

    public void onDuplicateClick(View view) {
        SmartToast.dismiss();
    }
}
