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
        mToast = Toast.makeText(this, "你好", Toast.LENGTH_SHORT);
        return R.layout.activity_smart_show;
    }

    public void onAppleClick(View view) {
        Snackbar snackbar = Snackbar.make(view,"你好", BaseTransientBottomBar.LENGTH_SHORT);
        snackbar.getView().clearAnimation();
        snackbar.getView().setAnimation(AnimationUtils.loadAnimation(this,android.R.anim.slide_in_left));
        ((ViewGroup.MarginLayoutParams) snackbar.getView().getLayoutParams()).bottomMargin = 500;

    }

    public void onBananaClick(View view) {
        SmartToast.showInCenter("hello!");
    }

    public void onNextPageClick(View view) {
        SmartToast.show("你好！");
    }

}
