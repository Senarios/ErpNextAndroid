package com.example.erpnext.utils;

import static com.example.erpnext.utils.RequestCodes.BUFFER_SIZE;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.example.erpnext.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

public class FileWriter {
    private static FileWriter fileWriter;

    private FileWriter() {
    }

    public static synchronized FileWriter getInstance() {
        if (fileWriter == null) {
            fileWriter = new FileWriter();
        }
        return fileWriter;
    }

    public static boolean checkFile(String path) {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path;
        File file = new File(path);
        return file.exists();
    }

    public static boolean checkFileFromDownloads(String path) {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + path;
        File file = new File(path);
        return file.exists();
    }

    public static String getRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static String getFilePathFromURI(Context context, Uri contentUri, String filename) {
        //copy file and send new file path
//        String fileName = getFileName(contentUri);
        File wallpaperDirectory = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            wallpaperDirectory = new File(
                    context.getExternalFilesDir(null), "/Documents");
        }
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(filename)) {
            File copyFile = new File(wallpaperDirectory + File.separator + filename);
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }

    private boolean checkFolder() {
        if (!getDirectory().exists()) {
            getDirectory().mkdirs();
        }
        return true;
    }

    public File getVoiceOutputFile(Context context) {
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + context.getResources().getString(R.string.app_name) + "/Voice Notes/" + "voice_" + System.currentTimeMillis()
                + ".m4a");
    }

    public File getDirectory() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Utils.getString(R.string.app_name));
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = new File(Environment.getExternalStorageDirectory().toString(), "whatever_directory_existing_or_not/sub_dir_if_needed/");
//        storageDir.mkdirs(); // make sure you call mkdirs() and not mkdir()
//        File image = null;
//        try {
//            image = File.createTempFile(
//                    imageFileName,  // prefix
//                    ".pdf",         // suffix
//                    storageDir      // directory
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Save a file: path for use with ACTION_VIEW intents
//
//        String mCurrentPhotoPath = "file:" + image.getAbsolutePath();
//        Log.e("our file", image.toString());
//        return image;
    }

    public boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }

    public File writeFile(String name, Bitmap bitmap) {
        checkFolder();
        File pdfFile = new File(getDirectory(), name + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(pdfFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 88, fos);
            fos.close();
        } catch (Exception e) {
        }
        return pdfFile;
    }

    private void getFile() {

    }

}