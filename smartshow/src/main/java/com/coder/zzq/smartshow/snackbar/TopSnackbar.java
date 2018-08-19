package com.coder.zzq.smartshow.snackbar;

import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.view.View;
import android.view.ViewGroup;

public class TopSnackbar extends BaseTransientBottomBar<TopSnackbar> {

    /**
     * Constructor for the transient bottom bar.
     *
     * @param parent              The parent for this transient bottom bar.
     * @param content             The content view for this transient bottom bar.
     * @param contentViewCallback The content view callback for this transient bottom bar.
     */
    protected TopSnackbar(@NonNull ViewGroup parent, @NonNull View content, @NonNull ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
    }
}
