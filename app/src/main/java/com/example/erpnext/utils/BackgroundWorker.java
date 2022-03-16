package com.example.erpnext.utils;

import static android.content.ContentValues.TAG;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.erpnext.R;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.fragments.POSProfileListFragment;
import com.example.erpnext.models.PendingOrder;
import com.example.erpnext.models.ProfileDoc;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.CompleteOrderRequestBody;
import com.example.erpnext.network.serializers.requestbody.LoginRequest;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.CheckOpeningEntryResponse;
import com.example.erpnext.network.serializers.response.DocTypeResponse;
import com.example.erpnext.network.serializers.response.GetItemsResponse;
import com.example.erpnext.network.serializers.response.GetProfileDocResponse;
import com.example.erpnext.network.serializers.response.LoginResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.roomDB.data.DBSearchLink;
import com.example.erpnext.roomDB.data.Room;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class BackgroundWorker extends Worker implements OnNetworkResponse {
    ProfileDoc profileDoc;
    String doctye;

    public BackgroundWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        //Do the work here--
        getDocType("POS Opening Entry");
        this.doctye = "POS Opening Entry";


//        try {
//            Log.d("PWLOG", "Let me sleep a moment...");
//            OneTimeWorkRequest refreshWork = new OneTimeWorkRequest.Builder(UploadWorker.class).build();
//            Thread.sleep(30000);//5 minutes cycle
//            sendNotification("work","Work manager");
//            WorkManager.getInstance(getApplicationContext()).enqueueUniqueWork("OneTimeWorker", ExistingWorkPolicy.REPLACE, refreshWork);
//        } catch (InterruptedException e) {
//            Log.d("PWLOG", "Thread sleep failed...");
//            e.printStackTrace();
//        }

        return Result.success();
    }

    private void completeOrder(CompleteOrderRequestBody completeOrderRequestBody) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.COMPLETE_ORDER)
                .enque(Network.apis().completeOrder(completeOrderRequestBody))
                .execute();
    }

    private void login() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.LOGIN)
                .enque(Network.apis().login(new LoginRequest(AppSession.get("email"), AppSession.get("password"))))
                .execute();

    }

    private void checkOpeningEntry() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.CHECK_OPENING_ENTRY)
                .enque(Network.apis().checkOpeningEntry(AppSession.get("email")))
                .execute();
    }

    private void getProfileDoc(String name) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_PROFILE_DOC)
                .enque(Network.apis().getProfileDoc("POS Profile", name))
                .execute();
    }

    private void getDocType(String docType) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.DOCTYPE)
                .enque(Network.apis().getDocType(docType))
                .execute();
    }

    private void getReportView(String docType, String fields, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .enque(Network.apis().getReportView(docType, fields, pageLength, isCommentCount, orderBy, limitStart))
                .execute();
    }

    private void getPOSItems() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_POS_ITEMS)
                .enque(Network.apis().getItems(0, 40, "Standard Selling", "All Item Groups", "", profileDoc.getName()))
                .execute();
    }

    private void getLinkSearch(int requestCode) {
        String searchDoctype = null, query = null, filters = null;
        if (requestCode == RequestCodes.API.LINK_SEARCH_CUSTOMER) {
            searchDoctype = "Customer";
            query = "erpnext.controllers.queries.customer_query";
            filters = null;
        } else if (requestCode == RequestCodes.API.LINK_SEARCH_ITEM_GROUP) {
            searchDoctype = "Item Group";
            query = "erpnext.selling.page.point_of_sale.point_of_sale.item_group_query";
            filters = "{\"pos_profile\": " + "\"" + profileDoc.getName() + "\"" + "}";
        } else if (requestCode == RequestCodes.API.LINK_SEARCH_UMO) {
            searchDoctype = "UOM";
            query = null;
            filters = null;
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(requestCode)
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", searchDoctype, "0", filters, "", query)))
                .execute();
    }


    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.LOGIN) {
            LoginResponse res = (LoginResponse) response.body();
            if (res != null) {
                List<String> cookielist = response.headers().values("Set-Cookie");
                String jsessionid = (cookielist.get(0).split(";"))[0];
                String fullName = (cookielist.get(2).split(";"))[0];
                String userId = (cookielist.get(3).split(";"))[0];
                AppSession.put(Constants.IS_LOGGED_IN, true);
                AppSession.put("sid", jsessionid);
                AppSession.put("user_id", userId);
                AppSession.put("full_name", fullName);
                if (MainApp.database.pendingOrderDao().getOrders() != null) {
                    List<PendingOrder> pendingOrders = MainApp.database.pendingOrderDao().getOrders();
                    for (PendingOrder pendingOrder : pendingOrders) {
                        PendingOrder pendingOrder1 = new PendingOrder();
                        pendingOrder1.setOreder(pendingOrder.getOreder());
                        completeOrder(pendingOrder1.getOreder());
                        MainApp.database.pendingOrderDao().deleteOrder(pendingOrder);
                    }
                }
            }
        } else if ((int) tag == RequestCodes.API.COMPLETE_ORDER) {
            Log.d("orderCompleted", "order");
        }
        if ((int) tag == RequestCodes.API.DOCTYPE) {
            DocTypeResponse res = (DocTypeResponse) response.body();
            if (res != null && res.getDocs() != null) {
                String docType = res.getDocs().get(0).getName();
                this.doctye = docType;
                Room.saveDoc(res, docType);
                Gson gson = new Gson();
                if (docType.equalsIgnoreCase("POS Profile")) {
                    String jsonString = gson.toJson(POSProfileListFragment.getFields(docType));
                    getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", 0);
                } else if (docType.equalsIgnoreCase("POS Opening Entry")) {
                    String jsonString = gson.toJson(POSProfileListFragment.getFieldsPOSOpeningEntry(docType));
                    getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", 0);
                } else if (docType.equalsIgnoreCase("POS Closing Entry")) {
                    String jsonString = gson.toJson(POSProfileListFragment.getFieldsPOSOpeningEntry(docType));
                    getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", 0);
                } else if (docType.equalsIgnoreCase("Loyalty Program")) {
                    String jsonString = gson.toJson(POSProfileListFragment.getFieldsLoyaltyProgram(docType));
                    getReportView(docType, jsonString.trim(), 10, true, "`tab" + docType + "`.`modified` desc", 0);
                }
            }
        } else if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            Log.d(TAG, "onSuccess: api");
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    List<List<String>> profilesList = res.getReportViewMessage().getValues();
                    if (profilesList != null && !profilesList.isEmpty()) {
                        Room.saveReportView(res, doctye);
                    }
                }
                checkOpeningEntry();
//                if (doctye.equalsIgnoreCase("POS Profile")) {
//                    getDocType("POS Opening Entry");
//                }
//                else if (doctye.equalsIgnoreCase("POS Opening Entry")) {
//                    getDocType("POS Closing Entry");
//                } else if (doctye.equalsIgnoreCase("POS Closing Entry")) {
//                    getDocType("Loyalty Program");
//                    Utils.cancelLoading();
//                }
            }
        }
        if ((int) tag == RequestCodes.API.CHECK_OPENING_ENTRY) {
            CheckOpeningEntryResponse res = (CheckOpeningEntryResponse) response.body();
            if (res != null && !res.getCheckOpeningEntryList().isEmpty()) {
                if (res.getCheckOpeningEntryList().get(0) != null) {
                    Room.saveCheckOPE(res);
                    getProfileDoc(res.getCheckOpeningEntryList().get(0).getPosProfile());
                }
            }
        } else if ((int) tag == RequestCodes.API.GET_PROFILE_DOC) {
            GetProfileDocResponse res = (GetProfileDocResponse) response.body();
            if (res != null) {
                if (res.getProfileDoc() != null) {
                    Room.saveProfileDoc(res);
                    profileDoc = res.getProfileDoc();
                    getPOSItems();
                }
            }
        }
        if ((int) tag == RequestCodes.API.GET_POS_ITEMS) {
            GetItemsResponse res = (GetItemsResponse) response.body();
            if (res != null) {
                if (!res.getItemMessage().getCartItemList().isEmpty()) {
                    Room.savePOSItems(res, "All Item Groups", profileDoc);
                    getLinkSearch(RequestCodes.API.LINK_SEARCH_CUSTOMER);
                }
            }
        } else if ((int) tag == RequestCodes.API.LINK_SEARCH_CUSTOMER) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            if (res != null) {
                DBSearchLink.save(res, "Customer", "erpnext.controllers.queries.customer_query");
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }

    private void sendNotification(String messageBody, String title) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
//        Intent ii = new Intent(getApplicationContext(), MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, ii, 0);
//
//        mBuilder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.mipmap.app_icon);
        } else {
            mBuilder.setSmallIcon(R.mipmap.app_icon);
        }
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(messageBody);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setVibrate(new long[0]);
        mBuilder.setSound(defaultSoundUri);
//        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());

//        EventBus.getDefault().post(new MessageEvent("New Appointment"));
    }

}
