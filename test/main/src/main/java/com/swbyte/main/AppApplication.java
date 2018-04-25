package com.swbyte.main;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.plugin.source.PluginSourceManager;

/**
 * Created by admin on 2017/7/1.
 */

public class AppApplication extends Application {

    public static Application mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        new PluginSourceManager(this);
        mContext=this;
        CrashHandler.getInstance().init(this);//初始化全局异常管理
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

}
