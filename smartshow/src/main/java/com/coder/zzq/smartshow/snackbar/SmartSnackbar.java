package com.coder.zzq.smartshow.snackbar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/15.
 */

public class SmartSnackbar extends Snackbar.Callback implements SnackbarSetting,SnackbarShow, View.OnClickListener, Runnable {
    private static SmartSnackbar sSmartSnackbar;
    private Context mAppContext;
    private Snackbar mSnackbar;
    private Context mPageContext;
    private CharSequence mCurMsg;
    private CharSequence mCurActionText;
    private View.OnClickListener mOnActionClickListener;
    private int mDuration;

    private View mBaseTraceView;


    @ColorInt
    private int mBgColor;
    @ColorInt
    private int mMsgColor;
    private int mMsgTextSizeSp;
    @ColorInt
    private int mActionColor;
    private int mActionSizeSp;

    private SmartSnackbar(Context context) {
        if (context == null) {
            throw new NullPointerException("初始化SmartSnackbar的Context不可以为null！");
        }
        mAppContext = context.getApplicationContext();
        mCurMsg = "";
        mCurActionText = "";

        mBgColor = -1;
        mMsgColor = -1;
        mMsgTextSizeSp = -1;
        mActionColor = -1;
        mActionSizeSp = -1;
    }

    private static SmartSnackbar getSmartSnackbar(Context context) {
          if (sSmartSnackbar == null){
              sSmartSnackbar = new SmartSnackbar(context);
          }
          return sSmartSnackbar;
    }

    public static SnackbarSetting init(Context context) {
        return getSmartSnackbar(context);
    }


    public static SnackbarShow get(Activity activity){
        getSmartSnackbar(activity).mPageContext = activity;
        View view = activity.findViewById(android.R.id.content);
        return get(view);
    }

    public static SnackbarShow get(CoordinatorLayout view){
        getSmartSnackbar(view.getContext()).mPageContext = view.getContext();
        return get(view);
    }

    private static SnackbarShow get(View view){
        if (sSmartSnackbar.mSnackbar == null || sSmartSnackbar.mBaseTraceView != view){
            sSmartSnackbar.rebuildSnackbar(view);
        }
        return sSmartSnackbar;
    }


    private void rebuildSnackbar(View view) {
        mCurMsg = "";
        mCurActionText = "";
        mBaseTraceView = view;
        sSmartSnackbar.mSnackbar = Snackbar.make(mBaseTraceView,mCurMsg,Snackbar.LENGTH_SHORT);

        if (mPageContext instanceof SnackbarCallback){
            sSmartSnackbar.mSnackbar.addCallback(this);
        }

        if (mBgColor != -1){
            sSmartSnackbar.mSnackbar.getView().setBackgroundColor(mBgColor);
        }
        TextView msgView = (TextView) sSmartSnackbar.mSnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        if (mMsgColor != -1){
            msgView.setTextColor(mMsgColor);
        }
        if (mMsgTextSizeSp != -1){
            msgView.setTextSize(TypedValue.COMPLEX_UNIT_SP,mMsgTextSizeSp);
        }

        TextView actionView = (TextView) sSmartSnackbar.mSnackbar.getView().findViewById(android.support.design.R.id.snackbar_action);
        if (mActionColor != -1){
            actionView.setTextColor(mActionColor);
        }
        if (mActionSizeSp != -1){
            actionView.setTextSize(TypedValue.COMPLEX_UNIT_SP,mActionSizeSp);
        }
    }


    public static void destroy(Activity activity){
        if (sSmartSnackbar != null && sSmartSnackbar.mPageContext == activity){

            sSmartSnackbar.mCurMsg = "";
            sSmartSnackbar.mCurActionText = "";
            sSmartSnackbar.mOnActionClickListener = null;

            sSmartSnackbar.mSnackbar = null;
            sSmartSnackbar.mPageContext = null;
            sSmartSnackbar.mBaseTraceView = null;
        }
    }

    private void showHelper(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener,int duration){

        msg = msg == null ? "" : msg;
        actionText = actionText == null ? "" : actionText;
        onActionClickListener = onActionClickListener == null ? this : onActionClickListener;

        boolean appearanceChanged = appearanceChanged(msg,actionText);
        setting(msg, actionText, onActionClickListener, duration);

        if (appearanceChanged && mSnackbar.isShown()){
            //如果Snackbar正在显示，且内容发生了变化，先隐藏掉再显示，具有切换效果
            dismissAndShowAgain();
        }else {
            //如果Snackbar没有显示或者“样貌”没有发生改变，正常显示即可
            normalShow();
        }
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
        show(msg,null,null);
    }

    @Override
    public void show(CharSequence msg, CharSequence actionText) {
        show(msg,actionText,null);
    }

    @Override
    public void show(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg,actionText,onActionClickListener,Snackbar.LENGTH_SHORT);
    }

    @Override
    public void showLong(CharSequence msg) {
        showLong(msg,null);
    }

    @Override
    public void showLong(CharSequence msg, CharSequence actionText) {
        showLong(msg,actionText,null);
    }

    @Override
    public void showLong(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg,actionText,onActionClickListener,Snackbar.LENGTH_LONG);
    }

    @Override
    public void showIndefinite(CharSequence msg) {
        showIndefinite(msg,null);
    }

    @Override
    public void showIndefinite(CharSequence msg, CharSequence actionText) {
        showIndefinite(msg,actionText,null);
    }

    @Override
    public void showIndefinite(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg,actionText,onActionClickListener,Snackbar.LENGTH_INDEFINITE);
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
    public SnackbarSetting msgTextColor(@ColorInt int color) {
        mMsgColor = color;
        return this;
    }

    @Override
    public SnackbarSetting msgTextColorRes(@ColorRes int colorRes) {
        return msgTextColor(ContextCompat.getColor(mAppContext,colorRes));
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
        return actionColor(ContextCompat.getColor(mAppContext,colorRes));
    }

    @Override
    public SnackbarSetting actionSizeSp(int textSizeSp) {
        mActionSizeSp = textSizeSp;
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
        if (mPageContext != null && mPageContext instanceof SnackbarCallback){
            ((SnackbarCallback) mPageContext).onSnackbarShown(sb);
        }
    }

    @Override
    public void onDismissed(Snackbar sb, int event) {
        if (mPageContext != null && mPageContext instanceof SnackbarCallback){
            ((SnackbarCallback) mPageContext).onSnackbarDismissed(sb,event);
        }
    }
}
