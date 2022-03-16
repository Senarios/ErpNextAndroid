package com.example.erpnext.network;

import com.example.erpnext.models.AddCustomerRes;
import com.example.erpnext.models.CustomerRes;
import com.example.erpnext.models.FcmRes;
import com.example.erpnext.models.MyTaskRes;
import com.example.erpnext.models.MyTaskUpdateRes;
import com.example.erpnext.models.SPLocHisRes;
import com.example.erpnext.models.SPLocHistoryRes;
import com.example.erpnext.models.SalesPersonLocRes;
import com.example.erpnext.models.ScanQRRes;
import com.example.erpnext.models.SendNotificationRes;
import com.example.erpnext.models.ShowCustomerRes;
import com.example.erpnext.models.StockSummRes;
import com.example.erpnext.models.TrackLocRes;
import com.example.erpnext.models.WarehouseItemRequestBody;
import com.example.erpnext.network.serializers.requestbody.CompleteOrderRequestBody;
import com.example.erpnext.network.serializers.requestbody.LoginRequest;
import com.example.erpnext.network.serializers.requestbody.PartyDetailRequestBody;
import com.example.erpnext.network.serializers.requestbody.SearchLinkRequestBody;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.AddAssigneesResponse;
import com.example.erpnext.network.serializers.response.AddCommentResponse;
import com.example.erpnext.network.serializers.response.AttachmentResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.CheckNameResponse;
import com.example.erpnext.network.serializers.response.CheckOpeningEntryResponse;
import com.example.erpnext.network.serializers.response.CompleteOrderResponse;
import com.example.erpnext.network.serializers.response.DepartmentResponse;
import com.example.erpnext.network.serializers.response.DiffAccountResponse;
import com.example.erpnext.network.serializers.response.DocDetailResponse;
import com.example.erpnext.network.serializers.response.DocTypeResponse;
import com.example.erpnext.network.serializers.response.EmailContactResponse;
import com.example.erpnext.network.serializers.response.FetchValuesResponse;
import com.example.erpnext.network.serializers.response.GenerateReportResponse;
import com.example.erpnext.network.serializers.response.GetDocPOSOpeningResponse;
import com.example.erpnext.network.serializers.response.GetItemDetailResponse;
import com.example.erpnext.network.serializers.response.GetItemsResponse;
import com.example.erpnext.network.serializers.response.GetLinkedInvoicesResponse;
import com.example.erpnext.network.serializers.response.GetProfileDocResponse;
import com.example.erpnext.network.serializers.response.GetPurchaseInvoiceResponse;
import com.example.erpnext.network.serializers.response.GetStokeDoctypeResponse;
import com.example.erpnext.network.serializers.response.GetValuesResponse;
import com.example.erpnext.network.serializers.response.ItemResponse;
import com.example.erpnext.network.serializers.response.LandedCostPurchaseItemsResponse;
import com.example.erpnext.network.serializers.response.LoginResponse;
import com.example.erpnext.network.serializers.response.MenuResponse;
import com.example.erpnext.network.serializers.response.PartyDetailResponse;
import com.example.erpnext.network.serializers.response.PosInvoiceResponse;
import com.example.erpnext.network.serializers.response.PurchaseInvoiceDetailResponse;
import com.example.erpnext.network.serializers.response.RemoveAssigneeResponse;
import com.example.erpnext.network.serializers.response.ReportResponse;
import com.example.erpnext.network.serializers.response.ReportViewResponse;
import com.example.erpnext.network.serializers.response.RunDocMethodResponse;
import com.example.erpnext.network.serializers.response.SaveClosingEntryResponse;
import com.example.erpnext.network.serializers.response.SaveDocResponse;
import com.example.erpnext.network.serializers.response.SaveLoyaltyProgramResponse;
import com.example.erpnext.network.serializers.response.SaveOpeningEntryResponse;
import com.example.erpnext.network.serializers.response.SearchItemDetailResponse;
import com.example.erpnext.network.serializers.response.SearchItemResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.network.serializers.response.TagSuggestionsResponse;
import com.example.erpnext.network.serializers.response.WarehouseItemsResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiServices {
    //-----------------Authentication Apis--------------
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("method/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("method/frappe.desk.desktop.get_desk_sidebar_items")
    Call<MenuResponse> getMenu(@Header("Cookie") String sid, @Header("Cookie") String userId, @Header("Cookie") String fullName);

    @POST("method/frappe.desk.desktop.get_desktop_page")
    Call<ItemResponse> getItem(@Query("page") CharSequence page);

    @GET("method/frappe.desk.form.load.getdoctype")
    Call<DocTypeResponse> getDocType(@Query("doctype") String doctype);

    @GET("method/frappe.client.get_value")
    Call<CheckNameResponse> checkName(@Query("doctype") String doctype,
                                      @Query("fieldname") String fieldname,
                                      @Query("filters") String filter);

    @GET("method/frappe.desk.reportview.get")
    Call<ReportViewResponse> getReportView(
            @Query("doctype") String doctype,
            @Query("fields") String fields,
            @Query("page_length") Integer pageLength,
            @Query("with_comment_count") boolean isCommentCount,
            @Query("order_by") String orderBy,
            @Query("limit_start") Integer limitStart);

    @GET("method/frappe.desk.reportview.get")
    Call<ReportViewResponse> getReportView(
            @Query("doctype") String doctype,
            @Query("fields") String fields,
            @Query("filters") String filter,
            @Query("page_length") Integer pageLength,
            @Query("with_comment_count") boolean isCommentCount,
            @Query("order_by") String orderBy,
            @Query("limit_start") Integer limitStart);

    @POST("method/frappe.desk.search.search_link")
    Call<SearchLinkResponse> getSearchLink(@Body SearchLinkRequestBody requestBody);

    @POST("method/frappe.desk.search.search_link")
    Call<SearchLinkResponse> getSearchLinkWithFilters(@Body SearchLinkWithFiltersRequestBody requestBody);


    @POST("method/erpnext.accounts.party.get_party_details")
    Call<PartyDetailResponse> getPartyDetails(@Body PartyDetailRequestBody requestBody);


    @POST("method/frappe.desk.form.save.savedocs")
    Call<SaveDocResponse> saveDoc(@Query("doc") JSONObject jsonObject,
                                  @Query("action") String action);

    @POST("method/frappe.desk.form.save.savedocs")
    Call<JSONObject> saveReconciliation(@Query("doc") JSONObject jsonObject,
                                        @Query("action") String action);

    @POST("method/frappe.desk.form.save.savedocs")
    Call<SaveOpeningEntryResponse> saveOpeningEntryDoc(@Query("doc") JSONObject jsonObject,
                                                       @Query("action") String action);

    @POST("method/frappe.desk.form.save.savedocs")
    Call<JSONObject> saveClosingEntry(@Query("doc") JSONObject jsonObject,
                                      @Query("action") String action);

    @Multipart
    @POST("method/frappe.desk.form.save.savedocs")
    Call<SaveClosingEntryResponse> saveClosingEntry(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("method/frappe.desk.form.save.savedocs")
    Call<JSONObject> saveDoc(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("method/frappe.client.save")
    Call<JSONObject> saveClient(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("method/frappe.client.insert")
    Call<JSONObject> saveItem(@PartMap Map<String, RequestBody> params);

    @POST("method/frappe.desk.form.save.savedocs")
    Call<SaveLoyaltyProgramResponse> saveLoyaltyDoc(@Query("doc") JSONObject jsonObject,
                                                    @Query("action") String action);

    @POST("method/frappe.desk.form.save.savedocs")
    Call<SaveDocResponse> saveEditedDoc(@Query("doc") JSONObject jsonObject,
                                        @Query("action") String action);

    @GET("method/frappe.desk.form.load.getdoc")
    Call<DocDetailResponse> getDoc(@Query("doctype") String doctype,
                                   @Query("name") String name);

    @GET("method/frappe.desk.form.utils.validate_link")
    Call<FetchValuesResponse> fetchValues(@Query("value") String value,
                                          @Query("options") String options,
                                          @Query("fetch") String fetch);

    @GET("method/frappe.desk.form.load.getdoc")
    Call<PosInvoiceResponse> getInvoice(@Query("doctype") String doctype,
                                        @Query("name") String name);

    @GET("method/frappe.desk.form.load.getdoc")
    Call<GetStokeDoctypeResponse> getDocStoke(@Query("doctype") String doctype,
                                              @Query("name") String name);

    @GET("method/frappe.client.get")
    Call<GetDocPOSOpeningResponse> getDocPOSOpening(@Query("doctype") String doctype,
                                                    @Query("name") String name);

    @Multipart
    @POST("method/run_doc_method")
    Call<LandedCostPurchaseItemsResponse> getLandedCostItems(@PartMap Map<String, RequestBody> params);

    @GET("method/run_doc_method")
    Call<DocDetailResponse> runDoc(@Query("doctype") String doctype,
                                   @Query("name") String name,
                                   @Query("filters") String filters);

    @POST("method/frappe.desk.doctype.bulk_update.bulk_update.submit_cancel_or_update_docs")
    Call<JSONObject> changeInvoiceStatus(@Query("doctype") String doctype,
                                         @Query("action") String action,
                                         @Query("docnames") JSONArray invoiceNames);

    @POST("method/frappe.desk.doctype.bulk_update.bulk_update.submit_cancel_or_update_docs")
    Call<JSONObject> changeInvoiceStatus(@Header("uid") int uid, @Query("doctype") String doctype,
                                         @Query("action") String action,
                                         @Query("docnames") JSONArray invoiceNames);

    @POST("method/frappe.desk.reportview.delete_items")
    Call<JSONObject> deleteInvoice(@Query("doctype") String doctype,
                                   @Query("items") JSONArray invoiceNames);

    @POST("method/frappe.desk.reportview.delete_items")
    Call<JSONObject> deleteInvoice(@Header("uid") int uid, @Query("doctype") String doctype,
                                   @Query("items") JSONArray invoiceNames);

    @POST("method/frappe.desk.form.assign_to.add")
    Call<AddAssigneesResponse> addAssignees(@Query("assign_to_me") Integer assign_to_me,
                                            @Query("assign_to") JSONArray jsonArray,
                                            @Query("description") String comment,
                                            @Query("date") String date,
                                            @Query("priority") String priority,
                                            @Query("doctype") String doctype,
                                            @Query("name") String name,
                                            @Query("bulk_assign") boolean bulk_assign,
                                            @Query("re_assign") boolean re_assign);

    @POST("method/frappe.desk.form.assign_to.remove")
    Call<RemoveAssigneeResponse> removeAssignee(@Query("assign_to") String assignee,
                                                @Query("doctype") String doctype,
                                                @Query("name") String name);

    @POST("method/frappe.desk.doctype.tag.tag.remove_tag")
    Call<BaseResponse> removeTag(@Query("tag") String tag,
                                 @Query("dt") String doctype,
                                 @Query("dn") String name);

    @POST("method/frappe.desk.doctype.tag.tag.add_tag")
    Call<BaseResponse> addTag(@Query("tag") String tag,
                              @Query("dt") String doctype,
                              @Query("dn") String name);

    @POST("method/frappe.desk.doctype.tag.tag.get_tags")
    Call<TagSuggestionsResponse> getTagSuggestion(@Query("doctype") String doctype, @Query("txt") String tag);

    @Multipart
    @POST("method/upload_file")
    Call<AttachmentResponse> uploadAttachment(@PartMap Map<String, RequestBody> params,
                                              @Part MultipartBody.Part file);

    @POST("method/frappe.desk.form.utils.remove_attach")
    Call<BaseResponse> removeAttachment(@Query("fid") String fid,
                                        @Query("dt") String doctype,
                                        @Query("dn") String name);

    @POST("method/frappe.desk.form.utils.add_comment")
    Call<AddCommentResponse> addComment(@Query("reference_doctype") String reference_doctype,
                                        @Query("reference_name") String reference_name,
                                        @Query("content") String content,
                                        @Query("comment_email") String comment_email,
                                        @Query("comment_by") String comment_by);

    @POST("method/frappe.client.delete")
    Call<BaseResponse> removeComment(@Query("doctype") String doctype,
                                     @Query("name") String commenName);

    @POST("method/erpnext.selling.page.point_of_sale.point_of_sale.check_opening_entry")
    Call<CheckOpeningEntryResponse> checkOpeningEntry(@Query("user") String user);

    @POST("method/frappe.client.get")
    Call<GetProfileDocResponse> getProfileDoc(@Query("doctype") String doctype,
                                              @Query("name") String name);

    @POST("method/run_doc_method")
    Call<RunDocMethodResponse> runDocMethod(@Query("docs") JSONObject docs,
                                            @Query("method") String name);

    @POST("method/erpnext.selling.page.point_of_sale.point_of_sale.get_items")
    Call<GetItemsResponse> getItems(@Query("start") Integer start,
                                    @Query("page_length") Integer page_length,
                                    @Query("price_list") String price_list,
                                    @Query("item_group") String item_group,
                                    @Query("search_term") String search_term,
                                    @Query("pos_profile") String pos_profile);

    @POST("method/erpnext.stock.dashboard.item_dashboard.get_data")
    Call<StockSummRes> getStockItems(@Query("warehouse") String warehouse,
                                @Query("start") Integer start,
                                @Query("sort_by") String sort_by,
                                @Query("sort_order") String sort_order);

    @POST("method/erpnext.stock.get_item_details.get_item_details")
    Call<GetItemDetailResponse> getItemDetail(@Query("doc") JSONObject doc,
                                              @Query("args") JSONObject args);

    @POST("method/erpnext.stock.get_item_details.get_item_details")
    Call<SearchItemDetailResponse> getSearchItemDetail(@Query("doc") JSONObject doc,
                                                       @Query("args") JSONObject args);

    @POST("resource/POS%20Invoice")
    Call<CompleteOrderResponse> completeOrder(@Body CompleteOrderRequestBody completeOrderRequestBody);

    @POST("method/erpnext.accounts.doctype.pos_closing_entry.pos_closing_entry.get_pos_invoices")
    Call<GetLinkedInvoicesResponse> getLinkedInvoices(@Query("start") String startDate,
                                                      @Query("end") String endDate,
                                                      @Query("pos_profile") String posProfile,
                                                      @Query("user") String user);

    @GET("method/frappe.desk.query_report.background_enqueue_run")
    Call<GenerateReportResponse> generateReport(@Query("report_name") String report_name, @Query("filters") JSONObject jsonObject);


    @GET("method/frappe.desk.query_report.run")
    Call<ReportResponse> getReport(@Query("report_name") String report_name, @Query("filters") JSONObject jsonObject);


    @GET("method/frappe.desk.search.search_widget")
    Call<SearchItemResponse> searchItem(@Query("doctype") String doctype,
                                        @Query("txt") String txt,
                                        @Query("searchfield") String searchfield,
                                        @Query("query") String query,
                                        @Query("filters") String filters);

    @GET("method/frappe.desk.search.search_widget")
    Call<GetPurchaseInvoiceResponse> getPurchaseInvoice(@Query("doctype") String doctype,
                                                        @Query("txt") String txt,
                                                        @Query("filter_fields") String filter_fields,
                                                        @Query("page_length") Integer page_length,
                                                        @Query("start") Integer start,
                                                        @Query("filters") String filters);

    @POST("method/frappe.model.mapper.map_docs")
    Call<PurchaseInvoiceDetailResponse> purchaseInvoiceDetail(@Query("method") String method,
                                                              @Query("source_names") JSONArray source_names,
                                                              @Query("target_doc") JSONObject target_doc);

    @POST("method/erpnext.stock.doctype.stock_reconciliation.stock_reconciliation.get_difference_account")
    Call<DiffAccountResponse> getDiffAccount(@Query("purpose") String purpose,
                                             @Query("company") String company);

    @POST("method/erpnext.stock.doctype.stock_reconciliation.stock_reconciliation.get_items")
    Call<WarehouseItemsResponse> getWarehouseItems(@Body WarehouseItemRequestBody body);

    @POST("method/frappe.email.get_contact_list")
    Call<EmailContactResponse> getEmailContact(@Query("txt") String text);

    @POST("method/frappe.client.get_value")
    Call<GetValuesResponse> getValue(@Query("doctype") String doctype,
                                     @Query("fieldname") String fieldname,
                                     @Query("filters") String filters);

    @POST("method/frappe.core.doctype.communication.email.make")
    Call<JSONObject> sendMail(@Query("recipients") String recipients,
                              @Query("cc") String cc,
                              @Query("bcc") String bcc,
                              @Query("subject") String subject,
                              @Query("send_email") int send_email,
                              @Query("send_me_a_copy") String send_me_a_copy,
                              @Query("read_receipt") String read_receipt,
                              @Query("content") String content);

    @GET("method/frappe.desk.form.utils.validate_link")
    Call<DepartmentResponse> getDepartment(@Query("value") String value,
                                           @Query("options") String options,
                                           @Query("fetch") String fetch);

    @FormUrlEncoded
    @POST("show_tasks.php")
    Call<MyTaskRes> getMyTaskitem(@Field("sales_person_email") String email);

    @GET("get_customers.php")
    Call<CustomerRes> getCustomers();

    @FormUrlEncoded
    @POST("update_tasks.php")
    Call<MyTaskUpdateRes> getMyTaskUpdateitem(@Field("sales_person_email") String email,
                                              @Field("task_name") String Taskname,
                                              @Field("shop_name") String shopname,
                                              @Field("shop_status") String shopstatus,
                                              @Field("comment") String comment);

    @FormUrlEncoded
    @POST("location_records.php")
    Call<SalesPersonLocRes> sendSalesPersonLoc(@Field("sales_person_email") String email,
                                               @Field("lat") Double lat,
                                               @Field("lng") Double lng);


    @FormUrlEncoded
    @POST("track_location.php")
    Call<SPLocHistoryRes> getSPLocHistory(@Field("sales_person_email") String email,
                                          @Field("date") String date);

    @FormUrlEncoded
    @POST("create_fcm.php")
    Call<FcmRes> getFcmToken(@Field("users") String users,
                             @Field("fcm") String fcm);

    @FormUrlEncoded
    @POST("push_notification.php")
    Call<SendNotificationRes> getNotification(@Field("sales_person_email") String sales_person_email);

    @FormUrlEncoded
    @POST("chat.php")
    Call<SendNotificationRes> getNot(@Field("sales_person_email") String sales_person_email,
                                     @Field("sales_officer") String sales_officer);

    @FormUrlEncoded
    @POST("officerAssign.php")
    Call<SPLocHisRes> getSpUnderSupervisor(@Field("sales_officer") String sales_officer);

    @FormUrlEncoded
    @POST("qrcode_scan.php")
    Call<ScanQRRes> getScannedCustomer(@Field("qrcode_id") String qrcode_id);

    @Multipart
    @POST("AddCustomer.php")
    Call<AddCustomerRes> addCustomer(@Part MultipartBody.Part image,
                                     @Part("customer_name") RequestBody customer_name,
                                     @Part("phone_no") RequestBody phone_no,
                                     @Part("reference") RequestBody reference,
                                     @Part("lat") RequestBody lat,
                                     @Part("lng") RequestBody lng);
    @GET("ShowCustomers.php")
    Call<ShowCustomerRes> getAllCustomers();
}

