package com.example.erpnext.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.erpnext.roomDB.TypeConverters.AssignmentListConverter;
import com.example.erpnext.roomDB.TypeConverters.AttachmentsListConverter;
import com.example.erpnext.roomDB.TypeConverters.CommentListConverter;
import com.example.erpnext.roomDB.TypeConverters.ObjectsListConverter;
import com.example.erpnext.roomDB.TypeConverters.VersionListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
@TypeConverters({AttachmentsListConverter.class, ObjectsListConverter.class
        , CommentListConverter.class, VersionListConverter.class, AssignmentListConverter.class})
public class DocInfo {

    @SerializedName("attachments")
    @Expose
    private List<Attachment> attachments = null;
    @SerializedName("attachment_logs")
    @Expose
    private List<Object> attachmentLogs = null;
    @SerializedName("communications")
    @Expose
    private List<Object> communications = null;
    @SerializedName("automated_messages")
    @Expose
    private List<Object> automatedMessages = null;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;
    @SerializedName("total_comments")
    @Expose
    private Integer totalComments;
    @SerializedName("versions")
    @Expose
    private List<Version> versions = null;
    @SerializedName("assignments")
    @Expose
    private List<Assignment> assignments = null;
    @SerializedName("assignment_logs")
    @Expose
    private List<Object> assignmentLogs = null;
    @SerializedName("permissions")
    @Expose
    @Embedded
    private SavePermissions permissions;
    @SerializedName("shared")
    @Expose
    private List<Object> shared = null;
    @SerializedName("info_logs")
    @Expose
    private List<Object> infoLogs = null;
    @SerializedName("share_logs")
    @Expose
    private List<Object> shareLogs = null;
    @SerializedName("like_logs")
    @Expose
    private List<Object> likeLogs = null;
    @SerializedName("views")
    @Expose
    private List<Object> views = null;
    @SerializedName("energy_point_logs")
    @Expose
    private List<Object> energyPointLogs = null;
    @SerializedName("additional_timeline_content")
    @Expose
    private List<Object> additionalTimelineContent = null;
    @SerializedName("milestones")
    @Expose
    private List<Object> milestones = null;
    @SerializedName("is_document_followed")
    @Expose
    @Embedded
    private Object isDocumentFollowed;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("document_email")
    @Expose
    @Embedded
    private Object documentEmail;

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Object> getAttachmentLogs() {
        return attachmentLogs;
    }

    public void setAttachmentLogs(List<Object> attachmentLogs) {
        this.attachmentLogs = attachmentLogs;
    }

    public List<Object> getCommunications() {
        return communications;
    }

    public void setCommunications(List<Object> communications) {
        this.communications = communications;
    }

    public List<Object> getAutomatedMessages() {
        return automatedMessages;
    }

    public void setAutomatedMessages(List<Object> automatedMessages) {
        this.automatedMessages = automatedMessages;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(Integer totalComments) {
        this.totalComments = totalComments;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public List<Object> getAssignmentLogs() {
        return assignmentLogs;
    }

    public void setAssignmentLogs(List<Object> assignmentLogs) {
        this.assignmentLogs = assignmentLogs;
    }

    public SavePermissions getPermissions() {
        return permissions;
    }

    public void setPermissions(SavePermissions permissions) {
        this.permissions = permissions;
    }

    public List<Object> getShared() {
        return shared;
    }

    public void setShared(List<Object> shared) {
        this.shared = shared;
    }

    public List<Object> getInfoLogs() {
        return infoLogs;
    }

    public void setInfoLogs(List<Object> infoLogs) {
        this.infoLogs = infoLogs;
    }

    public List<Object> getShareLogs() {
        return shareLogs;
    }

    public void setShareLogs(List<Object> shareLogs) {
        this.shareLogs = shareLogs;
    }

    public List<Object> getLikeLogs() {
        return likeLogs;
    }

    public void setLikeLogs(List<Object> likeLogs) {
        this.likeLogs = likeLogs;
    }

    public List<Object> getViews() {
        return views;
    }

    public void setViews(List<Object> views) {
        this.views = views;
    }

    public List<Object> getEnergyPointLogs() {
        return energyPointLogs;
    }

    public void setEnergyPointLogs(List<Object> energyPointLogs) {
        this.energyPointLogs = energyPointLogs;
    }

    public List<Object> getAdditionalTimelineContent() {
        return additionalTimelineContent;
    }

    public void setAdditionalTimelineContent(List<Object> additionalTimelineContent) {
        this.additionalTimelineContent = additionalTimelineContent;
    }

    public List<Object> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Object> milestones) {
        this.milestones = milestones;
    }

    public Object getIsDocumentFollowed() {
        return isDocumentFollowed;
    }

    public void setIsDocumentFollowed(Object isDocumentFollowed) {
        this.isDocumentFollowed = isDocumentFollowed;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Object getDocumentEmail() {
        return documentEmail;
    }

    public void setDocumentEmail(Object documentEmail) {
        this.documentEmail = documentEmail;
    }

}
