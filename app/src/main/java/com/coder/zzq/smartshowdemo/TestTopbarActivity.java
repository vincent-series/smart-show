package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.coder.zzq.smartshow.topbar.SmartTopbar;

public class TestTopbarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_topbar);
    }

    public void onShortClick(View view) {
        SmartTopbar.get(this).show("苹果");
    }

    public void onLongClick(View view) {
        SmartTopbar.get(this).showLong("香蕉");
    }

    public void onIndefinite(View view) {
        SmartTopbar.get(this).showIndefinite("为该库Start一下好么", "好的", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartToast.showInCenter("Thank you");
            }
        });

    }

    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestTopbarActivity.class));
    }
}
