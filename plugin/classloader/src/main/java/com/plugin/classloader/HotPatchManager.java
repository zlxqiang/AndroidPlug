package com.plugin.classloader;

import com.plugin.log.Logger;
import com.plugin.log.LoggerFactory;

import java.io.File;
import java.io.InputStream;


/**
 * Created by yb.wang on 15/7/30.
 */
public class HotPatchManager {

    private static volatile HotPatchManager instance;
    private static final Logger log;

    private File patchDir;

    static {
        log= LoggerFactory.getLogcatLogger("HotPatchItem");
    }

    private HotPatchManager() {

    }

    public static HotPatchManager getInstance() {
        if (instance == null) {
            synchronized (HotPatchManager.class) {
                if (instance == null) {
                    instance = new HotPatchManager();
                }
            }
        }
        return instance;
    }

    /**
     * 运行补丁
     */
    public void run() {
    }

    /**
     * 安装补丁
     */
    public boolean installHotPatch(String hotFixFileName, InputStream inputStream) {

        return  true;
    }

    public void purge() {

    }

   private boolean uninstallHotPatch(String hotFixFileName) {
        return false;
    }



}

