package com.swbyte.main;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by admin on 2017/7/1.
 */

public class AppApplication extends Application implements Application.ActivityLifecycleCallbacks{

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        CrashHandler.getInstance().init(this);//初始化全局异常管理
        registerActivityLifecycleCallbacks(this);
        registerComponentCallbacks(this);


    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

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
}
