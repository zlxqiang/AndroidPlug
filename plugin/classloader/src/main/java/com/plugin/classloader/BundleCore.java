package com.plugin.classloader;

import com.plugin.log.Logger;
import com.plugin.log.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yb.wang on 15/1/5.
 * Bundle机制外部核心类
 * 采用单例模式封装了外部调用方法
 */
public class BundleCore {

    protected static BundleCore instance;

    static final Logger log;

    private List<BundleInstalledListener> bundleSyncListeners;

    static {
        log = LoggerFactory.getLogcatLogger("BundleCore");

    }

    private BundleCore() {
        bundleSyncListeners = new ArrayList<BundleInstalledListener>();
    }

    public static synchronized BundleCore getInstance() {
        if (instance == null)
            instance = new BundleCore();
        return instance;
    }


    public void startup(boolean isInit) {
        //要安装的文件
    }

    public void run() {
        //安装
        //notifySyncBundleListers();
       // DelegateResources.newDelegateResources(RuntimeArgs.androidApplication, RuntimeArgs.delegateResources);
    }


    public void installBundle(String location, InputStream inputStream){

    }


    public void updateBundle(String location, InputStream inputStream) {

    }


    public void uninstallBundle(String location){

    }


}
