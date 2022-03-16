package com.example.erpnext.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.PendingEditPOS;
import com.example.erpnext.models.PendingInvoiceAction;
import com.example.erpnext.models.PendingLoyalty;
import com.example.erpnext.models.PendingOPE;
import com.example.erpnext.models.PendingOrder;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.CompleteOrderRequestBody;
import com.example.erpnext.network.serializers.requestbody.CreateLoyaltyRequestBody;
import com.example.erpnext.network.serializers.requestbody.CreateOPERequestBody;
import com.example.erpnext.network.serializers.requestbody.EditProfileRequestBody;
import com.example.erpnext.network.serializers.requestbody.LoginRequest;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.LoginResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class NetworkReceiver extends BroadcastReceiver implements OnNetworkResponse {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            new CheckOnlineStatus().execute();

        }
    }

    private void sendOPERequests() {
        if (MainApp.database.pendingOPEDao().getPendingOpeningEntries() != null) {
            List<PendingOPE> pendingOPES = MainApp.database.pendingOPEDao().getPendingOpeningEntries();
            for (PendingOPE pendingOPE : pendingOPES) {
                PendingOPE pendingOrder1 = new PendingOPE();
                pendingOrder1.setCreateOPERequestBody(pendingOPE.getCreateOPERequestBody());
                saveOPE(pendingOrder1.getCreateOPERequestBody());
                MainApp.database.pendingOPEDao().deleteOPE(pendingOPE);
            }
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> sendOrdersRequests(), 5000);
    }

    private void sendOrdersRequests() {
        if (MainApp.database.pendingOrderDao().getOrders() != null && !MainApp.database.pendingOrderDao().getOrders().isEmpty()) {
            List<PendingOrder> pendingOrders = MainApp.database.pendingOrderDao().getOrders();
            for (PendingOrder pendingOrder : pendingOrders) {
                PendingOrder pendingOrder1 = new PendingOrder();
                pendingOrder1.setOreder(pendingOrder.getOreder());
                completeOrder(pendingOrder1.getOreder());
                MainApp.database.pendingOrderDao().deleteOrder(pendingOrder);
            }
        }
    }

    private void sendLoyaltyRequests() {
        if (MainApp.database.pendingLoyaltyDao().getPendingLoyalty() != null && !MainApp.database.pendingLoyaltyDao().getPendingLoyalty().isEmpty()) {
            List<PendingLoyalty> pendingLoyalties = MainApp.database.pendingLoyaltyDao().getPendingLoyalty();
            for (PendingLoyalty pendingLoyalty : pendingLoyalties) {
                PendingLoyalty pendingOrder1 = new PendingLoyalty();
                pendingOrder1.setCreateOPERequestBody(pendingLoyalty.getCreateOPERequestBody());
                completeLoyalty(pendingOrder1.getCreateOPERequestBody());
                MainApp.database.pendingLoyaltyDao().delete(pendingLoyalty);
            }
        }
    }

    private void sendEditProRequests() {
        if (MainApp.database.pendingEditPOSDao().getPendingEditPOS() != null && !MainApp.database.pendingEditPOSDao().getPendingEditPOS().isEmpty()) {
            List<PendingEditPOS> pendingLoyalties = MainApp.database.pendingEditPOSDao().getPendingEditPOS();
            for (PendingEditPOS pendingEditPOS1 : pendingLoyalties) {
                PendingEditPOS pendingEditPOS = new PendingEditPOS();
                pendingEditPOS.setEditProfileRequestBody(pendingEditPOS1.getEditProfileRequestBody());
                saveDoc(pendingEditPOS.getEditProfileRequestBody());
                MainApp.database.pendingEditPOSDao().delete(pendingEditPOS1);
            }
        }
    }

    private void sendCancelInvoices() {
        if (MainApp.database.pendingCancelInvoiceDao().getPendingForCancel() != null && !MainApp.database.pendingCancelInvoiceDao().getPendingForCancel().isEmpty()) {
            List<PendingInvoiceAction> invoiceList = MainApp.database.pendingCancelInvoiceDao().getPendingForCancel();
            List<PendingInvoiceAction> cancelInvoicesList = new ArrayList<>();
            List<PendingInvoiceAction> deleteInvoicesList = new ArrayList<>();
            for (PendingInvoiceAction pendingInvoiceAction : invoiceList) {
                if (pendingInvoiceAction.getCancelInvoiceRequestBody().getAction().equalsIgnoreCase("cancel")) {
                    cancelInvoicesList.add(pendingInvoiceAction);
                } else if (pendingInvoiceAction.getCancelInvoiceRequestBody().getAction().equalsIgnoreCase("delete")) {
                    deleteInvoicesList.add(pendingInvoiceAction);
                }
            }
            for (PendingInvoiceAction invoice : cancelInvoicesList) {
                PendingInvoiceAction cancelInvoice = new PendingInvoiceAction();
                cancelInvoice.setCancelInvoiceRequestBody(invoice.getCancelInvoiceRequestBody());
                changeStatus(invoice.uid, cancelInvoice.getCancelInvoiceRequestBody().getDoctype(), cancelInvoice.getCancelInvoiceRequestBody().getName(), cancelInvoice.getCancelInvoiceRequestBody().getAction());
//                MainApp.database.pendingCancelInvoiceDao().delete(invoice);
            }
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    for (PendingInvoiceAction invoice : deleteInvoicesList) {
                        PendingInvoiceAction cancelInvoice = new PendingInvoiceAction();
                        cancelInvoice.uid = invoice.uid;
                        cancelInvoice.setCancelInvoiceRequestBody(invoice.getCancelInvoiceRequestBody());
                        changeStatus(invoice.uid, cancelInvoice.getCancelInvoiceRequestBody().getDoctype(), cancelInvoice.getCancelInvoiceRequestBody().getName(), cancelInvoice.getCancelInvoiceRequestBody().getAction());
                    }
                }
            }, 10000);
        }
    }

    private void completeLoyalty(CreateLoyaltyRequestBody body) {
        JSONObject json = body.getDoc();
        Gson gson = new Gson();
        JSONArray jsonObject = null;
        try {
            jsonObject = new JSONArray(gson.toJson(body.getCollectionTierList()));
            json.putOpt("collection_rules", jsonObject);
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCodes.API.SAVE_LOYALTY)
                    .enque(Network.apis().saveLoyaltyDoc(json, body.getAction()))
                    .execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void completeOrder(CompleteOrderRequestBody completeOrderRequestBody) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.COMPLETE_ORDER)
                .enque(Network.apis().completeOrder(completeOrderRequestBody))
                .execute();
    }

    public void login() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.LOGIN)
                .enque(Network.apis().login(new LoginRequest(AppSession.get("email"), AppSession.get("password"))))
                .execute();

    }

    private void changeStatus(Integer uid, String doctype, String names, String action) {
        List<String> list = new ArrayList<>();
        list.add(names);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(new Gson().toJson(list));
            if (action.equalsIgnoreCase("cancel")) {
                NetworkCall.make()
                        .setCallback(this)
                        .setTag(RequestCodes.API.CHANGE_STATUS)
                        .enque(Network.apis().changeInvoiceStatus(uid, doctype, action, jsonArray))
                        .execute();
            } else if (action.equalsIgnoreCase("delete")) {
                NetworkCall.make()
                        .setCallback(this)
                        .setTag(RequestCodes.API.DELETE_INVOICE)
                        .enque(Network.apis().deleteInvoice(uid, doctype, jsonArray))
                        .execute();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveOPE(CreateOPERequestBody body) {
        JSONObject json = body.getDoc();
        Gson gson = new Gson();
        JSONArray jsonObject = null;
        try {
            jsonObject = new JSONArray(gson.toJson(body.getBalanceDetailList()));
            json.putOpt("balance_details", jsonObject);
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCodes.API.SAVE_DOC)
                    .enque(Network.apis().saveOpeningEntryDoc(json, body.getAction()))
                    .execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void saveDoc(EditProfileRequestBody body) {
        JSONObject json = body.getDoc();
        Gson gson = new Gson();
        JSONArray jsonObject, jsonObject1, jsonObject2, jsonObject3 = null;
        try {
            if (body.getPaymentsList() != null) {
                jsonObject = new JSONArray(gson.toJson(body.getPaymentsList()));
                json.putOpt("payments", jsonObject);
            } else json.putOpt("payments", new JSONArray());
            if (body.getApplicableForuserslist() != null) {
                jsonObject1 = new JSONArray(gson.toJson(body.getApplicableForuserslist()));
                json.putOpt("applicable_for_users", jsonObject1);
            } else json.putOpt("applicable_for_users", new JSONArray());
            if (body.getItemsGroupList() != null) {
                jsonObject2 = new JSONArray(gson.toJson(body.getItemsGroupList()));
                json.putOpt("item_groups", jsonObject2);
            } else json.putOpt("item_groups", new JSONArray());
            if (body.getCustomergroupslist() != null) {
                jsonObject3 = new JSONArray(gson.toJson(body.getCustomergroupslist()));
                json.putOpt("customer_groups", jsonObject3);
            } else json.putOpt("customer_groups", new JSONArray());
            NetworkCall.make()
                    .setCallback(this)
                    .setTag(RequestCodes.API.SAVE_EDITED_DOC)
                    .enque(Network.apis().saveEditedDoc(json, "Save"))
                    .execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                sendOPERequests();
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> sendLoyaltyRequests(), 7000);

                handler.postDelayed(() -> sendEditProRequests(), 10000);
                handler.postDelayed(() -> sendCancelInvoices(), 12000);

//
            }
        } else if ((int) tag == RequestCodes.API.COMPLETE_ORDER) {
            Log.d("orderCompleted", "order");
        } else if ((int) tag == RequestCodes.API.SAVE_DOC) {
            Log.d("OPECompleted", "opening_entry");
        } else if ((int) tag == RequestCodes.API.SAVE_LOYALTY) {
            Log.d("LoyaltyCompleted", "loyalty_program");
        } else if ((int) tag == RequestCodes.API.SAVE_EDITED_DOC) {
            Log.d("POSEditCompleted", "save edit doc");
        } else if ((int) tag == RequestCodes.API.CHANGE_STATUS) {
            Log.d("CHANGE_STATUS", "cancel");
            Integer uid = Integer.parseInt(call.request().header("uid"));
            MainApp.database.pendingCancelInvoiceDao().delete(uid);
        } else if ((int) tag == RequestCodes.API.DELETE_INVOICE) {
            Log.d("DELETE_INVOICE", "delete");
            Integer uid = Integer.parseInt(call.request().header("uid"));
            MainApp.database.pendingCancelInvoiceDao().delete(uid);
        }
    }


    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
//        Notify.Toast(response.getMessage());
        Log.d("DELETE_INVOICE", "delete");
    }


    private class CheckOnlineStatus extends AsyncTask<Void, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(Void... params) {
            //This is a background thread, when it finishes executing will return the result from your function.
            Boolean isOnline = isOnline();
            if (isOnline) {
                Log.d("doInBackground", "online: ");
                if (MainApp.database.pendingOPEDao().getPendingOpeningEntries() != null && !MainApp.database.pendingOPEDao().getPendingOpeningEntries().isEmpty()) {
                    login();
                } else if (MainApp.database.pendingOrderDao().getOrders() != null && !MainApp.database.pendingOrderDao().getOrders().isEmpty()) {
                    login();
                } else if (MainApp.database.pendingLoyaltyDao().getPendingLoyalty() != null && !MainApp.database.pendingLoyaltyDao().getPendingLoyalty().isEmpty()) {
                    login();
                } else if (MainApp.database.pendingEditPOSDao().getPendingEditPOS() != null && !MainApp.database.pendingEditPOSDao().getPendingEditPOS().isEmpty()) {
                    login();
                }
                if (MainApp.database.pendingCancelInvoiceDao().getPendingForCancel() != null && !MainApp.database.pendingCancelInvoiceDao().getPendingForCancel().isEmpty()) {
                    login();
                }
            } else Log.d("doInBackground", "offline: ");
            return isOnline;
        }

        protected void onPostExecute(Boolean result) {
            //Here you will receive your result from doInBackground
            //This is on the UI Thread

        }

        public boolean isOnline() {

            Runtime runtime = Runtime.getRuntime();
            try {

                Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
                int exitValue = ipProcess.waitFor();
                return (exitValue == 0);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return false;
        }

    }
}

