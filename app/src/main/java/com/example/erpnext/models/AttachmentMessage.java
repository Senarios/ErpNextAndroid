package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttachmentMessage {
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
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("file_name")
    @Expose
    private String fileName;
    @SerializedName("is_private")
    @Expose
    private Integer isPrivate;
    @SerializedName("is_home_folder")
    @Expose
    private Integer isHomeFolder;
    @SerializedName("is_attachments_folder")
    @Expose
    private Integer isAttachmentsFolder;
    @SerializedName("file_size")
    @Expose
    private Integer fileSize;
    @SerializedName("file_url")
    @Expose
    private String fileUrl;
    @SerializedName("folder")
    @Expose
    private String folder;
    @SerializedName("is_folder")
    @Expose
    private Integer isFolder;
    @SerializedName("attached_to_doctype")
    @Expose
    private String attachedToDoctype;
    @SerializedName("attached_to_name")
    @Expose
    private String attachedToName;
    @SerializedName("content_hash")
    @Expose
    private String contentHash;
    @SerializedName("uploaded_to_dropbox")
    @Expose
    private Integer uploadedToDropbox;
    @SerializedName("uploaded_to_google_drive")
    @Expose
    private Integer uploadedToGoogleDrive;
    @SerializedName("doctype")
    @Expose
    private String doctype;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Integer isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Integer getIsHomeFolder() {
        return isHomeFolder;
    }

    public void setIsHomeFolder(Integer isHomeFolder) {
        this.isHomeFolder = isHomeFolder;
    }

    public Integer getIsAttachmentsFolder() {
        return isAttachmentsFolder;
    }

    public void setIsAttachmentsFolder(Integer isAttachmentsFolder) {
        this.isAttachmentsFolder = isAttachmentsFolder;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Integer getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(Integer isFolder) {
        this.isFolder = isFolder;
    }

    public String getAttachedToDoctype() {
        return attachedToDoctype;
    }

    public void setAttachedToDoctype(String attachedToDoctype) {
        this.attachedToDoctype = attachedToDoctype;
    }

    public String getAttachedToName() {
        return attachedToName;
    }

    public void setAttachedToName(String attachedToName) {
        this.attachedToName = attachedToName;
    }

    public String getContentHash() {
        return contentHash;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    public Integer getUploadedToDropbox() {
        return uploadedToDropbox;
    }

    public void setUploadedToDropbox(Integer uploadedToDropbox) {
        this.uploadedToDropbox = uploadedToDropbox;
    }

    public Integer getUploadedToGoogleDrive() {
        return uploadedToGoogleDrive;
    }

    public void setUploadedToGoogleDrive(Integer uploadedToGoogleDrive) {
        this.uploadedToGoogleDrive = uploadedToGoogleDrive;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }
}
