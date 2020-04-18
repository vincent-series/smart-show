package com.coder.zzq.smartshow.toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.coder.zzq.toolkit.Utils;

import java.util.HashMap;
import java.util.Map;

public class UIArguments {
    public static final String ARGUMENT_ICON = "argument_icon";
    public static final String ARGUMENT_DEFAULT_ICON = "argument_default_icon";
    public static final String ARGUMENT_ICON_POSITION = "argument_icon_position";
    public static final String ARGUMENT_BACKGROUND_COLOR = "argument_background_color";
    public static final String ARGUMENT_TEXT_COLOR = "argument_text_color";
    public static final String ARGUMENT_TEXT_SIZE_SP = "argument_text_size_sp";

    private Map<String, Object> argumentsMap;

    private Map<String, Object> ensureArgumentsMapCreated() {
        if (argumentsMap == null) {
            argumentsMap = new HashMap<>(4);
        }
        return argumentsMap;
    }

    public Map<String, Object> getArgumentsMap() {
        return argumentsMap;
    }

    public UIArguments addArg(@NonNull String argName, Object argValue) {
        Utils.requireNonNull(argName, "argument name can not be null!");
        ensureArgumentsMapCreated().put(argName, argValue);
        return this;
    }


    public Object getArg(@NonNull String argName) {
        Utils.requireNonNull(argName, "argument name can not be null!");
        return argumentsMap == null ? null : ensureArgumentsMapCreated().get(argName);
    }

    public boolean isEmpty() {
        return argumentsMap == null || argumentsMap.isEmpty();
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        return Utils.equals(getArgumentsMap(), ((UIArguments) obj).getArgumentsMap());
    }

    @NonNull
    @Override
    public String toString() {
        return getArgumentsMap() == null ? "{}" : getArgumentsMap().toString();
    }

    public void clear() {
        if (argumentsMap != null) {
            argumentsMap.clear();
        }
    }
}
