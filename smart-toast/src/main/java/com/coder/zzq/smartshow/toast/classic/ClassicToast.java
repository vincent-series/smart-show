package com.coder.zzq.smartshow.toast.classic;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.core.graphics.drawable.DrawableCompat;

import com.coder.zzq.smart_show.annotations.CustomToast;
import com.coder.zzq.smart_show.annotations.ToastConfig;
import com.coder.zzq.smart_show.annotations.ToastView;
import com.coder.zzq.smartshow.toast.Constants;
import com.coder.zzq.smartshow.toast.R;
import com.coder.zzq.smartshow.toast.factory.BaseToastConfig;
import com.coder.zzq.toolkit.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@CustomToast(alias = Constants.TOAST_TYPE_CLASSIC)
public class ClassicToast {

    @ToastConfig(withPrefix_m = true)
    public static class Config extends BaseToastConfig {

        public static final int ICON_POSITION_LEFT = 0;
        public static final int ICON_POSITION_RIGHT = 1;

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({ICON_POSITION_LEFT, ICON_POSITION_RIGHT})
        @interface IconPosition {
        }

        @DrawableRes
        public int mBackgroundResource = Constants.DEFAULT_VALUE;
        @ColorInt
        public int mBackgroundColor = Constants.DEFAULT_VALUE;
        public float mMsgSize = 14;
        @ColorInt
        public int mMsgColor = Color.WHITE;
        public boolean mMsgBold = false;
        @DrawableRes
        public int mIcon = Constants.DEFAULT_VALUE;
        @IconPosition
        public int mIconPosition = ICON_POSITION_LEFT;
        public int mIconSizeDp = Constants.DEFAULT_VALUE;
        public int mIconPaddingDp = Constants.DEFAULT_VALUE;
    }

    @ToastView
    public static View provideToastView(View rootView, LayoutInflater inflater, Config config) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.smart_show_classic_toast, null);
        }

        if (config.mBackgroundResource != Constants.DEFAULT_VALUE) {
            rootView.setBackgroundResource(config.mBackgroundResource);
        }

        if (config.mBackgroundColor != Constants.DEFAULT_VALUE) {
            Drawable bg = rootView.getBackground().mutate();
            bg.mutate();
            DrawableCompat.setTint(bg, config.mBackgroundColor);
        }

        TextView msgView = rootView.findViewById(R.id.smart_toast_message);
        msgView.setText(config.mMsg);
        msgView.setTextColor(config.mMsgColor);
        msgView.setTextSize(config.mMsgSize);
        msgView.getPaint().setFakeBoldText(config.mMsgBold);


        if (config.mIcon != Constants.DEFAULT_VALUE) {
            Drawable icon = Utils.getDrawableFromRes(config.mIcon);
            if (config.mIconSizeDp != Constants.DEFAULT_VALUE) {
                icon.setBounds(0, 0, config.mIconSizeDp, config.mIconSizeDp);
            }
            msgView.setCompoundDrawables(
                    config.mIconPosition == Config.ICON_POSITION_LEFT ? icon : null,
                    null,
                    config.mIconPosition == Config.ICON_POSITION_RIGHT ? icon : null,
                    null
            );

            if (config.mIconPaddingDp != Constants.DEFAULT_VALUE) {
                msgView.setCompoundDrawablePadding(config.mIconPaddingDp);
            }
        }

        return rootView;
    }
}
