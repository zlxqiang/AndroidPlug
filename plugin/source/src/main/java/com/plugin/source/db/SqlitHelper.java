package com.plugin.source.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.plugin.log.Logger;
import com.plugin.log.LoggerFactory;
import com.plugin.source.network.ServerMolde;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/4/13.
 */

public class SqlitHelper {

    private final String fDBName = "pluginDB";

    private final int fVersion = 2;

    private final DBHelper mDB;

    private static SqlitHelper mInstance;

    private Context mContext;

    private static final Logger log;

    static {
        log = LoggerFactory.getLogcatLogger("DownloadServicer");
    }

    private SqlitHelper(Context context) {
        mDB = new DBHelper(context, fDBName, fVersion);
    }

    public static SqlitHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SqlitHelper(context);
        }
        return mInstance;
    }


    /**
     * 插入操作
     *
     * @param filePathMolds
     */
    public void insert(int state,ServerMolde.PluginMolde filePathMolds, String path) {
        SQLiteDatabase db = null;
        try {
            db = mDB.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableFilePath._id, filePathMolds.get_id());
            contentValues.put(TableFilePath.code, filePathMolds.getFile_md5());
            contentValues.put(TableFilePath.path, path);
            contentValues.put(TableFilePath.version, filePathMolds.getVersion());
            contentValues.put(TableFilePath.enable, state);
            long id = db.insert(TableFilePath.tableName, null, contentValues);
            if (id > 0) {
                log.log("插入成功", Logger.LogLevel.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 查询可以路径
     *
     * @return
     */
    public List<FilePathMold> queryPath(String state) {
        Cursor cursor = null;
        SQLiteDatabase db = null;
        try {
            db = mDB.getReadableDatabase();
            cursor = db.query(TableFilePath.tableName, new String[]{"*"}, TableFilePath.enable + "=?", new String[]{state}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                List<FilePathMold> pathMolds = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(TableFilePath.id));
                    String _id=cursor.getString(cursor.getColumnIndex(TableFilePath._id));
                    String path = cursor.getString(cursor.getColumnIndex(TableFilePath.path));
                    String code = cursor.getString(cursor.getColumnIndex(TableFilePath.code));
                    int version = cursor.getInt(cursor.getColumnIndex(TableFilePath.version));
                    int enalbe = cursor.getInt(cursor.getColumnIndex(TableFilePath.enable));
                    pathMolds.add(new FilePathMold(id,_id, code, path, version, enalbe));
                }
                return pathMolds;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;
    }

    /**
     * 更新
     */
    public int update(int state,String md5) {
        SQLiteDatabase db = null;
        try {
            db = mDB.getReadableDatabase();
            if (db != null) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TableFilePath.enable, state);
                int id = db.update(TableFilePath.tableName, contentValues, TableFilePath.code + "=?", new String[]{md5});
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return -1;
    }


    /**
     * 删除
     *
     */
    public int delet(String state) {
        SQLiteDatabase db = null;
        try {
            db = mDB.getReadableDatabase();
                    return db.delete(TableFilePath.tableName, TableFilePath.enable + "=?", new String[]{state});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return -1;
    }

    public void cancle() {
        if (mDB != null) {
            mDB.close();
        }
        if (mInstance != null) {
            mInstance = null;
        }

    }

    public DBHelper getDBHelper() {
        return mDB;
    }
}
