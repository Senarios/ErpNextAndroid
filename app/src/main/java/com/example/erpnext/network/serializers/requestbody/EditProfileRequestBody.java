package com.example.erpnext.network.serializers.requestbody;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.erpnext.models.Payments;
import com.example.erpnext.roomDB.TypeConverters.JSONObjectTypeConverter;
import com.example.erpnext.roomDB.TypeConverters.ObjectsListConverter;
import com.example.erpnext.roomDB.TypeConverters.PaymentsListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

@Entity
@TypeConverters({JSONObjectTypeConverter.class, PaymentsListConverter.class, ObjectsListConverter.class})
public class EditProfileRequestBody {
    @SerializedName("doc")
    @Expose
    @ColumnInfo(name = "doc")
    JSONObject doc;
    @SerializedName("action")
    @Expose
    String action;
    @SerializedName("payments")
    @Expose
    List<Payments> paymentsList;
    @SerializedName("itemsGroupList")
    @Expose
    List<Object> itemsGroupList;
    @SerializedName("applicableForuserslist")
    @Expose
    List<Object> applicableForuserslist;
    @SerializedName("customergroupslist")
    @Expose
    List<Object> customergroupslist;

    public EditProfileRequestBody() {
    }

    public JSONObject getDoc() {
        return doc;
    }

    public void setDoc(JSONObject doc) {
        this.doc = doc;
    }

    public List<Payments> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(List<Payments> paymentsList) {
        this.paymentsList = paymentsList;
    }

    public List<Object> getCustomergroupslist() {
        return customergroupslist;
    }

    public void setCustomergroupslist(List<Object> customergroupslist) {
        this.customergroupslist = customergroupslist;
    }

    public List<Object> getItemsGroupList() {
        return itemsGroupList;
    }

    public void setItemsGroupList(List<Object> itemsGroupList) {
        this.itemsGroupList = itemsGroupList;
    }

    public List<Object> getApplicableForuserslist() {
        return applicableForuserslist;
    }

    public void setApplicableForuserslist(List<Object> applicableForuserslist) {
        this.applicableForuserslist = applicableForuserslist;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
