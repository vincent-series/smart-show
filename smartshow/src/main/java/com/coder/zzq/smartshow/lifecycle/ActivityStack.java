package com.coder.zzq.smartshow.lifecycle;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 朱志强 on 2018/08/19.
 */
public final class ActivityStack {

    private ActivityStack() {

    }

    private static List<Activity> sActivitySet;

    public static void push(Activity activity) {
        if (activity != null) {
            Log.d("zzq","push activity : " + activity.hashCode());
            getActivitySet().add(activity);
        }
    }


    public static void pop(Activity activity) {

        if (isEmpty()) {
            return;
        }

        int num = count();
        for (int index = (num - 1); index >= 0; index--) {
            if (getActivitySet().get(index) == activity) {
                getActivitySet().remove(index);
                Log.d("zzq","pop activity : " + activity.hashCode());
                break;
            }
        }
    }


    public static Activity getTop() {
        return isEmpty() ? null : getActivitySet().get(count() - 1);
    }


    private static List<Activity> getActivitySet() {
        if (sActivitySet == null) {
            sActivitySet = new ArrayList<>();
        }
        return sActivitySet;
    }

    public static boolean isEmpty() {
        return sActivitySet == null || sActivitySet.isEmpty();
    }


    public static int count() {
        return sActivitySet == null ? 0 : sActivitySet.size();
    }
}
