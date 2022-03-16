package com.example.erpnext.adapters.viewHolders;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewOpeningActivity;
import com.example.erpnext.activities.AddNewPOSProfileActivity;
import com.example.erpnext.adapters.LinksAdapter;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.fragments.POSProfileDetailFragment;
import com.example.erpnext.models.Field;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkWithFiltersRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.roomDB.data.DBSearchLink;
import com.example.erpnext.utils.DateTime;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class POSOpeningFieldsViewHolder extends RecyclerView.ViewHolder implements OnNetworkResponse, DateTime.datePickerCallback, DateTime.timePickerCallback {

    public TextView name, required, text_field, text_field_label, text_required;
    public AutoCompleteTextView editText;
    public RecyclerView linksRV;
    public LinksAdapter adapter;
    public View parent;
    RelativeLayout editFieldsLayout, textLayout;
    String selectedDateField = "";
    Context context;
    Field field;
    String label = "", query = null, filters = null, reference = "POS Opening Entry";

    public POSOpeningFieldsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        name = itemView.findViewById(R.id.field_label);
        required = itemView.findViewById(R.id.required);
        text_required = itemView.findViewById(R.id.required_text);
        editText = itemView.findViewById(R.id.autoCompleteEdit);
        editFieldsLayout = itemView.findViewById(R.id.edit_field);
        textLayout = itemView.findViewById(R.id.text_layout);
        text_field_label = itemView.findViewById(R.id.field_label_text);
        text_field = itemView.findViewById(R.id.autoCompleteEdit_text);


    }

    public void setData(Context context, Field field, ProfilesCallback callback, boolean isEditMode) {
        this.context = context;
        this.field = field;
        if (field.getLabel().equalsIgnoreCase("Cashier")) {
            label = "User";
            query = "erpnext.accounts.doctype.pos_closing_entry.pos_closing_entry.get_cashiers";
        } else if (field.getLabel().equalsIgnoreCase("Opening Balance Details")) {
            label = "Mode of Payment";
            filters = null;
            query = null;
        } else {
            label = field.getLabel();
            query = null;
            filters = null;
        }
        if (isEditMode) {
//            if (field.getLabel().equalsIgnoreCase("Company")) {
//                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getCompany());
//            } else if (field.getLabel().equalsIgnoreCase("Customer")) {
//                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getCustomer());
//            } else if (field.getLabel().equalsIgnoreCase("Warehouse")) {
//                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getWarehouse());
//            } else if (field.getLabel().equalsIgnoreCase("Campaign")) {
//                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getCampaign());
//            } else if (field.getLabel().equalsIgnoreCase("Company Address")) {
//                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getCompanyAddress());
//            }
        }
        if (field.getFieldtype().equalsIgnoreCase("Date") || field.getFieldtype().equalsIgnoreCase("DateTime")) {
            textLayout.setVisibility(View.VISIBLE);
            text_field_label.setText(field.getLabel());
            if (field.getReqd() == 1) {
                text_required.setVisibility(View.VISIBLE);
            }
            text_field.setOnClickListener(v -> {
                selectedDateField = field.getFieldname();
                showDatePicker();
            });
            if (field.getFieldname().equalsIgnoreCase("posting_date")) {
                text_field.setText(DateTime.getCurrentDate());
                AddNewOpeningActivity.data.put(field.getFieldname(), DateTime.getCurrentDate());
            }
        } else {
            editFieldsLayout.setVisibility(View.VISIBLE);
            name.setText(field.getLabel());
            if (field.getReqd() == 1) {
                required.setVisibility(View.VISIBLE);
            }

            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        if (Utils.isNetworkAvailable())
                            getLinkSearch(field);
                        else {
                            if (filters == null && query == null) {
                                DBSearchLink.load(context, field.getLabel(), editText);

                            } else
                                DBSearchLink.load(context, field.getLabel(), filters, query, editText);
                        }
                    }
                }
            });
            editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    if (isEditMode) {
//                        POSProfileDetailFragment.editedData.put(field.getFieldname(), selectedItem);
                    } else {
                        AddNewOpeningActivity.data.put(field.getFieldname(), selectedItem);
                    }
                }
            });
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (isEditMode) {
                        POSProfileDetailFragment.editedData.put(field.getFieldname(), s.toString());
                    } else {
                        AddNewPOSProfileActivity.data.put(field.getFieldname(), s.toString());
                    }
                }
            });

        }
    }


    private void showDatePicker() {
        DateTime.showDatePicker((Activity) context, this);
    }


    private void getLinkSearch(Field field) {
        if (field.getLabel().equalsIgnoreCase("Cashier")) {
            label = "User";
            query = "erpnext.accounts.doctype.pos_closing_entry.pos_closing_entry.get_cashiers";
        } else if (field.getLabel().equalsIgnoreCase("Opening Balance Details")) {
            label = "Mode of Payment";
        } else label = field.getLabel();
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.LINK_SEARCH)
                .autoLoadingCancel(Utils.getLoading((Activity) context, "searching"))
                .enque(Network.apis().getSearchLinkWithFilters(new SearchLinkWithFiltersRequestBody("", label.trim(), "0", filters, "POS Opening Entry", query)))
                .execute();
    }


    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
//                if (field.getLabel().equalsIgnoreCase("Cashier")) {
//                    field.setLabel("User");
//                } else if (field.getLabel().equalsIgnoreCase("Opening Balance Details")) {
//                    field.setLabel("Mode of Payment");
//                }
                DBSearchLink.save(res, field.getLabel(), filters, reference, query);
                for (SearchResult searchResult : res.getResults()) {
                    list.add(searchResult.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, list);
                editText.setAdapter(adapter);
                editText.showDropDown();
            }
        }
    }

    @Override
    public void onFailure(Call call, BaseResponse response, Object tag) {

    }

    @Override
    public void onSelected(String date) {
        text_field.setText(date);
        AddNewOpeningActivity.data.put(selectedDateField, date);
        if (field.getFieldname().equalsIgnoreCase("period_end_date")) {
            DateTime.showTimePicker((Activity) context, this);
        }
    }

    @Override
    public void onSelectedTime(String time) {
        SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
        String time1 = null;

        try {
            time1 = date24Format.format(date12Format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        text_field.setText(text_field.getText().toString() + " " + time1);
        AddNewOpeningActivity.data.put(selectedDateField, text_field.getText().toString());
    }
}
