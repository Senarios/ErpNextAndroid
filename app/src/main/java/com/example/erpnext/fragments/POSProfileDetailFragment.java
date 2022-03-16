package com.example.erpnext.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.MainActivity;
import com.example.erpnext.adapters.CommentsAdapter;
import com.example.erpnext.adapters.FieldsAdapter;
import com.example.erpnext.adapters.ReadOnlyFieldsAdapter;
import com.example.erpnext.adapters.SectionsBreaksFieldsAdapter;
import com.example.erpnext.app.AppSession;
import com.example.erpnext.app.MainApp;
import com.example.erpnext.callbacks.CommentsCallback;
import com.example.erpnext.models.Comment;
import com.example.erpnext.models.Doc;
import com.example.erpnext.models.Field;
import com.example.erpnext.models.Payments;
import com.example.erpnext.models.PendingEditPOS;
import com.example.erpnext.models.SaveDoc;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.EditProfileRequestBody;
import com.example.erpnext.network.serializers.response.AddCommentResponse;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.DocDetailResponse;
import com.example.erpnext.network.serializers.response.SaveDocResponse;
import com.example.erpnext.roomDB.data.Room;
import com.example.erpnext.utils.Notify;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link POSProfileDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class POSProfileDetailFragment extends Fragment implements View.OnClickListener, OnNetworkResponse, CommentsCallback {

    public static HashMap<String, String> editedData = new HashMap<String, String>();
    public static DocDetailResponse docDetail;
    public static Doc doc;
    public static List<String> profileStringList = new ArrayList<>();
    private final List<Field> linkFieldList = new ArrayList<>();
    private final List<Field> currentFieldList = new ArrayList<>();
    private final List<Field> fieldsListRv = new ArrayList<>();
    ArrayList<List<Field>> filteredSectionFieldLists = new ArrayList<>();
    ArrayList<List<Field>> filteredReadOnlyFieldLists = new ArrayList<>();
    boolean isSectionToAdd = false;
    boolean isReadOnlyToAdd = false;
    boolean isValidName = false;
    private SaveDoc editedDoc;
    private List<Field> fieldList = new ArrayList<>();
    private RecyclerView fieldsRv, sectionBreakRv, readOnlyRv, commentsRV;
    private FieldsAdapter fieldsAdapter;
    private ReadOnlyFieldsAdapter readOnlyFieldsAdapter;
    private SectionsBreaksFieldsAdapter sectionsBreaksFieldsAdapter;
    private CommentsAdapter commentsAdapter;
    private ImageView back, commentDropdownArrow;
    private TextView name_tv;
    private Button save_btn, comment_btn;
    private EditText comment_edit;
    private LinearLayout commentLayout;
    private RelativeLayout assigneesLayout, tagsLayout, reviewsLayout, sharedLayout, attachmentLayout, addComment;


    public POSProfileDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static POSProfileDetailFragment newInstance() {
        POSProfileDetailFragment fragment = new POSProfileDetailFragment();
        return fragment;
    }

    public static POSProfileDetailFragment newInstance2(Bundle bundle) {
        POSProfileDetailFragment fragment = new POSProfileDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_p_o_s_profile_detail, container, false);

        initViews(view);
        setClickisteners();
        editedData = new HashMap<>();
        docDetail = new DocDetailResponse();
        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey("doc")) {
            String docJsonString = (String) bundle.get("doc");
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
//            Type type = new TypeToken<List<Field>>() {}.getType();
//            fieldList = gson.fromJson(projectJsonString, type);
            doc = gson.fromJson(docJsonString, Doc.class);
            if (doc != null && bundle.containsKey("profileStringList")) {
                String profileJsonString = (String) bundle.get("profileStringList");
                Type type = new TypeToken<List<String>>() {
                }.getType();
                profileStringList = gson.fromJson(profileJsonString, type);
                if (!profileStringList.isEmpty()) {
                    if (Utils.isNetworkAvailable()) getProfileDoc();
                    else {
                        loadFromDB();
                    }
                }
            }
        }
        return view;

    }

    private void setClickisteners() {
        back.setOnClickListener(this);
        save_btn.setOnClickListener(this);
        assigneesLayout.setOnClickListener(this);
        attachmentLayout.setOnClickListener(this);
        reviewsLayout.setOnClickListener(this);
        tagsLayout.setOnClickListener(this);
        sharedLayout.setOnClickListener(this);
        comment_btn.setOnClickListener(this);
        addComment.setOnClickListener(this);

    }

    private void initViews(View view) {
        fieldsRv = view.findViewById(R.id.editFieldsRV);
        sectionBreakRv = view.findViewById(R.id.sectionBreakFieldsRV);
        readOnlyRv = view.findViewById(R.id.readOnlyFieldsRV);
        commentsRV = view.findViewById(R.id.comments_Rv);
        back = view.findViewById(R.id.back);
        save_btn = view.findViewById(R.id.save);
        name_tv = view.findViewById(R.id.name);
        assigneesLayout = view.findViewById(R.id.assignee_layout);
        attachmentLayout = view.findViewById(R.id.attachment_layout);
        reviewsLayout = view.findViewById(R.id.reviews_layout);
        tagsLayout = view.findViewById(R.id.tags_layout);
        sharedLayout = view.findViewById(R.id.shared_layout);
        commentDropdownArrow = view.findViewById(R.id.down_arrow);
        commentLayout = view.findViewById(R.id.comment_layout);
        comment_edit = view.findViewById(R.id.comment_edit);
        comment_btn = view.findViewById(R.id.comment);
        addComment = view.findViewById(R.id.add_comment);
        commentDropdownArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);

    }

    private void setFieldsAdapter(List<Field> fieldList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fieldsAdapter = new FieldsAdapter(getContext(), fieldList, true);
        fieldsRv.setLayoutManager(linearLayoutManager);
        fieldsRv.setAdapter(fieldsAdapter);
    }

    private void setSectionFieldsAdapter(List<List<Field>> fieldList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        sectionsBreaksFieldsAdapter = new SectionsBreaksFieldsAdapter(getContext(), fieldList, true);
        sectionBreakRv.setLayoutManager(linearLayoutManager);
        sectionBreakRv.setAdapter(sectionsBreaksFieldsAdapter);
    }

    private void setReadOnlyFieldsAdapter(List<List<Field>> fieldList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        readOnlyFieldsAdapter = new ReadOnlyFieldsAdapter(getContext(), fieldList, true);
        readOnlyRv.setLayoutManager(linearLayoutManager);
        readOnlyRv.setAdapter(readOnlyFieldsAdapter);
    }

    private void setCommentsAdapter(List<Comment> comments) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        commentsAdapter = new CommentsAdapter(getContext(), comments, this);
        commentsRV.setLayoutManager(linearLayoutManager);
        commentsRV.setAdapter(commentsAdapter);
    }

    private void filterSectionAndLinkLists(List<Field> fieldList) {
        if (!fieldList.isEmpty()) {

            for (Field field : fieldList) {
                if (field.getFieldtype().equalsIgnoreCase("Section Break")) {
                    isSectionToAdd = true;
                    createList(field, true);

                } else if (isSectionToAdd) {
                    createList(field, false);

                } else if (field.getFieldtype().equalsIgnoreCase("Link")) {
                    linkFieldList.add(field);
                }
            }

        }
    }

    private void filteredReadOnlyFieldLists(List<Field> fieldList) {
        if (!fieldList.isEmpty()) {
            if (!currentFieldList.isEmpty()) currentFieldList.clear();
            for (Field field : fieldList) {
                if (field.getFieldtype().equalsIgnoreCase("Read only")) {
                    isReadOnlyToAdd = true;
                    createRealOnlyList(field, true);

                } else if (isReadOnlyToAdd) {
                    boolean ended = false;
                    if (field.getFieldtype().equalsIgnoreCase("Column Break")) {
                        ended = true;
                    }

                    if (!ended) {
                        createRealOnlyList(field, false);
                    } else {
                        List<Field> list = new ArrayList<>();
                        for (Field field1 : currentFieldList) {
                            list.add(field1);
                        }
                        filteredReadOnlyFieldLists.add(list);
                        isReadOnlyToAdd = false;
                        currentFieldList.clear();
                    }
                }
            }

        }

    }

    private void createRealOnlyList(Field field, boolean isNewList) {
        if (isNewList) {
            if (!currentFieldList.isEmpty()) {
                List<Field> list = new ArrayList<>();
                for (Field field1 : currentFieldList) {
                    list.add(field1);
                }
                filteredReadOnlyFieldLists.add(list);
                currentFieldList.clear();

            }
            currentFieldList.add(field);

        } else currentFieldList.add(field);
    }

    private void createList(Field field, boolean isNewList) {
        if (isNewList) {
            if (!currentFieldList.isEmpty()) {
                List<Field> list = new ArrayList<>();
                for (Field field1 : currentFieldList) {
                    list.add(field1);
                }
                filteredSectionFieldLists.add(list);
                currentFieldList.clear();

            }
            currentFieldList.add(field);

        } else {
            if (field.getFieldname().equalsIgnoreCase("payments")) {
                field.setLabel("Mode of Payment");
            }
            currentFieldList.add(field);
        }
    }

    @Override
    public void onDeleteCommentClick(Comment comment) {
        removeComment(comment);
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.back:
                getActivity().onBackPressed();
                break;
            case R.id.save:
                getChanges();
                break;
            case R.id.assignee_layout:
                if (Utils.isNetworkAvailable()) {
                    bundle.putString("key", "assignment");
                    ((MainActivity) getActivity()).fragmentTrx(AddFragment.newInstance(), bundle, "AddFragment");
                } else Notify.Toast(getString(R.string.offline_mode));
                break;
            case R.id.tags_layout:
                if (Utils.isNetworkAvailable()) {
                    bundle.putString("key", "tag");
                    ((MainActivity) getActivity()).fragmentTrx(AddFragment.newInstance(), bundle, "AddFragment");
                } else Notify.Toast(getString(R.string.offline_mode));
                break;
            case R.id.reviews_layout:
            case R.id.shared_layout:
                Notify.Toast("Coming soon");
                break;
            case R.id.attachment_layout:
                if (Utils.isNetworkAvailable()) {
                    bundle.putString("key", "attachments");
                    ((MainActivity) getActivity()).fragmentTrx(AddFragment.newInstance(), bundle, "AddFragment");
                } else Notify.Toast(getString(R.string.offline_mode));
                break;
            case R.id.add_comment:
                if (Utils.isNetworkAvailable()) {
                    if (commentLayout.getVisibility() == View.VISIBLE) {
                        commentLayout.setVisibility(View.GONE);
                        commentDropdownArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    } else {
                        commentLayout.setVisibility(View.VISIBLE);
                        commentDropdownArrow.setBackgroundResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    }
                } else Notify.Toast(getString(R.string.offline_mode));
                break;
            case R.id.comment:
                if (comment_edit.getText().toString() == null || comment_edit.getText().toString().equalsIgnoreCase("")) {
                    Notify.Toast(getString(R.string.write_comment));
                } else addComment(comment_edit.getText().toString());
                break;
        }
    }


    private void getChanges() {
        editedDoc.setCompany(editedData.get("company"));
        editedDoc.setCustomer(editedData.get("customer"));
        editedDoc.setCountry(editedData.get("country"));
        editedDoc.setDisabled(Integer.valueOf(editedData.get("disabled")));
        editedDoc.setWarehouse(editedData.get("warehouse"));
        editedDoc.setCampaign(editedData.get("campaign"));
        editedDoc.setCompanyAddress(editedData.get("company_address"));
        editedDoc.setHideImages(Integer.valueOf(editedData.get("hide_images")));
        editedDoc.setHideUnavailableItems(Integer.valueOf(editedData.get("hide_unavailable_items")));
        editedDoc.setAutoAddItemToCart(Integer.valueOf(editedData.get("auto_add_item_to_cart")));
        editedDoc.setUpdateStock(Integer.valueOf(editedData.get("update_stock")));
        editedDoc.setIgnorePricingRule(Integer.valueOf(editedData.get("ignore_pricing_rule")));
        editedDoc.setAllowRateChange(Integer.valueOf(editedData.get("allow_rate_change")));
        editedDoc.setAllowDiscountChange(Integer.valueOf(editedData.get("allow_discount_change")));
        editedDoc.setPrintFormat(editedData.get("print_format"));
        editedDoc.setLetterHead(editedData.get("letter_head"));
        editedDoc.setTcName(editedData.get("tc_name"));
        editedDoc.setSelectPrintHeading(editedData.get("select_print_heading"));
        editedDoc.setSellingPriceList(editedData.get("selling_price_list"));
        editedDoc.setCurrency(editedData.get("currency"));
        editedDoc.setWriteOffAccount(editedData.get("write_off_account"));
        editedDoc.setWriteOffCostCenter(editedData.get("write_off_cost_center"));
        editedDoc.setAccountForChangeAmount(editedData.get("account_for_change_amount"));
        editedDoc.setIncomeAccount(editedData.get("income_account"));
        editedDoc.setExpenseAccount(editedData.get("expense_account"));
        editedDoc.setTaxesAndCharges(editedData.get("taxes_and_charges"));
        editedDoc.setTaxCategory(editedData.get("tax_category"));
        editedDoc.setApplyDiscountOn(editedData.get("apply_discount_on"));
        editedDoc.setCostCenter(editedData.get("cost_center"));

        editedDoc.setApplicableForUsers(docDetail.getDocs().get(0).getApplicableForUsers());
        editedDoc.setItemGroups(docDetail.getDocs().get(0).getItemGroups());
        editedDoc.setCustomerGroups(docDetail.getDocs().get(0).getCustomerGroups());
        List<Payments> paymentList = docDetail.getDocs().get(0).getPayments();
        if (editedData.containsKey("payments")) {
            paymentList = docDetail.getDocs().get(0).getPayments();
            paymentList.get(0).setModeOfPayment(editedData.get("payments"));
            editedDoc.setPayments(paymentList);
        }
        editedDoc.setModifiedBy(AppSession.get("email"));

        if (Utils.isNetworkAvailable()) {
            saveDoc();
        } else {
            EditProfileRequestBody editProfileRequestBody = new EditProfileRequestBody();
            List<Payments> payments = new ArrayList<>();
            List<Object> applicalblelist = new ArrayList<>();
            List<Object> itemsGroupList = new ArrayList<>();
            List<Object> customerGroupList = new ArrayList<>();
            if (editedDoc.getPayments() != null) {
                payments.addAll(editedDoc.getPayments());
            }
            editProfileRequestBody.setPaymentsList(payments);
            editedDoc.setPayments(null);
            if (editedDoc.getApplicableForUsers() != null) {
                applicalblelist.addAll(editedDoc.getApplicableForUsers());
            }
            editProfileRequestBody.setApplicableForuserslist(applicalblelist);
            editedDoc.setApplicableForUsers(null);
            if (editedDoc.getItemGroups() != null) {
                itemsGroupList.addAll(editedDoc.getItemGroups());
            }
            editProfileRequestBody.setItemsGroupList(itemsGroupList);
            editedDoc.setItemGroups(null);
            if (editedDoc.getCustomerGroups() != null) {
                customerGroupList.addAll(editedDoc.getCustomerGroups());
            }
            editProfileRequestBody.setCustomergroupslist(customerGroupList);
            editedDoc.setCustomerGroups(null);
            try {
                editProfileRequestBody.setDoc(new JSONObject(new Gson().toJson(editedDoc)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            editProfileRequestBody.setAction("Save");
            PendingEditPOS pendingEditPOS = new PendingEditPOS();
            pendingEditPOS.setEditProfileRequestBody(editProfileRequestBody);
            MainApp.database.pendingEditPOSDao().insert(pendingEditPOS);
            List<SaveDoc> saveDocs = new ArrayList<>();
            saveDocs.add(editedDoc);
            docDetail.setDocs(saveDocs);
            Room.saveProfileDetail(docDetail);
            Notify.ToastLong(getString(R.string.profile_updated));
            getActivity().onBackPressed();
        }

    }

    private void setDocData() {
        editedData.put("company", docDetail.getDocs().get(0).getCompany());
        editedData.put("customer", docDetail.getDocs().get(0).getCustomer());
        editedData.put("country", docDetail.getDocs().get(0).getCountry());
        editedData.put("disabled", String.valueOf(docDetail.getDocs().get(0).getDisabled()));
        editedData.put("warehouse", docDetail.getDocs().get(0).getWarehouse());
        editedData.put("campaign", docDetail.getDocs().get(0).getCampaign());
        editedData.put("company_address", docDetail.getDocs().get(0).getCompanyAddress());
        if (docDetail.getDocs().get(0).getApplicableForUsers() != null) {
            editedData.put("applicable_for_users", docDetail.getDocs().get(0).getApplicableForUsers().toString());
        }
        editedData.put("hide_images", String.valueOf(docDetail.getDocs().get(0).getHideImages()));
        editedData.put("hide_unavailable_items", String.valueOf(docDetail.getDocs().get(0).getHideUnavailableItems()));
        editedData.put("auto_add_item_to_cart", String.valueOf(docDetail.getDocs().get(0).getAutoAddItemToCart()));
        editedData.put("update_stock", String.valueOf(docDetail.getDocs().get(0).getUpdateStock()));
        editedData.put("ignore_pricing_rule", String.valueOf(docDetail.getDocs().get(0).getIgnorePricingRule()));
        editedData.put("allow_rate_change", String.valueOf(docDetail.getDocs().get(0).getAllowRateChange()));
        editedData.put("allow_discount_change", String.valueOf(docDetail.getDocs().get(0).getAllowDiscountChange()));
        if (docDetail.getDocs().get(0).getItemGroups() != null) {
            editedData.put("item_groups", docDetail.getDocs().get(0).getItemGroups().toString());
        }
        if (docDetail.getDocs().get(0).getCustomerGroups() != null) {
            editedData.put("customer_groups", docDetail.getDocs().get(0).getCustomerGroups().toString());
        }
        editedData.put("print_format", docDetail.getDocs().get(0).getPrintFormat());
        editedData.put("letter_head", docDetail.getDocs().get(0).getLetterHead());
        editedData.put("tc_name", docDetail.getDocs().get(0).getTcName());
        editedData.put("select_print_heading", docDetail.getDocs().get(0).getSelectPrintHeading());
        editedData.put("selling_price_list", docDetail.getDocs().get(0).getSellingPriceList());
        editedData.put("currency", docDetail.getDocs().get(0).getCurrency());
        editedData.put("write_off_account", docDetail.getDocs().get(0).getWriteOffAccount());
        editedData.put("write_off_cost_center", docDetail.getDocs().get(0).getWriteOffCostCenter());
        editedData.put("account_for_change_amount", docDetail.getDocs().get(0).getAccountForChangeAmount());
        editedData.put("income_account", docDetail.getDocs().get(0).getIncomeAccount());
        editedData.put("expense_account", docDetail.getDocs().get(0).getExpenseAccount());
        editedData.put("taxes_and_charges", docDetail.getDocs().get(0).getTaxesAndCharges());
        editedData.put("tax_category", docDetail.getDocs().get(0).getTaxCategory());
        editedData.put("apply_discount_on", docDetail.getDocs().get(0).getApplyDiscountOn());
        editedData.put("cost_center", docDetail.getDocs().get(0).getCostCenter());


//        List<Payment> paymentList = new ArrayList<>();
//        List<SavePayment> savePaymentList=docDetail.getDocs().get(0).getPayments();
//        for (SavePayment savePayment:savePaymentList){
//            Payment payment = new Payment();
//            payment.setDefault(savePayment.getDefault());
//            payment.setModeOfPayment(savePayment.getModeOfPayment());
//            paymentList.add(payment);
//        }
//        data.put("payments",new Gson().toJson(paymentList));

    }

    private void loadFromDB() {
        DocDetailResponse docDetail = Room.getProfileDetail(profileStringList.get(0));
        if (docDetail != null && docDetail.getDocs() != null) {
            POSProfileDetailFragment.docDetail = docDetail;
        }
        if (docDetail != null) {
            Room.saveProfileDetail(docDetail);
            name_tv.setText(docDetail.getDocs().get(0).getName());
            editedDoc = docDetail.getDocs().get(0);
            filteredSectionFieldLists.clear();
            filteredReadOnlyFieldLists.clear();
            linkFieldList.clear();
            setDocData();
            if (doc != null && doc.getFields() != null) {
                fieldList = doc.getFields();
                if (!fieldList.isEmpty()) {
                    filterSectionAndLinkLists(fieldList);
                    if (!currentFieldList.isEmpty()) {
                        List<Field> fields = new ArrayList<>();
                        fields.addAll(currentFieldList);
                        filteredSectionFieldLists.add(fields);
                        currentFieldList.clear();
                    }
                    filteredReadOnlyFieldLists(fieldList);
                }
                if (!linkFieldList.isEmpty()) setFieldsAdapter(linkFieldList);
                if (!filteredReadOnlyFieldLists.isEmpty())
                    setReadOnlyFieldsAdapter(filteredReadOnlyFieldLists);
                if (!filteredSectionFieldLists.isEmpty())
                    setSectionFieldsAdapter(filteredSectionFieldLists);
                if (docDetail.getDocinfo().getComments() != null && !docDetail.getDocinfo().getComments().isEmpty()) {
                    setCommentsAdapter(docDetail.getDocinfo().getComments());
                }
            }
        }
    }

    public void getProfileDoc() {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.GET_DOC)
                .autoLoadingCancel(Utils.getLoading((Activity) getContext(), "Loading..."))
                .enque(Network.apis().getDoc(doc.getName(), profileStringList.get(0)))
                .execute();
    }

    private void addComment(String comment) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.ADD_COMMENT)
                .autoLoadingCancel(Utils.getLoading((Activity) getContext(), "Please wait..."))
                .enque(Network.apis().addComment("POS Profile", docDetail.getDocs().get(0).getName(), comment, AppSession.get("email"), AppSession.get("full_name")))
                .execute();
    }

    public void removeComment(Comment comment) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.REMOVE_COMMENT)
                .autoLoadingCancel(Utils.getLoading((Activity) getContext(), "Removing..."))
                .enque(Network.apis().removeComment("Comment", comment.getName()))
                .execute();
    }

    private void saveDoc() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(new Gson().toJson(editedDoc));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.SAVE_EDITED_DOC)
                .autoLoadingCancel(Utils.getLoading((Activity) getContext(), "Loading..."))
                .enque(Network.apis().saveEditedDoc(jsonObject, "Save"))
                .execute();
    }

    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.GET_DOC) {
            docDetail = (DocDetailResponse) response.body();
            if (docDetail != null) {
                Room.saveProfileDetail(docDetail);
                name_tv.setText(docDetail.getDocs().get(0).getName());
                editedDoc = docDetail.getDocs().get(0);
                filteredSectionFieldLists.clear();
                filteredReadOnlyFieldLists.clear();
                linkFieldList.clear();
                setDocData();
                if (doc != null && doc.getFields() != null) {
                    fieldList = doc.getFields();
                    if (!fieldList.isEmpty()) {
                        filterSectionAndLinkLists(fieldList);
                        if (!currentFieldList.isEmpty()) {
                            List<Field> fields = new ArrayList<>();
                            fields.addAll(currentFieldList);
                            filteredSectionFieldLists.add(fields);
                            currentFieldList.clear();
                        }
                        filteredReadOnlyFieldLists(fieldList);
                    }
                    if (!linkFieldList.isEmpty()) setFieldsAdapter(linkFieldList);
                    if (!filteredReadOnlyFieldLists.isEmpty())
                        setReadOnlyFieldsAdapter(filteredReadOnlyFieldLists);
                    if (!filteredSectionFieldLists.isEmpty())
                        setSectionFieldsAdapter(filteredSectionFieldLists);
                    if (docDetail.getDocinfo().getComments() != null && !docDetail.getDocinfo().getComments().isEmpty()) {
                        setCommentsAdapter(docDetail.getDocinfo().getComments());
                    } else setCommentsAdapter(new ArrayList<>());
                }
            }
        } else if ((int) tag == RequestCodes.API.SAVE_EDITED_DOC) {
            SaveDocResponse res = (SaveDocResponse) response.body();
            if (res != null && res.getDocs() != null) {
                getActivity().onBackPressed();
                Notify.Toast("Successfully updated");
            }
        } else if ((int) tag == RequestCodes.API.REMOVE_COMMENT) {
            BaseResponse res = (BaseResponse) response.body();
            getProfileDoc();
            Notify.Toast("Removed");
        } else if ((int) tag == RequestCodes.API.ADD_COMMENT) {
            AddCommentResponse res = (AddCommentResponse) response.body();
            comment_edit.setText("");
            getProfileDoc();
        }
    }


    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }
}
