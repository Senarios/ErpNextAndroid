package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class SavePermissions {
    @SerializedName("if_owner")
    @Expose
    @Embedded
    private IfOwner ifOwner;
    @SerializedName("has_if_owner_enabled")
    @Expose
    private Boolean hasIfOwnerEnabled;
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
    @SerializedName("print")
    @Expose
    private Integer print;
    @SerializedName("email")
    @Expose
    private Integer email;
    @SerializedName("report")
    @Expose
    private Integer report;
    @SerializedName("import")
    @Expose
    private Integer _import;
    @SerializedName("export")
    @Expose
    private Integer export;
    @SerializedName("set_user_permissions")
    @Expose
    private Integer setUserPermissions;
    @SerializedName("share")
    @Expose
    private Integer share;

    public IfOwner getIfOwner() {
        return ifOwner;
    }

    public void setIfOwner(IfOwner ifOwner) {
        this.ifOwner = ifOwner;
    }

    public Boolean getHasIfOwnerEnabled() {
        return hasIfOwnerEnabled;
    }

    public void setHasIfOwnerEnabled(Boolean hasIfOwnerEnabled) {
        this.hasIfOwnerEnabled = hasIfOwnerEnabled;
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

    public Integer getReport() {
        return report;
    }

    public void setReport(Integer report) {
        this.report = report;
    }

    public Integer getImport() {
        return _import;
    }

    public void setImport(Integer _import) {
        this._import = _import;
    }

    public Integer getExport() {
        return export;
    }

    public void setExport(Integer export) {
        this.export = export;
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
}