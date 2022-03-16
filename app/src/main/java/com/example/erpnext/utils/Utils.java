package com.example.erpnext.utils;

import static com.example.erpnext.utils.RequestCodes.TWO_MINUTES;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    private static final CharSequence ALLOWED_CHARACTERS = "qwertyuiopasdfghjklzxcvbnm1234567890";
    public static ProgressDialog progressDialog = null;

    public static void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog.cancel();
            progressDialog.hide();
        }
    }

    public static void forceHideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    public static void changeImageSrc(Context context, ImageView imageView, int drawable) {
        imageView.setImageDrawable(ContextCompat.getDrawable(context, drawable));
    }


    public static String getDuration(Context context, String path) {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + path;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Uri uri = Uri.parse(path);
        retriever.setDataSource(context, uri);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInmillisec = Long.parseLong(time);
        long duration = timeInmillisec / 1000;
        long hours = duration / 3600;
        long minutes = (duration - hours * 3600) / 60;
        long seconds = duration - (hours * 3600 + minutes * 60);
        String timeString;
        if (hours > 0) {
            timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeString = String.format("%02d:%02d", minutes, seconds);
        }
        return timeString;
    }

    public static boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1;
        }
        return false;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public static String getDurationFromDownloads(Context context, String path) {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + path;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Uri uri = Uri.parse(path);
        retriever.setDataSource(context, uri);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInmillisec = Long.parseLong(time);
        long duration = timeInmillisec / 1000;
        long hours = duration / 3600;
        long minutes = (duration - hours * 3600) / 60;
        long seconds = duration - (hours * 3600 + minutes * 60);
        String timeString;
        if (hours > 0) {
            timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeString = String.format("%02d:%02d", minutes, seconds);
        }
        return timeString;
    }

    public static void setSearchAdapter(Activity activity, AutoCompleteTextView textView, SearchLinkResponse searchItemResponse) {
        List<String> list = new ArrayList<>();
        for (SearchResult searchResult : searchItemResponse.getResults()) {
            list.add(searchResult.getValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, list);
        textView.setAdapter(adapter);
        textView.showDropDown();
    }
    public static void setSearchAdapter(Activity activity, AutoCompleteTextView textView, List<SearchResult> searchResultList) {
        List<String> list = new ArrayList<>();
        for (SearchResult searchResult : searchResultList) {
            list.add(searchResult.getValue());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_1, list);
        textView.setAdapter(adapter);
        textView.showDropDown();
    }
    public static File convertToPDF(Bitmap bitmap, File imageFile) {
        File myPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + R.string.app_name + System.currentTimeMillis() + ".pdf");

        try {
            Document document = new Document(PageSize.A4, 25, 25, 30, 30);
            PdfWriter.getInstance(document, new FileOutputStream(myPath));
            document.open();
            Image image = Image.getInstance(imageFile.getAbsolutePath());
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
            image.scalePercent(scaler);
            image.scaleToFit(PageSize.A4);
            document.setPageSize(PageSize.A4);
            document.setMargins(0, 0, 1, 1);
            document.newPage();
            image.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(image);
            document.close();
            if (imageFile.exists()) imageFile.delete();
        } catch (Exception e) {
            Notify.Toast(e.getMessage());
        }
        return myPath;
    }

    public static void openPDFFile(File pdfFile, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        activity.startActivity(intent);
    }

    public static String addStorageDirectory(String path) {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + path;
        return path;
    }

    public static String removeExtra(String path) {
        path = path.replace(Environment.getExternalStorageDirectory().getAbsolutePath() + "/", "");
        return path;
    }

    public static boolean isCause(
            Class<? extends Throwable> expected,
            Throwable exc) {
        return expected.isInstance(exc) || (
                exc != null && isCause(expected, exc.getCause())
        );
    }

    public static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public static BigDecimal setDecimalPlaces(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, BigDecimal.ROUND_UP);
        return bd;
    }

    public static Loading getLoading(Activity activity) {
        return Loading.make(activity)
                .setCancelable(false)
                .setMessage(Utils.getString(R.string.pleaseWait))
                .show();
    }

    public static String getString(int id) {
        return MainApp.getAppContext().getResources().getString(id);
    }

    public static Loading getLoading(Activity activity, String mesage) {
        return Loading.make(activity)
                .setCancelable(false)
                .setMessage(mesage)
                .show();
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MainApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//        boolean success = false;
//        new Thread(() -> {
//            //Do whatever
//            try {
//                URL url = new URL("https://google.com");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setConnectTimeout(10000);
//                connection.connect();
//                success = connection.getResponseCode() == 200;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//        return success;
    }

    public static boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }

    public static void showLoading(Activity activity) {
        progressDialog = new ProgressDialog(activity, R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Fetching data...");
        progressDialog.show();

    }

    public static void cancelLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static String getPath(Uri uri, Activity activity) {

        String path = null;
        String[] projection = {MediaStore.Files.FileColumns.DATA};
        Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);

        if (cursor == null) {
            path = uri.getPath();
        } else {
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }

    public static void downloadFile(Activity activity, String url, String path, String type) {
        DownloadManager.Request request1 = new DownloadManager.Request(Uri.parse(url));
        switch (type) {
            case "voice":
                request1.setDescription("Downloading voice note...");
                request1.setTitle("Voice Note");
                break;
            case "file":
                request1.setDescription("Downloading file...");
                request1.setTitle("File");
                break;
        }
        //appears the same in Notification bar while downloading
        request1.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request1.allowScanningByMediaScanner();
        request1.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        if (type.equalsIgnoreCase("voice")) {
            request1.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, path);
        } else {
            request1.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Helloo/Files/" + System.currentTimeMillis() + ".pdf");
        }
        DownloadManager manager1 = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        manager1.enqueue(request1);
        if (DownloadManager.STATUS_SUCCESSFUL == 8) {
//            Notify.Toast("Downloading");
        }
    }

    /**
     * Determines whether one location reading is better than the current location fix
     *
     * @param location            The new location that you want to evaluate
     * @param currentBestLocation The current location fix, to which you want to compare the new one.
     */
    public static boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location,
        // because the user has likely moved.
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse.
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else return isNewer && !isSignificantlyLessAccurate && isFromSameProvider;
    }

    // Checks whether two providers are the same
    private static boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


//    public static Bitmap customImageIcon(String name) {
//        String array[] = name.split(" ");
//        if (array.length == 1) {
//            String fname = array[0];
//            name = String.valueOf(fname.charAt(0));
//        } else {
//            String fname = array[0];
//            String lname = array[1];
//            name = String.valueOf(fname.charAt(0)) + String.valueOf(lname.charAt(0));
//        }
//
//        Drawable drawable = TextDrawable.builder()
//                .beginConfig()
//                .width(60)
//                .height(60)
//                .textColor(Color.WHITE)
//                .useFont(Typeface.DEFAULT)
//                .fontSize(25) /* size in px */
//                .toUpperCase()
//                .endConfig()
//                .buildRound(name, Color.parseColor("#49ae8e"));
//
//        return drawableToBitmap(drawable);
//    }

//    public static Bitmap OneLetterCustomImageIcon(String name) {
//        String array[] = name.split(" ");
//        if (array.length == 1) {
//            String fname = array[0];
//            name = String.valueOf(fname.charAt(0));
//        } else {
//            String fname = array[0];
//            String lname = array[1];
//            name = String.valueOf(fname.charAt(0));
//        }
//
//        Drawable drawable = TextDrawable.builder()
//                .beginConfig()
//                .width(60)
//                .height(60)
//                .textColor(Color.parseColor("#BBFBE7"))
//                .useFont(Typeface.DEFAULT)
//                .fontSize(25) /* size in px */
//                .toUpperCase()
//                .bold()
//                .endConfig()
//                .buildRound(name, Color.parseColor("#59D3AC"));
//
//        return drawableToBitmap(drawable);
//    }

    public static Intent fileChooser() {
        String[] mimeTypes =
                {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        return intent;
    }

    public static void openImage(Context Context, File myPath) {
        Uri uri = Uri.fromFile(myPath);
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//        String mime = "*/*";
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        if (mimeTypeMap.hasExtension(
//                mimeTypeMap.getFileExtensionFromUrl(uri.toString())))
//            mime = mimeTypeMap.getMimeTypeFromExtension(
//                    mimeTypeMap.getFileExtensionFromUrl(uri.toString()));
//        intent.setDataAndType(uri, mime);
//        Context.startActivity(intent);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/*");
        Context.startActivity(intent);
    }

    public static void loadImageToGallery(Activity activity, File myPath) {
        Uri contentUri = Uri.fromFile(myPath);
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(contentUri);
        activity.sendBroadcast(mediaScanIntent);
    }

    public static boolean isOnline() {

        return false;
    }
}
