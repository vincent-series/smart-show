package com.coder.zzq.smartshow.toast;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.zzq.smartshow.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 朱志强 on 2017/11/14.
 */

public final class SmartToast implements PlainToastSetting, CustomToastSetting, Runnable {
    private static SmartToast sSmartToast;
    private Toast mToast;

    private Context mAppContext;
    private CharSequence mCurMsg;
    private View mCustomView;
    private TextView mCustomMsgView;

    private int mXOffset;
    private int mYOffset;
    private int mVerticalAxisOffset;
    private int mGravity;

    private ProcessViewCallback mProcessViewCallback;

    @ColorInt
    private int mBgColor;
    @ColorInt
    private int mTextColor;
    private int mTextSizeSp;
    private boolean mTextBold;

    private SmartToast(Context Context) {
        if (Context == null) {
            throw new NullPointerException("初始化SmartToast的Context不可为null！");
        }
        mAppContext = Context.getApplicationContext();
        mCurMsg = "";
        mBgColor = -1;
        mTextColor = -1;
        mTextSizeSp = -1;
    }

    private static SmartToast getSmartToast(Context context) {
        if (sSmartToast == null) {
            sSmartToast = new SmartToast(context);
        }

        return sSmartToast;
    }

    public static PlainToastSetting plainToast(Context context) {
        return getSmartToast(context);
    }

    public static CustomToastSetting customToast(Context context) {
        return getSmartToast(context);
    }

    private static Toast getToast() {
        if (sSmartToast == null) {
            throw new IllegalStateException("尚未初始化SmartToast:SmartToast.plainToast()或者SmartToast.customToast()。");
        }
        if (sSmartToast.mToast == null) {
            if (sSmartToast.mCustomView == null) {
                sSmartToast.mToast = Toast.makeText(sSmartToast.mAppContext, "", Toast.LENGTH_SHORT);
                sSmartToast.setupPlainToast();
            } else {
                sSmartToast.mToast = new Toast(sSmartToast.mAppContext);
                sSmartToast.setupCustomToast();
            }
            sSmartToast.setInitialPosInfo();
        }

        return sSmartToast.mToast;
    }

    private void setInitialPosInfo() {
        mGravity = mToast.getGravity();
        mXOffset = mToast.getXOffset();
        mVerticalAxisOffset = mYOffset = mToast.getYOffset();
    }

    private void updateToast(){
        if (mCustomMsgView != null) {
            mCustomMsgView.setText(mCurMsg);
        } else {
            getToast().setText(mCurMsg);
        }
        getToast().setGravity(mGravity, mXOffset, mYOffset);
    }


    private void setupPlainToast() {
        LinearLayout outParent = (LinearLayout) mToast.getView();
        TextView msgView = (TextView) outParent.findViewById(android.R.id.message);
        if (mBgColor != -1) {
            NinePatchDrawable ninePatchDrawable = (NinePatchDrawable) ContextCompat.getDrawable(mAppContext, android.R.drawable.toast_frame);
            Rect rect = new Rect();
            ninePatchDrawable.getPadding(rect);
            msgView.setPadding(msgView.getPaddingLeft() + rect.left, msgView.getPaddingTop(), msgView.getPaddingRight() + rect.right, msgView.getPaddingBottom());
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(mBgColor);
            gradientDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.5f, mAppContext.getResources().getDisplayMetrics()));
            ViewCompat.setBackground(outParent, gradientDrawable);
        }
        if (mTextColor != -1) {
            msgView.setTextColor(mTextColor);
        }
        if (mTextSizeSp != -1) {
            msgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSizeSp);
        }
        msgView.getPaint().setFakeBoldText(mTextBold);
        if (mProcessViewCallback != null) {
            mProcessViewCallback.processPlainView(outParent, msgView);
        }
    }

    private void setupCustomToast() {
        if (mProcessViewCallback != null) {
            mProcessViewCallback.processCustomView(mCustomView);
        }
        mCustomMsgView = (TextView) mCustomView.findViewById(R.id.custom_toast_msg);
        mToast.setView(mCustomView);
        mCurMsg = "";
    }

    public static void show(CharSequence msg){
        getToast();
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,sSmartToast.mVerticalAxisOffset,Toast.LENGTH_SHORT);
    }
    public static void showAtTop(CharSequence msg){
        getToast();
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL,0,sSmartToast.mVerticalAxisOffset,Toast.LENGTH_SHORT);
    }
    public static void showInCenter(CharSequence msg){
        getToast();
        showHelper(msg, Gravity.CENTER,0,0,Toast.LENGTH_SHORT);
    }

    public static void showAtLocation(CharSequence msg,int gravity,int xOffset,int yOffset){
        getToast();
        showHelper(msg, gravity,xOffset,yOffset,Toast.LENGTH_SHORT);
    }



    public static void showLong(CharSequence msg){
        getToast();
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0,sSmartToast.mVerticalAxisOffset,Toast.LENGTH_LONG);
    }
    public static void showLongAtTop(CharSequence msg){
        getToast();
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL,0,sSmartToast.mVerticalAxisOffset,Toast.LENGTH_LONG);
    }
    public static void showLongInCenter(CharSequence msg){
        getToast();
        showHelper(msg, Gravity.CENTER,0,0,Toast.LENGTH_LONG);
    }

    public static void showLongAtLocation(CharSequence msg,int gravity,int xOffset,int yOffset){
        getToast();
        showHelper(msg, gravity,xOffset,yOffset,Toast.LENGTH_LONG);
    }



    private static void showHelper(CharSequence msg, int gravity, int xOffsetDp, int yOffsetDp, int duration) {

        if (sSmartToast.mCustomView != null && sSmartToast.mCustomMsgView == null) {
            return;
        }

        msg = msg == null ? "" : msg;
        getToast().setDuration(duration);

        boolean locationChanged = sSmartToast.locationChanged(gravity,xOffsetDp,yOffsetDp);
        boolean contentChanged = !sSmartToast.mCurMsg.equals(msg);

        sSmartToast.mCurMsg = msg;
        sSmartToast.mGravity = gravity;
        sSmartToast.mXOffset = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,xOffsetDp,sSmartToast.mAppContext.getResources().getDisplayMetrics()));
        sSmartToast.mYOffset = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,yOffsetDp,sSmartToast.mAppContext.getResources().getDisplayMetrics()));;

        if (ViewCompat.isAttachedToWindow(getToast().getView()) && (contentChanged || locationChanged)) {
            SmartToast.dismiss();
            getToast().getView().postDelayed(sSmartToast, 150);
        }else {
            sSmartToast.updateToast();
            getToast().show();
        }
    }

    private boolean locationChanged(int gravity, int xOffset, int yOffset) {
        return mGravity != gravity || mXOffset != xOffset || mYOffset != yOffset;
    }


    public static void dismiss() {
        if (sSmartToast != null && sSmartToast.mToast != null) {
            try {
                Field tnField = Toast.class.getDeclaredField("mTN");
                tnField.setAccessible(true);
                Object tn = tnField.get(sSmartToast.mToast);
                Method hideMethod = tn.getClass().getDeclaredMethod("hide");
                hideMethod.setAccessible(true);
                hideMethod.invoke(tn);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public PlainToastSetting backgroundColor(@ColorInt int color) {
        mBgColor = color;
        return this;
    }

    @Override
    public PlainToastSetting backgroundColorRes(@ColorRes int colorRes) {
        return backgroundColor(ContextCompat.getColor(mAppContext, colorRes));
    }

    @Override
    public PlainToastSetting textColor(@ColorInt int color) {
        mTextColor = color;
        return this;
    }

    @Override
    public PlainToastSetting textColorRes(@ColorRes int colorRes) {
        return textColor(ContextCompat.getColor(mAppContext, colorRes));
    }

    @Override
    public PlainToastSetting textSizeSp(int sp) {
        mTextSizeSp = sp;
        return this;
    }

    @Override
    public PlainToastSetting textBold(boolean bold) {
        mTextBold = bold;
        return this;
    }

    @Override
    public PlainToastSetting processPlainView(ProcessViewCallback callback) {
        mProcessViewCallback = callback;
        return this;
    }


    @Override
    public CustomToastSetting view(View view) {
        mCustomView = view;
        return this;
    }

    @Override
    public CustomToastSetting view(@LayoutRes int layout) {
        return view(LayoutInflater.from(mAppContext).inflate(layout, null));
    }

    @Override
    public CustomToastSetting processCustomView(ProcessViewCallback callback) {
        mProcessViewCallback = callback;
        return this;
    }



    @Override
    public void run() {
        updateToast();
        getToast().show();
    }
}
