package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onToastClick(View view) {
        startActivity(new Intent(this, TestToastActivity.class));
    }

    public void onSnackbarClick(View view) {
        startActivity(new Intent(this, TestSnackbarActivity.class));
    }

    public void onTopBarClick(View view) {
        startActivity(new Intent(this, TestTopbarActivity.class));
    }

    public void onTypeToastClick(View view) {
        startActivity(new Intent(this, TestTypeToastActivity.class));
    }

    public void onDialogClick(View view) {
        startActivity(new Intent(this, TestDialogActivity.class));
    }
}
