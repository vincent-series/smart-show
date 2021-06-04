package com.coder.zzq.smart_show.annotation_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public class ClassNames {
    public static final ClassName BASE_TOAST_CONFIG =
            ClassName.get("com.coder.zzq.smartshow.toast.factory", "BaseToastConfig");

    public static final ClassName BASE_TOAST_FACTORY =
            ClassName.get("com.coder.zzq.smartshow.toast.factory", "AbstractToastFactory");
    public static final ClassName CONFIG_SETTER = ClassName.get("", "ConfigSetter");
    public static final ClassName PLAIN_TOAST_API = ClassName.get("com.coder.zzq.smartshow.toast", "PlainToastApi");
    public static final ClassName TOAST_SCHEDULER = ClassName.get("com.coder.zzq.smartshow.toast.schedule", "ToastScheduler");
    public static final ClassName GRAVITY = ClassName.get("android.view", "Gravity");
    public static final ClassName CONSTANTS = ClassName.get("com.coder.zzq.smartshow.toast", "Constants");
    public static final ClassName TOAST = ClassName.get("android.widget", "Toast");
    public static final ClassName TOAST_RETRIEVER = ClassName.get("com.coder.zzq.smartshow.toast", "VariousToastRetriever");

    public static final ClassName CLASSIC_TOAST_OVERALL = ClassName.get("com.coder.zzq.smartshow.toast.classic", "ClassicToastView", "Overall");
    public static final ClassName CLASSIC_TOAST_INVOKER = ClassName.get("com.coder.zzq.smartshow.toast.classic", "ClassicToastInvoker");
    public static final ClassName EMOTION_TOAST_OVERALL = ClassName.get("com.coder.zzq.smartshow.toast.emotion", "EmotionToastView", "Overall");
    public static final ClassName EMOTION_TOAST_INVOKER = ClassName.get("com.coder.zzq.smartshow.toast.emotion", "EmotionToastInvoker");
    public static final ClassName LAYOUT_INFLATER = ClassName.get("android.view", "LayoutInflater");

    public static final ClassName COMPACT_TOAST = ClassName.get("com.coder.zzq.smartshow.toast.compact", "CompactToast");

    public static ParameterizedTypeName getGenericToastFactoryClassName(TypeName paramType) {
        return ParameterizedTypeName.get(BASE_TOAST_FACTORY, paramType);
    }

    public static final ClassName VIEW_CLASS = ClassName.get("android.view", "View");

    public static final ClassName UTILS = ClassName.get("com.coder.zzq.toolkit", "Utils");
}
