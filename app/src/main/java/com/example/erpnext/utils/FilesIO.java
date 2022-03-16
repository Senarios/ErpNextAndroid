package com.example.erpnext.utils;

import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.OpenableColumns;

import com.example.erpnext.app.MainApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FilesIO {
    private static final String appInternalFolderPath = Environment.getExternalStorageDirectory() + "/Android/data/" + MainApp.getAppContext().getPackageName();

    public static String getAppInternalFolderPath() {
        File dir = new File(appInternalFolderPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return appInternalFolderPath;
    }

    public static File writeFile(File file, String data) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            try {
                if (fos != null) {
                    fos.write(data.getBytes());
                    fos.write("\n".getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    public static String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = MainApp.getAppContext().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

}
