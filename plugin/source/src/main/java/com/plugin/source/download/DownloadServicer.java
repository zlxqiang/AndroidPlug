package com.plugin.source.download;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.plugin.log.Logger;
import com.plugin.log.LoggerFactory;
import com.plugin.source.PluginSourceManager;
import com.plugin.source.db.FilePathMold;
import com.plugin.source.db.SqlitHelper;
import com.plugin.source.network.DownloadCallBack;
import com.plugin.source.network.Network;
import com.plugin.source.network.ServerMolde;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import ctrip.android.bundle.util.APKUtil;
import okhttp3.Response;

/**
 * Created by zzq on 2018/4/13.
 */

public class DownloadServicer extends IntentService {

    private static final Logger log;

    static {
        log = LoggerFactory.getLogcatLogger("DownloadServicer");
    }

    public DownloadServicer() {
        this("file");
    }

    public DownloadServicer(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        //1.添加
        ArrayList<ServerMolde.PluginMolde> datas = (ArrayList<ServerMolde.PluginMolde>) intent.getSerializableExtra("values");
        //2.更新
        ArrayList<FilePathMold> localDatas = (ArrayList<FilePathMold>) intent.getSerializableExtra("localValues");
        if (datas != null) {
            //删除
            List<FilePathMold> whileDeletDatas = SqlitHelper.getInstance(DownloadServicer.this).queryPath("2");
            if (whileDeletDatas != null && whileDeletDatas.size() > 0) {
                SqlitHelper.getInstance(DownloadServicer.this).delet("2");
                for (FilePathMold filePathMold : whileDeletDatas) {
                    File file = new File(filePathMold.getPath());
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }

            for (final ServerMolde.PluginMolde pluginMolde : datas) {

                int index = pluginMolde.getDownload_path().lastIndexOf("/");
                String fileName = "bundle.zip";
                File file = new File(FilePath.makeDBFilePath(pluginMolde.getFile_md5()), fileName);
                if (file.exists() && file.isDirectory()) {
                    file.delete();
                    file.getParentFile().mkdirs();
                }

                Response responce = Network.getInstance().fileDownload(PluginSourceManager.BaseUrl + pluginMolde.getDownload_path());
                // getDatas(responce, file);
                try {
                    APKUtil.copyInputStreamToFile(new ZipInputStream(responce.body().byteStream()), file);
                    //降级0-2
                    if (localDatas != null) {
                        for (FilePathMold filePathMold : localDatas) {
                            if (filePathMold.get_id().equals(pluginMolde.get_id()) && filePathMold.getVersion() < pluginMolde.getVersion()) {
                                SqlitHelper.getInstance(DownloadServicer.this).update(2, filePathMold.getCode());
                            }
                        }
                    }

                    if (file.exists()) {
                        SqlitHelper.getInstance(DownloadServicer.this.getApplication()).insert(0, pluginMolde, file.getAbsolutePath());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void getDatas(Response response, File file) throws IOException {

        if (response != null && file != null) {
            InputStream is = response.body().byteStream();
            FileOutputStream fos = null;
            fos = new FileOutputStream(file.getAbsolutePath());
            int len = 0;
            byte[] buffer = new byte[2048];
            while (-1 != (len = is.read(buffer))) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            fos.close();
            is.close();
        }

    }

}


