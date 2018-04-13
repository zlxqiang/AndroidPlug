package com.plugin.source.download;

/**
 * Created by zzq on 2018/4/13.
 */

public interface DownloadCallBack {
    /**
     * 开始下载
     */
    void startDownload();

    /**
     * 下载进度
     * @param all
     * @param lenth
     */
    void progressDownload(long all,long lenth);

    /**
     * 下载完成
     */
    void endDownload(String path);

    /**
     * 下载失败
     * @param e
     */
    void errorDownload(Exception e);
}
