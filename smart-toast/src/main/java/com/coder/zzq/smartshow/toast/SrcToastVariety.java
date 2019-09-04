package com.coder.zzq.smartshow.toast;

import android.widget.Toast;

import com.coder.zzq.toolkit.Toolkit;

import static com.coder.zzq.smartshow.toast.ToastTags.TOAST_TAG_SRC;

final class SrcToastVariety extends TextToastVariety {
    public SrcToastVariety() {
        super(TOAST_TAG_SRC);
    }

    @Override
    protected Toast createToast() {
        return Toast.makeText(Toolkit.getContext(), "", Toast.LENGTH_SHORT);
    }
}
