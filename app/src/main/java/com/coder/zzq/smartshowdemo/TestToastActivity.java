package com.coder.zzq.smartshowdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

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
        mToast.cancel();
        mToast.setText("苹果!");
        mToast.show();
    }

    public void onAnotherShow(View view) {
        mToast.cancel();
        mToast.setText("香蕉!");
        mToast.show();
    }


    public void onShowInCenterClick(View view) {
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.setText("桔子!");
        mToast.show();
    }
}
