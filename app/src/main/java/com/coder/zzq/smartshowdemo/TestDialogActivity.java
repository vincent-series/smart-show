package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.coder.zzq.smartshow.dialog.SmartDialog;
import com.coder.zzq.smartshow.dialog.type.IDialogShow;
import com.coder.zzq.smartshow.toast.SmartToast;

public class TestDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dialog);

        SmartDialog.message("余额不足，无法购买")
                .buttonMode(IDialogShow.BOTH_ENSURE_AND_CANCEL)
                .ensureBtn("知道了")
                .ensureClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SmartToast.showInCenter("余额不足么？");
                    }
                })
                .cancelBtn("充值")
                .cancelClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SmartToast.showInCenter("充值成功！");
                    }
                })
                .show();


    }


    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestDialogActivity.class));
    }
}
