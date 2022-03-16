package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentMessage {
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
    private Object parent;
    @SerializedName("parentfield")
    @Expose
    private Object parentfield;
    @SerializedName("parenttype")
    @Expose
    private Object parenttype;
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("comment_type")
    @Expose
    private String commentType;
    @SerializedName("comment_email")
    @Expose
    private String commentEmail;
    @SerializedName("subject")
    @Expose
    private Object subject;
    @SerializedName("comment_by")
    @Expose
    private String commentBy;
    @SerializedName("published")
    @Expose
    private Integer published;
    @SerializedName("seen")
    @Expose
    private Integer seen;
    @SerializedName("reference_doctype")
    @Expose
    private String referenceDoctype;
    @SerializedName("reference_name")
    @Expose
    private String referenceName;
    @SerializedName("link_doctype")
    @Expose
    private Object linkDoctype;
    @SerializedName("link_name")
    @Expose
    private Object linkName;
    @SerializedName("reference_owner")
    @Expose
    private Object referenceOwner;
    @SerializedName("content")
    @Expose
    private String content;
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

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Object getParentfield() {
        return parentfield;
    }

    public void setParentfield(Object parentfield) {
        this.parentfield = parentfield;
    }

    public Object getParenttype() {
        return parenttype;
    }

    public void setParenttype(Object parenttype) {
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

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getCommentEmail() {
        return commentEmail;
    }

    public void setCommentEmail(String commentEmail) {
        this.commentEmail = commentEmail;
    }

    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }

    public String getCommentBy() {
        return commentBy;
    }

    public void setCommentBy(String commentBy) {
        this.commentBy = commentBy;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public Integer getSeen() {
        return seen;
    }

    public void setSeen(Integer seen) {
        this.seen = seen;
    }

    public String getReferenceDoctype() {
        return referenceDoctype;
    }

    public void setReferenceDoctype(String referenceDoctype) {
        this.referenceDoctype = referenceDoctype;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public Object getLinkDoctype() {
        return linkDoctype;
    }

    public void setLinkDoctype(Object linkDoctype) {
        this.linkDoctype = linkDoctype;
    }

    public Object getLinkName() {
        return linkName;
    }

    public void setLinkName(Object linkName) {
        this.linkName = linkName;
    }

    public Object getReferenceOwner() {
        return referenceOwner;
    }

    public void setReferenceOwner(Object referenceOwner) {
        this.referenceOwner = referenceOwner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

}
