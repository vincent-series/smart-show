package com.coder.zzq.smartshow;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
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

import com.coder.zzq.smartshow.toast.ProcessToastCallback;
import com.coder.zzq.smartshow.toast.SafeHandler;
import com.coder.zzq.smartshow.toast.ToastSetting;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 朱志强 on 2017/11/14.
 */

public final class SmartToast implements ToastSetting, View.OnAttachStateChangeListener {
    private static SmartToast sSmartToast;
    private static Context sAppContext;
    private static boolean sDismissOnLeave;
    private Object mTn;
    private Field mViewField;


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

    private int mActionWhenToastDuplicate = ACTION_IGNORE;

    private ProcessToastCallback mProcessViewCallback;
    private Method mHideMethod;


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
    public ToastSetting actionWhenDuplicate(int action) {
        mActionWhenToastDuplicate = action;
        return this;
    }

    @Override
    public ToastSetting processView(ProcessToastCallback callback) {
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


        switch (sSmartToast.mActionWhenToastDuplicate) {
            case ACTION_IGNORE:
                showOnActionIgnore(locationChanged, contentChanged);
                break;
            case ACTION_REPEAT_SHOW_LIKE_SNACKBAR:
                dismiss();
                sSmartToast.mToast.setText(sSmartToast.mCurMsg);
                break;
            default:
                showOnActionIgnore(locationChanged, contentChanged);
                break;
        }


        sSmartToast.mToast.setGravity(sSmartToast.mGravity, sSmartToast.mXOffset, sSmartToast.mYOffset);
        sSmartToast.mToast.setDuration(sSmartToast.mDuration);

        sSmartToast.mToast.show();
    }

    private static void showOnActionIgnore(boolean locationChanged, boolean contentChanged) {
        if (sSmartToast.mActionWhenToastDuplicate == ACTION_IGNORE) {
            if (isShowing() && (locationChanged || contentChanged)) {
                dismiss();
            } else {
                sSmartToast.mToast.setText(sSmartToast.mCurMsg);
            }
        }
    }


    private static void ensureToastExists() {

        if (sSmartToast == null) {
            init(SmartShow.getContext());
        }
        if (sSmartToast.mToast == null) {
            sSmartToast.mToast = Toast.makeText(sAppContext, "", Toast.LENGTH_SHORT);
            sSmartToast.setInitialPosInfo();
            setupToast();
        }
    }

    private static void setupToast() {
        View rootView = isCustom() ? sSmartToast.mCustomView : sSmartToast.mToast.getView();
        rootView.addOnAttachStateChangeListener(sSmartToast);
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

        try {
            Field tnField = Toast.class.getDeclaredField("mTN");
            tnField.setAccessible(true);
            sSmartToast.mTn = tnField.get(sSmartToast.mToast);
            sSmartToast.mViewField = sSmartToast.mTn.getClass().getDeclaredField("mView");
            sSmartToast.mViewField.setAccessible(true);
            sSmartToast.mHideMethod = sSmartToast.mTn.getClass().getDeclaredMethod("handleHide");
            sSmartToast.mHideMethod.setAccessible(true);
            if (isSdk25()) {
                Field handerField = sSmartToast.mTn.getClass().getDeclaredField("mHandler");
                handerField.setAccessible(true);
                Handler handler = (Handler) handerField.get(sSmartToast.mTn);
                handerField.set(sSmartToast.mTn, new SafeHandler(handler));
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    private void setInitialPosInfo() {
        mGravity = mToast.getGravity();
        mXOffset = mToast.getXOffset();
        mVerticalAxisOffset = mYOffset = mToast.getYOffset();
    }


    private boolean locationChanged(int gravity, int xOffset, int yOffset) {
        return mGravity != gravity || mXOffset != xOffset || mYOffset != yOffset;
    }

    public static boolean isShowing() {
        return sSmartToast != null && sSmartToast.mToast != null
                && ViewCompat.isAttachedToWindow(sSmartToast.mToast.getView());
    }


    public static void dismiss() {
        try {
            sSmartToast.mHideMethod.invoke(sSmartToast.mTn);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private static boolean isSdk25() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1;
    }


    @Override
    public void onViewAttachedToWindow(View v) {

    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        mMsgView.setText(mCurMsg);
    }
}
