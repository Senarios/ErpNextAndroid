package com.example.erpnext.models;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Permission {

    @SerializedName("doctype")
    @Expose
    private String doctype;
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
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("if_owner")
    @Expose
    private Integer ifOwner;
    @SerializedName("permlevel")
    @Expose
    private Integer permlevel;
    @SerializedName("select")
    @Expose
    private Integer select;
    @SerializedName("read")
    @Expose
    private Integer read;
    @SerializedName("write")
    @Expose
    private Integer write;
    @SerializedName("create")
    @Expose
    private Integer create;
    @SerializedName("delete")
    @Expose
    private Integer delete;
    @SerializedName("submit")
    @Expose
    private Integer submit;
    @SerializedName("cancel")
    @Expose
    private Integer cancel;
    @SerializedName("amend")
    @Expose
    private Integer amend;
    @SerializedName("report")
    @Expose
    private Integer report;
    @SerializedName("export")
    @Expose
    private Integer export;
    @SerializedName("import")
    @Expose
    private Integer _import;
    @SerializedName("set_user_permissions")
    @Expose
    private Integer setUserPermissions;
    @SerializedName("share")
    @Expose
    private Integer share;
    @SerializedName("print")
    @Expose
    private Integer print;
    @SerializedName("email")
    @Expose
    private Integer email;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("link_doctype")
    @Expose
    private String linkDoctype;
    @SerializedName("link_fieldname")
    @Expose
    private String linkFieldname;
    @SerializedName("hidden")
    @Expose
    private Integer hidden;
    @SerializedName("custom")
    @Expose
    private Integer custom;
    @SerializedName("added")
    @Expose
    private Boolean added;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getIfOwner() {
        return ifOwner;
    }

    public void setIfOwner(Integer ifOwner) {
        this.ifOwner = ifOwner;
    }

    public Integer getPermlevel() {
        return permlevel;
    }

    public void setPermlevel(Integer permlevel) {
        this.permlevel = permlevel;
    }

    public Integer getSelect() {
        return select;
    }

    public void setSelect(Integer select) {
        this.select = select;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public Integer getWrite() {
        return write;
    }

    public void setWrite(Integer write) {
        this.write = write;
    }

    public Integer getCreate() {
        return create;
    }

    public void setCreate(Integer create) {
        this.create = create;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public Integer getSubmit() {
        return submit;
    }

    public void setSubmit(Integer submit) {
        this.submit = submit;
    }

    public Integer getCancel() {
        return cancel;
    }

    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }

    public Integer getAmend() {
        return amend;
    }

    public void setAmend(Integer amend) {
        this.amend = amend;
    }

    public Integer getReport() {
        return report;
    }

    public void setReport(Integer report) {
        this.report = report;
    }

    public Integer getExport() {
        return export;
    }

    public void setExport(Integer export) {
        this.export = export;
    }

    public Integer getImport() {
        return _import;
    }

    public void setImport(Integer _import) {
        this._import = _import;
    }

    public Integer getSetUserPermissions() {
        return setUserPermissions;
    }

    public void setSetUserPermissions(Integer setUserPermissions) {
        this.setUserPermissions = setUserPermissions;
    }

    public Integer getShare() {
        return share;
    }

    public void setShare(Integer share) {
        this.share = share;
    }

    public Integer getPrint() {
        return print;
    }

    public void setPrint(Integer print) {
        this.print = print;
    }

    public Integer getEmail() {
        return email;
    }

    public void setEmail(Integer email) {
        this.email = email;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLinkDoctype() {
        return linkDoctype;
    }

    public void setLinkDoctype(String linkDoctype) {
        this.linkDoctype = linkDoctype;
    }

    public String getLinkFieldname() {
        return linkFieldname;
    }

    public void setLinkFieldname(String linkFieldname) {
        this.linkFieldname = linkFieldname;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public Integer getCustom() {
        return custom;
    }

    public void setCustom(Integer custom) {
        this.custom = custom;
    }

    public Boolean getAdded() {
        return added;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }
}