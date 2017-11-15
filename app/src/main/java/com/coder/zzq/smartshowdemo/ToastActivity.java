package com.coder.zzq.smartshowdemo;


import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.SmartToast;


public class ToastActivity extends BaseActivity {
    private Toast mToast;

    @Override
    protected int contentLayout() {
        mToast = Toast.makeText(this, "你好", Toast.LENGTH_SHORT);
        return R.layout.activity_smart_show;
    }

    public void onAppleClick(View view) {
        SmartToast.showAtLocation("hello!", Gravity.TOP | Gravity.LEFT, (int)Utils.DimenUtil.dp2px(30),(int)Utils.DimenUtil.dp2px(30));
    }

    public void onBananaClick(View view) {
        SmartToast.showInCenter("hello!");
    }

    public void onNextPageClick(View view) {
        SmartToast.show("你好！");
    }

}
