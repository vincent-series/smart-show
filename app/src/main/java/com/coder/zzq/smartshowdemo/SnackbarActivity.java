package com.coder.zzq.smartshowdemo;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.coder.zzq.smartshow.snackbar.SmartSnackbar;
import com.coder.zzq.smartshow.snackbar.SnackbarCallback;
import com.coder.zzq.smartshow.toast.SmartToast;


public class SnackbarActivity extends BaseActivity implements SnackbarCallback {

    @Override
    protected int contentLayout() {
        return R.layout.activity_smart_show;
    }


    public void onAppleClick(View view) {



        //传入Activity，获取当前页面的Snackbar，显示消息和动作文本，传入点击动作文本的回调代码
        SmartSnackbar.get(this).showIndefinite("我是朱志强", "打赏", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SmartShow","Thank you !");
            }
        });

        //传入Activity，获取当前页面的Snackbar，显示消息和动作文本，不传第三个参数，默认行为为Snackar消失
        SmartSnackbar.get(this).show("我是朱志强","打赏");

    }

    public void onBananaClick(View view) {

    }

    public void onNextPageClick(View view) {

    }


    @Override
    public void onSnackbarShown(Snackbar sb) {
        Log.d("Main", "shown --> " + sb.toString());
    }

    @Override
    public void onSnackbarDismissed(Snackbar sb, int event) {
        Log.d("Main", "dismiss --> " + sb.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SmartSnackbar.destroy(this);
    }
}
