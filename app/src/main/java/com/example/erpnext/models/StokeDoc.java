package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StokeDoc {
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
    @SerializedName("idx")
    @Expose
    private Integer idx;
    @SerializedName("docstatus")
    @Expose
    private Integer docstatus;
    @SerializedName("issingle")
    @Expose
    private Integer issingle;
    @SerializedName("is_tree")
    @Expose
    private Integer isTree;
    @SerializedName("istable")
    @Expose
    private Integer istable;
    @SerializedName("editable_grid")
    @Expose
    private Integer editableGrid;
    @SerializedName("track_changes")
    @Expose
    private Integer trackChanges;
    @SerializedName("module")
    @Expose
    private String module;
    @SerializedName("autoname")
    @Expose
    private String autoname;
    @SerializedName("sort_field")
    @Expose
    private String sortField;
    @SerializedName("sort_order")
    @Expose
    private String sortOrder;
    @SerializedName("read_only")
    @Expose
    private Integer readOnly;
    @SerializedName("in_create")
    @Expose
    private Integer inCreate;
    @SerializedName("allow_copy")
    @Expose
    private Integer allowCopy;
    @SerializedName("allow_rename")
    @Expose
    private Integer allowRename;
    @SerializedName("allow_import")
    @Expose
    private Integer allowImport;
    @SerializedName("hide_toolbar")
    @Expose
    private Integer hideToolbar;
    @SerializedName("track_seen")
    @Expose
    private Integer trackSeen;
    @SerializedName("max_attachments")
    @Expose
    private Integer maxAttachments;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("engine")
    @Expose
    private String engine;
    @SerializedName("is_submittable")
    @Expose
    private Integer isSubmittable;
    @SerializedName("show_name_in_global_search")
    @Expose
    private Integer showNameInGlobalSearch;
    @SerializedName("custom")
    @Expose
    private Integer custom;
    @SerializedName("beta")
    @Expose
    private Integer beta;
    @SerializedName("has_web_view")
    @Expose
    private Integer hasWebView;
    @SerializedName("allow_guest_to_view")
    @Expose
    private Integer allowGuestToView;
    @SerializedName("email_append_to")
    @Expose
    private Integer emailAppendTo;
    @SerializedName("quick_entry")
    @Expose
    private Integer quickEntry;
    @SerializedName("track_views")
    @Expose
    private Integer trackViews;
    @SerializedName("is_virtual")
    @Expose
    private Integer isVirtual;
    @SerializedName("allow_events_in_timeline")
    @Expose
    private Integer allowEventsInTimeline;
    @SerializedName("allow_auto_repeat")
    @Expose
    private Integer allowAutoRepeat;
    @SerializedName("show_preview_popup")
    @Expose
    private Integer showPreviewPopup;
    @SerializedName("index_web_pages_for_search")
    @Expose
    private Integer indexWebPagesForSearch;
    @SerializedName("fields")
    @Expose
    private List<StokeField> fields = null;
    @SerializedName("permissions")
    @Expose
    private List<Permission> permissions = null;
    @SerializedName("__js")
    @Expose
    private Object js;
    @SerializedName("__custom_js")
    @Expose
    private Object customJs;
    @SerializedName("__custom_list_js")
    @Expose
    private Object customListJs;
    @SerializedName("__assets_loaded")
    @Expose
    private Boolean assetsLoaded;
    @SerializedName("__css")
    @Expose
    private Object css;
    @SerializedName("__list_js")
    @Expose
    private Object listJs;
    @SerializedName("__calendar_js")
    @Expose
    private Object calendarJs;
    @SerializedName("__map_js")
    @Expose
    private Object mapJs;
    @SerializedName("__linked_with")
    @Expose
    private Object linkedWith;
    @SerializedName("__messages")
    @Expose
    private Object messages;
    @SerializedName("__print_formats")
    @Expose
    private Object printFormats;
    @SerializedName("__workflow_docs")
    @Expose
    private Object workflowDocs;
    @SerializedName("__form_grid_templates")
    @Expose
    private Object formGridTemplates;
    @SerializedName("__listview_template")
    @Expose
    private Object listviewTemplate;
    @SerializedName("__tree_js")
    @Expose
    private Object treeJs;
    @SerializedName("__dashboard")
    @Expose
    private Object dashboard;
    @SerializedName("__kanban_column_fields")
    @Expose
    private Object kanbanColumnFields;
    @SerializedName("__templates")
    @Expose
    private Object templates;

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

    public Integer getIssingle() {
        return issingle;
    }

    public void setIssingle(Integer issingle) {
        this.issingle = issingle;
    }

    public Integer getIsTree() {
        return isTree;
    }

    public void setIsTree(Integer isTree) {
        this.isTree = isTree;
    }

    public Integer getIstable() {
        return istable;
    }

    public void setIstable(Integer istable) {
        this.istable = istable;
    }

    public Integer getEditableGrid() {
        return editableGrid;
    }

    public void setEditableGrid(Integer editableGrid) {
        this.editableGrid = editableGrid;
    }

    public Integer getTrackChanges() {
        return trackChanges;
    }

    public void setTrackChanges(Integer trackChanges) {
        this.trackChanges = trackChanges;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAutoname() {
        return autoname;
    }

    public void setAutoname(String autoname) {
        this.autoname = autoname;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Integer readOnly) {
        this.readOnly = readOnly;
    }

    public Integer getInCreate() {
        return inCreate;
    }

    public void setInCreate(Integer inCreate) {
        this.inCreate = inCreate;
    }

    public Integer getAllowCopy() {
        return allowCopy;
    }

    public void setAllowCopy(Integer allowCopy) {
        this.allowCopy = allowCopy;
    }

    public Integer getAllowRename() {
        return allowRename;
    }

    public void setAllowRename(Integer allowRename) {
        this.allowRename = allowRename;
    }

    public Integer getAllowImport() {
        return allowImport;
    }

    public void setAllowImport(Integer allowImport) {
        this.allowImport = allowImport;
    }

    public Integer getHideToolbar() {
        return hideToolbar;
    }

    public void setHideToolbar(Integer hideToolbar) {
        this.hideToolbar = hideToolbar;
    }

    public Integer getTrackSeen() {
        return trackSeen;
    }

    public void setTrackSeen(Integer trackSeen) {
        this.trackSeen = trackSeen;
    }

    public Integer getMaxAttachments() {
        return maxAttachments;
    }

    public void setMaxAttachments(Integer maxAttachments) {
        this.maxAttachments = maxAttachments;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Integer getIsSubmittable() {
        return isSubmittable;
    }

    public void setIsSubmittable(Integer isSubmittable) {
        this.isSubmittable = isSubmittable;
    }

    public Integer getShowNameInGlobalSearch() {
        return showNameInGlobalSearch;
    }

    public void setShowNameInGlobalSearch(Integer showNameInGlobalSearch) {
        this.showNameInGlobalSearch = showNameInGlobalSearch;
    }

    public Integer getCustom() {
        return custom;
    }

    public void setCustom(Integer custom) {
        this.custom = custom;
    }

    public Integer getBeta() {
        return beta;
    }

    public void setBeta(Integer beta) {
        this.beta = beta;
    }

    public Integer getHasWebView() {
        return hasWebView;
    }

    public void setHasWebView(Integer hasWebView) {
        this.hasWebView = hasWebView;
    }

    public Integer getAllowGuestToView() {
        return allowGuestToView;
    }

    public void setAllowGuestToView(Integer allowGuestToView) {
        this.allowGuestToView = allowGuestToView;
    }

    public Integer getEmailAppendTo() {
        return emailAppendTo;
    }

    public void setEmailAppendTo(Integer emailAppendTo) {
        this.emailAppendTo = emailAppendTo;
    }

    public Integer getQuickEntry() {
        return quickEntry;
    }

    public void setQuickEntry(Integer quickEntry) {
        this.quickEntry = quickEntry;
    }

    public Integer getTrackViews() {
        return trackViews;
    }

    public void setTrackViews(Integer trackViews) {
        this.trackViews = trackViews;
    }

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
    }

    public Integer getAllowEventsInTimeline() {
        return allowEventsInTimeline;
    }

    public void setAllowEventsInTimeline(Integer allowEventsInTimeline) {
        this.allowEventsInTimeline = allowEventsInTimeline;
    }

    public Integer getAllowAutoRepeat() {
        return allowAutoRepeat;
    }

    public void setAllowAutoRepeat(Integer allowAutoRepeat) {
        this.allowAutoRepeat = allowAutoRepeat;
    }

    public Integer getShowPreviewPopup() {
        return showPreviewPopup;
    }

    public void setShowPreviewPopup(Integer showPreviewPopup) {
        this.showPreviewPopup = showPreviewPopup;
    }

    public Integer getIndexWebPagesForSearch() {
        return indexWebPagesForSearch;
    }

    public void setIndexWebPagesForSearch(Integer indexWebPagesForSearch) {
        this.indexWebPagesForSearch = indexWebPagesForSearch;
    }

    public List<StokeField> getFields() {
        return fields;
    }

    public void setFields(List<StokeField> fields) {
        this.fields = fields;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Object getJs() {
        return js;
    }

    public void setJs(Object js) {
        this.js = js;
    }

    public Object getCustomJs() {
        return customJs;
    }

    public void setCustomJs(Object customJs) {
        this.customJs = customJs;
    }

    public Object getCustomListJs() {
        return customListJs;
    }

    public void setCustomListJs(Object customListJs) {
        this.customListJs = customListJs;
    }

    public Boolean getAssetsLoaded() {
        return assetsLoaded;
    }

    public void setAssetsLoaded(Boolean assetsLoaded) {
        this.assetsLoaded = assetsLoaded;
    }

    public Object getCss() {
        return css;
    }

    public void setCss(Object css) {
        this.css = css;
    }

    public Object getListJs() {
        return listJs;
    }

    public void setListJs(Object listJs) {
        this.listJs = listJs;
    }

    public Object getCalendarJs() {
        return calendarJs;
    }

    public void setCalendarJs(Object calendarJs) {
        this.calendarJs = calendarJs;
    }

    public Object getMapJs() {
        return mapJs;
    }

    public void setMapJs(Object mapJs) {
        this.mapJs = mapJs;
    }

    public Object getLinkedWith() {
        return linkedWith;
    }

    public void setLinkedWith(Object linkedWith) {
        this.linkedWith = linkedWith;
    }

    public Object getMessages() {
        return messages;
    }

    public void setMessages(Object messages) {
        this.messages = messages;
    }

    public Object getPrintFormats() {
        return printFormats;
    }

    public void setPrintFormats(Object printFormats) {
        this.printFormats = printFormats;
    }

    public Object getWorkflowDocs() {
        return workflowDocs;
    }

    public void setWorkflowDocs(Object workflowDocs) {
        this.workflowDocs = workflowDocs;
    }

    public Object getFormGridTemplates() {
        return formGridTemplates;
    }

    public void setFormGridTemplates(Object formGridTemplates) {
        this.formGridTemplates = formGridTemplates;
    }

    public Object getListviewTemplate() {
        return listviewTemplate;
    }

    public void setListviewTemplate(Object listviewTemplate) {
        this.listviewTemplate = listviewTemplate;
    }

    public Object getTreeJs() {
        return treeJs;
    }

    public void setTreeJs(Object treeJs) {
        this.treeJs = treeJs;
    }

    public Object getDashboard() {
        return dashboard;
    }

    public void setDashboard(Object dashboard) {
        this.dashboard = dashboard;
    }

    public Object getKanbanColumnFields() {
        return kanbanColumnFields;
    }

    public void setKanbanColumnFields(Object kanbanColumnFields) {
        this.kanbanColumnFields = kanbanColumnFields;
    }

    public Object getTemplates() {
        return templates;
    }

    public void setTemplates(Object templates) {
        this.templates = templates;
    }
}
