package com.coder.zzq.smartshow.toast;

final class Setting implements ISetting {
    private boolean mDismissOnLeave;
    private int mEmotionToastThemeColor = DEFAULT_EMOTION_TOAST_THEME_COLOR;
    private int mDefaultToastTag = ToastTags.TOAST_TAG_SRC;

    @Override
    public ISetting dismissOnLeave(boolean b) {
        mDismissOnLeave = b;
        return this;
    }

    public boolean isDismissOnLeave() {
        return mDismissOnLeave;
    }

    @Override
    public ISetting emotionToastThemeColor(int emotionToastThemeColor) {
        mEmotionToastThemeColor = emotionToastThemeColor;
        return this;
    }

    @Override
    public ISetting defaultToastTag(int tag) {
        mDefaultToastTag = tag;
        return this;
    }

    public int getDefaultToastTag() {
        return mDefaultToastTag;
    }

    public int getEmotionToastThemeColor() {
        return mEmotionToastThemeColor;
    }


    public ISetting toastProvide(IToastProvider provider) {
        ToastCache.get().setToastProvider(provider);
        return this;
    }
}
