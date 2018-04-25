package com.swbyte.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.plugin.source.db.DBHelper;
import com.plugin.source.db.SqlitHelper;
import com.plugin.source.db.TableFilePath;

/**
 * Created by admin on 2018/4/25.
 */

public class CourseContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.swbyte.contentprovider";

    public static final int INSERT=1;

    public static final int QUERY=2;

    public static final Uri CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" +  TableFilePath.tableName);

    private SQLiteDatabase db;

    private static UriMatcher uriMatcher;



    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TableFilePath.tableName, INSERT);
        uriMatcher.addURI(AUTHORITY,TableFilePath.tableName,QUERY);
    }

    private SQLiteOpenHelper dbHelper;

    public CourseContentProvider() {
    }

    @Override
    public boolean onCreate() {
            dbHelper = SqlitHelper.getInstance(this.getContext()).getDBHelper();
            return (dbHelper == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case INSERT:
                break;
            case QUERY:
                db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(TableFilePath.tableName, new String[]{"*"}, selection, selectionArgs, null, null, sortOrder);
                return cursor;
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
