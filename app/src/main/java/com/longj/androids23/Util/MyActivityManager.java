package com.longj.androids23.Util;

import android.app.Activity;

import java.lang.ref.WeakReference;

/**
 * Created by xp on 2017/11/29.
 */

public class MyActivityManager {

    private static MyActivityManager sInstance = new MyActivityManager();
    private WeakReference<Activity> sCurrentActivityWeakRef;//采用弱应用持有Activity，避免造成内存泄漏

    private MyActivityManager(){

    }

    public static MyActivityManager getInstance() {
        return sInstance;
    }

    //用于存储、获取当前展示的Activity
    public Activity getCurrentActivity() {
        Activity currentAc = null;
        if (sCurrentActivityWeakRef != null) {
            currentAc = sCurrentActivityWeakRef.get();
        }
        return currentAc;
    }

    public void setCurrentActivity(Activity currentAc) {
        sCurrentActivityWeakRef = new WeakReference<Activity>(currentAc);
    }



}
