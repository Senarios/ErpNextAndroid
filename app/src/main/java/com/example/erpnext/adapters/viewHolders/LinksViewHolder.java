package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.LinksCallback;
import com.example.erpnext.models.Link;


public class LinksViewHolder extends RecyclerView.ViewHolder {

    public TextView linkName;
    public RecyclerView linksRV;

    public View parent;

    public LinksViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        linkName = itemView.findViewById(R.id.link_name);


    }

    public void setData(Context context, Link item, LinksCallback callback) {
        linkName.setText(item.getLabel());

        parent.setOnClickListener(view -> callback.onLinkClick(item));

    }


}
