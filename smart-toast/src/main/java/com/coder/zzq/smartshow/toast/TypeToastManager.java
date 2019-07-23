package com.coder.zzq.smartshow.toast;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.RestrictTo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.coder.zzq.smartshow.core.EasyLogger;
import com.coder.zzq.smartshow.core.SmartShow;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class TypeToastManager extends BaseToastManager implements ITypeShow {

    public static final int ICON_DEFAULT = 0;

    public static final int TYPE_INFO_NORMAL = 0;
    public static final int TYPE_INFO_WARNING = 1;
    public static final int TYPE_INFO_SUCCESS = 2;
    public static final int TYPE_INFO_ERROR = 3;
    public static final int TYPE_INFO_FAIL = 4;
    public static final int TYPE_INFO_COMPLETE = 5;
    public static final int TYPE_INFO_FORBID = 6;
    public static final int TYPE_INFO_WAITING = 7;

    private int mCurInfoType;
    private ImageView mIconView;
    private int mCurIcon;

    public TypeToastManager() {
        super();
    }

    @Override
    protected Toast createToast() {
        mToast = new Toast(SmartShow.getContext());
        mView = LayoutInflater.from(SmartShow.getContext()).inflate(R.layout.layout_type_info, null);
        mMsgView = mView.findViewById(R.id.type_info_message);
        mIconView = mView.findViewById(R.id.type_info_icon);
        mToast.setView(mView);
        return mToast;
    }


    @Override
    protected void updateToast() {
        super.updateToast();
        mIconView.setImageResource(mCurIcon);
    }

    @Override
    protected void initSetup() {
        super.initSetup();
        mCurInfoType = TYPE_INFO_NORMAL;
        mCurIcon = R.drawable.type_info_normal;
        mWindowParams.windowAnimations = R.style.type_info_toast_anim;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected void applySetting() {
        super.applySetting();
        if (!ToastDelegate.get().hasTypeSetting()) {
            return;
        }

        int color = ToastDelegate.get().getTypeSetting().getThemeColor();
        if (color != 0) {
            GradientDrawable drawable = (GradientDrawable) mView.getBackground();
            drawable.setColor(color);
        }

    }

    @Override
    protected int getToastType() {
        return TYPE_TOAST;
    }

    private final void showHelper(CharSequence msg, int infoType, int duration) {
        showHelper(msg, infoType, ICON_DEFAULT, duration);
    }

    private final void showHelper(CharSequence msg, int infoType, @DrawableRes int iconRes, int duration) {
        ToastDelegate.get().dismissPlainShowIfNeed();
        msg = (msg == null) ? "" : msg;
        //文本是否改变
        boolean contentChanged = !mCurMsg.equals(msg.toString().trim());

        //类型是否改变
        boolean typeChanged = mCurInfoType != infoType;

        iconRes = iconRes == ICON_DEFAULT ? getIcon(infoType) : iconRes;

        boolean iconChanged = mCurIcon != iconRes;

        EasyLogger.d("typeChanged:" + typeChanged + ",iconChanged:" + iconChanged +
                ",contentChanged:" + contentChanged);

        boolean needInvokeShow = !isShowing();
        if (isShowing() && (contentChanged || typeChanged || iconChanged)) {
            dismiss();
            needInvokeShow = true;
        }

        mCurMsg = msg;
        mDuration = duration;
        mCurInfoType = infoType;
        mCurIcon = iconRes;

        updateToast();

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(mDuration);
        needInvokeShow = needInvokeShow && !isShortInterval();
        if (needInvokeShow) {
            showToast();
        }
    }

    private final int getIcon(int infoType) {
        switch (infoType) {
            case TYPE_INFO_NORMAL:
                return R.drawable.type_info_normal;
            case TYPE_INFO_WARNING:
                return R.drawable.type_info_warning;
            case TYPE_INFO_SUCCESS:
                return R.drawable.type_info_success;
            case TYPE_INFO_ERROR:
                return R.drawable.type_info_error;
            case TYPE_INFO_FAIL:
                return R.drawable.type_info_fail;
            case TYPE_INFO_COMPLETE:
                return R.drawable.type_info_complete;
            case TYPE_INFO_WAITING:
                return R.drawable.type_info_waiting;
            case TYPE_INFO_FORBID:
                return R.drawable.type_info_forbid;
            default:
                return R.drawable.type_info_normal;
        }
    }

    @Override
    public void info(CharSequence msg) {
        showHelper(msg, TYPE_INFO_NORMAL, Toast.LENGTH_SHORT);
    }

    @Override
    public void info(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_NORMAL, iconRes, Toast.LENGTH_SHORT);
    }

    @Override
    public void infoLong(CharSequence msg) {
        showHelper(msg, TYPE_INFO_NORMAL, Toast.LENGTH_LONG);
    }

    @Override
    public void infoLong(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_NORMAL, iconRes, Toast.LENGTH_LONG);
    }

    @Override
    public void warning(CharSequence msg) {
        showHelper(msg, TYPE_INFO_WARNING, Toast.LENGTH_SHORT);
    }

    @Override
    public void warning(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_WARNING, iconRes, Toast.LENGTH_SHORT);
    }

    @Override
    public void warningLong(CharSequence msg) {
        showHelper(msg, TYPE_INFO_WARNING, Toast.LENGTH_LONG);
    }

    @Override
    public void warningLong(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_WARNING, iconRes, Toast.LENGTH_LONG);
    }

    @Override
    public void success(CharSequence msg) {
        showHelper(msg, TYPE_INFO_SUCCESS, Toast.LENGTH_SHORT);
    }

    @Override
    public void success(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_SUCCESS, iconRes, Toast.LENGTH_SHORT);
    }

    @Override
    public void successLong(CharSequence msg) {
        showHelper(msg, TYPE_INFO_SUCCESS, Toast.LENGTH_LONG);
    }

    @Override
    public void successLong(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_SUCCESS, iconRes, Toast.LENGTH_LONG);
    }

    @Override
    public void error(CharSequence msg) {
        showHelper(msg, TYPE_INFO_ERROR, Toast.LENGTH_SHORT);
    }

    @Override
    public void error(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_ERROR, iconRes, Toast.LENGTH_SHORT);
    }

    @Override
    public void errorLong(CharSequence msg) {
        showHelper(msg, TYPE_INFO_ERROR, Toast.LENGTH_LONG);
    }

    @Override
    public void errorLong(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_ERROR, iconRes, Toast.LENGTH_LONG);
    }

    @Override
    public void fail(CharSequence msg) {
        showHelper(msg, TYPE_INFO_FAIL, Toast.LENGTH_SHORT);
    }

    @Override
    public void fail(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_FAIL, iconRes, Toast.LENGTH_SHORT);
    }

    @Override
    public void failLong(CharSequence msg) {
        showHelper(msg, TYPE_INFO_FAIL, Toast.LENGTH_LONG);
    }

    @Override
    public void failLong(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_FAIL, iconRes, Toast.LENGTH_LONG);
    }

    @Override
    public void complete(CharSequence msg) {
        showHelper(msg, TYPE_INFO_COMPLETE, Toast.LENGTH_SHORT);
    }

    @Override
    public void complete(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_COMPLETE, iconRes, Toast.LENGTH_SHORT);
    }

    @Override
    public void completeLong(CharSequence msg) {
        showHelper(msg, TYPE_INFO_COMPLETE, Toast.LENGTH_LONG);
    }

    @Override
    public void completeLong(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_COMPLETE, iconRes, Toast.LENGTH_LONG);
    }

    @Override
    public void forbid(CharSequence msg) {
        showHelper(msg, TYPE_INFO_FORBID, Toast.LENGTH_SHORT);
    }

    @Override
    public void forbid(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_FORBID, iconRes, Toast.LENGTH_SHORT);
    }

    @Override
    public void forbidLong(CharSequence msg) {
        showHelper(msg, TYPE_INFO_FORBID, Toast.LENGTH_LONG);
    }

    @Override
    public void forbidLong(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_FORBID, iconRes, Toast.LENGTH_LONG);
    }

    @Override
    public void waiting(CharSequence msg) {
        showHelper(msg, TYPE_INFO_WAITING, Toast.LENGTH_SHORT);
    }

    @Override
    public void waiting(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_WAITING, iconRes, Toast.LENGTH_SHORT);
    }

    @Override
    public void waitingLong(CharSequence msg) {
        showHelper(msg, TYPE_INFO_WAITING, Toast.LENGTH_LONG);
    }

    @Override
    public void waitingLong(CharSequence msg, int iconRes) {
        showHelper(msg, TYPE_INFO_WAITING, iconRes, Toast.LENGTH_LONG);
    }

}
