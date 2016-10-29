package com.example.administrator.news1028.activities.app;

import android.app.Application;

import org.xutils.BuildConfig;
import org.xutils.x;


/**
 * @author Administrator.
 * @version .
 * @time .继承Application，作为全局的，方便调用
 */

public class GlobalApp extends Application {
    //清单需注册
    private static GlobalApp globalApp;
    @Override
    public void onCreate() {
        super.onCreate();//对xUtils3完成依赖后，导包即可
        globalApp=this;
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }

    public static GlobalApp getGlobalApp() {
        return globalApp;
    }
}
