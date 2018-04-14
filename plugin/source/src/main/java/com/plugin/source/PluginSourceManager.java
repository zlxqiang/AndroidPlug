package com.plugin.source;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.plugin.log.Logger;
import com.plugin.log.LoggerFactory;
import com.plugin.source.db.FilePathMold;
import com.plugin.source.db.SqlitHelper;
import com.plugin.source.network.Network;
import com.plugin.source.network.NetworkCallback;
import com.plugin.source.network.ServerMolde;

import java.util.List;

import okhttp3.Call;

/**
 * Created by zzq on 2018/4/13.
 * 管理插件的下载、删除、存储、提供调用
 */

public class PluginSourceManager implements NetworkCallback<ServerMolde> {

    private final List<FilePathMold> mPath;

    private final String Url = "http://192.168.1.101:3000/project/query";
    //网络权限
    //获取本次启动可以资源路径
    //1.网络请求，是否有资源要更新
    //2.更新资源包，放到下一次启动加载路径，保存配置
    //3.删除无用资源

    private Context mContext;


    private static final Logger log;


    static {
        log=LoggerFactory.getLogcatLogger("PluginSourceManager");
    }


    public PluginSourceManager(Context context) {
        mContext = context;
        mPath = SqlitHelper.getInstance(context).queryPath();
        String param = new Gson().toJson(mPath);
        new Network().checkServer(Url, "5ace1ace5078f3073857521f",param, this);
    }

    public List<FilePathMold> getPluginPath() {
        return mPath;
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
    }
}
