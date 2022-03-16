package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.CollectionTierCallback;
import com.example.erpnext.models.CollectionTier;


public class CollectionTierViewHolder extends RecyclerView.ViewHolder {

    public TextView name, collectionFactor, date, counter;
    public RecyclerView linksRV;
    public View parent;
    ImageView cross;

    public CollectionTierViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        name = itemView.findViewById(R.id.tier_name);
        collectionFactor = itemView.findViewById(R.id.collection_factor);
        counter = itemView.findViewById(R.id.number);
        cross = itemView.findViewById(R.id.cross);
        date = itemView.findViewById(R.id.date);


    }

    public void setData(Context context, CollectionTier item, CollectionTierCallback callback, int position, CollectionTierViewHolder holder) {
        name.setText(item.getName());
        collectionFactor.setText("" + item.getCollectionFactor());
//      amount.setText("$ "+item.getTotal());
        counter.setText(String.valueOf(position + 1));
        cross.setOnClickListener(v -> {
            callback.onDeleteTierClick(item, holder, position);
        });

//        if (position == 0) {
//            AddNewClosingActivity.netTotal = 0;
//            AddNewClosingActivity.grandTotal = 0;
//            AddNewClosingActivity.totalQuantity = 0;
//        }
//            AddNewClosingActivity.netTotal = AddNewClosingActivity.netTotal +Float.parseFloat( String.valueOf(item.getNetTotal()));
//            AddNewClosingActivity.grandTotal = AddNewClosingActivity.grandTotal +  Float.parseFloat( String.valueOf(item.getGrandTotal()));
//            AddNewClosingActivity.totalQuantity = AddNewClosingActivity.totalQuantity +  Float.parseFloat( String.valueOf(item.getTotalQty()));
//
//
//        AddNewClosingActivity.setTotal();
    }

}
