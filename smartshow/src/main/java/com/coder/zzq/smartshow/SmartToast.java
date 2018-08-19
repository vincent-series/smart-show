package com.coder.zzq.smartshow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.zzq.smartshow.lifecycle.ActivityStack;
import com.coder.zzq.smartshow.toast.ProcessViewCallback;
import com.coder.zzq.smartshow.toast.ToastSetting;
import com.coder.zzq.smartshow.toast.dummy.DummyToast;

import java.lang.reflect.Field;

/**
 * Created by 朱志强 on 2017/11/14.
 */

public final class SmartToast implements ToastSetting {
    private static SmartToast sSmartToast;
    private static Context sAppContext;
    private static boolean sDismissOnLeave;


    private Toast mToast;
    private CharSequence mCurMsg;
    private View mCustomView;
    private TextView mMsgView;

    private int mXOffset;
    private int mYOffset;
    private int mVerticalAxisOffset;
    private int mGravity;
    private int mDuration;

    @ColorInt
    private int mBgColor;
    @ColorInt
    private int mTextColor;
    private int mTextSizeSp;
    private boolean mTextBold;

    private ProcessViewCallback mProcessViewCallback;
    private boolean mHasRetrieveWindowAnimStyle;


    private SmartToast() {
        mCurMsg = "";
        mBgColor = -1;
        mTextColor = -1;
        mTextSizeSp = -1;
        mDuration = Toast.LENGTH_SHORT;
    }

    protected static SmartToast init(Context context) {
        if (context == null) {
            throw new NullPointerException("初始化SmartToast的context不可谓null！");
        }
        sAppContext = context.getApplicationContext();
        if (sSmartToast == null) {
            sSmartToast = new SmartToast();
        }
        return sSmartToast;
    }


    @Override
    public ToastSetting view(View view) {
        mCustomView = view;
        return this;
    }

    @Override
    public ToastSetting view(@LayoutRes int layout) {
        return view(LayoutInflater.from(sAppContext).inflate(layout, null));
    }

    @Override
    public ToastSetting backgroundColor(@ColorInt int color) {
        mBgColor = color;
        return this;
    }

    @Override
    public ToastSetting backgroundColorRes(@ColorRes int colorRes) {
        return backgroundColor(ContextCompat.getColor(sAppContext, colorRes));
    }

    @Override
    public ToastSetting textColor(@ColorInt int color) {
        mTextColor = color;
        return this;
    }

    @Override
    public ToastSetting textColorRes(@ColorRes int colorRes) {
        return textColor(ContextCompat.getColor(sAppContext, colorRes));
    }

    @Override
    public ToastSetting textSizeSp(int sp) {
        mTextSizeSp = sp;
        return this;
    }

    @Override
    public ToastSetting textBold(boolean bold) {
        mTextBold = bold;
        return this;
    }

    @Override
    public ToastSetting dismissOnLeave(boolean b) {
        setDismissOnLeave(b);
        return this;
    }

    @Override
    public ToastSetting processView(ProcessViewCallback callback) {
        mProcessViewCallback = callback;
        return this;
    }

    private static boolean isCustom() {
        return sSmartToast.mCustomView != null;
    }


    public static boolean isDismissOnLeave() {
        return sDismissOnLeave;
    }

    public static void setDismissOnLeave(boolean b) {
        sDismissOnLeave = b;
    }


    public static void show(CharSequence msg) {
        ensureToastExists();
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, sSmartToast.mVerticalAxisOffset, Toast.LENGTH_SHORT);
    }

    public static void showAtTop(CharSequence msg) {
        ensureToastExists();
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, sSmartToast.mVerticalAxisOffset, Toast.LENGTH_SHORT);
    }

    public static void showInCenter(CharSequence msg) {
        ensureToastExists();
        showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }

    public static void showAtLocation(CharSequence msg, int gravity, int xOffsetDp, int yOffsetDp) {
        ensureToastExists();
        int xOffset = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, xOffsetDp, sSmartToast.sAppContext.getResources().getDisplayMetrics()));
        int yOffset = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, yOffsetDp, sSmartToast.sAppContext.getResources().getDisplayMetrics()));
        showHelper(msg, gravity, xOffset, yOffset, Toast.LENGTH_SHORT);
    }


    public static void showLong(CharSequence msg) {
        ensureToastExists();
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, sSmartToast.mVerticalAxisOffset, Toast.LENGTH_LONG);
    }

    public static void showLongAtTop(CharSequence msg) {
        ensureToastExists();
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, sSmartToast.mVerticalAxisOffset, Toast.LENGTH_LONG);
    }

    public static void showLongInCenter(CharSequence msg) {
        ensureToastExists();
        showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }

    public static void showLongAtLocation(CharSequence msg, int gravity, int xOffsetDp, int yOffsetDp) {
        ensureToastExists();
        int xOffset = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, xOffsetDp, sSmartToast.sAppContext.getResources().getDisplayMetrics()));
        int yOffset = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, yOffsetDp, sSmartToast.sAppContext.getResources().getDisplayMetrics()));
        showHelper(msg, gravity, xOffset, yOffset, Toast.LENGTH_LONG);
    }


    private static void showHelper(CharSequence msg, int gravity, int xOffset, int yOffset, int duration) {
        msg = (msg == null) ? "" : msg;
        //位置是否改变
        boolean locationChanged = sSmartToast.locationChanged(gravity, xOffset, yOffset);
        //文本是否改变
        boolean contentChanged = !sSmartToast.mCurMsg.equals(msg);

        sSmartToast.mCurMsg = msg;
        sSmartToast.mDuration = duration;
        sSmartToast.mGravity = gravity;
        sSmartToast.mXOffset = xOffset;
        sSmartToast.mYOffset = yOffset;


        if (isShowing() && locationChanged || contentChanged) {
            SmartToast.dismiss();
            rebuildToast();
        }
        updateToast();
        showToast();
    }


    private static void ensureToastExists() {

        if (sSmartToast == null) {
            init(SmartShow.getContext());
        }
        if (sSmartToast.mToast == null) {
            sSmartToast.mToast = Toast.makeText(sAppContext, "", Toast.LENGTH_SHORT);
            sSmartToast.setInitialPosInfo();
            setupDummyToast();
            setupToast();
        }
    }

    private static void setupToast() {
        View rootView = isCustom() ? sSmartToast.mCustomView : sSmartToast.mToast.getView();
        LinearLayout outParent = isCustom() ? null : (LinearLayout) sSmartToast.mToast.getView();
        if (isCustom()) {
            sSmartToast.mMsgView = rootView.findViewById(R.id.custom_toast_msg);
        } else {
            sSmartToast.mMsgView = outParent.findViewById(android.R.id.message);
        }

        if (sSmartToast.mBgColor != -1) {
            if (isCustom()) {
                sSmartToast.mCustomView.setBackgroundColor(sSmartToast.mBgColor);
            } else {
                Drawable drawable = ContextCompat.getDrawable(sAppContext, android.R.drawable.toast_frame);
                Rect rect = new Rect();
                drawable.getPadding(rect);
                sSmartToast.mMsgView.setPadding(sSmartToast.mMsgView.getPaddingLeft() + rect.left, sSmartToast.mMsgView.getPaddingTop(), sSmartToast.mMsgView.getPaddingRight() + rect.right, sSmartToast.mMsgView.getPaddingBottom());
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(sSmartToast.mBgColor);
                gradientDrawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2.5f, sAppContext.getResources().getDisplayMetrics()));
                ViewCompat.setBackground(outParent, gradientDrawable);
            }
        }

        if (sSmartToast.mTextColor != -1) {
            sSmartToast.mMsgView.setTextColor(sSmartToast.mTextColor);
        }
        if (sSmartToast.mTextSizeSp != -1) {
            sSmartToast.mMsgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sSmartToast.mTextSizeSp);
        }

        sSmartToast.mMsgView.setGravity(Gravity.CENTER);
        sSmartToast.mMsgView.getPaint().setFakeBoldText(sSmartToast.mTextBold);
        if (sSmartToast.mProcessViewCallback != null) {
            sSmartToast.mProcessViewCallback.processView(isCustom(), rootView, outParent, sSmartToast.mMsgView);
        }

    }


    private static void setupDummyToast() {
        if (isSdk25() && !sSmartToast.mHasRetrieveWindowAnimStyle) {
            DummyToast.setWindowAnimationStyle(getToastWindowAnimStyleRes());
        }
    }


    private void setInitialPosInfo() {
        mGravity = mToast.getGravity();
        mXOffset = mToast.getXOffset();
        mVerticalAxisOffset = mYOffset = mToast.getYOffset();
    }


    private static void rebuildToast() {

        if (isSdk25()) {
            return;
        }

        if (isCustom()) {
            sSmartToast.mToast = new Toast(sAppContext);
            sSmartToast.mToast.setView(sSmartToast.mCustomView);
        } else {
            sSmartToast.mToast = Toast.makeText(sAppContext, "", Toast.LENGTH_SHORT);
        }
        setupToast();
    }


    private static void showToast() {

        if (!isSdk25()) {
            sSmartToast.mToast.show();
            return;
        }

        if (ActivityStack.getTop() != null) {
            DummyToast.show(ActivityStack.getTop().findViewById(android.R.id.content),
                    sSmartToast.mToast.getView(),
                    sSmartToast.mGravity, sSmartToast.mXOffset, sSmartToast.mYOffset, sSmartToast.mDuration);
            return;
        }

    }

    private static void updateToast() {
        sSmartToast.mToast.setText(sSmartToast.mCurMsg);
        sSmartToast.mToast.setGravity(sSmartToast.mGravity, sSmartToast.mXOffset, sSmartToast.mYOffset);
        sSmartToast.mToast.setDuration(sSmartToast.mDuration);
    }


    private boolean locationChanged(int gravity, int xOffset, int yOffset) {
        return mGravity != gravity || mXOffset != xOffset || mYOffset != yOffset;
    }

    public static boolean isShowing() {
        if (isSdk25()) {
            return DummyToast.isShowing();
        }

        return sSmartToast != null && sSmartToast.mToast != null
                && ViewCompat.isAttachedToWindow(sSmartToast.mToast.getView());

    }


    public static void dismiss() {
        if (isSdk25()) {
            DummyToast.dismiss();
            return;
        }

        if (sSmartToast != null && sSmartToast.mToast != null
                && ViewCompat.isAttachedToWindow(sSmartToast.mToast.getView())) {
            sSmartToast.mToast.cancel();
            sSmartToast.mToast = null;
        }
    }


    private static int getToastWindowAnimStyleRes() {
        int windowAnimations = 0;
        try {
            Field tnField = Toast.class.getDeclaredField("mTN");
            tnField.setAccessible(true);
            Object tn = tnField.get(sSmartToast.mToast);
            Field windowLayoutParamsField = tn.getClass().getDeclaredField("mParams");
            windowLayoutParamsField.setAccessible(true);
            WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) windowLayoutParamsField.get(tn);
            windowAnimations = layoutParams.windowAnimations;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        sSmartToast.mHasRetrieveWindowAnimStyle = true;
        return windowAnimations;
    }


    private static boolean isSdk25() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1;
    }


}
