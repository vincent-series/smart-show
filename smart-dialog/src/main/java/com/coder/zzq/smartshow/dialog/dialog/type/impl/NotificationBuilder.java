package com.coder.zzq.smartshow.dialog.dialog.type.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ButtonBarLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.dialog.type.INotificationBuilder;


public class NotificationBuilder<B> extends NormalBuilder<B> implements INotificationBuilder<B> {
    protected TextView mTitleView;
    protected TextView mMessageView;

    public NotificationBuilder(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    public B message(CharSequence msg) {
        if (Utils.isEmpty(msg)) {
            return (B) this;
        }
        if (mMessageView == null) {
            View root = Utils.inflate(R.layout.default_message, null);
            mMessageView = root.findViewById(R.id.smart_show_default_message);
            setView(root);
        }
        mMessageView.setText(msg);
        return (B) this;
    }

    @Override
    public B confirmBtn(CharSequence label, DialogInterface.OnClickListener clickListener) {
        setPositiveButton(label, clickListener);
        return (B) this;
    }

    @Override
    public B confirmBtnTextStyle(int color, float textSizeSp, boolean bold) {

        return (B) this;
    }

    @Override
    public B delayConfirmSeconds(int seconds) {
        return (B) this;
    }

    @Override
    public B title(CharSequence title) {
        if (Utils.isEmpty(title)) {
            return (B) this;
        }
        if (mTitleView == null) {
            mTitleView = (TextView) Utils.inflate(R.layout.default_title, null);
            setCustomTitle(mTitleView);
        }
        mTitleView.setText(title);
        return (B) this;
    }

    @Override
    protected void onShowCallback(AlertDialog dialog) {
        super.onShowCallback(dialog);
        if (mMessageView != null) {
            mMessageView.setTranslationY(mTitleView == null ? Utils.dpToPx(7) : 0);
        }
        dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setVisibility(View.GONE);
        ViewGroup buttonPanel = dialog.getWindow().getDecorView().findViewById(android.support.v7.appcompat.R.id.buttonPanel);
        ButtonBarLayout buttonBarLayout = (ButtonBarLayout) buttonPanel.getChildAt(0);
        initButton(dialog, buttonBarLayout);
    }

    protected void initButton(AlertDialog dialog, ButtonBarLayout buttonBarLayout) {
        buttonBarLayout.setPadding(0, 0, 0, 0);
        for (int index = 0; index < buttonBarLayout.getChildCount(); index++) {
            View child = buttonBarLayout.getChildAt(index);
            if (child.getId() != android.R.id.button1 && child.getId() != android.R.id.button2) {
                child.setVisibility(View.GONE);
            }
        }
        setupButton(dialog, DialogInterface.BUTTON_POSITIVE);
    }

    protected void setupButton(AlertDialog dialog, int which) {
        Button btn = dialog.getButton(which);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        if (which == DialogInterface.BUTTON_POSITIVE) {
            btn.setTextColor(Utils.getColorFromRes(R.color.colorPrimary));
        }
        btn.setBackgroundResource(R.drawable.smart_show_dialog_btn_bg);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) btn.getLayoutParams();
        lp.width = 0;
        lp.topMargin = 0;
        lp.leftMargin = 0;
        lp.rightMargin = 0;
        lp.bottomMargin = 0;
        lp.height = Utils.dpToPx(44);
        lp.weight = 1;
    }
}
