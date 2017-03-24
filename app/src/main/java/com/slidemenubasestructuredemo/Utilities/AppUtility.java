package com.slidemenubasestructuredemo.Utilities;


import android.content.Context;
import android.os.Environment;

import com.slidemenubasestructuredemo.R;

import java.io.File;

public class AppUtility {
    public static String getAppStoragePath(Context context, String dir) {
        String path;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            path = Environment.getExternalStorageDirectory() + "/" + context.getString(R.string.app_name);
        } else {
            path = context.getFilesDir() + "/" + context.getString(R.string.app_name);
        }

        String directory = +dir.trim().length() > 0 ? (dir) : "";
        File file = new File(path + directory);
        if (!file.exists()) file.mkdirs();

        return file.getAbsolutePath();
    }
}
