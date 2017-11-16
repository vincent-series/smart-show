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
        //在默认位置显示
        SmartToast.showLong("我是朱志强！");
        //在屏幕顶部显示，距离顶部位置为Toast在Y方向默认的偏移距离
        SmartToast.showLongAtTop("我是朱志强!");
        //在屏幕中央显示
        SmartToast.showLongInCenter("我是朱志强！");
        //在指定位置显示，x,y方向偏移量单位为dp
        SmartToast.showLongAtLocation("我是朱志强",Gravity.LEFT | Gravity.TOP,10,10);
    }

    public void onBananaClick(View view) {
        SmartToast.showInCenter("hello!");
    }

    public void onNextPageClick(View view) {
        SmartToast.show("你好！");
    }

}
