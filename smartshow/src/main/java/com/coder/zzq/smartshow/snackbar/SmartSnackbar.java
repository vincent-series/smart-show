package com.coder.zzq.smartshow.snackbar;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.CoordinatorLayout;

import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.lifecycle.ActivityStack;
import com.coder.zzq.smartshow.snackbar.custom.BaseTransientBar;
import com.coder.zzq.smartshow.snackbar.custom.Snackbar;

/**
 * Created by 朱志强 on 2017/11/15.
 */

public final class SmartSnackbar extends Snackbar.Callback implements SnackbarSetting, SnackbarShow, View.OnClickListener, Runnable {
    private static SmartSnackbar sSmartSnackbar;

    private static boolean sDismissOnLeave;

    private Context mAppContext;
    private Context mPageContext;
    private Snackbar mSnackbar;
    private CharSequence mCurMsg;
    private CharSequence mCurActionText;
    private View.OnClickListener mOnActionClickListener;
    private int mDuration;

    private View mBaseTraceView;


    @ColorInt
    private int mBgColor;
    private boolean mSameBgWhenTop;
    @ColorInt
    private int mMsgColor;
    private int mMsgTextSizeSp;
    @ColorInt
    private int mActionColor;
    private int mActionSizeSp;

    private ProcessSnackbarCallback mProcessViewCallback;
    private String mDefaultActionTextForIndefinite = "确定";


    private SmartSnackbar(Context context) {
        if (context == null) {
            throw new NullPointerException("初始化SmartSnackbar的Context不可以为null！");
        }
        mAppContext = context.getApplicationContext();
        mCurMsg = "";
        mCurActionText = "";

        mBgColor = -1;
        mBgColor = -1;
        mMsgColor = -1;
        mMsgTextSizeSp = -1;
        mActionColor = -1;
        mActionSizeSp = -1;
    }

    private static SmartSnackbar getSmartSnackbar(Context context) {
        if (sSmartSnackbar == null) {
            sSmartSnackbar = new SmartSnackbar(context);
        }
        return sSmartSnackbar;
    }

    public static SnackbarSetting init(Context context) {
        return getSmartSnackbar(context);
    }


    public static SnackbarShow get() {
        //保存当前页面的Context
        Activity activity = ActivityStack.getTop();

        getSmartSnackbar(SmartShow.getContext()).mPageContext = activity;

        //取出android.R.id.content

        View view = activity.findViewById(android.R.id.content);

        return getFromView(view);
    }

    public static SnackbarShow get(CoordinatorLayout view) {
        //保存当前页面的Context
        getSmartSnackbar(SmartShow.getContext()).mPageContext = view.getContext();
        return getFromView(view);
    }

    private static SnackbarShow getFromView(View view) {
        /*

        如果Snackbar尚未创建创建，则创建之

        mBaseTraceView 用来保存创建Snackbar的View，由于入口做了控制，所以只可能是android.R.id.content

        或者CoordinatorLayout。它发生变化包括两种情形：1.同一页面，本次获取方式与上次不同，导致容纳Snackbar

        的容器改变 2.进入新的页面，两种情况都需要重建Snackbar

         */
        if (sSmartSnackbar.mSnackbar == null || sSmartSnackbar.mBaseTraceView != view || sSmartSnackbar.mSnackbar.getView().getVisibility() != View.VISIBLE) {
            sSmartSnackbar.rebuildSnackbar(view);
        }
        return sSmartSnackbar;
    }


    private void rebuildSnackbar(View view) {
        mBaseTraceView = view;
        sSmartSnackbar.mSnackbar = Snackbar.make(mBaseTraceView, "", BaseTransientBar.LENGTH_SHORT);

        if (mPageContext instanceof SnackbarCallback) {
            sSmartSnackbar.mSnackbar.addCallback(this);
        }

        TextView msgView = (TextView) sSmartSnackbar.mSnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        if (mMsgColor != -1) {
            msgView.setTextColor(mMsgColor);
        }
        if (mMsgTextSizeSp != -1) {
            msgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mMsgTextSizeSp);
        }

        TextView actionView = (TextView) sSmartSnackbar.mSnackbar.getView().findViewById(android.support.design.R.id.snackbar_action);
        if (mActionColor != -1) {
            actionView.setTextColor(mActionColor);
        }
        if (mActionSizeSp != -1) {
            actionView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mActionSizeSp);
        }

        if (mProcessViewCallback != null) {
            mProcessViewCallback.processSnackbarView((Snackbar.SnackbarLayout) mSnackbar.getView(), msgView, actionView);
        }

    }


    public static void destroy(Activity activity) {

        if (sSmartSnackbar != null && sSmartSnackbar.mPageContext == activity) {

            sSmartSnackbar.mCurMsg = "";
            sSmartSnackbar.mCurActionText = "";
            sSmartSnackbar.mOnActionClickListener = null;

            sSmartSnackbar.mSnackbar = null;
            sSmartSnackbar.mPageContext = null;
            sSmartSnackbar.mBaseTraceView = null;
        }
    }

    public static void dismiss() {
        if (sSmartSnackbar != null && sSmartSnackbar.mSnackbar != null) {
            sSmartSnackbar.mSnackbar.dismiss();
        }
    }

    private void showHelper(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener, int duration, int pos) {
        msg = msg == null ? "" : msg;
        actionText = actionText == null ? "" : actionText;
        onActionClickListener = onActionClickListener == null ? this : onActionClickListener;


        boolean appearanceChanged = appearanceChanged(msg, actionText);
        boolean positionChanged = (pos != mSnackbar.getPos());
        setting(msg, actionText, onActionClickListener, duration);

        if (positionChanged && isShowing()) {
            dismiss();
            if (mBaseTraceView != null) {
                rebuildSnackbar(mBaseTraceView);
            }
        }

        mSnackbar.setPos(pos);

        adjustHeight(pos);

        adjustBg(pos);

        if (appearanceChanged && mSnackbar.isShown()) {
            //如果Snackbar正在显示，且内容发生了变化，先隐藏掉再显示，具有切换效果
            dismissAndShowAgain();
        } else {
            //如果Snackbar没有显示或者“样貌”没有发生改变，正常显示即可
            normalShow();
        }
    }

    private void adjustBg(int pos) {
        switch (pos){
            case Snackbar.POS_TOP:
                if (mSameBgWhenTop){
                    setBgWhenBottom();
                }else {
                    mSnackbar.getView().setBackgroundColor(getStatusBarColor());
                }
                break;
            case Snackbar.POS_BOTTOM:
                setBgWhenBottom();
                break;
        }
    }

    private int getStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            return ActivityStack.getTop().getWindow().getStatusBarColor();
        }else {
            return ContextCompat.getColor(mAppContext, R.color.colorPrimaryDark);
        }
    }

    private void setBgWhenBottom() {
        if (mBgColor != -1){
            mSnackbar.getView().setBackgroundColor(mBgColor);
        }else {
            mSnackbar.getView().setBackgroundResource(android.support.design.R.drawable.design_snackbar_background);
        }
    }

    private void adjustHeight(int pos) {
        ViewGroup.LayoutParams lp = mSnackbar.getView().getLayoutParams();
        lp.height = (pos == Snackbar.POS_TOP) ? dpToPx(60) : ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.toString();
    }

    private boolean appearanceChanged(CharSequence msg, CharSequence actionText) {
        return !mCurMsg.equals(msg) || !mCurActionText.equals(actionText);
    }

    //先隐藏再显示
    private void dismissAndShowAgain() {
        mSnackbar.dismiss();
        mBaseTraceView.postDelayed(this, 400);
    }

    //正常显示Snackbar
    private void normalShow() {
        mSnackbar.setText(mCurMsg).setAction(mCurActionText, mOnActionClickListener).setDuration(mDuration).show();
    }

    private void setting(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener, int duration) {
        mCurMsg = msg;
        mCurActionText = actionText;
        mOnActionClickListener = onActionClickListener;
        mDuration = duration;
    }

    @Override
    public void show(CharSequence msg) {
        show(msg, null, null);
    }

    @Override
    public void showAtTop(CharSequence msg) {
        showAtTop(msg, null, null);
    }

    @Override
    public void show(CharSequence msg, CharSequence actionText) {
        show(msg, actionText, null);
    }

    @Override
    public void showAtTop(CharSequence msg, CharSequence actionText) {
        showAtTop(msg, actionText, null);
    }

    @Override
    public void show(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg, actionText, onActionClickListener, Snackbar.LENGTH_SHORT, Snackbar.POS_BOTTOM);
    }

    @Override
    public void showAtTop(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg, actionText, onActionClickListener, Snackbar.LENGTH_SHORT, Snackbar.POS_TOP);
    }

    @Override
    public void showLong(CharSequence msg) {
        showLong(msg, null);
    }

    @Override
    public void showLongAtTop(CharSequence msg) {
        showLongAtTop(msg, null);
    }

    @Override
    public void showLong(CharSequence msg, CharSequence actionText) {
        showLong(msg, actionText, null);
    }

    @Override
    public void showLongAtTop(CharSequence msg, CharSequence actionText) {
        showLongAtTop(msg, actionText, null);
    }

    @Override
    public void showLong(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg, actionText, onActionClickListener, Snackbar.LENGTH_LONG, Snackbar.POS_BOTTOM);
    }

    @Override
    public void showLongAtTop(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg, actionText, onActionClickListener, Snackbar.LENGTH_LONG, Snackbar.POS_TOP);
    }

    @Override
    public void showIndefinite(CharSequence msg) {
        showIndefinite(msg, mDefaultActionTextForIndefinite);
    }

    @Override
    public void showIndefiniteAtTop(CharSequence msg) {
        showIndefiniteAtTop(msg, mDefaultActionTextForIndefinite);
    }

    @Override
    public void showIndefinite(CharSequence msg, CharSequence actionText) {
        showIndefinite(msg, actionText, null);
    }

    @Override
    public void showIndefiniteAtTop(CharSequence msg, CharSequence actionText) {
        showIndefiniteAtTop(msg, actionText, null);
    }

    @Override
    public void showIndefinite(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg, actionText, onActionClickListener, Snackbar.LENGTH_INDEFINITE, Snackbar.POS_BOTTOM);
    }

    @Override
    public void showIndefiniteAtTop(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg, actionText, onActionClickListener, Snackbar.LENGTH_INDEFINITE, Snackbar.POS_TOP);
    }


    @Override
    public SnackbarSetting backgroundColor(@ColorInt int color) {
        mBgColor = color;
        return this;
    }

    @Override
    public SnackbarSetting backgroundColorRes(@ColorRes int colorRes) {
        return backgroundColor(ContextCompat.getColor(mAppContext, colorRes));
    }

    @Override
    public SnackbarSetting sameBackgroundWhenTop(boolean same) {
        mSameBgWhenTop = same;
        return this;
    }

    @Override
    public SnackbarSetting msgTextColor(@ColorInt int color) {
        mMsgColor = color;
        return this;
    }

    @Override
    public SnackbarSetting msgTextColorRes(@ColorRes int colorRes) {
        return msgTextColor(ContextCompat.getColor(mAppContext, colorRes));
    }

    @Override
    public SnackbarSetting msgTextSizeSp(int textSizeSp) {
        mMsgTextSizeSp = textSizeSp;
        return this;
    }

    @Override
    public SnackbarSetting actionColor(@ColorInt int color) {
        mActionColor = color;
        return this;
    }

    @Override
    public SnackbarSetting actionColorRes(@ColorRes int colorRes) {
        return actionColor(ContextCompat.getColor(mAppContext, colorRes));
    }

    @Override
    public SnackbarSetting actionSizeSp(int textSizeSp) {
        mActionSizeSp = textSizeSp;
        return this;
    }

    @Override
    public SnackbarSetting defaultActionTextForIndefinite(String actionText) {
        mDefaultActionTextForIndefinite = actionText;
        return this;
    }

    @Override
    public SnackbarSetting dismissOnLeave(boolean b) {
        setDismissOnLeave(b);
        return this;
    }

    @Override
    public SnackbarSetting processView(ProcessSnackbarCallback callback) {
        mProcessViewCallback = callback;
        return this;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void run() {
        if (mSnackbar != null) {
            mSnackbar.setText(mCurMsg).setAction(mCurActionText, mOnActionClickListener).setDuration(mDuration).show();
        }
    }


    @Override
    public void onShown(Snackbar sb) {
        if (mPageContext != null && mPageContext instanceof SnackbarCallback) {
            ((SnackbarCallback) mPageContext).onSnackbarShown(sb);
        }
    }

    @Override
    public void onDismissed(Snackbar sb, int event) {
        if (mPageContext != null && mPageContext instanceof SnackbarCallback) {
            ((SnackbarCallback) mPageContext).onSnackbarDismissed(sb, event);
        }
    }


    public static boolean isShowing() {
        return sSmartSnackbar != null && sSmartSnackbar.mSnackbar != null && sSmartSnackbar.mSnackbar.isShown();
    }

    public static boolean isDismissOnLeave() {
        return sDismissOnLeave;
    }

    public static void setDismissOnLeave(boolean b) {
        sDismissOnLeave = b;
    }


    private int dpToPx(float dp){
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,mAppContext.getResources().getDisplayMetrics()));
    }
}
