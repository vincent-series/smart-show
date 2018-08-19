package com.coder.zzq.smartshow.lifecycle;

import android.app.Activity;
import android.widget.LinearLayout;

import java.util.Deque;
import java.util.LinkedList;
/**
 * Created by 朱志强 on 2018/08/19.
 */
public final class ActivityStack {

    private ActivityStack() {

    }

    private static Deque<Activity> sActivitySet;

    public static void push(Activity activity) {
        if (activity != null){
            getActivitySet().push(activity);
        }
    }


    public static Activity pop(Activity activity){
        if (getActivitySet().isEmpty()){
            return null;
        }
        return getActivitySet().pop();
    }


    public static Activity getTop(){
        return getActivitySet().peekFirst();
    }


    private static Deque<Activity> getActivitySet() {
        if (sActivitySet == null) {
            sActivitySet = new LinkedList<>();
        }
        return sActivitySet;
    }
}
