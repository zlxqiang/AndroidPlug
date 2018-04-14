package com.plugin.source.db;

import java.io.Serializable;

/**
 * Created by admin on 2018/4/13.
 */

public class FilePathMold implements Serializable{

    private int id;

    /**
     * _id
     */
    private String _id;

    /**
     * 编码,md5
     */
    private String code;

    /**
     * 路径
     */
    private String path;

    /**
     * 版本
     */
    private int version;

    /**
     * 是否有效
     */
    private int enable;

    public FilePathMold(int id,String _id, String code, String path, int version,int enable) {
        this.id=id;
        this._id = _id;
        this.code = code;
        this.path = path;
        this.version = version;
        this.enable=enable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId() {
        return _id;
    }

    public String getCode() {
        return code;
    }

    public String getPath() {
        return path;
    }

    public int getVersion() {
        return version;
    }

    public int getEnable() {
        return enable;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }
}
