package com.plugin.source;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.plugin.source.db.FilePathMold;
import com.plugin.source.db.SqlitHelper;
import com.plugin.source.network.Network;
import com.plugin.source.network.NetworkCallback;
import com.plugin.source.network.ServerMolde;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zzq on 2018/4/13.
 * 管理插件的下载、删除、存储、提供调用
 */

public class PluginSourceManager implements NetworkCallback<ServerMolde> {

    private final List<FilePathMold> mPath;

    private final String Url = "https://192.168.1.101:3000";
    //网络权限
    //获取本次启动可以资源路径
    //1.网络请求，是否有资源要更新
    //2.更新资源包，放到下一次启动加载路径，保存配置
    //3.删除无用资源

    private Context mContext;


    public PluginSourceManager(Context context) {
        mContext = context;
        mPath = SqlitHelper.getInstance(context).queryPath();
        String param = new Gson().toJson(mPath);
        new Network().checkServer(Url, param, this);
    }

    public List<FilePathMold> getPluginPath() {
        return mPath;
    }

    @Override
    public void onFailure(String msg) {

    }

    @Override
    public void onNetwortError(int code, String e) {

    }

    @Override
    public void onSuccess(Call call, ServerMolde serverMolde) {

    }
}
