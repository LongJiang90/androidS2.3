package com.longj.androids23;

import android.app.Application;

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

    }
}
