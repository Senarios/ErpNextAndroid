package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StokeField {
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
    @SerializedName("fieldname")
    @Expose
    private String fieldname;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("fieldtype")
    @Expose
    private String fieldtype;
    @SerializedName("search_index")
    @Expose
    private Integer searchIndex;
    @SerializedName("hidden")
    @Expose
    private Integer hidden;
    @SerializedName("set_only_once")
    @Expose
    private Integer setOnlyOnce;
    @SerializedName("allow_in_quick_entry")
    @Expose
    private Integer allowInQuickEntry;
    @SerializedName("print_hide")
    @Expose
    private Integer printHide;
    @SerializedName("report_hide")
    @Expose
    private Integer reportHide;
    @SerializedName("reqd")
    @Expose
    private Integer reqd;
    @SerializedName("bold")
    @Expose
    private Integer bold;
    @SerializedName("in_global_search")
    @Expose
    private Integer inGlobalSearch;
    @SerializedName("collapsible")
    @Expose
    private Integer collapsible;
    @SerializedName("unique")
    @Expose
    private Integer unique;
    @SerializedName("no_copy")
    @Expose
    private Integer noCopy;
    @SerializedName("allow_on_submit")
    @Expose
    private Integer allowOnSubmit;
    @SerializedName("show_preview_popup")
    @Expose
    private Integer showPreviewPopup;
    @SerializedName("permlevel")
    @Expose
    private Integer permlevel;
    @SerializedName("ignore_user_permissions")
    @Expose
    private Integer ignoreUserPermissions;
    @SerializedName("columns")
    @Expose
    private Integer columns;
    @SerializedName("in_list_view")
    @Expose
    private Integer inListView;
    @SerializedName("fetch_if_empty")
    @Expose
    private Integer fetchIfEmpty;
    @SerializedName("in_filter")
    @Expose
    private Integer inFilter;
    @SerializedName("remember_last_selected_value")
    @Expose
    private Integer rememberLastSelectedValue;
    @SerializedName("ignore_xss_filter")
    @Expose
    private Integer ignoreXssFilter;
    @SerializedName("print_hide_if_no_value")
    @Expose
    private Integer printHideIfNoValue;
    @SerializedName("allow_bulk_edit")
    @Expose
    private Integer allowBulkEdit;
    @SerializedName("in_standard_filter")
    @Expose
    private Integer inStandardFilter;
    @SerializedName("in_preview")
    @Expose
    private Integer inPreview;
    @SerializedName("read_only")
    @Expose
    private Integer readOnly;
    @SerializedName("length")
    @Expose
    private Integer length;
    @SerializedName("translatable")
    @Expose
    private Integer translatable;
    @SerializedName("hide_border")
    @Expose
    private Integer hideBorder;
    @SerializedName("hide_days")
    @Expose
    private Integer hideDays;
    @SerializedName("hide_seconds")
    @Expose
    private Integer hideSeconds;
    @SerializedName("non_negative")
    @Expose
    private Integer nonNegative;
    @SerializedName("search_fields")
    @Expose
    private Object searchFields;
    @SerializedName("is_custom_field")
    @Expose
    private Object isCustomField;
    @SerializedName("linked_document_type")
    @Expose
    private String linkedDocumentType;
    @SerializedName("options")
    @Expose
    private String options;
    @SerializedName("depends_on")
    @Expose
    private String dependsOn;
    @SerializedName("default")
    @Expose
    private String _default;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("oldfieldname")
    @Expose
    private String oldfieldname;
    @SerializedName("oldfieldtype")
    @Expose
    private String oldfieldtype;

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

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFieldtype() {
        return fieldtype;
    }

    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    public Integer getSearchIndex() {
        return searchIndex;
    }

    public void setSearchIndex(Integer searchIndex) {
        this.searchIndex = searchIndex;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }

    public Integer getSetOnlyOnce() {
        return setOnlyOnce;
    }

    public void setSetOnlyOnce(Integer setOnlyOnce) {
        this.setOnlyOnce = setOnlyOnce;
    }

    public Integer getAllowInQuickEntry() {
        return allowInQuickEntry;
    }

    public void setAllowInQuickEntry(Integer allowInQuickEntry) {
        this.allowInQuickEntry = allowInQuickEntry;
    }

    public Integer getPrintHide() {
        return printHide;
    }

    public void setPrintHide(Integer printHide) {
        this.printHide = printHide;
    }

    public Integer getReportHide() {
        return reportHide;
    }

    public void setReportHide(Integer reportHide) {
        this.reportHide = reportHide;
    }

    public Integer getReqd() {
        return reqd;
    }

    public void setReqd(Integer reqd) {
        this.reqd = reqd;
    }

    public Integer getBold() {
        return bold;
    }

    public void setBold(Integer bold) {
        this.bold = bold;
    }

    public Integer getInGlobalSearch() {
        return inGlobalSearch;
    }

    public void setInGlobalSearch(Integer inGlobalSearch) {
        this.inGlobalSearch = inGlobalSearch;
    }

    public Integer getCollapsible() {
        return collapsible;
    }

    public void setCollapsible(Integer collapsible) {
        this.collapsible = collapsible;
    }

    public Integer getUnique() {
        return unique;
    }

    public void setUnique(Integer unique) {
        this.unique = unique;
    }

    public Integer getNoCopy() {
        return noCopy;
    }

    public void setNoCopy(Integer noCopy) {
        this.noCopy = noCopy;
    }

    public Integer getAllowOnSubmit() {
        return allowOnSubmit;
    }

    public void setAllowOnSubmit(Integer allowOnSubmit) {
        this.allowOnSubmit = allowOnSubmit;
    }

    public Integer getShowPreviewPopup() {
        return showPreviewPopup;
    }

    public void setShowPreviewPopup(Integer showPreviewPopup) {
        this.showPreviewPopup = showPreviewPopup;
    }

    public Integer getPermlevel() {
        return permlevel;
    }

    public void setPermlevel(Integer permlevel) {
        this.permlevel = permlevel;
    }

    public Integer getIgnoreUserPermissions() {
        return ignoreUserPermissions;
    }

    public void setIgnoreUserPermissions(Integer ignoreUserPermissions) {
        this.ignoreUserPermissions = ignoreUserPermissions;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Integer getInListView() {
        return inListView;
    }

    public void setInListView(Integer inListView) {
        this.inListView = inListView;
    }

    public Integer getFetchIfEmpty() {
        return fetchIfEmpty;
    }

    public void setFetchIfEmpty(Integer fetchIfEmpty) {
        this.fetchIfEmpty = fetchIfEmpty;
    }

    public Integer getInFilter() {
        return inFilter;
    }

    public void setInFilter(Integer inFilter) {
        this.inFilter = inFilter;
    }

    public Integer getRememberLastSelectedValue() {
        return rememberLastSelectedValue;
    }

    public void setRememberLastSelectedValue(Integer rememberLastSelectedValue) {
        this.rememberLastSelectedValue = rememberLastSelectedValue;
    }

    public Integer getIgnoreXssFilter() {
        return ignoreXssFilter;
    }

    public void setIgnoreXssFilter(Integer ignoreXssFilter) {
        this.ignoreXssFilter = ignoreXssFilter;
    }

    public Integer getPrintHideIfNoValue() {
        return printHideIfNoValue;
    }

    public void setPrintHideIfNoValue(Integer printHideIfNoValue) {
        this.printHideIfNoValue = printHideIfNoValue;
    }

    public Integer getAllowBulkEdit() {
        return allowBulkEdit;
    }

    public void setAllowBulkEdit(Integer allowBulkEdit) {
        this.allowBulkEdit = allowBulkEdit;
    }

    public Integer getInStandardFilter() {
        return inStandardFilter;
    }

    public void setInStandardFilter(Integer inStandardFilter) {
        this.inStandardFilter = inStandardFilter;
    }

    public Integer getInPreview() {
        return inPreview;
    }

    public void setInPreview(Integer inPreview) {
        this.inPreview = inPreview;
    }

    public Integer getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Integer readOnly) {
        this.readOnly = readOnly;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getTranslatable() {
        return translatable;
    }

    public void setTranslatable(Integer translatable) {
        this.translatable = translatable;
    }

    public Integer getHideBorder() {
        return hideBorder;
    }

    public void setHideBorder(Integer hideBorder) {
        this.hideBorder = hideBorder;
    }

    public Integer getHideDays() {
        return hideDays;
    }

    public void setHideDays(Integer hideDays) {
        this.hideDays = hideDays;
    }

    public Integer getHideSeconds() {
        return hideSeconds;
    }

    public void setHideSeconds(Integer hideSeconds) {
        this.hideSeconds = hideSeconds;
    }

    public Integer getNonNegative() {
        return nonNegative;
    }

    public void setNonNegative(Integer nonNegative) {
        this.nonNegative = nonNegative;
    }

    public Object getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(Object searchFields) {
        this.searchFields = searchFields;
    }

    public Object getIsCustomField() {
        return isCustomField;
    }

    public void setIsCustomField(Object isCustomField) {
        this.isCustomField = isCustomField;
    }

    public String getLinkedDocumentType() {
        return linkedDocumentType;
    }

    public void setLinkedDocumentType(String linkedDocumentType) {
        this.linkedDocumentType = linkedDocumentType;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(String dependsOn) {
        this.dependsOn = dependsOn;
    }

    public String getDefault() {
        return _default;
    }

    public void setDefault(String _default) {
        this._default = _default;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOldfieldname() {
        return oldfieldname;
    }

    public void setOldfieldname(String oldfieldname) {
        this.oldfieldname = oldfieldname;
    }

    public String getOldfieldtype() {
        return oldfieldtype;
    }

    public void setOldfieldtype(String oldfieldtype) {
        this.oldfieldtype = oldfieldtype;
    }

}
