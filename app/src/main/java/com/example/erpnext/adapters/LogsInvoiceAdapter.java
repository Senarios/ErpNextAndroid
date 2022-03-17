package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.LogsInvoiceViewHolder;
import com.example.erpnext.adapters.viewHolders.StockListsViewHolder;
import com.example.erpnext.models.PendingOrder;

import java.util.List;

public class LogsInvoiceAdapter extends RecyclerView.Adapter<LogsInvoiceViewHolder> {
    private Context context;
    private List<PendingOrder> orderList;

    public LogsInvoiceAdapter(Context context, List<PendingOrder> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public LogsInvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pos_profile_list_item, parent, false);
        return new LogsInvoiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LogsInvoiceViewHolder holder, int position) {
        holder.name.setText(orderList.get(position).getItem_group());
        holder.setData(context, orderList.get(position).getOreder());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
