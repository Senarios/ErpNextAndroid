package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.ModeOfPaymentViewHolder;
import com.example.erpnext.callbacks.CartItemCallback;
import com.example.erpnext.models.InvoicePayment;

import java.util.ArrayList;
import java.util.List;

public class ModeOfPaymentsAdapter extends RecyclerView.Adapter<ModeOfPaymentViewHolder> {

    private final Context context;
    private List<InvoicePayment> itemList;
    private CartItemCallback callback;

    public ModeOfPaymentsAdapter(Context context, List<InvoicePayment> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ModeOfPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.payments_concilation_list_item, parent, false);
        return new ModeOfPaymentViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ModeOfPaymentViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<InvoicePayment> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<InvoicePayment> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }


    public List<InvoicePayment> getAllItems() {
        return this.itemList;
    }

}