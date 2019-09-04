package com.coder.zzq.smartshow.toast;

import android.util.SparseArray;
import android.widget.Toast;

import com.coder.zzq.toolkit.Toolkit;

class ToastCache {
    private static ToastCache sToastCache;
    private SparseArray<AbstractToastVariety> mToastVarieties;
    private IToastProvider mToastProvider;

    private ToastCache() {

    }

    public static ToastCache get() {
        if (sToastCache == null) {
            sToastCache = new ToastCache();
        }

        return sToastCache;
    }


    private SparseArray<AbstractToastVariety> getToastTagContainer() {
        if (mToastVarieties == null) {
            mToastVarieties = new SparseArray<>(4);
        }
        return mToastVarieties;
    }


    private boolean isToastVarietyCacheEmpty() {
        return mToastVarieties == null || mToastVarieties.size() == 0;
    }

    public void setToastProvider(IToastProvider toastProvider) {
        mToastProvider = toastProvider;
    }

    public AbstractToastVariety retrieveToastTagFromCache(final int toastTag) {
        AbstractToastVariety toastVariety = getToastTagContainer().get(toastTag);
        if (toastVariety == null) {
            switch (toastTag) {
                case ToastTags.TOAST_TAG_SRC:
                    toastVariety = new SrcToastVariety();
                    break;
                case AbstractToastVariety.TOAST_TAG_EMOTION:
                    int color = ToastDelegate.get().hasSetting() ? ToastDelegate.get().setting().getEmotionToastThemeColor() : ISetting.DEFAULT_EMOTION_TOAST_THEME_COLOR;
                    toastVariety = new EmotionToastVariety(color);
                    break;
                default:
                    toastVariety = new TextToastVariety(toastTag) {
                        @Override
                        protected Toast createToast() {
                            return mToastProvider.createCustomToast(toastTag, Toolkit.getContext());
                        }
                    };
                    break;
            }
            toastVariety.setShowCallback(ToastDelegate.get());
            getToastTagContainer().put(toastTag, toastVariety);
        }

        return toastVariety;
    }


}
