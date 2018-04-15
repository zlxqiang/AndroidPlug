package com.plugin.source.network;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2018/4/13.
 */

public class ServerMolde implements Serializable{

    private int code;

    private String msg;

    private List<PluginMolde> data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<PluginMolde> getData() {
        return data;
    }

    public void setData(List<PluginMolde> data) {
        this.data = data;
    }

    public static class PluginMolde implements Serializable{

       private String _id;

       private String download_path;

       private String file_md5;

       private int version;

       private int file_size;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getDownload_path() {
            return download_path;
        }

        public void setDownload_path(String download_path) {
            this.download_path = download_path;
        }

        public String getFile_md5() {
            return file_md5;
        }

        public void setFile_md5(String file_md5) {
            this.file_md5 = file_md5;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public int getFile_size() {
            return file_size;
        }

        public void setFile_size(int file_size) {
            this.file_size = file_size;
        }
    }
}
