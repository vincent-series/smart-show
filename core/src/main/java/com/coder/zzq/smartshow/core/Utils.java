package com.coder.zzq.smartshow.core;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.coder.zzq.smartshow.core.lifecycle.ActivityStack;

public class Utils {


    public static int dpToPx(float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, SmartShow.getContext().getResources().getDisplayMetrics()));
    }

    @ColorInt
    public static int getColorFromRes(@ColorRes int colorRes) {
        return ContextCompat.getColor(SmartShow.getContext(), colorRes);
    }

    public static <T> T requireNonNull(T obj, String tip) {
        if (obj == null) {
            throw new NullPointerException(tip);
        }

        return obj;
    }


    public static boolean isEmpty(CharSequence c) {
        return TextUtils.isEmpty(c) || c.toString().trim().isEmpty();
    }

    public static int screenWidth() {
        return SmartShow.getContext().getResources().getDisplayMetrics().widthPixels;
    }


    public static int getStatusBarHeight() {
        int resourceId = SmartShow.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        int height = 0;
        try {
            height = SmartShow.getContext().getResources().getDimensionPixelSize(resourceId);
        } catch (Resources.NotFoundException e) {
            height = Utils.dpToPx(24);
        }

        return height;
    }

    public static int getToolbarHeight() {
        int resourceId = SmartShow.getContext().getResources().getIdentifier("abc_action_bar_default_height_material", "dimen", "android");
        int height = 0;
        try {
            height = SmartShow.getContext().getResources().getDimensionPixelSize(resourceId);
        } catch (Resources.NotFoundException e) {
            height = Utils.dpToPx(56);
        }

        return height;
    }


    public static boolean isActivityDestroyed(Activity activity) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 ?
                activity.isDestroyed() : !ActivityStack.isInStack(activity);
    }

    public static void popKeyboardWhenDialogShow(Dialog dialog) {
        if (dialog != null) {
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) SmartShow.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static View inflate(@LayoutRes int layoutRes, ViewGroup viewGroup, boolean attach) {
        return LayoutInflater.from(SmartShow.getContext()).inflate(layoutRes, viewGroup, attach);
    }

    public static View inflate(@LayoutRes int layoutRes, ViewGroup viewGroup) {
        return inflate(layoutRes, viewGroup, viewGroup != null);
    }

    public static Drawable getDrawableFromRes(int drawableRes) {
        return ContextCompat.getDrawable(SmartShow.getContext(), drawableRes);
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (view == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    /**
     * 判断 activity的状态是可以操作UI
     *
     * @param activity
     * @return
     */
    public static boolean isUpdateActivityUIPermitted(Activity activity) {
        return activity != null && !activity.isFinishing() && !Utils.isActivityDestroyed(activity);
    }

    public static String getStringFromRes(@StringRes int msg) {
        return SmartShow.getContext().getString(msg);
    }

    public static float pxToDp(int px) {
        float scale = SmartShow.getContext().getResources().getDisplayMetrics().density;
        return px / scale;
    }
}
