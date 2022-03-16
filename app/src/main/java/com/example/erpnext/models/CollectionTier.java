package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectionTier {
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("__islocal")
    @Expose
    private Integer islocal;
    @SerializedName("__unsaved")
    @Expose
    private Integer unsaved;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("parent")
    @Expose
    private String parent;
    @SerializedName("parentfield")
    @Expose
    private String parentfield;
    @SerializedName("parenttype")
    @Expose
    private String parenttype;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("__unedited")
    @Expose
    private Boolean unedited;
    @SerializedName("tier_name")
    @Expose
    private String tierName;
    @SerializedName("collection_factor")
    @Expose
    private Integer collectionFactor;
    @SerializedName("min_spent")
    @Expose
    private Integer minSpent;

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIslocal() {
        return islocal;
    }

    public void setIslocal(Integer islocal) {
        this.islocal = islocal;
    }

    public Integer getUnsaved() {
        return unsaved;
    }

    public void setUnsaved(Integer unsaved) {
        this.unsaved = unsaved;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentfield() {
        return parentfield;
    }

    public void setParentfield(String parentfield) {
        this.parentfield = parentfield;
    }

    public String getParenttype() {
        return parenttype;
    }

    public void setParenttype(String parenttype) {
        this.parenttype = parenttype;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Boolean getUnedited() {
        return unedited;
    }

    public void setUnedited(Boolean unedited) {
        this.unedited = unedited;
    }

    public String getTierName() {
        return tierName;
    }

    public void setTierName(String tierName) {
        this.tierName = tierName;
    }

    public Integer getCollectionFactor() {
        return collectionFactor;
    }

    public void setCollectionFactor(Integer collectionFactor) {
        this.collectionFactor = collectionFactor;
    }

    public Integer getMinSpent() {
        return minSpent;
    }

    public void setMinSpent(Integer minSpent) {
        this.minSpent = minSpent;
    }
}
