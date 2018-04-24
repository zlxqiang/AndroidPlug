package com.plugin.source;

import android.app.Application;
import android.content.Intent;

import com.google.gson.Gson;
import com.plugin.log.Logger;
import com.plugin.log.LoggerFactory;
import com.plugin.source.db.FilePathMold;
import com.plugin.source.db.SqlitHelper;
import com.plugin.source.download.DownloadServicer;
import com.plugin.source.download.FilePath;
import com.plugin.source.network.Network;
import com.plugin.source.network.NetworkCallback;
import com.plugin.source.network.ServerMolde;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.plugin.classloader.loader.BundlePathLoader;

import ctrip.android.bundle.hack.AndroidHack;
import ctrip.android.bundle.hack.SysHacks;
import ctrip.android.bundle.runtime.DelegateResources;
import ctrip.android.bundle.runtime.InstrumentationHook;
import ctrip.android.bundle.runtime.RuntimeArgs;
import ctrip.android.bundle.util.APKUtil;
import okhttp3.Call;

/**
 * Created by zzq on 2018/4/13.
 * 管理插件的下载、删除、存储、提供调用
 */

public class PluginSourceManager extends NetworkCallback<ServerMolde> {

    private List<FilePathMold> mPath = null;

    public static final String BaseUrl = "http://192.168.1.101:3000/";
    private final String Url = BaseUrl + "project/query";


    private Application mContext;


    private static final Logger log;


    static {
        log = LoggerFactory.getLogcatLogger("PluginSourceManager");
    }


    public PluginSourceManager(Application context) {
        mContext = context;
        new FilePath().init(context);

        try {
            init();
            mPath = SqlitHelper.getInstance(context).queryPath("0");
            if (mPath != null && mPath.size() > 0) {
                install(mPath);
                assertAddPath();
                chackNetServer();
            } else {
                //首次
                copyFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() throws Exception {
        SysHacks.defineAndVerify();
        RuntimeArgs.androidApplication = mContext;
        RuntimeArgs.delegateResources = mContext.getResources();
        AndroidHack.injectInstrumentationHook(new InstrumentationHook(AndroidHack.getInstrumentation(), mContext));
    }

    /**
     * 网络校验
     */
    private void chackNetServer() {
        String param = new Gson().toJson(mPath);
        Network.getInstance().checkServer(Url, "5ace1ace5078f3073857521f", param, this);

    }

    private void copyFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ZipFile zipFile = null;
                try {
                    zipFile = new ZipFile(mContext.getApplicationInfo().sourceDir);
                    //assert目录读取文件
                    Enumeration entries = zipFile.entries();
                    while (entries.hasMoreElements()) {
                        String path = ((ZipEntry) entries.nextElement()).getName();
                        if (path.startsWith("") && path.endsWith(".so")) {
                            //存储
                            String fileName ="bundle.zip";
                            String versionName = path.substring(path.lastIndexOf("cl-")+3, path.lastIndexOf("_v-"));
                            String versionCode = path.substring(path.lastIndexOf("_v-")+3, path.lastIndexOf("."));

                            String md5 = System.currentTimeMillis() + "";
                          //  File file = new File(FilePath.getVersionDirPath(versionName,versionCode));
                            File file = new File(FilePath.makeDBFilePath(md5),fileName);
                            if(!file.exists()){
                                if(!file.getParentFile().exists()){
                                    file.getParentFile().mkdirs();
                                }
                                file.createNewFile();
                            }
                            APKUtil.copyInputStreamToFile(zipFile.getInputStream(zipFile.getEntry(path)), file);
                            ServerMolde.PluginMolde pluginMode = new ServerMolde.PluginMolde();
                            pluginMode.set_id(md5);
                            pluginMode.setFile_md5(md5);
                            pluginMode.setVersion(Integer.valueOf(versionCode));
                            SqlitHelper.getInstance(mContext).insert(0, pluginMode, file.getPath());
                            //安装
                            install(file, false);
                            //
                            mPath = SqlitHelper.getInstance(mContext).queryPath("0");

                        }
                    }
                    // notifySyncBundleListers();
                    assertAddPath();
                    chackNetServer();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Throwable e) {
                    log.log(e.getMessage(), Logger.LogLevel.ERROR);
                }

            }
        }).start();
    }

    private void assertAddPath(){
        if(mPath!=null) {
            ArrayList<String> paths = new ArrayList<>();
            for(FilePathMold filePathMold:mPath){
                paths.add(filePathMold.getPath());
            }
            try {
                DelegateResources.newDelegateResources(RuntimeArgs.androidApplication, RuntimeArgs.delegateResources, paths);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void install(List<FilePathMold> paths) {
        if (paths != null && paths.size() > 0) {

            for (FilePathMold filePathMold : paths) {
                File file = new File(filePathMold.getPath());
                try {
                    install(file, false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    //dex实例化失败
                    SqlitHelper.getInstance(mContext).update(3, filePathMold.getCode());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void install(File file, boolean isHost) throws IllegalAccessException, InstantiationException, IOException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        if (file.exists() && file.isFile()) {
            ArrayList<File> files = new ArrayList<>();
            files.clear();
            files.add(file);
            BundlePathLoader.installBundleDexs(mContext.getClassLoader(), new File(file.getParent()), files, isHost);
        }
    }


    @Override
    public void onFailure(String msg) {
        log.log(msg, Logger.LogLevel.ERROR);
    }

    @Override
    public void onNetwortError(int code, String e) {
        log.log(e, Logger.LogLevel.ERROR);
    }

    @Override
    public void onSuccess(Call call, ServerMolde serverMolde) {
        log.log(new Gson().toJson(serverMolde), Logger.LogLevel.ERROR);
        if (serverMolde != null && serverMolde.getData() != null) {
            List<ServerMolde.PluginMolde> datas = serverMolde.getData();
            Intent intent = new Intent(mContext, DownloadServicer.class);
            intent.putExtra("values", (Serializable) datas);
            intent.putExtra("localValues", (Serializable) mPath);
            mContext.startService(intent);
        }
    }


}
