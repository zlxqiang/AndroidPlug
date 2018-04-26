package com.swbyte.contentprovider;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.plugin.source.db.FilePathMold;
import com.plugin.source.db.TableFilePath;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.sample_text);

        Cursor cursor = getContentResolver().query(CourseContentProvider.CONTENT_URI, new String[]{"*"}, TableFilePath.enable + "=?", new String[]{"0"}, null);
        if (cursor != null && cursor.getCount() > 0) {
            List<FilePathMold> pathMolds = new ArrayList<>();
            String paths="";
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(TableFilePath.id));
                String _id=cursor.getString(cursor.getColumnIndex(TableFilePath._id));
                String path = cursor.getString(cursor.getColumnIndex(TableFilePath.path));
                String code = cursor.getString(cursor.getColumnIndex(TableFilePath.code));
                int version = cursor.getInt(cursor.getColumnIndex(TableFilePath.version));
                int enalbe = cursor.getInt(cursor.getColumnIndex(TableFilePath.enable));
                pathMolds.add(new FilePathMold(id,_id, code, path, version, enalbe));
                paths=paths+"\n"+path;
            }
            tv.setText("当前数据："+paths);
        }

    }
}
