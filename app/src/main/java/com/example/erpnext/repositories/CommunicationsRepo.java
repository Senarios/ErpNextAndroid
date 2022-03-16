package com.example.erpnext.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.EmailContactResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class CommunicationsRepo implements OnNetworkResponse {

    private static CommunicationsRepo instance;
    public MutableLiveData<List<List<String>>> items = new MutableLiveData<>();
    public MutableLiveData<List<SearchResult>> contacts = new MutableLiveData<>();
    public MutableLiveData<JSONObject> savedDoc = new MutableLiveData<>();
    int limitSet;

    public static CommunicationsRepo getInstance() {
        if (instance == null) {
            instance = new CommunicationsRepo();
        }
        return instance;
    }


    public LiveData<List<List<String>>> getItems() {
        return items;
    }
    public LiveData<List<SearchResult>> getContacts() {
        return contacts;
    }
    public LiveData<JSONObject> emailSent() {
        return savedDoc;
    }
    public void getItemsApi(String docType, int pageLength, boolean isCommentCount, String orderBy, int limitStart) {
        limitSet = limitStart;
        String fields = "[\"`tabCommunication`.`name`\",\"`tabCommunication`.`owner`\",\"`tabCommunication`.`creation`\",\"`tabCommunication`.`modified`\",\"`tabCommunication`.`modified_by`\",\"`tabCommunication`.`_user_tags`\",\"`tabCommunication`.`_comments`\",\"`tabCommunication`.`_assign`\",\"`tabCommunication`.`_liked_by`\",\"`tabCommunication`.`docstatus`\",\"`tabCommunication`.`parent`\",\"`tabCommunication`.`parenttype`\",\"`tabCommunication`.`parentfield`\",\"`tabCommunication`.`idx`\",\"`tabCommunication`.`comment_type`\",\"`tabCommunication`.`status`\",\"`tabCommunication`.`sent_or_received`\",\"`tabCommunication`.`subject`\",\"`tabCommunication`.`recipients`\",\"`tabCommunication`.`communication_medium`\",\"`tabCommunication`.`communication_type`\",\"`tabCommunication`.`sender`\",\"`tabCommunication`.`seen`\",\"`tabCommunication`.`reference_doctype`\",\"`tabCommunication`.`reference_name`\",\"`tabCommunication`.`has_attachment`\",\"`tabCommunication`.`communication_date`\",\"`tabCommunication`.`_seen`\"]";
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REPORT_VIEW)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "Loading..."))
                .enque(Network.apis().getReportView(docType, fields, "[[\"Communication\",\"owner\",\"=\",\"" + AppSession.get("email") + "\"]]", pageLength, isCommentCount, orderBy, limitSet))
                .execute();
    }
    public void getContact(String text) {
           NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SEARCH_EMAIL)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching..."))
                .enque(Network.apis().getEmailContact(text))
                .execute();
    }
    public void sendMail(HashMap<String,String> data) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SEND_Mail)
                .autoLoadingCancel(Utils.getLoading(MainApp.INSTANCE.getCurrentActivity(), "searching..."))
                .enque(Network.apis().sendMail(data.get("recipients"),data.get("cc"),data.get("bcc"),data.get("subject"),1,data.get("send_me_a_copy"),data.get("read_receipt"),data.get("content")))
                .execute();
    }
    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.REPORT_VIEW) {
            ReportViewResponse res = (ReportViewResponse) response.body();
            if (res != null && res.getReportViewMessage() != null) {
                if (!res.getReportViewMessage().getValues().isEmpty()) {
                    if (limitSet == 0) {
                        if (items.getValue() != null && !items.getValue().isEmpty()) {
                            items.getValue().clear();
                            items.setValue(res.getReportViewMessage().getValues());
                        } else items.setValue(res.getReportViewMessage().getValues());
//                        Room.saveReportView(res, "POS Invoice");
                    } else {
                        List<List<String>> list = new ArrayList<>();
                        list = items.getValue();
                        if (res.getReportViewMessage().getValues() != null && !res.getReportViewMessage().getValues().isEmpty()) {
                            list.addAll(res.getReportViewMessage().getValues());
                            items.setValue(list);
//                            Room.saveMoreReportView(res, "POS Invoice");
                        }
                    }
                }
            }
        }else if ((int) tag == RequestCodes.API.SEARCH_EMAIL) {
            EmailContactResponse res = (EmailContactResponse) response.body();
            if (res != null && res.getResults() != null) {
                contacts.setValue(res.getResults());
            }
        }else if ((int) tag == RequestCodes.API.SEND_Mail) {
            JSONObject res = (JSONObject) response.body();
            if (res != null) {
                savedDoc.setValue(res);
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {
//        Notify.ToastLong(response.getServerMessages());
    }
}
