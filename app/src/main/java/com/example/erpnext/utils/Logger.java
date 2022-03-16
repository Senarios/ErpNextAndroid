package com.example.erpnext.utils;

import android.util.Log;

import com.example.erpnext.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by devandro on 11/9/16.
 */

public class Logger {

    public static String EXCEPTION = "EXCEPTION";
    public static Pattern DELIMITER = Pattern.compile("XYZ.123");

    public static void log(final String TAG, final String MSG) {
        if (TAG != null && MSG != null) {
        }
    }

    public static void log(final String MSG) {
        if (MSG != null) {
            Log.d("com.dryvecars.dryve", MSG);
            //Log.d(MainApplication.getAppContext().getString(R.string.app_name), MSG);
        }
    }

    public static void log(final String TAG, final Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        log(TAG, sw.toString());
    }

    public static void log(final String TAG, final Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        log(TAG, sw.toString());
    }


    private synchronized static void writeLog(String message) {
        try {
            String directory = FilesIO.getAppInternalFolderPath();
            File file = new File(directory, Utils.getString(R.string.app_name) + "_logs.txt");

            if (fileSize() > 2) {
                file.delete();
                file = new File(directory, Utils.getString(R.string.app_name) + "_logs.txt");
            }

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Logger.log("Logger", e);
                    return;
                }
            }

            if (Configurations.isDevelopment()) {
                FilesIO.writeFile(file, message);
            } else {
                String encryptedString = Encryption.getDefault().encryptOrNull(message) + DELIMITER.pattern();
                FilesIO.writeFile(file, encryptedString);
            }
        } catch (Exception e) {
        }
    }

    public static String readFileWithDecryption() {

        File file = new File(getFilePath());
        if (!file.exists())
            return "File not found!";
        StringBuilder text = new StringBuilder();
        Scanner read = null;
        try {
            read = new Scanner(file);
            if (!Configurations.isDevelopment()) read.useDelimiter(DELIMITER);
            while (read.hasNext()) {
                String line = read.next();
                if (Configurations.isDevelopment()) {
                    text.append(line);
                } else {
                    String decryptedString = Encryption.getDefault().decryptOrNull(line);
                    text.append(decryptedString);
                }
                text.append('\n');
            }
            read.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return text.toString();
    }


    /**
     * @return will create a new file object in app internal folder path in production.
     * its your responsibility to clean up the created file
     **/
    public static File readFile() {
        if (Configurations.isDevelopment())
            return new File(Logger.getFilePath());
        else {
            File file = new File(FilesIO.getAppInternalFolderPath() + "/" + "log.txt");
            if (!file.exists())
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return FilesIO.writeFile(file, readFileWithDecryption());
        }
    }

    public static long fileSize() {
        File file = new File(getFilePath());
        if (file.exists()) {
            long sizeInBytes = file.length();
            long sizeInMb = sizeInBytes / (1024 * 1024);
            return sizeInMb;
        }
        return 0;
    }

    public static String getFilePath() {
        return FilesIO.getAppInternalFolderPath() + "/" + Utils.getString(R.string.app_name) + "_logs.txt";
    }

    public static void clearLogs() {
        String directory = FilesIO.getAppInternalFolderPath();
        File file = new File(directory, Utils.getString(R.string.app_name) + "_logs.txt");
        file.delete();
    }

    public static String readException(Exception e) {
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } else {
            return "Error not found";
        }
    }

    public static String readException(Throwable e) {
        if (e != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        } else {
            return "Error Not Found";
        }
    }
}
