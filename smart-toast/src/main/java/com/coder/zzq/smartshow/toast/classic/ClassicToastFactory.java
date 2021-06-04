package com.coder.zzq.smartshow.toast.classic;

import android.view.View;

import com.coder.zzq.smartshow.toast.factory.AbstractToastFactory;
import com.coder.zzq.smartshow.toast.Constants;
import com.coder.zzq.toolkit.Utils;

public class ClassicToastFactory extends AbstractToastFactory<ClassicToast.Config> {
    private static final ClassicToastFactory sClassicToastFactory = new ClassicToastFactory();

    public static ClassicToastFactory get() {
        return sClassicToastFactory;
    }

    @Override
    public String toastAlias() {
        return Constants.TOAST_TYPE_CLASSIC;
    }

    @Override
    protected View setupConfig(View rootView, ClassicToast.Config toastConfig) {
        super.setupConfig(rootView, toastConfig);
        return ClassicToast.provideToastView(rootView, Utils.getInflater(), toastConfig);
    }
}
