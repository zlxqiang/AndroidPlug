package com.plugin.source.db;

/**
 * Created by admin on 2018/4/13.
 */

public class TableFilePath {

    /**
     * 数据表名称
     */
    public static final String tableName="file_path";

    /**
     * id
     */
    public static final String id ="id";

    public static final String _id="_id";
    /**
     * 编码
     */
    public static final String code="code";

    /**
     * 路径
     */
    public static final String path="path";

    /**
     * 版本
     */
    public static final String version = "version";

    /**
     * 是否有效 0,当前；1有效（保留），2上一次
     */
    public static final String enable="enable";

    public static String getSql() {
        return "CREATE TABLE "
                +tableName
                +" ("
                +id +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +_id+" VARCHAR(50),"
                +code+" VARCHAR(50),"
                +path+" VARCHAR(50),"
                +version+" INTEGER,"
                +enable+" INTEGER)";
    }
}
