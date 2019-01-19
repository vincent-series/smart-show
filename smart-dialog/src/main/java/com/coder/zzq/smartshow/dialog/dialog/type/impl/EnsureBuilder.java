package com.coder.zzq.smartshow.dialog.dialog.type.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ButtonBarLayout;
import android.widget.Button;
import android.widget.LinearLayout;

import com.coder.zzq.smartshow.core.Utils;
import com.coder.zzq.smartshow.dialog.dialog.type.IEnsureBuilder;

public class EnsureBuilder extends NotificationBuilder<IEnsureBuilder> implements IEnsureBuilder {

    public EnsureBuilder(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public IEnsureBuilder cancelBtn(CharSequence label, DialogInterface.OnClickListener clickListener) {
        setNegativeButton(label, clickListener);
        return this;
    }

    @Override
    public IEnsureBuilder cancelBtnTextStyle(int color, float textSizeSp, boolean bold) {
        return null;
    }

    @Override
    protected void initButton(AlertDialog dialog, ButtonBarLayout buttonBarLayout) {
        super.initButton(dialog, buttonBarLayout);
        setupButton(dialog, DialogInterface.BUTTON_NEGATIVE);
        buttonBarLayout.setBackgroundColor(Color.parseColor("#cccccc"));
    }

    @Override
    protected void setupButton(AlertDialog dialog, int which) {
        super.setupButton(dialog, which);
        Button btn = dialog.getButton(which);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) btn.getLayoutParams();
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                lp.leftMargin = Utils.dpToPx(0.25f);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                lp.rightMargin = Utils.dpToPx(0.25f);
                break;
        }
    }
}
