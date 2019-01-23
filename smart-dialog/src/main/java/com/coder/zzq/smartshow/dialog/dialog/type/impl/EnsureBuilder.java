package com.coder.zzq.smartshow.dialog.dialog.type.impl;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ButtonBarLayout;
import android.view.ViewGroup;

import com.coder.zzq.smartshow.dialog.R;
import com.coder.zzq.smartshow.dialog.dialog.type.IEnsureBuilder;

public class EnsureBuilder extends MessageDialogCreator<IEnsureBuilder> implements IEnsureBuilder {

    public EnsureBuilder(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mButtonLayoutRes = R.layout.smart_show_default_double_btn;
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
    protected void customButtonLayout(AlertDialog dialog, ButtonBarLayout buttonBarLayout) {
        super.customButtonLayout(dialog, buttonBarLayout);
        setupButton(dialog, DialogInterface.BUTTON_NEGATIVE, buttonBarLayout, (ViewGroup) buttonBarLayout.findViewById(R.id.smart_show_dialog_cancel_btn));
    }
}
