package com.coder.zzq.smartshow;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
    private View mPlainView;
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

    private boolean mNoTypeSetup;
    private WindowManager.LayoutParams mWindowParams;
    private int mNoTypeAnim;


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

    private static boolean isCustomWhenNoType() {
        return sSmartToast.mCustomView != null;
    }


    public static boolean isDismissOnLeave() {
        return sDismissOnLeave;
    }

    public static void setDismissOnLeave(boolean b) {
        sDismissOnLeave = b;
    }


    public static void show(CharSequence msg) {
        prepareNoTypeToast();
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, sSmartToast.mVerticalAxisOffset, Toast.LENGTH_SHORT);
    }

    public static void showAtTop(CharSequence msg) {
        prepareNoTypeToast();
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, sSmartToast.mVerticalAxisOffset, Toast.LENGTH_SHORT);
    }

    public static void showInCenter(CharSequence msg) {
        prepareNoTypeToast();
        showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_SHORT);
    }

    public static void showAtLocation(CharSequence msg, int gravity, int xOffsetDp, int yOffsetDp) {
        prepareNoTypeToast();
        int xOffset = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, xOffsetDp, sSmartToast.sAppContext.getResources().getDisplayMetrics()));
        int yOffset = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, yOffsetDp, sSmartToast.sAppContext.getResources().getDisplayMetrics()));
        showHelper(msg, gravity, xOffset, yOffset, Toast.LENGTH_SHORT);
    }


    public static void showLong(CharSequence msg) {
        prepareNoTypeToast();
        showHelper(msg, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, sSmartToast.mVerticalAxisOffset, Toast.LENGTH_LONG);
    }

    public static void showLongAtTop(CharSequence msg) {
        prepareNoTypeToast();
        showHelper(msg, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, sSmartToast.mVerticalAxisOffset, Toast.LENGTH_LONG);
    }

    public static void showLongInCenter(CharSequence msg) {
        prepareNoTypeToast();
        showHelper(msg, Gravity.CENTER, 0, 0, Toast.LENGTH_LONG);
    }

    public static void showLongAtLocation(CharSequence msg, int gravity, int xOffsetDp, int yOffsetDp) {
        prepareNoTypeToast();
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
                updateToast();
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
        if (isShowing() && (locationChanged || contentChanged)) {
            dismiss();
        } else {
            updateToast();
        }
    }

    private static void ensureToastExist() {
        if (sSmartToast == null) {
            init(SmartShow.getContext());
        }

        if (sSmartToast.mToast == null) {
            sSmartToast.mToast = Toast.makeText(sAppContext, "", Toast.LENGTH_SHORT);
            sSmartToast.setInitialPosInfo();
            setupReflectInfo();
            if (!isCustomWhenNoType()) {
                sSmartToast.mPlainView = sSmartToast.mToast.getView();
            }
        }

    }

    private static void prepareNoTypeToast() {

        ensureToastExist();

        if (!sSmartToast.mNoTypeSetup) {
            setupNoTypeToast();
        }

        if (isShowing() && sSmartToast.mToast.getView() == sSmartToast.mTypeInfoView) {
            dismiss();
        }
        sSmartToast.mToast.setView(isCustomWhenNoType() ? sSmartToast.mCustomView : sSmartToast.mPlainView);
        sSmartToast.mWindowParams.windowAnimations = sSmartToast.mNoTypeAnim;
        sSmartToast.mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private static void setupNoTypeToast() {
        View rootView = isCustomWhenNoType() ? sSmartToast.mCustomView : sSmartToast.mPlainView;
        rootView.addOnAttachStateChangeListener(sSmartToast);
        LinearLayout outParent = isCustomWhenNoType() ? null : (LinearLayout) sSmartToast.mPlainView;
        if (isCustomWhenNoType()) {
            sSmartToast.mMsgView = rootView.findViewById(R.id.custom_toast_msg);
        } else {
            sSmartToast.mMsgView = outParent.findViewById(android.R.id.message);
        }

        if (sSmartToast.mBgColor != -1) {
            if (isCustomWhenNoType()) {
                sSmartToast.mCustomView.setBackgroundColor(sSmartToast.mBgColor);
            } else {
                DrawableCompat.setTint(sSmartToast.mPlainView.getBackground(), sSmartToast.mBgColor);
            }
        }else {

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
            sSmartToast.mProcessViewCallback.processView(isCustomWhenNoType(), rootView, outParent, sSmartToast.mMsgView);
        }
        sSmartToast.mNoTypeSetup = true;
    }

    private static void setupReflectInfo() {
        try {
            Field tnField = Toast.class.getDeclaredField("mTN");
            tnField.setAccessible(true);
            sSmartToast.mTn = tnField.get(sSmartToast.mToast);
            sSmartToast.mViewField = sSmartToast.mTn.getClass().getDeclaredField("mView");
            sSmartToast.mViewField.setAccessible(true);
            sSmartToast.mHideMethod = sSmartToast.mTn.getClass().getDeclaredMethod("handleHide");
            sSmartToast.mHideMethod.setAccessible(true);
            Field windowParamsField = sSmartToast.mTn.getClass().getDeclaredField("mParams");
            windowParamsField.setAccessible(true);
            sSmartToast.mWindowParams = (WindowManager.LayoutParams) windowParamsField.get(sSmartToast.mTn);
            sSmartToast.mNoTypeAnim = sSmartToast.mWindowParams.windowAnimations;

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
            if (sSmartToast != null && sSmartToast.mHideMethod != null) {
                sSmartToast.mHideMethod.invoke(sSmartToast.mTn);
            }
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
        updateToast();
    }




    public static final int TYPE_INFO_SUCCESS = 0;
    public static final int TYPE_INFO_FAIL = 1;
    public static final int TYPE_INFO_NORMAL = 3;
    public static final int TYPE_INFO_WARNING = 4;
    public static final int TYPE_INFO_ERROR = 5;

    private View mTypeInfoView;
    private TextView mTypeInfoMsgView;
    private ImageView mTypeInfoIconView;
    private int mCurTypeInfo = -1;
    private int mTypeIconRes = R.drawable.type_info_success;
    private String mCurTypeInfoMsg = "";

    public static void success(String msg) {
        prepareTypeInfoToast();
        typeInfoShowHelper(msg, TYPE_INFO_SUCCESS, Toast.LENGTH_SHORT);
    }

    public static void fail(String msg){
        prepareTypeInfoToast();
        typeInfoShowHelper(msg, TYPE_INFO_FAIL, Toast.LENGTH_SHORT);
    }

    public static void error(String msg){
        prepareTypeInfoToast();
        typeInfoShowHelper(msg, TYPE_INFO_ERROR, Toast.LENGTH_SHORT);
    }

    public static void warning(String msg){
        prepareTypeInfoToast();
        typeInfoShowHelper(msg, TYPE_INFO_WARNING, Toast.LENGTH_SHORT);
    }


    public static void normal(String msg){
        prepareTypeInfoToast();
        typeInfoShowHelper(msg, TYPE_INFO_NORMAL, Toast.LENGTH_SHORT);
    }



    private static void prepareTypeInfoToast() {
        ensureToastExist();
        if (isShowing() && sSmartToast.mToast.getView() != sSmartToast.mTypeInfoView) {
            dismiss();
        }
        if (sSmartToast.mTypeInfoView == null) {
            sSmartToast.mTypeInfoView = LayoutInflater.from(sAppContext).inflate(R.layout.layout_type_info, null);
            sSmartToast.mTypeInfoView.addOnAttachStateChangeListener(sSmartToast);
            sSmartToast.mTypeInfoMsgView = sSmartToast.mTypeInfoView.findViewById(R.id.type_info_message);
            sSmartToast.mTypeInfoIconView = sSmartToast.mTypeInfoView.findViewById(R.id.type_info_icon);
        }
        sSmartToast.mToast.setView(sSmartToast.mTypeInfoView);
        sSmartToast.mWindowParams.windowAnimations = R.style.type_info_toast_anim;
        sSmartToast.mWindowParams.height = sSmartToast.dpToPx(85);
    }

    private static void typeInfoShowHelper(String msg, int typeInfo, int duration) {
        msg = (msg == null) ? "" : msg;
        //文本是否改变
        boolean contentChanged = !sSmartToast.mCurTypeInfoMsg.equals(msg);

        //类型是否改变
        boolean typeChanged = typeInfo != sSmartToast.mCurTypeInfo;

        sSmartToast.mCurMsg = msg;
        sSmartToast.mDuration = duration;
        sSmartToast.mCurTypeInfo = typeInfo;
        switch (typeInfo){
            case TYPE_INFO_SUCCESS:
                sSmartToast.mTypeIconRes = R.drawable.type_info_success;
                break;
            case TYPE_INFO_ERROR:
                sSmartToast.mTypeIconRes = R.drawable.type_info_error;
                break;
            case TYPE_INFO_FAIL:
                sSmartToast.mTypeIconRes = R.drawable.type_info_fail;
                break;
            case TYPE_INFO_NORMAL:
                sSmartToast.mTypeIconRes = R.drawable.type_info_normal;
                break;
            case TYPE_INFO_WARNING:
                sSmartToast.mTypeIconRes = R.drawable.type_info_warning;
                break;
        }

        switch (sSmartToast.mActionWhenToastDuplicate) {
            case ACTION_IGNORE:
                if (isShowing() && (contentChanged || typeChanged)) {
                    dismiss();
                } else {
                    updateToast();
                }
                break;
            case ACTION_REPEAT_SHOW_LIKE_SNACKBAR:
                dismiss();
                updateToast();
                break;
        }


        sSmartToast.mToast.setGravity(Gravity.CENTER, 0, 0);
        sSmartToast.mToast.setDuration(sSmartToast.mDuration);

        sSmartToast.mToast.show();

    }

    private static void updateToast() {
        TextView msgView = sSmartToast.mToast.getView() == sSmartToast.mTypeInfoView ?
                sSmartToast.mTypeInfoMsgView :
                sSmartToast.mMsgView;
        msgView.setText(sSmartToast.mCurMsg);
        if (sSmartToast.mToast.getView() == sSmartToast.mTypeInfoView){
            sSmartToast.mTypeInfoIconView.setImageResource(sSmartToast.mTypeIconRes);
        }
    }



    public int dpToPx(float dp){
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,sAppContext.getResources().getDisplayMetrics()));
    }
}
