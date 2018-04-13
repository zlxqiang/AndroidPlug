package com.plugin.source;

import android.app.Application;
import android.content.Context;

import com.plugin.source.network.Network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zzq on 2018/4/13.
 * 管理插件的下载、删除、存储、提供调用
 */

public class PluginSourceManager implements Callback{

    //网络权限
    //获取本次启动可以资源路径
    //1.网络请求，是否有资源要更新
    //2.更新资源包，放到下一次启动加载路径，保存配置
    //3.删除无用资源

    private Context mContext;


    public PluginSourceManager(Context context) {
        mContext=context;

        new Network().checkServer("","",this);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
