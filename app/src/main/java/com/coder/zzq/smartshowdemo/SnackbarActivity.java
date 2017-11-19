package com.coder.zzq.smartshowdemo;

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
        SmartSnackbar.get(this).show("苹果");
    }

    public void onBananaClick(View view) {
        SmartSnackbar.get(this).showLong("香蕉");
    }


    public void onOrangeClick(View view) {
        SmartSnackbar.get(this).showIndefinite("桔子","好吃");
    }

    public void onNameClick(View view) {
        SmartSnackbar.get(this).show("我是朱志强", "打赏", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmartToast.showInCenter("Thank you ！");
            }
        });
    }


    @Override
    public void onSnackbarShown(Snackbar sb) {
        Log.d("Main", "shown");
    }

    @Override
    public void onSnackbarDismissed(Snackbar sb, int event) {
        Log.d("Main", "dismiss");
    }

}
