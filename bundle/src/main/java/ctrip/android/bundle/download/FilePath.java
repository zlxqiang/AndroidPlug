package ctrip.android.bundle.download;

import android.content.Context;

import java.io.File;

/**
 * Created by 72byte on 2018/1/24.
 */

public class FilePath {

    private static String mBasePath;

    public void init(Context context) {
        mBasePath = context.getFilesDir().getAbsolutePath() + File.separator + context.getPackageName();
        File file=new File(mBasePath);
        if (!file.exists() || !file.isDirectory()) {
            new File(mBasePath).mkdirs();
        }
    }


    public static String getBaseDirPath() {
        if (mBasePath.equals(null)) {
            new NullPointerException("请先初始化FilePath类");
        }
        return mBasePath;
    }


    public static String getVersionDirPath(int version) {
        String vertionPath = mBasePath + "/" + version;
        File file=new File(vertionPath);
        if (!file.exists() || file.isFile()) {
            new File(vertionPath).mkdirs();
        }

        return vertionPath;
    }


}
