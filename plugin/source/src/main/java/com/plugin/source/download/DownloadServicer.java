package com.plugin.source.download;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by zzq on 2018/4/13.
 */

public class DownloadServicer extends IntentService {

    public DownloadServicer() {
        this("file");
    }

    public DownloadServicer(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

}
