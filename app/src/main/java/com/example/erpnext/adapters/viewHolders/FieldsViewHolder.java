package com.example.erpnext.adapters.viewHolders;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewPOSProfileActivity;
import com.example.erpnext.adapters.LinksAdapter;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.fragments.POSProfileDetailFragment;
import com.example.erpnext.models.Field;
import com.example.erpnext.models.SearchResult;
import com.example.erpnext.network.Network;
import com.example.erpnext.network.NetworkCall;
import com.example.erpnext.network.OnNetworkResponse;
import com.example.erpnext.network.serializers.requestbody.SearchLinkRequestBody;
import com.example.erpnext.network.serializers.response.BaseResponse;
import com.example.erpnext.network.serializers.response.SearchLinkResponse;
import com.example.erpnext.roomDB.data.DBSearchLink;
import com.example.erpnext.utils.RequestCodes;
import com.example.erpnext.utils.Utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class FieldsViewHolder extends RecyclerView.ViewHolder implements OnNetworkResponse {

    public TextView name, required;
    public AutoCompleteTextView editText;
    public RecyclerView linksRV;
    public LinksAdapter adapter;
    public View parent;
    Field field;
    Context context;

    public FieldsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        name = itemView.findViewById(R.id.field_label);
        required = itemView.findViewById(R.id.required);
        editText = itemView.findViewById(R.id.autoCompleteEdit);


    }

    public void setData(Context context, Field field, ProfilesCallback callback, boolean isEditMode) {
        this.context = context;
        this.field = field;
        if (isEditMode) {
            if (field.getLabel().equalsIgnoreCase("Company")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getCompany());
            } else if (field.getLabel().equalsIgnoreCase("Customer")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getCustomer());
            } else if (field.getLabel().equalsIgnoreCase("Warehouse")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getWarehouse());
            } else if (field.getLabel().equalsIgnoreCase("Campaign")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getCampaign());
            } else if (field.getLabel().equalsIgnoreCase("Company Address")) {
                editText.setText(POSProfileDetailFragment.docDetail.getDocs().get(0).getCompanyAddress());
            }
        }
        name.setText(field.getLabel());
        if (field.getReqd() == 1) {
            required.setVisibility(View.VISIBLE);
        }

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (Utils.isNetworkAvailable()) getLinkSearch(field);
                    else DBSearchLink.load(context, field.getLabel(), editText);
                }
            }
        });
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (isEditMode) {
                    POSProfileDetailFragment.editedData.put(field.getFieldname(), selectedItem);
                } else {
                    AddNewPOSProfileActivity.data.put(field.getFieldname(), selectedItem);
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


//        parent.setOnClickListener(view -> callback.onProfileClick(item));


    }


    private void getLinkSearch(Field field) {
        NetworkCall.make()
                .setCallback(this)
                .setTag(RequestCodes.API.LINK_SEARCH)
                .autoLoadingCancel(Utils.getLoading((Activity) context, "searching"))
                .enque(Network.apis().getSearchLink(new SearchLinkRequestBody("", field.getLabel(), "0", "")))
                .execute();
    }


    @Override
    public void onSuccess(Call call, Response response, Object tag) throws ParseException {
        if ((int) tag == RequestCodes.API.LINK_SEARCH) {
            SearchLinkResponse res = (SearchLinkResponse) response.body();
            List<String> list = new ArrayList<>();
            if (res != null && !res.getResults().isEmpty()) {
                DBSearchLink.save(res, field.getLabel());
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
}
