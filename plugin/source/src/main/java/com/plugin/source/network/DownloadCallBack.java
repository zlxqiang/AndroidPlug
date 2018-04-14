package com.plugin.source.network;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zzq on 2018/4/13.
 */

public abstract class DownloadCallBack implements Callback {

    private File mFile;

    private ServerMolde.PluginMolde mPluginMolde;

    public DownloadCallBack(File file, ServerMolde.PluginMolde pluginMolde) {
        this.mFile=file;
        this.mPluginMolde=pluginMolde;
    }

    /**
     * 开始下载
     */
    protected abstract void startDownload();

    /**
     * 下载进度
     * @param all
     * @param lenth
     */
    protected abstract void progressDownload(long all, long lenth);

    /**
     * 下载完成
     */
    protected abstract void endDownload(File path, ServerMolde.PluginMolde pluginMolde);

    /**
     * 下载失败
     * @param e
     */
    protected abstract void errorDownload(Exception e);


    @Override
    public void onFailure(Call call, IOException e) {
       errorDownload(e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response != null && mFile!=null) {
            InputStream is = response.body().byteStream();
            FileOutputStream fos = new FileOutputStream(mFile);
            int len = 0;
            long hasLoadLength=len;
            long allLength = response.body().contentLength();
            byte[] buffer = new byte[2048];
            progressDownload(allLength,0);
            while (-1 != (len = is.read(buffer))) {
                fos.write(buffer, 0, len);
                progressDownload(allLength,hasLoadLength+=len);
            }
            fos.flush();
            fos.close();
            is.close();
            endDownload(mFile,mPluginMolde);
        }
    }
}
