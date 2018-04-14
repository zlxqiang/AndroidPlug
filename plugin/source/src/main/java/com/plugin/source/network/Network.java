package com.plugin.source.network;


import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 2018/4/13.
 */

public class Network {

    private OkHttpClient mClient;

    private static Network mNetwork;

    private Network() {
        mClient=new OkHttpClient.Builder().writeTimeout(1000*60, TimeUnit.MILLISECONDS).build();
    }


    public Network(OkHttpClient client) {
        mClient=client;
    }

    public static Network getInstance(){
        if(mNetwork==null){
            mNetwork=new Network();
        }
        return mNetwork;
    }

   public void checkServer(String url,String key, String json, NetworkCallback callback){
       FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
       formBody.add("key",key);//传递键值对参数
       formBody.add("params",json);
       Request request = new Request.Builder()
               .url(url)
               .post(formBody.build())
               .build();
       mClient.newCall(request).enqueue(callback);
   }


   public void fileDownload(String url,DownloadCallBack downloadCallback){
       Request request=new Request.Builder().url(url).build();
       mClient.newCall(request).enqueue(downloadCallback);
       downloadCallback.startDownload();
   }




}
