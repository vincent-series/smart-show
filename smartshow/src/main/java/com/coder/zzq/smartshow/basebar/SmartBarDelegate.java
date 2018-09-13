package com.coder.zzq.smartshow.basebar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.RestrictTo;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coder.zzq.smartshow.lifecycle.ActivityStack;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public abstract class SmartBarDelegate<Bar, ViewParam, BarSetting extends BarSettingImpl> implements IBarShow, View.OnClickListener, Runnable {

    protected Context mPageContext;
    protected CharSequence mCurMsg;
    protected CharSequence mCurActionText;
    protected View.OnClickListener mOnActionClickListener;
    protected int mDuration;
    protected View mBaseTraceView;
    protected Bar mBar;
    protected BarSetting mBarSetting;


    protected SmartBarDelegate() {
        mCurMsg = "";
        mCurActionText = "";
    }

    protected IBarShow getFromView(View view) {
        if (mBar == null || mBaseTraceView != view || isDismissByGesture()) {
            mBaseTraceView = view;
            rebuildBar(view);
        }
        return this;
    }

    protected abstract boolean isDismissByGesture();


    private void rebuildBar(View view) {

        if (view == null) {
            return;
        }

        mBar = createBar(view);
        if (mPageContext instanceof IBarShowCallback) {
            setShowCallback();
        }


        if (hasBarSetting()) {
            TextView msgView = getMsgView();
            msgView.setTextColor(mBarSetting.getMsgColor());
            msgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBarSetting.getMsgTextSizeSp());
            Button actionView = getActionView();
            actionView.setTextColor(mBarSetting.getActionColor());
            actionView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBarSetting.getActionSizeSp());
            if (mBarSetting.getProcessBarCallback() != null) {
                mBarSetting.getProcessBarCallback().processBarView((ViewGroup) getBarView(), msgView, actionView);
            }
            setup();
        }

    }


    public abstract void setup();

    protected abstract ViewParam getBarView();

    protected abstract Button getActionView();

    protected abstract TextView getMsgView();


    protected abstract Bar createBar(View view);

    protected abstract void setShowCallback();


    public void destroy(Activity activity) {
        if (mPageContext == activity) {
            mCurMsg = "";
            mCurActionText = "";
            mOnActionClickListener = null;
            mBar = null;
            mPageContext = null;
            mBaseTraceView = null;
        }
    }


    private void showHelper(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener, int duration) {

        if (mBaseTraceView == null) {
            return;
        }

        msg = msg == null ? "" : msg;
        actionText = actionText == null ? "" : actionText;
        onActionClickListener = onActionClickListener == null ? this : onActionClickListener;

        boolean appearanceChanged = appearanceChanged(msg, actionText);

        setting(msg, actionText, onActionClickListener, duration);

        if ((isShowing() && appearanceChanged) || mBaseTraceView.getVisibility() != View.VISIBLE) {
            //如果Snackbar正在显示，且内容发生了变化，先隐藏掉再显示，具有切换效果
            dismissAndShowAgain();
        } else {
            //如果Snackbar没有显示或者“样貌”没有发生改变，正常显示即可
            normalShow();
        }

    }

    protected void dismissAndShowAgain() {
        dismiss();
        mBaseTraceView.postDelayed(this, 400);
    }

    @Override
    public void run() {
        normalShow();
    }


    private boolean appearanceChanged(CharSequence msg, CharSequence actionText) {
        return !mCurMsg.equals(msg) || !mCurActionText.equals(actionText);
    }

    //正常显示bar
    protected abstract void normalShow();

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
    public void show(CharSequence msg, CharSequence actionText) {
        show(msg, actionText, null);
    }


    @Override
    public void show(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg, actionText, onActionClickListener, getShortDuration());
    }


    @Override
    public void showLong(CharSequence msg) {
        showLong(msg, null);
    }


    @Override
    public void showLong(CharSequence msg, CharSequence actionText) {
        showLong(msg, actionText, null);
    }

    @Override
    public void showLong(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg, actionText, onActionClickListener, getLongDuration());
    }


    @Override
    public void showIndefinite(CharSequence msg) {
        showIndefinite(msg, hasBarSetting() ? ((BarSettingImpl) mBarSetting).getDefaultActionTextForIndefinite() : "确定");
    }


    @Override
    public void showIndefinite(CharSequence msg, CharSequence actionText) {
        showIndefinite(msg, actionText, null);
    }


    @Override
    public void showIndefinite(CharSequence msg, CharSequence actionText, View.OnClickListener onActionClickListener) {
        showHelper(msg, actionText, onActionClickListener, getIndefiniteDuration());
    }


    @Override
    public void onClick(View v) {

    }

    protected abstract int getShortDuration();

    protected abstract int getLongDuration();

    protected abstract int getIndefiniteDuration();


    public abstract boolean isShowing();

    public abstract void dismiss();


    public boolean isDismissOnLeave() {
        return mBarSetting != null && ((BarSettingImpl) mBarSetting).isDismissOnLeave();
    }


    public boolean hasBarSetting() {
        return mBarSetting != null;
    }

    public BarSetting barSetting() {
        if (mBarSetting == null) {
            createBarSetting();
        }
        return mBarSetting;
    }

    protected abstract void createBarSetting();
}
