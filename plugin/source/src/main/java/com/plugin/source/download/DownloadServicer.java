package com.plugin.source.download;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.plugin.log.Logger;
import com.plugin.log.LoggerFactory;
import com.plugin.source.db.SqlitHelper;
import com.plugin.source.network.DownloadCallBack;
import com.plugin.source.network.Network;
import com.plugin.source.network.ServerMolde;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zzq on 2018/4/13.
 */

public class DownloadServicer extends IntentService {

    private static final Logger log;

    static {
        log= LoggerFactory.getLogcatLogger("DownloadServicer");
    }

    public DownloadServicer() {
        this("file");
    }

    public DownloadServicer(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        ArrayList<ServerMolde.PluginMolde> datas = (ArrayList<ServerMolde.PluginMolde>) intent.getSerializableExtra("values");
        if (datas != null) {
            for (final ServerMolde.PluginMolde pluginMolde : datas) {
                int index = pluginMolde.getDownload_path().lastIndexOf("/");
                String fileName = pluginMolde.getDownload_path().substring(index);
                File file = new File(FilePath.getBaseDirPath() + "/plugins" + fileName);
                if(!file.exists()){
                    file.mkdirs();
                }
                Network.getInstance().fileDownload(pluginMolde.getDownload_path(), new DownloadCallBack(file,pluginMolde) {
                    @Override
                    protected void startDownload() {

                    }

                    @Override
                    protected void progressDownload(long all, long lenth) {

                    }

                    @Override
                    protected void endDownload(File path, ServerMolde.PluginMolde pluginMolde1) {
                            if(path.exists()){
                                SqlitHelper.getInstance(DownloadServicer.this.getApplication()).insert(pluginMolde1,path.getAbsolutePath());
                            }
                    }

                    @Override
                    protected void errorDownload(Exception e) {

                    }
                });
            }
        }
    }

}
