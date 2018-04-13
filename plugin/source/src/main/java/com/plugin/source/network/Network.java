package com.plugin.source.network;

import android.net.ConnectivityManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
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
        mClient=new OkHttpClient.Builder().build();
    }

    public Network(OkHttpClient client) {
        mClient=client;
    }

   public void checkServer(String url, String json, final NetworkCallback callback){
       RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), json);
       Request request = new Request.Builder()
               .url(url)
               .post(body)
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
                        NetworkCallback obj = new Gson().fromJson(response.body().toString(), actualTypeArguments[0]);
                        callback.onSuccess(call,obj);
                    }
                }
           }
       });
   }

}
