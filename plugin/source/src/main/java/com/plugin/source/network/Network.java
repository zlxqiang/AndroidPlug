package com.plugin.source.network;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

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

   public void checkServer(String url, String json, Callback callback){
       RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), json);
       Request request = new Request.Builder()
               .url(url)
               .post(body)
               .build();
       mClient.newCall(request).enqueue(callback);
   }

}
