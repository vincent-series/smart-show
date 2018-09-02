package com.coder.zzq.smartshow.toast;

import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.Toast;

import com.coder.zzq.smartshow.R;

import static com.coder.zzq.smartshow.toast.IToastSetting.ACTION_IGNORE;
import static com.coder.zzq.smartshow.toast.IToastSetting.ACTION_REPEAT;

public class TypeToastManager extends ToastManager implements ITypeShow {

    public static final int TYPE_INFO_NORMAL = 0;
    public static final int TYPE_INFO_WARNING = 1;
    public static final int TYPE_INFO_SUCCESS = 2;
    public static final int TYPE_INFO_FAIL = 3;
    public static final int TYPE_INFO_ERROR = 4;

    private int mCurInfoType;
    private ImageView mIconView;
    private int mCurIcon;

    public TypeToastManager(ToastSettingImpl toastSetting) {
        super(toastSetting);
    }

    @Override
    protected Toast createToast() {
        mToast = new Toast(SmartToast.getContext());
        mView = LayoutInflater.from(SmartToast.getContext()).inflate(R.layout.layout_type_info, null);
        mMsgView = mView.findViewById(R.id.type_info_message);
        mIconView = mView.findViewById(R.id.type_info_icon);
        mToast.setView(mView);
        return mToast;
    }

    @Override
    protected void rebuildToast() {
        mToast = createToast();
        setupReflectInfo();
        setupToast();
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
        mWindowParams.height = dpToPx(85);
        if (mToastSetting.isTypeInfoThemeColorSetup()){
            GradientDrawable drawable = (GradientDrawable) mView.getBackground();
            drawable.setAlpha(238);
            drawable.setColor(mToastSetting.getTypeInfoThemeColor());
        }

    }

    public void showHelper(String msg, int infoType, int duration) {
        SmartToast.dismissPlainShowIfNeed();
        msg = (msg == null) ? "" : msg;
        //文本是否改变
        boolean contentChanged = !mCurMsg.equals(msg.trim());

        //类型是否改变
        boolean typeChanged = mCurInfoType != infoType;

        mCurMsg = msg;
        mDuration = duration;
        mCurInfoType = infoType;
        mCurIcon = getIcon(mCurInfoType);

        switch (mToastSetting.getActionWhenToastDuplicate()) {
            case ACTION_IGNORE:
                if (isShowing() && (contentChanged || typeChanged)) {
                    dismiss();
                } else {
                    updateToast();
                }
                break;
            case ACTION_REPEAT:
                dismiss();
                updateToast();
                break;
        }


        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(mDuration);
        mToast.show();
    }

    private int getIcon(int infoType) {
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
            default:
                return R.drawable.type_info_normal;
        }
    }

    @Override
    public void normal(String msg) {
        showHelper(msg,TYPE_INFO_NORMAL,Toast.LENGTH_SHORT);
    }
    @Override
    public void normalLong(String msg) {
        showHelper(msg,TYPE_INFO_NORMAL,Toast.LENGTH_LONG);
    }
    @Override
    public void warning(String msg) {
        showHelper(msg,TYPE_INFO_WARNING,Toast.LENGTH_SHORT);
    }
    @Override
    public void warningLong(String msg) {
        showHelper(msg,TYPE_INFO_WARNING,Toast.LENGTH_LONG);
    }
    @Override
    public void fail(String msg) {
        showHelper(msg,TYPE_INFO_FAIL,Toast.LENGTH_SHORT);
    }
    @Override
    public void failLong(String msg) {
        showHelper(msg,TYPE_INFO_FAIL,Toast.LENGTH_LONG);
    }
    @Override
    public void success(String msg) {
        showHelper(msg,TYPE_INFO_SUCCESS,Toast.LENGTH_SHORT);
    }
    @Override
    public void successLong(String msg) {
        showHelper(msg,TYPE_INFO_SUCCESS,Toast.LENGTH_LONG);
    }
    @Override
    public void error(String msg) {
        showHelper(msg,TYPE_INFO_ERROR,Toast.LENGTH_SHORT);
    }

    @Override
    public void errorLong(String msg) {
        showHelper(msg,TYPE_INFO_ERROR,Toast.LENGTH_LONG);
    }

    public void cancel() {
        mToast.cancel();
        rebuildToast();
    }
}
