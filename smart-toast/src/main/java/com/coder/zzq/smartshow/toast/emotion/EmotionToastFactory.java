package com.coder.zzq.smartshow.toast.emotion;

import android.view.View;

import com.coder.zzq.smartshow.toast.factory.AbstractToastFactory;
import com.coder.zzq.smartshow.toast.Constants;
import com.coder.zzq.toolkit.Utils;

public class EmotionToastFactory extends AbstractToastFactory<EmotionToast.Config> {
    private static final EmotionToastFactory sEmotionToastFactory = new EmotionToastFactory();

    public static EmotionToastFactory get() {
        return sEmotionToastFactory;
    }

    @Override
    protected String provideToastAlias() {
        return Constants.TOAST_TYPE_EMOTION;
    }

    @Override
    protected View setupConfig(View rootView, EmotionToast.Config toastConfig) {
        super.setupConfig(rootView,toastConfig);
        return EmotionToast.provideToastView(rootView, Utils.getInflater(), toastConfig);
    }
}
