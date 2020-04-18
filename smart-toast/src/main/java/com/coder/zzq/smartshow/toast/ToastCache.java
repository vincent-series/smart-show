package com.coder.zzq.smartshow.toast;

import com.coder.zzq.toolkit.Utils;
import com.coder.zzq.toolkit.log.EasyLogger;

import java.util.LinkedList;
import java.util.List;

public class ToastCache {
    private static List<CustomToast> sCustomToasts;
    private static List<OriginalToast> sOriginalToasts;
    private static List<EmotionToast> sEmotionToasts;

    private static List<CustomToast> getCustomToasts() {
        if (sCustomToasts == null) {
            sCustomToasts = new LinkedList<>();
        }
        return sCustomToasts;
    }

    private static List<OriginalToast> getOriginalToasts() {
        if (sOriginalToasts == null) {
            sOriginalToasts = new LinkedList<>();
        }
        return sOriginalToasts;
    }


    private static List<EmotionToast> getEmotionToasts() {
        if (sEmotionToasts == null) {
            sEmotionToasts = new LinkedList<>();
        }
        return sEmotionToasts;
    }

    private static boolean withoutCachedToasts(List toastRecords) {
        return toastRecords == null || toastRecords.isEmpty();
    }

    public static void cacheRecord(AbstractToast record) {
        if (record == null) {
            return;
        }
        record.reset();
        EasyLogger.d("cached toast " + Utils.getObjectDesc(record));
        if (record.getClass() == PlainToast.class) {
            getCustomToasts().add((CustomToast) record);
        } else if (record.getClass() == OriginalToast.class) {
            getOriginalToasts().add((OriginalToast) record);
        } else if (record.getClass() == EmotionToast.class) {
            getEmotionToasts().add((EmotionToast) record);
        }
    }

    public static CustomToast provideCustomToast() {
        if (withoutCachedToasts(sCustomToasts)) {
            EasyLogger.d("no cached custom toast and create new one");
            return new CustomToast();
        } else {
            EasyLogger.d("use cached custom toast " + Utils.getObjectDesc(sCustomToasts.get(0)));
            return sCustomToasts.remove(0);
        }
    }

    public static OriginalToast provideOriginalToast() {
        if (withoutCachedToasts(sOriginalToasts)) {
            EasyLogger.d("no cached original toast and create new one");
            return new OriginalToast();
        } else {
            EasyLogger.d("use cached original toast " + Utils.getObjectDesc(sOriginalToasts.get(0)));
            return (OriginalToast) sOriginalToasts.remove(0).toastUI(new OriginalToastUI());
        }
    }

    public static IEmotionToast provideEmotionToast() {
        if (withoutCachedToasts(sEmotionToasts)) {
            EasyLogger.d("no cached emotion toast and create new one");
            return new EmotionToast();
        } else {
            EasyLogger.d("use cached emotion toast " + Utils.getObjectDesc(sEmotionToasts.get(0)));
            return (EmotionToast) sEmotionToasts.remove(0).toastUI(new EmotionToastUI());
        }
    }
}
