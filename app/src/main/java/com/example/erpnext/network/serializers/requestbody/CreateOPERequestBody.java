package com.example.erpnext.network.serializers.requestbody;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.erpnext.models.BalanceDetail;
import com.example.erpnext.roomDB.TypeConverters.BalanceDetailListConverter;
import com.example.erpnext.roomDB.TypeConverters.JSONObjectTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

@Entity
@TypeConverters({JSONObjectTypeConverter.class, BalanceDetailListConverter.class})
public class CreateOPERequestBody {
    @SerializedName("doc")
    @Expose
    @ColumnInfo(name = "doc")
    JSONObject doc;
    @SerializedName("action")
    @Expose
    String action;
    @SerializedName("balance_details")
    @Expose
    List<BalanceDetail> balanceDetailList;

    public CreateOPERequestBody() {
    }

    public JSONObject getDoc() {
        return doc;
    }

    public void setDoc(JSONObject doc) {
        this.doc = doc;
    }

    public List<BalanceDetail> getBalanceDetailList() {
        return balanceDetailList;
    }

    public void setBalanceDetailList(List<BalanceDetail> balanceDetailList) {
        this.balanceDetailList = balanceDetailList;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
