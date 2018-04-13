package com.plugin.source.db;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/4/13.
 */

public class SqlitHelper {

    private final String fDBName = "pluginDB";

    private final int fVersion = 1;

    private final DBHelper mDB;

    private SqlitHelper mInstance;

    private Context mContext;


    private SqlitHelper(Context context) {
        mDB = new DBHelper(context, fDBName, fVersion);
    }

    public SqlitHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SqlitHelper(context);
        }
        return mInstance;
    }


    public void insert(List<FilePathMold> filePathMolds) {
        SQLiteDatabase db = null;
        try {
            db = mDB.getWritableDatabase();
            db.beginTransaction();
            for (FilePathMold filePathMold : filePathMolds) {
                ContentValues contentValues = new ContentValues();
                // contentValues.put(TableFilePath.id,);
                contentValues.put(TableFilePath.code, filePathMold.getCode());
                contentValues.put(TableFilePath.path, filePathMold.getPath());
                contentValues.put(TableFilePath.version, filePathMold.getVersion());
                contentValues.put(TableFilePath.enable, filePathMold.getEnable());
                db.insert(TableFilePath.tableName, null, contentValues);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 查询可以路径
     *
     * @return
     */
    public List<FilePathMold> queryPath() {
        Cursor cursor = null;
        SQLiteDatabase db = null;
        try {
            db = mDB.getReadableDatabase();
            cursor = db.query(TableFilePath.tableName, new String[]{"*"}, TableFilePath.enable + "=？", new String[]{"1"}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                List<FilePathMold> pathMolds = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int _id = cursor.getInt(cursor.getColumnIndex(TableFilePath.id));
                    String path = cursor.getString(cursor.getColumnIndex(TableFilePath.path));
                    String code = cursor.getString(cursor.getColumnIndex(TableFilePath.code));
                    int version = cursor.getInt(cursor.getColumnIndex(TableFilePath.version));
                    int enalbe = cursor.getInt(cursor.getColumnIndex(TableFilePath.enable));
                    pathMolds.add(new FilePathMold(_id, code, path, version, enalbe));
                }
                return pathMolds;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    /**
     * 更新
     */
    public void update(List<FilePathMold> filePathMolds) {
        SQLiteDatabase db = null;
        try {
            db = mDB.getReadableDatabase();
            for(FilePathMold filePathMold:filePathMolds) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(TableFilePath.enable,filePathMold.getEnable());
                db.update(TableFilePath.tableName, contentValues,TableFilePath.id+"=?",new String[]{filePathMold.getId()+""});
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


}
