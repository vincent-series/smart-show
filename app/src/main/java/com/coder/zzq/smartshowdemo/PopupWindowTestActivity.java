package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.lang.reflect.Field;

public class PopupWindowTestActivity extends AppCompatActivity implements Runnable {
    private PopupWindow mWindow;
    private Toast mToast;
    private Object mTn;
    private WindowManager.LayoutParams mLayoutParams;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_test);
        mToast = Toast.makeText(this,"hello",Toast.LENGTH_SHORT);
        View contentView = mToast.getView();
        mWindow = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, false);
        // 设置PopupWindow的背景
//             window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        mWindow.setOutsideTouchable(false);
        // 设置PopupWindow是否能响应点击事件
        mWindow.setTouchable(false);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移


        try {
            Field tnField = Toast.class.getDeclaredField("mTN");
            tnField.setAccessible(true);
            mTn = tnField.get(mToast);
            Field windowParamField = mTn.getClass().getDeclaredField("mParams");
            windowParamField.setAccessible(true);
            mLayoutParams = (WindowManager.LayoutParams) windowParamField.get(mTn);
            mWindow.setAnimationStyle(mLayoutParams.windowAnimations);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void onBananaClick(View view) {
        view.removeCallbacks(this);
        mWindow.showAtLocation(view,mToast.getGravity(),mToast.getXOffset(),mToast.getYOffset());
        view.postDelayed(this,2000);
    }

    public void onAppleClick(View view) {
        mWindow.dismiss();
        mToast.setText("123");
        mWindow.showAtLocation(view,mToast.getGravity(),mToast.getXOffset(),mToast.getYOffset());
    }

    @Override
    public void run() {
        mWindow.dismiss();
    }

    public void onJumpClick(View view) {
        startActivity(new Intent(this,PopupWindowTestActivity.class));
    }
}
