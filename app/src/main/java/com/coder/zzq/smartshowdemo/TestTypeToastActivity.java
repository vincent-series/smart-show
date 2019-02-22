package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;

public class TestTypeToastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_type_toast);
    }

    public void onInfoClick(View view) {
        SmartToast.info(R.string.net_fine);
    }

    public void onSuccessClick(View view) {
        SmartToast.success(R.string.delete_succ);
    }

    public void onErrorClick(View view) {
        SmartToast.error(R.string.data_parse_error);
    }

    public void onWarningClick(View view) {
        SmartToast.warning(R.string.power_low);
    }

    public void onFailClick(View view) {
        SmartToast.fail(R.string.save_fail);
    }

    public void onCompleteClick(View view) {
        SmartToast.complete(R.string.download_complete);
    }


    public void onForbidClick(View view) {
        SmartToast.forbid(R.string.forbid_op);
    }

    public void onWaitingClick(View view) {
        SmartToast.waiting(R.string.wait_to_download);
    }


    public void onNextPageClick(View view) {
        startActivity(new Intent(this, TestTypeToastActivity.class));
    }


}
