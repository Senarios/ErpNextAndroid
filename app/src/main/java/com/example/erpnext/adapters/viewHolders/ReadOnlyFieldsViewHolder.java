package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.MainActivity;
import com.example.erpnext.adapters.InReadOnlyFieldsAdapter;
import com.example.erpnext.callbacks.LinksCallback;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.fragments.POSProfileListFragment;
import com.example.erpnext.models.Field;
import com.example.erpnext.models.Link;

import java.util.ArrayList;
import java.util.List;


public class ReadOnlyFieldsViewHolder extends RecyclerView.ViewHolder implements LinksCallback {

    public TextView name;
    public RecyclerView readOnlyRv;
    public InReadOnlyFieldsAdapter inReadOnlyFieldsAdapter;
    public View parent;
    boolean isEditMode;
    Context context;

    public ReadOnlyFieldsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        name = itemView.findViewById(R.id.field_label);
        readOnlyRv = itemView.findViewById(R.id.read_only_rv);
//        linksRV = itemView.findViewById(R.id.items_RV);


    }

    public void setData(Context context, List<Field> item, ProfilesCallback callback, boolean isEditMode) {
        this.isEditMode = isEditMode;
        name.setText(item.get(0).getLabel());
        item.remove(0);
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < item.size(); i++) {
            fields.add(item.get(i));
        }
        if (!fields.isEmpty()) setReadOnlyAdapter(context, fields);


//        parent.setOnClickListener(view -> callback.onProfileClick(item));
//        setStacKImageView();

    }

    private void setReadOnlyAdapter(Context context, List<Field> linkList) {
        this.context = context;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        inReadOnlyFieldsAdapter = new InReadOnlyFieldsAdapter(context, linkList, isEditMode);
        readOnlyRv.setLayoutManager(linearLayoutManager);
        readOnlyRv.setAdapter(inReadOnlyFieldsAdapter);
    }

    @Override
    public void onLinkClick(Link link) {
        if (link.getLabel().equalsIgnoreCase("Point-of-sale profile")) {
            Bundle bundle = new Bundle();
            bundle.putString("docType", "POS Profile");
            ((MainActivity) context).fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
        }

    }


}
