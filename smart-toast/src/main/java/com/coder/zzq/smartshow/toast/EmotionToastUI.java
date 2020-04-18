package com.coder.zzq.smartshow.toast;

import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coder.zzq.toolkit.Toolkit;

import static com.coder.zzq.smartshow.toast.UIArguments.ARGUMENT_BACKGROUND_COLOR;
import static com.coder.zzq.smartshow.toast.UIArguments.ARGUMENT_DEFAULT_ICON;
import static com.coder.zzq.smartshow.toast.UIArguments.ARGUMENT_ICON;
import static com.coder.zzq.smartshow.toast.UIArguments.ARGUMENT_TEXT_COLOR;
import static com.coder.zzq.smartshow.toast.UIArguments.ARGUMENT_TEXT_SIZE_SP;

class EmotionToastUI extends ToastUI {
    @Override
    protected View createUI(CharSequence msg, UIArguments arguments) {
        View toastView = LayoutInflater.from(Toolkit.getContext()).inflate(R.layout.layout_emotion_toast, null);
        TextView msgView = toastView.findViewById(R.id.type_info_message);
        ImageView iconView = toastView.findViewById(R.id.type_info_icon);
        msgView.setText(msg);
        if (arguments != null && !arguments.isEmpty()) {
            Object iconArg = arguments.getArg(ARGUMENT_ICON);
            if (iconArg != null) {
                iconView.setImageResource((int) iconArg);
            } else {
                iconView.setImageResource((int) arguments.getArg(ARGUMENT_DEFAULT_ICON));
            }
            Object backgroundColorArg = arguments.getArg(ARGUMENT_BACKGROUND_COLOR);
            if (backgroundColorArg != null) {
                GradientDrawable bg = (GradientDrawable) toastView.getBackground();
                bg.setColor((int) backgroundColorArg);
            }
            Object textColorArg = arguments.getArg(ARGUMENT_TEXT_COLOR);
            if (textColorArg != null) {
                msgView.setTextColor((int) textColorArg);
            }
            Object textSizeSpArg = arguments.getArg(ARGUMENT_TEXT_SIZE_SP);

            if (textSizeSpArg != null) {
                msgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (Float) textSizeSpArg);
            }
        }
        return toastView;
    }
}
