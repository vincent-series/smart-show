package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        SmartTopbar.get().show("苹果");
    }

    public void onLongClick(View view) {
        SmartTopbar.get().showLong("香蕉");
    }

    public void onIndefinite(View view) {
        SmartTopbar.get().showIndefinite("橘子", "好吃", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartToast.showAtTop("好吃你就多吃点");
            }
        });
    }

    public void onNextPageClick(View view) {
        startActivity(new Intent(this,TestTopbarActivity.class));
    }
}
