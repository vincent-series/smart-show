package com.coder.zzq.smartshow.toast;

import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;

import com.coder.zzq.smartshow.R;
import com.coder.zzq.smartshow.SmartShow;
import com.coder.zzq.smartshow.Utils;

public class TypeToastManager extends BaseToastManager implements ITypeShow {

    public static final int TYPE_INFO_NORMAL = 0;
    public static final int TYPE_INFO_WARNING = 1;
    public static final int TYPE_INFO_SUCCESS = 2;
    public static final int TYPE_INFO_ERROR = 3;

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
    protected void setupToast() {
        super.setupToast();
        mCurInfoType = TYPE_INFO_NORMAL;
        mCurIcon = R.drawable.type_info_normal;
        mWindowParams.windowAnimations = R.style.type_info_toast_anim;
        mWindowParams.height = Utils.dpToPx(85);
        if (SmartToastDelegate.get().hasToastSetting()
                && SmartToastDelegate.get().getToastSetting().isTypeInfoThemeColorSetup()) {
            GradientDrawable drawable = (GradientDrawable) mView.getBackground();
            drawable.setAlpha(238);
            drawable.setColor(SmartToastDelegate.get().toastSetting().getTypeInfoThemeColor());
        }
    }

    private final void showHelper(String msg, int infoType, int duration) {
        SmartToastDelegate.get().dismissPlainShowIfNeed();
        msg = (msg == null) ? "" : msg;
        //文本是否改变
        boolean contentChanged = !mCurMsg.equals(msg.trim());

        //类型是否改变
        boolean typeChanged = mCurInfoType != infoType;

        mCurMsg = msg;
        mDuration = duration;
        mCurInfoType = infoType;
        mCurIcon = getIcon(mCurInfoType);

        if (isShowing() && (contentChanged || typeChanged)) {
            dismiss();
        } else {
            updateToast();
        }

        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(mDuration);
        mToast.show();
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
            default:
                return R.drawable.type_info_normal;
        }
    }

    @Override
    public void info(String msg) {
        showHelper(msg, TYPE_INFO_NORMAL, Toast.LENGTH_SHORT);
    }

    @Override
    public void infoLong(String msg) {
        showHelper(msg, TYPE_INFO_NORMAL, Toast.LENGTH_LONG);
    }

    @Override
    public void warning(String msg) {
        showHelper(msg, TYPE_INFO_WARNING, Toast.LENGTH_SHORT);
    }

    @Override
    public void warningLong(String msg) {
        showHelper(msg, TYPE_INFO_WARNING, Toast.LENGTH_LONG);
    }

    @Override
    public void success(String msg) {
        showHelper(msg, TYPE_INFO_SUCCESS, Toast.LENGTH_SHORT);
    }

    @Override
    public void successLong(String msg) {
        showHelper(msg, TYPE_INFO_SUCCESS, Toast.LENGTH_LONG);
    }

    @Override
    public void error(String msg) {
        showHelper(msg, TYPE_INFO_ERROR, Toast.LENGTH_SHORT);
    }

    @Override
    public void errorLong(String msg) {
        showHelper(msg, TYPE_INFO_ERROR, Toast.LENGTH_LONG);
    }

    public void cancel() {
        mToast.cancel();
        rebuildToast();
    }
}
