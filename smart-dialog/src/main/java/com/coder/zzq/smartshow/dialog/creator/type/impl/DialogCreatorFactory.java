package com.coder.zzq.smartshow.dialog.creator.type.impl;

import com.coder.zzq.smartshow.dialog.creator.type.IEnsureDialogCreator;
import com.coder.zzq.smartshow.dialog.creator.type.ILoadingDialogCreator;
import com.coder.zzq.smartshow.dialog.creator.type.INotificationCreator;

public class DialogCreatorFactory {
    public static INotificationCreator notification() {
        return new NotificationDialogCreator();
    }

    public static IEnsureDialogCreator ensure() {
        return new EnsureDialogCreator();
    }

    public static ILoadingDialogCreator loading() {
        return new LoadingDialogCreator();
    }
}
