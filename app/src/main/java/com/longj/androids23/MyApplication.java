package com.longj.androids23;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.longj.androids23.Util.MyActivityManager;
import com.longj.androids23.Util.OkHttpUtil;

import org.xutils.x;

/**
 * Created by xp on 2017/11/21.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
//        x.Ext.setDebug(BuildConfig.DEBUG);//是否输出debug日志，开启会影响性能

        //初始化图片加载类(Fresco)
        Fresco.initialize(this);

        OkHttpUtil.getInstance();


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActivityManager.getInstance().setCurrentActivity(activity);//为管理类设置当前展示的是那个Activity
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

    }
}
