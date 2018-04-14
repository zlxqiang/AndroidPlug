package com.plugin.source.network;

import android.net.ConnectivityManager;

import com.google.gson.Gson;

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

    public Network() {
        mClient=new OkHttpClient.Builder().writeTimeout(1000*60, TimeUnit.MILLISECONDS).build();
    }

    public Network(OkHttpClient client) {
        mClient=client;
    }

   public void checkServer(String url,String key, String json, final NetworkCallback callback){
       FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
       formBody.add("key",key);//传递键值对参数
       formBody.add("params",json);
       Request request = new Request.Builder()
               .url(url)
               .post(formBody.build())
               .build();
       mClient.newCall(request).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               if(callback!=null){
                   callback.onFailure(e.getMessage());
               }
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
                if(response.code()== 200){
                    if(callback!=null){
                        ParameterizedType parameterizedType = (ParameterizedType) callback.getClass().getGenericInterfaces()[0];
                       Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        String json=response.body().string();
                        Object obj = new Gson().fromJson(json, actualTypeArguments[0]);
                        callback.onSuccess(call,obj);
                    }
                }
           }
       });
   }

}
