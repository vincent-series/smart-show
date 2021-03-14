package com.coder.zzq.smartshow.toast.emotion;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

@CustomToast(alias = Constants.TOAST_TYPE_EMOTION)
public class EmotionToast {

    @ToastConfig
    public static class Config extends BaseToastConfig {

        public static final int EMOTION_TYPE_INFO = 0;
        public static final int EMOTION_TYPE_WARNING = 1;
        public static final int EMOTION_TYPE_SUCCESS = 2;
        public static final int EMOTION_TYPE_ERROR = 3;
        public static final int EMOTION_TYPE_FAIL = 4;
        public static final int EMOTION_TYPE_COMPLETE = 5;
        public static final int EMOTION_TYPE_FORBID = 6;
        public static final int EMOTION_TYPE_WAITING = 7;

        @Retention(RetentionPolicy.SOURCE)
        @IntDef({EMOTION_TYPE_INFO, EMOTION_TYPE_WARNING, EMOTION_TYPE_SUCCESS, EMOTION_TYPE_ERROR,
                EMOTION_TYPE_FAIL, EMOTION_TYPE_COMPLETE, EMOTION_TYPE_FORBID, EMOTION_TYPE_WAITING})
        @interface EmotionType {
        }

        @EmotionType
        public int mEmotionType = EMOTION_TYPE_INFO;

        @ColorInt
        public int mBackgroundColor = Constants.DEFAULT_VALUE;
        @DrawableRes
        public int mIcon = Constants.DEFAULT_VALUE;
        public int mIconSize = Constants.DEFAULT_VALUE;
        public int mIconPadding = Utils.dpToPx(10);

        public int mMsgColor = Color.WHITE;
        public float mMsgSizeSp = 14;
        public boolean mMsgBold = false;
    }

    @ToastView
    public static View provideToastView(View rootView, LayoutInflater inflater, Config config) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.smart_show_emotion_toast, null);
        }

        int bgColor = config.mBackgroundColor != Constants.DEFAULT_VALUE ? config.mBackgroundColor : Constants.DEFAULT_TOAST_BACKGROUND_COLOR;

        Drawable bg = rootView.getBackground();
        bg.mutate();
        DrawableCompat.setTint(bg, bgColor);

        ImageView iconView = rootView.findViewById(R.id.emotion_icon);

        int iconResource = config.mIcon != Constants.DEFAULT_VALUE ?
                config.mIcon
                : parseDefaultIconResource(config.mEmotionType);
        Drawable icon = Utils.getDrawableFromRes(iconResource);

        if (icon != null){
            int iconSize = config.mIconSize != Constants.DEFAULT_VALUE ?
                    config.mIconSize
                    : parseDefaultIconSize(config, icon, config.mIcon == Constants.DEFAULT_VALUE);

            iconView.getLayoutParams().width = iconView.getLayoutParams().height = iconSize;
            icon.setBounds(0, 0, iconSize, iconSize);
        }
        iconView.setImageDrawable(icon);
        ((ViewGroup.MarginLayoutParams) iconView.getLayoutParams()).bottomMargin = config.mIconPadding;

        TextView msgView = rootView.findViewById(R.id.emotion_message);
        msgView.setText(config.mMsg);
        msgView.setTextSize(config.mMsgSizeSp);
        msgView.setTextColor(config.mMsgColor);
        msgView.getPaint().setFakeBoldText(config.mMsgBold);

        return rootView;
    }

    private static int parseDefaultIconSize(Config config, Drawable icon, boolean defaultIcon) {
        return defaultIcon ? Utils.dpToPx(30) : (icon.getIntrinsicWidth() == -1 ? Math.round(config.mMsgSizeSp) * 2 : Math.max(icon.getIntrinsicWidth(), icon.getIntrinsicHeight()));
    }

    private static int parseDefaultIconResource(final int emotionType) {
        switch (emotionType) {
            case Config.EMOTION_TYPE_INFO:
                return R.drawable.ic_smart_toast_emotion_info;
            case Config.EMOTION_TYPE_WARNING:
                return R.drawable.ic_smart_toast_emotion_warning;
            case Config.EMOTION_TYPE_SUCCESS:
                return R.drawable.ic_smart_toast_emotion_success;
            case Config.EMOTION_TYPE_ERROR:
                return R.drawable.ic_smart_toast_emotion_error;
            case Config.EMOTION_TYPE_FORBID:
                return R.drawable.ic_smart_toast_emotion_forbid;
            case Config.EMOTION_TYPE_WAITING:
                return R.drawable.ic_smart_toast_emotion_wait;
            case Config.EMOTION_TYPE_COMPLETE:
                return R.drawable.ic_smart_toast_emotion_complete;
            case Config.EMOTION_TYPE_FAIL:
                return R.drawable.ic_smart_toast_emotion_fail;
        }
        return 0;
    }

}

