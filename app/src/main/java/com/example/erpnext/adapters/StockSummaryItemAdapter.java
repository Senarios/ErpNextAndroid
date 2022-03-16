package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.models.StockSummDatum;

import java.util.ArrayList;
import java.util.List;

public class StockSummaryItemAdapter extends RecyclerView.Adapter<StockSummaryItemAdapter.ViewHolder> {
    private final Context context;
    private List<StockSummDatum> itemList = new ArrayList<>();

    public StockSummaryItemAdapter(Context context, List<StockSummDatum> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.stock_summary_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.warehouse_name.setText(itemList.get(position).getWarehouse());
        holder.item_name.setText(itemList.get(position).getItemCode()+"("+itemList.get(position).getItemName()+")");
        holder.item_qty.setText(itemList.get(position).getReservedQty()+"  "+itemList.get(position).getActualQty());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView warehouse_name, item_name, item_qty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            warehouse_name = itemView.findViewById(R.id.stock_warehouse_name);
            item_name = itemView.findViewById(R.id.stock_item);
            item_qty = itemView.findViewById(R.id.item_qty);
        }
    }
}
