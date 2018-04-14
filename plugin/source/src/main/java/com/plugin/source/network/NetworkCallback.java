package com.plugin.source.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by admin on 2018/4/13.
 */

public abstract class NetworkCallback<T> implements Callback {

    protected abstract void onFailure(String msg);

    protected abstract void onNetwortError(int code, String e);

    protected abstract void onSuccess(Call call, T t);


    @Override
    public void onFailure(Call call, IOException e) {
        onFailure(e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.code() == 200) {
            String json = response.body().string();
            ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            T obj = new Gson().fromJson(json,actualTypeArguments[0]);
            onSuccess(call, (T) obj);
        }else{
            String json = response.body().string();
            onNetwortError(response.code(),json);
        }
    }
}
