package com.plugin.source.download;

import android.content.Context;

import java.io.File;

/**
 * Created by zzq on 2018/1/24.
 */

public class FilePath {

    private static String mBasePath;

    private static String mDBDirFilePath;

    private static String mPluginInstallDirPath;

    public void init(Context context) {
        mBasePath = context.getFilesDir().getAbsolutePath() + File.separator + "hotPath";
        File file=new File(mBasePath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }

        mDBDirFilePath=mBasePath+"/"+"dbFile";
        File file1=new File(mDBDirFilePath);
        if (!file1.exists() || !file1.isDirectory()) {
            file1.mkdirs();
        }

        mPluginInstallDirPath=mBasePath+"/"+"storge";
        File file2=new File(mPluginInstallDirPath);
        if (!file2.exists() || !file2.isDirectory()) {
            file2.mkdirs();
        }

    }


    public static String getBaseDirPath() {
        if (mBasePath.equals(null)) {
            new NullPointerException("请先初始化FilePath类");
        }
        return mBasePath;
    }

    /**
     * 数据库下载文件位置
     * @return
     */
    public static String getDBFileDirPath(){
        if (mDBDirFilePath.equals(null)) {
            new NullPointerException("请先初始化FilePath类");
        }
        return  mDBDirFilePath;
    }


    public static String makeDBFilePath(String dir,String name){
        return mDBDirFilePath+"/"+dir+"/"+name;
    }

    public static String makeDBFilePath(String dir){
        return mDBDirFilePath+"/"+dir;
    }

    /**
     * 数据库下载文件位置
     * @return
     */
    public static String getInstallDirPath(){
        if (mPluginInstallDirPath.equals(null)) {
            new NullPointerException("请先初始化FilePath类");
        }
        return  mPluginInstallDirPath;
    }




    public static String getVersionDirPath(String versionName,String versionCode) {
        String vertionPath = getInstallDirPath() + "/" + versionName+"_"+versionCode;
        File file=new File(vertionPath);
        if (!file.exists() || file.isFile()) {
            new File(vertionPath).mkdirs();
        }
        return vertionPath;
    }


}
