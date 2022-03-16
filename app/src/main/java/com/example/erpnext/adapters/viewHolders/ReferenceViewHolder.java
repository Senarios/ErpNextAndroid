package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.ReferenceAdapter;
import com.example.erpnext.callbacks.AddLandedCostCallback;
import com.example.erpnext.models.Reference;


public class ReferenceViewHolder extends RecyclerView.ViewHolder {


    public TextView linkDocType, linkName, itemQuantity, linkTitle, warehouse;
    public View parent;
    ReferenceAdapter adapter;
    ImageView cross;
    int position;

    public ReferenceViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        linkDocType = itemView.findViewById(R.id.link_doc_type);
        linkName = itemView.findViewById(R.id.link_name);
        cross = itemView.findViewById(R.id.cross);
        linkTitle = itemView.findViewById(R.id.link_title);


    }

    public void setData(Context context, Reference item, AddLandedCostCallback callback, int position, ReferenceAdapter adapter) {
        this.adapter = adapter;
        this.position = position;
        linkDocType.setText(item.getLinkDocType());
        linkTitle.setText(item.getLinkTitle());
        linkName.setText(item.getLinkName());

        cross.setOnClickListener(v -> {
//            callback.onDeleteChargesClick(item, position);
        });
    }

}
