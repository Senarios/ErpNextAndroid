package com.example.erpnext.adapters.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.StockEntryCallback;
import com.example.erpnext.models.StockEntryItem;

public class StockEntryItemViewHolder extends RecyclerView.ViewHolder {

    public TextView number, sourceWarehouse, targetWarehouse, itemCode, qty;
    public ImageView delete;


    public StockEntryItemViewHolder(@NonNull View itemView) {
        super(itemView);
        number = itemView.findViewById(R.id.number);
        sourceWarehouse = itemView.findViewById(R.id.source_warehouse);
        targetWarehouse = itemView.findViewById(R.id.target_warehouse);
        itemCode = itemView.findViewById(R.id.item_code);
        qty = itemView.findViewById(R.id.qty);
        delete = itemView.findViewById(R.id.cross);
    }

    public void setData(StockEntryItem item, int position, StockEntryCallback callback) {
        this.number.setText(String.valueOf(position));
        this.sourceWarehouse.setText(item.getSourceWarehouse());
        this.targetWarehouse.setText(item.getTargetWarehouse());
        this.itemCode.setText(item.getItemCode());
        this.qty.setText(item.getQty());
        delete.setOnClickListener(v -> callback.onDeleteItemClick(position));
    }
}
