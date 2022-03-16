package com.example.erpnext.models;

import androidx.room.TypeConverters;

import com.example.erpnext.roomDB.TypeConverters.LinkListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@TypeConverters({LinkListConverter.class})
public class Item {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("creation")
    @Expose
    private String creation;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modified_by")
    @Expose
    private String modifiedBy;
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
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("icon")
    @Expose
    private Object icon;
    @SerializedName("only_for")
    @Expose
    private Object onlyFor;
    @SerializedName("hidden")
    @Expose
    private Integer hidden;
    @SerializedName("link_type")
    @Expose
    private Object linkType;
    @SerializedName("link_to")
    @Expose
    private Object linkTo;
    @SerializedName("dependencies")
    @Expose
    private Object dependencies;
    @SerializedName("onboard")
    @Expose
    private Integer onboard;
    @SerializedName("is_query_report")
    @Expose
    private Integer isQueryReport;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("links")
    @Expose
    private List<Link> links = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
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

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Object getIcon() {
        return icon;
    }

    public void setIcon(Object icon) {
        this.icon = icon;
    }

    public Object getOnlyFor() {
        return onlyFor;
    }

    public void setOnlyFor(Object onlyFor) {
        this.onlyFor = onlyFor;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public Object getLinkType() {
        return linkType;
    }

    public void setLinkType(Object linkType) {
        this.linkType = linkType;
    }

    public Object getLinkTo() {
        return linkTo;
    }

    public void setLinkTo(Object linkTo) {
        this.linkTo = linkTo;
    }

    public Object getDependencies() {
        return dependencies;
    }

    public void setDependencies(Object dependencies) {
        this.dependencies = dependencies;
    }

    public Integer getOnboard() {
        return onboard;
    }

    public void setOnboard(Integer onboard) {
        this.onboard = onboard;
    }

    public Integer getIsQueryReport() {
        return isQueryReport;
    }

    public void setIsQueryReport(Integer isQueryReport) {
        this.isQueryReport = isQueryReport;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
