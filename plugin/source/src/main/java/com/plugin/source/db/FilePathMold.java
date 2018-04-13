package com.plugin.source.db;

/**
 * Created by admin on 2018/4/13.
 */

public class FilePathMold{
    /**
     * id
     */
    private int id;

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

    public FilePathMold(int id, String code, String path, int version,int enable) {
        this.id = id;
        this.code = code;
        this.path = path;
        this.version = version;
        this.enable=enable;
    }

    public int getId() {
        return id;
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

}
