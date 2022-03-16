package com.example.erpnext.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Invoice {
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
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("naming_series")
    @Expose
    private String namingSeries;
    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("tax_id")
    @Expose
    private Object taxId;
    @SerializedName("pos_profile")
    @Expose
    private String posProfile;
    @SerializedName("consolidated_invoice")
    @Expose
    private Object consolidatedInvoice;
    @SerializedName("is_pos")
    @Expose
    private Integer isPos;
    @SerializedName("is_return")
    @Expose
    private Integer isReturn;
    @SerializedName("update_billed_amount_in_sales_order")
    @Expose
    private Integer updateBilledAmountInSalesOrder;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("posting_date")
    @Expose
    private String postingDate;
    @SerializedName("posting_time")
    @Expose
    private String postingTime;
    @SerializedName("set_posting_time")
    @Expose
    private Integer setPostingTime;
    @SerializedName("due_date")
    @Expose
    private String dueDate;
    @SerializedName("amended_from")
    @Expose
    private Object amendedFrom;
    @SerializedName("return_against")
    @Expose
    private Object returnAgainst;
    @SerializedName("project")
    @Expose
    private Object project;
    @SerializedName("cost_center")
    @Expose
    private Object costCenter;
    @SerializedName("po_no")
    @Expose
    private Object poNo;
    @SerializedName("po_date")
    @Expose
    private Object poDate;
    @SerializedName("customer_address")
    @Expose
    private Object customerAddress;
    @SerializedName("address_display")
    @Expose
    private Object addressDisplay;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("contact_display")
    @Expose
    private String contactDisplay;
    @SerializedName("contact_mobile")
    @Expose
    private String contactMobile;
    @SerializedName("contact_email")
    @Expose
    private String contactEmail;
    @SerializedName("territory")
    @Expose
    private String territory;
    @SerializedName("shipping_address_name")
    @Expose
    private String shippingAddressName;
    @SerializedName("shipping_address")
    @Expose
    private Object shippingAddress;
    @SerializedName("company_address")
    @Expose
    private Object companyAddress;
    @SerializedName("company_address_display")
    @Expose
    private Object companyAddressDisplay;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("conversion_rate")
    @Expose
    private Double conversionRate;
    @SerializedName("selling_price_list")
    @Expose
    private String sellingPriceList;
    @SerializedName("price_list_currency")
    @Expose
    private String priceListCurrency;
    @SerializedName("plc_conversion_rate")
    @Expose
    private Double plcConversionRate;
    @SerializedName("ignore_pricing_rule")
    @Expose
    private Integer ignorePricingRule;
    @SerializedName("set_warehouse")
    @Expose
    private String setWarehouse;
    @SerializedName("update_stock")
    @Expose
    private Integer updateStock;
    @SerializedName("scan_barcode")
    @Expose
    private Object scanBarcode;
    @SerializedName("total_billing_amount")
    @Expose
    private Double totalBillingAmount;
    @SerializedName("total_qty")
    @Expose
    private Double totalQty;
    @SerializedName("base_total")
    @Expose
    private Double baseTotal;
    @SerializedName("base_net_total")
    @Expose
    private Double baseNetTotal;
    @SerializedName("total")
    @Expose
    private Double total;
    @SerializedName("net_total")
    @Expose
    private Double netTotal;
    @SerializedName("total_net_weight")
    @Expose
    private Double totalNetWeight;
    @SerializedName("taxes_and_charges")
    @Expose
    private Object taxesAndCharges;
    @SerializedName("shipping_rule")
    @Expose
    private Object shippingRule;
    @SerializedName("tax_category")
    @Expose
    private String taxCategory;
    @SerializedName("other_charges_calculation")
    @Expose
    private Object otherChargesCalculation;
    @SerializedName("base_total_taxes_and_charges")
    @Expose
    private Double baseTotalTaxesAndCharges;
    @SerializedName("total_taxes_and_charges")
    @Expose
    private Double totalTaxesAndCharges;
    @SerializedName("loyalty_points")
    @Expose
    private Integer loyaltyPoints;
    @SerializedName("loyalty_amount")
    @Expose
    private Double loyaltyAmount;
    @SerializedName("redeem_loyalty_points")
    @Expose
    private Integer redeemLoyaltyPoints;
    @SerializedName("loyalty_program")
    @Expose
    private Object loyaltyProgram;
    @SerializedName("loyalty_redemption_account")
    @Expose
    private Object loyaltyRedemptionAccount;
    @SerializedName("loyalty_redemption_cost_center")
    @Expose
    private Object loyaltyRedemptionCostCenter;
    @SerializedName("apply_discount_on")
    @Expose
    private String applyDiscountOn;
    @SerializedName("base_discount_amount")
    @Expose
    private Double baseDiscountAmount;
    @SerializedName("additional_discount_percentage")
    @Expose
    private Double additionalDiscountPercentage;
    @SerializedName("discount_amount")
    @Expose
    private Double discountAmount;
    @SerializedName("base_grand_total")
    @Expose
    private Double baseGrandTotal;
    @SerializedName("base_rounding_adjustment")
    @Expose
    private Double baseRoundingAdjustment;
    @SerializedName("base_rounded_total")
    @Expose
    private Double baseRoundedTotal;
    @SerializedName("base_in_words")
    @Expose
    private String baseInWords;
    @SerializedName("grand_total")
    @Expose
    private Double grandTotal;
    @SerializedName("rounding_adjustment")
    @Expose
    private Double roundingAdjustment;
    @SerializedName("rounded_total")
    @Expose
    private Double roundedTotal;
    @SerializedName("in_words")
    @Expose
    private String inWords;
    @SerializedName("total_advance")
    @Expose
    private Double totalAdvance;
    @SerializedName("outstanding_amount")
    @Expose
    private Double outstandingAmount;
    @SerializedName("allocate_advances_automatically")
    @Expose
    private Integer allocateAdvancesAutomatically;
    @SerializedName("payment_terms_template")
    @Expose
    private Object paymentTermsTemplate;
    @SerializedName("cash_bank_account")
    @Expose
    private Object cashBankAccount;
    @SerializedName("base_paid_amount")
    @Expose
    private Double basePaidAmount;
    @SerializedName("paid_amount")
    @Expose
    private Double paidAmount;
    @SerializedName("base_change_amount")
    @Expose
    private Double baseChangeAmount;
    @SerializedName("change_amount")
    @Expose
    private Double changeAmount;
    @SerializedName("account_for_change_amount")
    @Expose
    private String accountForChangeAmount;
    @SerializedName("write_off_amount")
    @Expose
    private Double writeOffAmount;
    @SerializedName("base_write_off_amount")
    @Expose
    private Double baseWriteOffAmount;
    @SerializedName("write_off_outstanding_amount_automatically")
    @Expose
    private Integer writeOffOutstandingAmountAutomatically;
    @SerializedName("write_off_account")
    @Expose
    private Object writeOffAccount;
    @SerializedName("write_off_cost_center")
    @Expose
    private Object writeOffCostCenter;
    @SerializedName("tc_name")
    @Expose
    private Object tcName;
    @SerializedName("terms")
    @Expose
    private Object terms;
    @SerializedName("letter_head")
    @Expose
    private String letterHead;
    @SerializedName("group_same_items")
    @Expose
    private Integer groupSameItems;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("select_print_heading")
    @Expose
    private Object selectPrintHeading;
    @SerializedName("inter_company_invoice_reference")
    @Expose
    private Object interCompanyInvoiceReference;
    @SerializedName("customer_group")
    @Expose
    private String customerGroup;
    @SerializedName("campaign")
    @Expose
    private Object campaign;
    @SerializedName("is_discounted")
    @Expose
    private Integer isDiscounted;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("source")
    @Expose
    private Object source;
    @SerializedName("debit_to")
    @Expose
    private String debitTo;
    @SerializedName("party_account_currency")
    @Expose
    private String partyAccountCurrency;
    @SerializedName("is_opening")
    @Expose
    private String isOpening;
    @SerializedName("c_form_applicable")
    @Expose
    private String cFormApplicable;
    @SerializedName("c_form_no")
    @Expose
    private Object cFormNo;
    @SerializedName("remarks")
    @Expose
    private Object remarks;
    @SerializedName("sales_partner")
    @Expose
    private Object salesPartner;
    @SerializedName("commission_rate")
    @Expose
    private Double commissionRate;
    @SerializedName("total_commission")
    @Expose
    private Double totalCommission;
    @SerializedName("from_date")
    @Expose
    private Object fromDate;
    @SerializedName("to_date")
    @Expose
    private Object toDate;
    @SerializedName("auto_repeat")
    @Expose
    private Object autoRepeat;
    @SerializedName("against_income_account")
    @Expose
    private Object againstIncomeAccount;
    @SerializedName("doctype")
    @Expose
    private String doctype;
    @SerializedName("items")
    @Expose
    private List<InvoiceItem> items = null;
    @SerializedName("pricing_rules")
    @Expose
    private List<Object> pricingRules = null;
    @SerializedName("packed_items")
    @Expose
    private List<Object> packedItems = null;
    @SerializedName("timesheets")
    @Expose
    private List<Object> timesheets = null;
    @SerializedName("taxes")
    @Expose
    private List<Object> taxes = null;
    @SerializedName("advances")
    @Expose
    private List<Object> advances = null;
    @SerializedName("payment_schedule")
    @Expose
    private List<Object> paymentSchedule = null;
    @SerializedName("payments")
    @Expose
    private List<InvoicePayment> payments = null;
    @SerializedName("sales_team")
    @Expose
    private List<Object> salesTeam = null;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNamingSeries() {
        return namingSeries;
    }

    public void setNamingSeries(String namingSeries) {
        this.namingSeries = namingSeries;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Object getTaxId() {
        return taxId;
    }

    public void setTaxId(Object taxId) {
        this.taxId = taxId;
    }

    public String getPosProfile() {
        return posProfile;
    }

    public void setPosProfile(String posProfile) {
        this.posProfile = posProfile;
    }

    public Object getConsolidatedInvoice() {
        return consolidatedInvoice;
    }

    public void setConsolidatedInvoice(Object consolidatedInvoice) {
        this.consolidatedInvoice = consolidatedInvoice;
    }

    public Integer getIsPos() {
        return isPos;
    }

    public void setIsPos(Integer isPos) {
        this.isPos = isPos;
    }

    public Integer getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(Integer isReturn) {
        this.isReturn = isReturn;
    }

    public Integer getUpdateBilledAmountInSalesOrder() {
        return updateBilledAmountInSalesOrder;
    }

    public void setUpdateBilledAmountInSalesOrder(Integer updateBilledAmountInSalesOrder) {
        this.updateBilledAmountInSalesOrder = updateBilledAmountInSalesOrder;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getPostingTime() {
        return postingTime;
    }

    public void setPostingTime(String postingTime) {
        this.postingTime = postingTime;
    }

    public Integer getSetPostingTime() {
        return setPostingTime;
    }

    public void setSetPostingTime(Integer setPostingTime) {
        this.setPostingTime = setPostingTime;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Object getAmendedFrom() {
        return amendedFrom;
    }

    public void setAmendedFrom(Object amendedFrom) {
        this.amendedFrom = amendedFrom;
    }

    public Object getReturnAgainst() {
        return returnAgainst;
    }

    public void setReturnAgainst(Object returnAgainst) {
        this.returnAgainst = returnAgainst;
    }

    public Object getProject() {
        return project;
    }

    public void setProject(Object project) {
        this.project = project;
    }

    public Object getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(Object costCenter) {
        this.costCenter = costCenter;
    }

    public Object getPoNo() {
        return poNo;
    }

    public void setPoNo(Object poNo) {
        this.poNo = poNo;
    }

    public Object getPoDate() {
        return poDate;
    }

    public void setPoDate(Object poDate) {
        this.poDate = poDate;
    }

    public Object getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(Object customerAddress) {
        this.customerAddress = customerAddress;
    }

    public Object getAddressDisplay() {
        return addressDisplay;
    }

    public void setAddressDisplay(Object addressDisplay) {
        this.addressDisplay = addressDisplay;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactDisplay() {
        return contactDisplay;
    }

    public void setContactDisplay(String contactDisplay) {
        this.contactDisplay = contactDisplay;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getShippingAddressName() {
        return shippingAddressName;
    }

    public void setShippingAddressName(String shippingAddressName) {
        this.shippingAddressName = shippingAddressName;
    }

    public Object getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Object shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Object getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(Object companyAddress) {
        this.companyAddress = companyAddress;
    }

    public Object getCompanyAddressDisplay() {
        return companyAddressDisplay;
    }

    public void setCompanyAddressDisplay(Object companyAddressDisplay) {
        this.companyAddressDisplay = companyAddressDisplay;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public String getSellingPriceList() {
        return sellingPriceList;
    }

    public void setSellingPriceList(String sellingPriceList) {
        this.sellingPriceList = sellingPriceList;
    }

    public String getPriceListCurrency() {
        return priceListCurrency;
    }

    public void setPriceListCurrency(String priceListCurrency) {
        this.priceListCurrency = priceListCurrency;
    }

    public Double getPlcConversionRate() {
        return plcConversionRate;
    }

    public void setPlcConversionRate(Double plcConversionRate) {
        this.plcConversionRate = plcConversionRate;
    }

    public Integer getIgnorePricingRule() {
        return ignorePricingRule;
    }

    public void setIgnorePricingRule(Integer ignorePricingRule) {
        this.ignorePricingRule = ignorePricingRule;
    }

    public String getSetWarehouse() {
        return setWarehouse;
    }

    public void setSetWarehouse(String setWarehouse) {
        this.setWarehouse = setWarehouse;
    }

    public Integer getUpdateStock() {
        return updateStock;
    }

    public void setUpdateStock(Integer updateStock) {
        this.updateStock = updateStock;
    }

    public Object getScanBarcode() {
        return scanBarcode;
    }

    public void setScanBarcode(Object scanBarcode) {
        this.scanBarcode = scanBarcode;
    }

    public Double getTotalBillingAmount() {
        return totalBillingAmount;
    }

    public void setTotalBillingAmount(Double totalBillingAmount) {
        this.totalBillingAmount = totalBillingAmount;
    }

    public Double getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(Double totalQty) {
        this.totalQty = totalQty;
    }

    public Double getBaseTotal() {
        return baseTotal;
    }

    public void setBaseTotal(Double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public Double getBaseNetTotal() {
        return baseNetTotal;
    }

    public void setBaseNetTotal(Double baseNetTotal) {
        this.baseNetTotal = baseNetTotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(Double netTotal) {
        this.netTotal = netTotal;
    }

    public Double getTotalNetWeight() {
        return totalNetWeight;
    }

    public void setTotalNetWeight(Double totalNetWeight) {
        this.totalNetWeight = totalNetWeight;
    }

    public Object getTaxesAndCharges() {
        return taxesAndCharges;
    }

    public void setTaxesAndCharges(Object taxesAndCharges) {
        this.taxesAndCharges = taxesAndCharges;
    }

    public Object getShippingRule() {
        return shippingRule;
    }

    public void setShippingRule(Object shippingRule) {
        this.shippingRule = shippingRule;
    }

    public String getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    public Object getOtherChargesCalculation() {
        return otherChargesCalculation;
    }

    public void setOtherChargesCalculation(Object otherChargesCalculation) {
        this.otherChargesCalculation = otherChargesCalculation;
    }

    public Double getBaseTotalTaxesAndCharges() {
        return baseTotalTaxesAndCharges;
    }

    public void setBaseTotalTaxesAndCharges(Double baseTotalTaxesAndCharges) {
        this.baseTotalTaxesAndCharges = baseTotalTaxesAndCharges;
    }

    public Double getTotalTaxesAndCharges() {
        return totalTaxesAndCharges;
    }

    public void setTotalTaxesAndCharges(Double totalTaxesAndCharges) {
        this.totalTaxesAndCharges = totalTaxesAndCharges;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Double getLoyaltyAmount() {
        return loyaltyAmount;
    }

    public void setLoyaltyAmount(Double loyaltyAmount) {
        this.loyaltyAmount = loyaltyAmount;
    }

    public Integer getRedeemLoyaltyPoints() {
        return redeemLoyaltyPoints;
    }

    public void setRedeemLoyaltyPoints(Integer redeemLoyaltyPoints) {
        this.redeemLoyaltyPoints = redeemLoyaltyPoints;
    }

    public Object getLoyaltyProgram() {
        return loyaltyProgram;
    }

    public void setLoyaltyProgram(Object loyaltyProgram) {
        this.loyaltyProgram = loyaltyProgram;
    }

    public Object getLoyaltyRedemptionAccount() {
        return loyaltyRedemptionAccount;
    }

    public void setLoyaltyRedemptionAccount(Object loyaltyRedemptionAccount) {
        this.loyaltyRedemptionAccount = loyaltyRedemptionAccount;
    }

    public Object getLoyaltyRedemptionCostCenter() {
        return loyaltyRedemptionCostCenter;
    }

    public void setLoyaltyRedemptionCostCenter(Object loyaltyRedemptionCostCenter) {
        this.loyaltyRedemptionCostCenter = loyaltyRedemptionCostCenter;
    }

    public String getApplyDiscountOn() {
        return applyDiscountOn;
    }

    public void setApplyDiscountOn(String applyDiscountOn) {
        this.applyDiscountOn = applyDiscountOn;
    }

    public Double getBaseDiscountAmount() {
        return baseDiscountAmount;
    }

    public void setBaseDiscountAmount(Double baseDiscountAmount) {
        this.baseDiscountAmount = baseDiscountAmount;
    }

    public Double getAdditionalDiscountPercentage() {
        return additionalDiscountPercentage;
    }

    public void setAdditionalDiscountPercentage(Double additionalDiscountPercentage) {
        this.additionalDiscountPercentage = additionalDiscountPercentage;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getBaseGrandTotal() {
        return baseGrandTotal;
    }

    public void setBaseGrandTotal(Double baseGrandTotal) {
        this.baseGrandTotal = baseGrandTotal;
    }

    public Double getBaseRoundingAdjustment() {
        return baseRoundingAdjustment;
    }

    public void setBaseRoundingAdjustment(Double baseRoundingAdjustment) {
        this.baseRoundingAdjustment = baseRoundingAdjustment;
    }

    public Double getBaseRoundedTotal() {
        return baseRoundedTotal;
    }

    public void setBaseRoundedTotal(Double baseRoundedTotal) {
        this.baseRoundedTotal = baseRoundedTotal;
    }

    public String getBaseInWords() {
        return baseInWords;
    }

    public void setBaseInWords(String baseInWords) {
        this.baseInWords = baseInWords;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Double getRoundingAdjustment() {
        return roundingAdjustment;
    }

    public void setRoundingAdjustment(Double roundingAdjustment) {
        this.roundingAdjustment = roundingAdjustment;
    }

    public Double getRoundedTotal() {
        return roundedTotal;
    }

    public void setRoundedTotal(Double roundedTotal) {
        this.roundedTotal = roundedTotal;
    }

    public String getInWords() {
        return inWords;
    }

    public void setInWords(String inWords) {
        this.inWords = inWords;
    }

    public Double getTotalAdvance() {
        return totalAdvance;
    }

    public void setTotalAdvance(Double totalAdvance) {
        this.totalAdvance = totalAdvance;
    }

    public Double getOutstandingAmount() {
        return outstandingAmount;
    }

    public void setOutstandingAmount(Double outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public Integer getAllocateAdvancesAutomatically() {
        return allocateAdvancesAutomatically;
    }

    public void setAllocateAdvancesAutomatically(Integer allocateAdvancesAutomatically) {
        this.allocateAdvancesAutomatically = allocateAdvancesAutomatically;
    }

    public Object getPaymentTermsTemplate() {
        return paymentTermsTemplate;
    }

    public void setPaymentTermsTemplate(Object paymentTermsTemplate) {
        this.paymentTermsTemplate = paymentTermsTemplate;
    }

    public Object getCashBankAccount() {
        return cashBankAccount;
    }

    public void setCashBankAccount(Object cashBankAccount) {
        this.cashBankAccount = cashBankAccount;
    }

    public Double getBasePaidAmount() {
        return basePaidAmount;
    }

    public void setBasePaidAmount(Double basePaidAmount) {
        this.basePaidAmount = basePaidAmount;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getBaseChangeAmount() {
        return baseChangeAmount;
    }

    public void setBaseChangeAmount(Double baseChangeAmount) {
        this.baseChangeAmount = baseChangeAmount;
    }

    public Double getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        this.changeAmount = changeAmount;
    }

    public String getAccountForChangeAmount() {
        return accountForChangeAmount;
    }

    public void setAccountForChangeAmount(String accountForChangeAmount) {
        this.accountForChangeAmount = accountForChangeAmount;
    }

    public Double getWriteOffAmount() {
        return writeOffAmount;
    }

    public void setWriteOffAmount(Double writeOffAmount) {
        this.writeOffAmount = writeOffAmount;
    }

    public Double getBaseWriteOffAmount() {
        return baseWriteOffAmount;
    }

    public void setBaseWriteOffAmount(Double baseWriteOffAmount) {
        this.baseWriteOffAmount = baseWriteOffAmount;
    }

    public Integer getWriteOffOutstandingAmountAutomatically() {
        return writeOffOutstandingAmountAutomatically;
    }

    public void setWriteOffOutstandingAmountAutomatically(Integer writeOffOutstandingAmountAutomatically) {
        this.writeOffOutstandingAmountAutomatically = writeOffOutstandingAmountAutomatically;
    }

    public Object getWriteOffAccount() {
        return writeOffAccount;
    }

    public void setWriteOffAccount(Object writeOffAccount) {
        this.writeOffAccount = writeOffAccount;
    }

    public Object getWriteOffCostCenter() {
        return writeOffCostCenter;
    }

    public void setWriteOffCostCenter(Object writeOffCostCenter) {
        this.writeOffCostCenter = writeOffCostCenter;
    }

    public Object getTcName() {
        return tcName;
    }

    public void setTcName(Object tcName) {
        this.tcName = tcName;
    }

    public Object getTerms() {
        return terms;
    }

    public void setTerms(Object terms) {
        this.terms = terms;
    }

    public String getLetterHead() {
        return letterHead;
    }

    public void setLetterHead(String letterHead) {
        this.letterHead = letterHead;
    }

    public Integer getGroupSameItems() {
        return groupSameItems;
    }

    public void setGroupSameItems(Integer groupSameItems) {
        this.groupSameItems = groupSameItems;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Object getSelectPrintHeading() {
        return selectPrintHeading;
    }

    public void setSelectPrintHeading(Object selectPrintHeading) {
        this.selectPrintHeading = selectPrintHeading;
    }

    public Object getInterCompanyInvoiceReference() {
        return interCompanyInvoiceReference;
    }

    public void setInterCompanyInvoiceReference(Object interCompanyInvoiceReference) {
        this.interCompanyInvoiceReference = interCompanyInvoiceReference;
    }

    public String getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(String customerGroup) {
        this.customerGroup = customerGroup;
    }

    public Object getCampaign() {
        return campaign;
    }

    public void setCampaign(Object campaign) {
        this.campaign = campaign;
    }

    public Integer getIsDiscounted() {
        return isDiscounted;
    }

    public void setIsDiscounted(Integer isDiscounted) {
        this.isDiscounted = isDiscounted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public String getDebitTo() {
        return debitTo;
    }

    public void setDebitTo(String debitTo) {
        this.debitTo = debitTo;
    }

    public String getPartyAccountCurrency() {
        return partyAccountCurrency;
    }

    public void setPartyAccountCurrency(String partyAccountCurrency) {
        this.partyAccountCurrency = partyAccountCurrency;
    }

    public String getIsOpening() {
        return isOpening;
    }

    public void setIsOpening(String isOpening) {
        this.isOpening = isOpening;
    }

    public String getcFormApplicable() {
        return cFormApplicable;
    }

    public void setcFormApplicable(String cFormApplicable) {
        this.cFormApplicable = cFormApplicable;
    }

    public Object getcFormNo() {
        return cFormNo;
    }

    public void setcFormNo(Object cFormNo) {
        this.cFormNo = cFormNo;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public Object getSalesPartner() {
        return salesPartner;
    }

    public void setSalesPartner(Object salesPartner) {
        this.salesPartner = salesPartner;
    }

    public Double getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(Double commissionRate) {
        this.commissionRate = commissionRate;
    }

    public Double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Object getFromDate() {
        return fromDate;
    }

    public void setFromDate(Object fromDate) {
        this.fromDate = fromDate;
    }

    public Object getToDate() {
        return toDate;
    }

    public void setToDate(Object toDate) {
        this.toDate = toDate;
    }

    public Object getAutoRepeat() {
        return autoRepeat;
    }

    public void setAutoRepeat(Object autoRepeat) {
        this.autoRepeat = autoRepeat;
    }

    public Object getAgainstIncomeAccount() {
        return againstIncomeAccount;
    }

    public void setAgainstIncomeAccount(Object againstIncomeAccount) {
        this.againstIncomeAccount = againstIncomeAccount;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }


    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public List<Object> getPricingRules() {
        return pricingRules;
    }

    public void setPricingRules(List<Object> pricingRules) {
        this.pricingRules = pricingRules;
    }

    public List<Object> getPackedItems() {
        return packedItems;
    }

    public void setPackedItems(List<Object> packedItems) {
        this.packedItems = packedItems;
    }

    public List<Object> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(List<Object> timesheets) {
        this.timesheets = timesheets;
    }

    public List<Object> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Object> taxes) {
        this.taxes = taxes;
    }

    public List<Object> getAdvances() {
        return advances;
    }

    public void setAdvances(List<Object> advances) {
        this.advances = advances;
    }

    public List<Object> getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(List<Object> paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public List<InvoicePayment> getPayments() {
        return payments;
    }

    public void setPayments(List<InvoicePayment> payments) {
        this.payments = payments;
    }

    public List<Object> getSalesTeam() {
        return salesTeam;
    }

    public void setSalesTeam(List<Object> salesTeam) {
        this.salesTeam = salesTeam;
    }


}
