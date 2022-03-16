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
import com.example.erpnext.adapters.LinksAdapter;
import com.example.erpnext.callbacks.LinksCallback;
import com.example.erpnext.callbacks.ProfilesCallback;
import com.example.erpnext.fragments.POSProfileListFragment;
import com.example.erpnext.models.Item;
import com.example.erpnext.models.Link;

import java.util.List;


public class CardItemsViewHolder extends RecyclerView.ViewHolder implements LinksCallback {

    public TextView name;
    public RecyclerView linksRV;
    public LinksAdapter adapter;
    public View parent;
    Context context;

    public CardItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        name = itemView.findViewById(R.id.card_name);
        linksRV = itemView.findViewById(R.id.items_RV);


    }

    public void setData(Context context, Item item, ProfilesCallback callback) {
        name.setText(item.getLabel());
        if (item.getLinks() != null && !item.getLinks().isEmpty()) {
            setLinkAdapter(context, item.getLinks());
        }

//        parent.setOnClickListener(view -> callback.onProfileClick(item));
//        setStacKImageView();

    }

    private void setLinkAdapter(Context context, List<Link> linkList) {
        this.context = context;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        adapter = new LinksAdapter(context, linkList, this);
        linksRV.setLayoutManager(linearLayoutManager);
        linksRV.setAdapter(adapter);
    }

    @Override
    public void onLinkClick(Link link) {
        if (link.getLabel().equalsIgnoreCase("Point-of-sale profile")) {
            Bundle bundle = new Bundle();
            bundle.putString("docType", "POS Profile");
            ((MainActivity) context).fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
        } else if (link.getLabel().equalsIgnoreCase("POS Opening Entry")) {
            Bundle bundle = new Bundle();
            bundle.putString("docType", "POS Opening Entry");
            ((MainActivity) context).fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
        } else if (link.getLabel().equalsIgnoreCase("POS Closing Entry")) {
            Bundle bundle = new Bundle();
            bundle.putString("docType", "POS Closing Entry");
            ((MainActivity) context).fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
        } else if (link.getLabel().equalsIgnoreCase("Loyalty Program")) {
            Bundle bundle = new Bundle();
            bundle.putString("docType", "Loyalty Program");
            ((MainActivity) context).fragmentTrx(POSProfileListFragment.newInstance(), bundle, "POSProfileListFragment");
        }

    }


}
