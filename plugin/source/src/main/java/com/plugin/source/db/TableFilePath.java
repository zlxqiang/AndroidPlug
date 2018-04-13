package com.plugin.source.db;

/**
 * Created by admin on 2018/4/13.
 */

public class TableFilePath implements SqlString {

    /**
     * 数据表名称
     */
    public static final String tableName="file_path";

    /**
     * id
     */
    public static final String id ="id";

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
     * 是否有效 0,无效；1有效
     */
    public static final String enable="enable";

    @Override
    public String getSql() {
        return "CREATE TABLE "
                +tableName
                +" ("
                +id +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +code+" VARCHAR(50),"
                +path+" VARCHAR(50),"
                +version+" INTEGER,"
                +enable+" INTEGER))";
    }
}
