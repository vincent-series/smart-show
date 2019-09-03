package com.coder.zzq.smartshow.toast;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.toolkit.log.EasyLogger;

import java.lang.reflect.Field;

import static com.coder.zzq.smartshow.toast.ToastTags.TOAST_TAG_EMOTION;
import static com.coder.zzq.smartshow.toast.ToastTags.TOAST_TAG_SRC;

public abstract class AbstractToastVariety {

    public static final int EMOTION_NONE = 0;

    public static final int EMOTION_INFO = 1;
    public static final int EMOTION_WARNING = 2;
    public static final int EMOTION_SUCCESS = 3;
    public static final int EMOTION_ERROR = 4;
    public static final int EMOTION_FAIL = 5;
    public static final int EMOTION_COMPLETE = 6;
    public static final int EMOTION_FORBID = 7;
    public static final int EMOTION_WAITING = 8;


    public AbstractToastVariety(int tag) {
        mTag = tag;
        ensureToastHasCreated();
        EasyLogger.d("toast variety" + Utils.getObjectDesc(this) + "has created");
    }

    protected int mTag;
    protected Toast mToast;

    protected CharSequence mCurMsg = "";
    protected int mCurEmotionType = EMOTION_NONE;


    protected int mXOffset;
    protected int mYOffset;
    protected int mGravity;
    protected int mVerticalAxisOffsetWhenBottom;
    protected int mVerticalAxisOffsetWhenTop;

    protected int mDuration;

    protected View mView;
    protected TextView mMsgView;


    protected Object mTn;
    protected WindowManager.LayoutParams mWindowParams;

    protected ShowCallback mShowCallback;

    protected long mLastShowTime;


    protected void showHelper(CharSequence msg, int emotionType, int gravity, int xOffset, int yOffset, int duration) {

        if (mShowCallback != null) {
            mShowCallback.beforeShow(this);
        }

        boolean contentChanged = contentChanged(msg);
        boolean emotionTypeChanged = emotionTypeChanged(emotionType);
        boolean locationChanged = locationChanged(gravity, xOffset, yOffset);

        if (isShowing() && (emotionTypeChanged || contentChanged || locationChanged)) {
            rebuildToast();
        }

        mCurEmotionType = emotionType;
        mCurMsg = msg;
        mGravity = gravity;
        mXOffset = xOffset;
        mYOffset = yOffset;
        mDuration = duration;

        updateToast();

        if (!isShowing()) {
            show();
        }

        if (mShowCallback != null) {
            mShowCallback.afterShow(this);
        }
    }

    protected void show() {
        if (Utils.isNotificationPermitted()) {
            mToast.show();
        } else {
            VirtualToastManager.get().show(mToast, mWindowParams);
        }
        mLastShowTime = System.currentTimeMillis();
    }

    protected void updateToast() {
        if (mMsgView != null) {
            mMsgView.setText(mCurMsg);
        }
        mToast.setGravity(mGravity, mXOffset, mYOffset);
    }

    protected void ensureToastHasCreated() {
        if (mToast == null) {
            rebuildToast();
        }
    }

    protected void rebuildToast() {
        dismiss();
        mToast = Utils.requireNonNull(createToast(), "createToast can not return null!");
        mView = mToast.getView();
        retrieveMsgView();
        mVerticalAxisOffsetWhenBottom = mYOffset = mToast.getYOffset();
        mVerticalAxisOffsetWhenTop = Utils.getToolbarHeight() + Utils.dpToPx(40);
        setupReflectInfo();
        EasyLogger.d("rebuild toast" + Utils.getObjectDesc(mToast));
    }

    protected void retrieveMsgView() {

        switch (mTag) {
            case TOAST_TAG_SRC:
                mMsgView = mView.findViewById(android.R.id.message);
                break;
            case TOAST_TAG_EMOTION:
                mMsgView = mView.findViewById(R.id.type_info_message);
                break;
            default:
                mMsgView = mView.findViewById(android.R.id.message);
                if (mMsgView == null) {
                    mMsgView = mView.findViewById(R.id.custom_toast_msg);
                }
        }
    }

    protected abstract Toast createToast();


    protected boolean contentChanged(CharSequence msg, Object... others) {
        return !TextUtils.equals(msg, mCurMsg);
    }

    protected boolean emotionTypeChanged(int emotionType) {
        return emotionType != mCurEmotionType;
    }

    protected boolean locationChanged(int gravity, int xOffset, int yOffset) {
        return gravity != mGravity || xOffset != mXOffset || yOffset != mYOffset;
    }

    protected void setupReflectInfo() {
        try {
            Field tnField = Toast.class.getDeclaredField("mTN");
            tnField.setAccessible(true);
            mTn = tnField.get(mToast);
            Field windowParamsField = mTn.getClass().getDeclaredField("mParams");
            windowParamsField.setAccessible(true);
            mWindowParams = (WindowManager.LayoutParams) windowParamsField.get(mTn);
            Field handlerField = mTn.getClass().getDeclaredField("mHandler");
            handlerField.setAccessible(true);
            Handler handlerOfTn = (Handler) handlerField.get(mTn);
            handlerField.set(mTn, new SafeHandler(handlerOfTn, mToast.getView()));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public boolean isShowing() {
        boolean isShowing = Utils.isNotificationPermitted() ? mToast != null && mToast.getView().getWindowVisibility() == View.VISIBLE
                : VirtualToastManager.get().isShowing();
        return isShowing;
    }

    public void dismiss() {
        if (isShowing()) {
            if (Utils.isNotificationPermitted()) {
                mToast.cancel();
            } else {
                VirtualToastManager.get().dismiss();
            }

        }
    }

    protected boolean isShortInterval() {
        return System.currentTimeMillis() - mLastShowTime < 100;
    }


    public interface ShowCallback {
        void afterShow(AbstractToastVariety toastVariety);

        void beforeShow(AbstractToastVariety toastVariety);
    }

    public void setShowCallback(ShowCallback callback) {
        mShowCallback = callback;
    }

}
