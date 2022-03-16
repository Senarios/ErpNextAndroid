package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.LinksAdapter;
import com.example.erpnext.callbacks.LinksCallback;
import com.example.erpnext.callbacks.ShortcutsCallback;
import com.example.erpnext.models.Item;
import com.example.erpnext.models.Link;

import java.util.List;


public class ShortcutsItemsViewHolder extends RecyclerView.ViewHolder implements LinksCallback {

    public TextView name;
    public RecyclerView linksRV;
    public LinksAdapter adapter;

    public View parent;

    public ShortcutsItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        name = itemView.findViewById(R.id.card_name);
        linksRV = itemView.findViewById(R.id.items_RV);


    }

    public void setData(Context context, Item item, ShortcutsCallback callback) {
        name.setText(item.getLabel());
        if (item.getLinks() != null && !item.getLinks().isEmpty()) {
            setLinkAdapter(context, item.getLinks());
        }

        parent.setOnClickListener(view -> callback.onShortcutClick(item));

    }

    private void setLinkAdapter(Context context, List<Link> linkList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        adapter = new LinksAdapter(context, linkList, this);
        linksRV.setLayoutManager(linearLayoutManager);
        linksRV.setAdapter(adapter);
    }

    @Override
    public void onLinkClick(Link link) {

    }
}
