package com.coder.zzq.smartshow.dialog;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coder.zzq.toolkit.Utils;

public class LoadingDialog extends NormalDialog<LoadingDialog> {
    private static final int SIZE_MODE_LARGE = 0;
    private static final int SIZE_MIDDLE = 1;
    private static final int SIZE_SMALL = 2;

    private static final int HEIGHT_LARGE_WITH_MSG = Utils.dpToPx(110);
    private static final int MIN_WIDTH_LARGE_WITH_MSG = Utils.dpToPx(120);
    private static final int HEIGHT_LARGE_NO_MSG = Utils.dpToPx(90);
    private static final int WIDTH_LARGE_NO_MSG = Utils.dpToPx(95);
    private static final int TEXT_SIZE_LARGE = 15;
    private static final int MSG_TOP_PADDING_LARGE = Utils.dpToPx(12);

    private static final int HEIGHT_MIDDLE_WIDTH_MSG = Utils.dpToPx(80);
    private static final int MIN_WIDTH_MIDDLE_WITH_MSG = Utils.dpToPx(85);
    private static final int HEIGHT_MIDDLE_NO_MSG = Utils.dpToPx(60);
    private static final int WIDTH_MIDDLE_NO_MSG = HEIGHT_MIDDLE_NO_MSG;
    private static final int TEXT_SIZE_MIDDLE = 12;
    private static final int MSG_TOP_PADDING_MIDDLE = Utils.dpToPx(8);

    private static final int HEIGHT_AND_WITH_SMALL = Utils.dpToPx(36);

    private static final int PROGRESS_BAR_SIZE_LARGE = Utils.dpToPx(31);
    private static final int PROGRESS_BAR_SIZE_MIDDLE = Utils.dpToPx(24);
    private static final int PROGRESS_BAR_SIZE_SMALL = Utils.dpToPx(20);


    protected CharSequence mMsg = "正在加载";
    protected int mSize = SIZE_MODE_LARGE;
    private boolean mWithMsg = true;
    protected RelativeLayout mLoadingPartView;
    protected TextView mMsgView;
    private ProgressBar mLoadingProgressBar;

    public LoadingDialog large() {
        mSize = SIZE_MODE_LARGE;
        applySize(mNestedDialog);
        return this;
    }

    public LoadingDialog middle() {
        mSize = SIZE_MIDDLE;
        applySize(mNestedDialog);
        return this;
    }

    public LoadingDialog small() {
        mSize = SIZE_SMALL;
        applySize(mNestedDialog);
        applyWithMsg(mNestedDialog);
        return this;
    }

    public LoadingDialog message(CharSequence msg) {
        mMsg = msg;
        applyMsg(mNestedDialog);
        return this;
    }

    protected void applyMsg(AppCompatDialog dialog) {
        if (dialog != null) {
            mMsgView.setText(mMsg);
        }
    }

    public LoadingDialog message(@StringRes int msgRes) {
        return message(Utils.getStringFromRes(msgRes));
    }

    public LoadingDialog withMsg(boolean with) {
        mWithMsg = with;
        applyWithMsg(mNestedDialog);
        return this;
    }

    protected void applyWithMsg(AppCompatDialog dialog) {
        if (dialog != null) {
            mMsgView.setVisibility(mSize != SIZE_SMALL && mWithMsg ? View.VISIBLE : View.GONE);
        }
    }

    public LoadingDialog() {
        mCancelableOnTouchOutside = false;
        mDarkAroundWhenShow = false;
        mWindowBackground = R.drawable.smart_show_loading_bg;
    }

    @Override
    protected int provideContentLayout() {
        return R.layout.smart_show_loading_dialog;
    }

    @Override
    protected void initView(AppCompatDialog dialog, View contentView) {
        super.initView(dialog, contentView);
        mLoadingPartView = contentView.findViewById(R.id.smart_show_loading_part);
        mMsgView = contentView.findViewById(R.id.smart_show_loading_message_view);
        mMsgView.setMaxWidth(Math.min(Utils.screenWidth() - Utils.dpToPx(110), Utils.dpToPx(250)));
        mLoadingProgressBar = contentView.findViewById(R.id.smart_show_loading_progress_bar);
    }

    @Override
    protected void applySetting(AppCompatDialog dialog) {
        super.applySetting(dialog);
        applyMsg(dialog);
        applyWithMsg(dialog);
        applySize(dialog);
    }

    protected void applySize(AppCompatDialog dialog) {
        if (dialog == null) {
            return;
        }
        FrameLayout.LayoutParams loadingPartViewLp = (FrameLayout.LayoutParams) mLoadingPartView.getLayoutParams();
        LinearLayout.LayoutParams progressbarLp = (LinearLayout.LayoutParams) mLoadingProgressBar.getLayoutParams();
        int msgTextSize = TEXT_SIZE_LARGE;
        int msgTopPadding = MSG_TOP_PADDING_LARGE;
        int minWidth = MIN_WIDTH_LARGE_WITH_MSG;
        switch (mSize) {
            case SIZE_MODE_LARGE:
                loadingPartViewLp.height = mWithMsg ? HEIGHT_LARGE_WITH_MSG : HEIGHT_LARGE_NO_MSG;
                loadingPartViewLp.width = mWithMsg ? FrameLayout.LayoutParams.WRAP_CONTENT : WIDTH_LARGE_NO_MSG;
                progressbarLp.height = progressbarLp.width = PROGRESS_BAR_SIZE_LARGE;
                minWidth = mWithMsg ? MIN_WIDTH_LARGE_WITH_MSG : 0;
                msgTextSize = TEXT_SIZE_LARGE;
                msgTopPadding = MSG_TOP_PADDING_LARGE;
                break;
            case SIZE_MIDDLE:
                loadingPartViewLp.height = mWithMsg ? HEIGHT_MIDDLE_WIDTH_MSG : HEIGHT_MIDDLE_NO_MSG;
                loadingPartViewLp.width = mWithMsg ? FrameLayout.LayoutParams.WRAP_CONTENT : WIDTH_MIDDLE_NO_MSG;
                progressbarLp.height = progressbarLp.width = PROGRESS_BAR_SIZE_MIDDLE;
                minWidth = mWithMsg ? MIN_WIDTH_MIDDLE_WITH_MSG : 0;
                msgTextSize = TEXT_SIZE_MIDDLE;
                msgTopPadding = MSG_TOP_PADDING_MIDDLE;
                break;
            case SIZE_SMALL:
                loadingPartViewLp.height = loadingPartViewLp.width = HEIGHT_AND_WITH_SMALL;
                mLoadingPartView.setMinimumWidth(0);
                progressbarLp.height = progressbarLp.width = PROGRESS_BAR_SIZE_SMALL;
                break;
        }
        mLoadingPartView.setMinimumWidth(minWidth);
        mMsgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, msgTextSize);
        mMsgView.setPadding(mMsgView.getPaddingLeft(), msgTopPadding,
                mMsgView.getPaddingRight(), mMsgView.getPaddingBottom());
        mLoadingPartView.setLayoutParams(loadingPartViewLp);
        mLoadingProgressBar.setLayoutParams(progressbarLp);
    }

    @Override
    protected int provideDialogStyle() {
        return R.style.smart_show_dialog;
    }

    @Override
    protected int provideDialogWidth() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}
