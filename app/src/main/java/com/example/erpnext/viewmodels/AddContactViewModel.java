package com.example.erpnext.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.erpnext.adapters.ContactNoAdapter;
import com.example.erpnext.adapters.EmailAdapter;
import com.example.erpnext.adapters.ReferenceAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.models.ContactNo;
import com.example.erpnext.models.Email;
import com.example.erpnext.models.Reference;
import com.example.erpnext.network.serializers.response.GetValuesResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.repositories.AddContactRepo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AddContactViewModel extends ViewModel {
    private MutableLiveData<List<List<String>>> reportViewList;
    private AddContactRepo repo;

    public AddContactViewModel() {
        repo = AddContactRepo.getInstance();
    }


    public void getSearchLinkApi(String text, String doctype, String query, String filters, int requestCode) {
        repo.getSearchLink(requestCode, doctype, query, filters, text);
    }

    public void geValue(String selectedLinkName, String fieldname, String filters) {
        repo.getValue(selectedLinkName, fieldname, filters);
    }

    public LiveData<SearchLinkResponse> getSearchResult() {
        return repo.getSearchResult();
    }

    public void saveDocApi(EmailAdapter emailAdapter, ContactNoAdapter contactNoAdapter, ReferenceAdapter referenceAdapter, HashMap<String, String> data) {
        try {
            JSONObject jsonDoc = new JSONObject("{\"docstatus\":0,\"doctype\":\"Contact\",\"name\":\"new-contact-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"sync_with_google_contacts\":0,\"pulled_from_google_contacts\":0,\"is_primary_contact\":0,\"unsubscribed\":0}");

            JSONObject jsonObject = new JSONObject(data);

            JSONObject json = new JSONObject();
            Iterator i1 = jsonObject.keys();
            Iterator i2 = jsonDoc.keys();
            String tmp_key;
            while (i1.hasNext()) {
                tmp_key = (String) i1.next();
                json.put(tmp_key, jsonObject.get(tmp_key));
            }
            while (i2.hasNext()) {
                tmp_key = (String) i2.next();
                json.put(tmp_key, jsonDoc.get(tmp_key));
            }

            JSONArray emailsArray = new JSONArray();
            JSONArray contactsArray = new JSONArray();
            JSONArray refArray = new JSONArray();
            if (emailAdapter != null && emailAdapter.getAllItems() != null && !emailAdapter.getAllItems().isEmpty()) {
                List<Email> emailList = emailAdapter.getAllItems();
                for (Email email : emailList) {
                    JSONObject jsonObject1 = new JSONObject("{\"docstatus\":0,\"doctype\":\"Contact Email\",\"name\":\"new-contact-email-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"parent\":\"new-contact-1\",\"parentfield\":\"email_ids\",\"parenttype\":\"Contact\",\"idx\":1,\"__unedited\":false}");
                    jsonObject1.put("email_id", email.getEmail());
                    if (email.isPrimary()) {
                        jsonObject1.put("is_primary", 1);
                    } else {
                        jsonObject1.put("is_primary", 0);
                    }

                    emailsArray.put(jsonObject1);
                }
            }
            if (contactNoAdapter != null && contactNoAdapter.getAllItems() != null && !contactNoAdapter.getAllItems().isEmpty()) {
                List<ContactNo> contactNoList = contactNoAdapter.getAllItems();
                for (ContactNo contactNo : contactNoList) {
                    JSONObject jsonObject1 = new JSONObject("{\"docstatus\":0,\"doctype\":\"Contact Phone\",\"name\":\"new-contact-phone-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"is_primary_mobile_no\":0,\"parent\":\"new-contact-1\",\"parentfield\":\"phone_nos\",\"parenttype\":\"Contact\",\"idx\":1,\"__unedited\":false}");
                    jsonObject1.put("phone", contactNo.getNumber());
                    if (contactNo.isPrimary()) {
                        jsonObject1.put("is_primary_phone", 1);
                    } else {
                        jsonObject1.put("is_primary_phone", 0);
                    }
                    contactsArray.put(jsonObject1);
                }
            }

            if (referenceAdapter != null && referenceAdapter.getAllItems() != null && !referenceAdapter.getAllItems().isEmpty()) {
                List<Reference> referenceList = referenceAdapter.getAllItems();
                for (Reference reference : referenceList) {
                    JSONObject jsonObject1 = new JSONObject("{\"docstatus\":0,\"doctype\":\"Dynamic Link\",\"name\":\"new-dynamic-link-1\",\"__islocal\":1,\"__unsaved\":1,\"owner\":\"" + AppSession.get("email") + "\",\"parent\":\"new-contact-1\",\"parentfield\":\"links\",\"parenttype\":\"Contact\",\"idx\":1,\"__unedited\":false}");
                    jsonObject1.put("link_doctype", reference.getLinkDocType());
                    jsonObject1.put("link_name", reference.getLinkName());
                    jsonObject1.put("link_title", reference.getLinkTitle());
                    refArray.put(jsonObject1);
                }
            }
            json.putOpt("email_ids", emailsArray);
            json.putOpt("phone_nos", contactsArray);
            json.putOpt("links", refArray);
            repo.saveDoc(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public LiveData<JSONObject> docSaved() {
        return repo.getDoc();
    }

    public LiveData<GetValuesResponse> getValue() {
        return repo.getValue();
    }
}
