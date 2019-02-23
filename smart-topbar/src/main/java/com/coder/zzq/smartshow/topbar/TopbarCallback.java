package com.coder.zzq.smartshow.topbar;

import android.app.Activity;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.coder.zzq.smartshow.core.lifecycle.ITopbarCallback;


public class TopbarCallback implements ITopbarCallback {
    @Override
    public void dismissOnLeave(Activity activity) {
        if (TopbarDelegate.hasCreated() && TopbarDelegate.get().isDismissOnLeave()) {
            TopbarDelegate.get().onLeave(activity);
        }
    }

    @Override
    public void recycleOnDestroy(Activity activity) {
        if (TopbarDelegate.hasCreated()) {
            TopbarDelegate.get().destroy(activity);
        }
    }

    public void adjustTopbarContainerIfNecessary(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().getViewTreeObserver()
                    .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                            CoordinatorLayout topContainer = decorView.findViewById(R.id.smart_show_top_bar_container);
                            if (topContainer != null && decorView.getChildAt(decorView.getChildCount() - 1) != topContainer) {
                                decorView.removeView(topContainer);
                                decorView.addView(topContainer);
                            }
                            decorView.getViewTreeObserver().removeOnPreDrawListener(this);
                            return true;
                        }
                    });
        }
    }
}
