package com.coder.zzq.smartshowdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.SmartToast;

public class TestToastActivity extends AppCompatActivity {
    private Toast mToast;
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
        SmartToast.show("苹果！");
    }

    public void onAnotherShow(View view) {
        SmartToast.show("香蕉！");
    }


    public void onShowInCenterClick(View view) {
        SmartToast.showInCenter("桔子！");
    }
}
