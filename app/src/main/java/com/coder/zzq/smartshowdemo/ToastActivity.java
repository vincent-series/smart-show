package com.coder.zzq.smartshowdemo;


import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.ContentFrameLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.coder.zzq.smartshow.toast.SmartToast;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class ToastActivity extends BaseActivity {
    private Toast mToast;

    @Override
    protected int contentLayout() {
        return R.layout.activity_smart_show;
    }

    public void onAppleClick(View view) {

    }

    public void onBananaClick(View view) {
        SmartToast.showInCenter("hello!");
    }

    public void onNextPageClick(View view) {
        SmartToast.show("你好！");
    }


    public void onNameClick(View view) {

    }


    @Override
    public void onBackPressed() {
            SmartToast.dismiss();
            super.onBackPressed();
    }


    public void onOrangeClick(View view) {
    }
}
