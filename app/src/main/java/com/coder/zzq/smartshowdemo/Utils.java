package com.coder.zzq.smartshowdemo;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

/**
 * Created by 喜欢、陪你看风景 on 2017/9/21.
 */

public class Utils {
    //全局上下文环境
    private static Application sApplication;

    public static void init(@NonNull Application application){
        sApplication = NullUtil.requireNonNull(application,"初始化Utils的Application对象不可为Null!");
    }

    public static Application getApplication(){
        return NullUtil.requireNonNull(sApplication,"尚未使用Application初始化Utils");
    }

    public static class DimenUtil{

        public static float dp2px(float dp){
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getApplication().getResources().getDisplayMetrics());
        }

        public static float sp2px(float sp){
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getApplication().getResources().getDisplayMetrics());
        }
    }


    public static class ResUtil{

        public static @ColorInt int getColor(@ColorRes int colorRes){
            return ContextCompat.getColor(getApplication(),colorRes);
        }

        public static Drawable getDrawable(@DrawableRes int drawableRes){
            return ContextCompat.getDrawable(getApplication(),drawableRes);
        }

    }



    public static class NullUtil{

        public static  <T> T requireNonNull(T obj){
            return requireNonNull(obj,"");
        }

        public static  <T> T requireNonNull(T obj,String errorTip){
            if (obj == null){
                throw new NullPointerException(errorTip);
            }

            return obj;
        }
    }
}
