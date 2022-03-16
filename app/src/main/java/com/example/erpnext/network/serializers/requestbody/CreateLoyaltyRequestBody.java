package com.example.erpnext.network.serializers.requestbody;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.erpnext.models.CollectionTier;
import com.example.erpnext.roomDB.TypeConverters.CollectionTierListConverter;
import com.example.erpnext.roomDB.TypeConverters.JSONObjectTypeConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.List;

@Entity
@TypeConverters({JSONObjectTypeConverter.class, CollectionTierListConverter.class})
public class CreateLoyaltyRequestBody {
    @SerializedName("doc")
    @Expose
    @ColumnInfo(name = "doc")
    JSONObject doc;
    @SerializedName("action")
    @Expose
    String action;
    @SerializedName("collection_tiers")
    @Expose
    List<CollectionTier> collectionTierList;

    public CreateLoyaltyRequestBody() {
    }

    public JSONObject getDoc() {
        return doc;
    }

    public void setDoc(JSONObject doc) {
        this.doc = doc;
    }

    public List<CollectionTier> getCollectionTierList() {
        return collectionTierList;
    }

    public void setCollectionTierList(List<CollectionTier> collectionTierList) {
        this.collectionTierList = collectionTierList;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
