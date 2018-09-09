package com.coder.zzq.smartshow;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;

import com.coder.zzq.smartshow.lifecycle.ActivityStack;

public class Utils {
    public static int dpToPx(float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, SmartShow.getContext().getResources().getDisplayMetrics()));
    }

    @ColorInt
    public static int getColorFromRes(@ColorRes int colorRes) {
        return ContextCompat.getColor(SmartShow.getContext(), colorRes);
    }


    @ColorInt
    public static int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ActivityStack.getTop().getWindow().getStatusBarColor();
        } else {
            return Utils.getColorFromRes(R.color.colorPrimaryDark);
        }
    }

    public static <T> T requireNonNull(T obj,String tip) {
        if (obj == null){
            throw new NullPointerException(tip);
        }

        return obj;
    }


    public static boolean hasActionbar(Activity activity){
        if (activity == null){
            return false;
        }
        if (activity instanceof AppCompatActivity){
            return ((AppCompatActivity) activity).getSupportActionBar() != null;
        }else {
            return activity.getActionBar() != null;
        }
    }

}
