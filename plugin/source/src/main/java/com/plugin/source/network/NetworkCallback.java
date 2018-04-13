package com.plugin.source.network;

import okhttp3.Call;

/**
 * Created by admin on 2018/4/13.
 */

public interface NetworkCallback<T> {

    void onFailure(String msg);

    void onNetwortError(int code,String e);

    void onSuccess(Call call,T t);
}
