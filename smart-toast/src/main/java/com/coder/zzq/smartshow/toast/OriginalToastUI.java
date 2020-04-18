package com.coder.zzq.smartshow.toast;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;

import com.coder.zzq.toolkit.Toolkit;
import com.coder.zzq.toolkit.Utils;

import static com.coder.zzq.smartshow.toast.IOriginalToast.ICON_POSITION_LEFT;
import static com.coder.zzq.smartshow.toast.IOriginalToast.ICON_POSITION_RIGHT;
import static com.coder.zzq.smartshow.toast.UIArguments.ARGUMENT_BACKGROUND_COLOR;
import static com.coder.zzq.smartshow.toast.UIArguments.ARGUMENT_ICON;
import static com.coder.zzq.smartshow.toast.UIArguments.ARGUMENT_ICON_POSITION;
import static com.coder.zzq.smartshow.toast.UIArguments.ARGUMENT_TEXT_COLOR;
import static com.coder.zzq.smartshow.toast.UIArguments.ARGUMENT_TEXT_SIZE_SP;

class OriginalToastUI extends AbstractToastUI {

    @Override
    protected Toast createToast(CharSequence msg, UIArguments arguments) {
        Toast toast = Toast.makeText(Toolkit.getContext(), "", Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        TextView msgView = toast.getView().findViewById(android.R.id.message);
        msgView.setGravity(Gravity.CENTER);
        msgView.setText(msg);

        if (arguments != null && !arguments.isEmpty()) {
            Object backgroundColorArg = arguments.getArg(ARGUMENT_BACKGROUND_COLOR);
            if (backgroundColorArg != null) {
                int backgroundColor = (int) backgroundColorArg;
                Drawable bg = Utils.getDrawableFromRes(android.R.drawable.toast_frame);
                bg.mutate();
                if (bg instanceof GradientDrawable) {
                    ((GradientDrawable) bg).setColor(backgroundColor);
                } else {
                    DrawableCompat.setTint(bg, backgroundColor);
                }
                ViewCompat.setBackground(toast.getView(), bg);
            }
            Object iconArg = arguments.getArg(ARGUMENT_ICON);
            if (iconArg != null) {
                Object iconPositionArg = arguments.getArg(ARGUMENT_ICON_POSITION);
                int iconPosition = iconPositionArg == null ? ICON_POSITION_LEFT : (int) iconPositionArg;
                msgView.setCompoundDrawablePadding(Utils.dpToPx(8));
                Drawable iconDrawable = Utils.getDrawableFromRes((int) iconArg);
                iconDrawable.setBounds(0, 0, iconDrawable.getIntrinsicWidth(), iconDrawable.getIntrinsicHeight());
                msgView.setCompoundDrawables(iconPosition == ICON_POSITION_LEFT ? iconDrawable : null,
                        null, iconPosition == ICON_POSITION_RIGHT ? iconDrawable : null, null);
            }

            Object textColorArg = arguments.getArg(ARGUMENT_TEXT_COLOR);
            if (textColorArg != null) {
                msgView.setTextColor((int) textColorArg);
            }

            Object textSizeSpArg = arguments.getArg(ARGUMENT_TEXT_SIZE_SP);


            if (textSizeSpArg != null) {
                msgView.setTextSize(TypedValue.COMPLEX_UNIT_SP, (float) textSizeSpArg);
            }
        }

        return toast;
    }
}
