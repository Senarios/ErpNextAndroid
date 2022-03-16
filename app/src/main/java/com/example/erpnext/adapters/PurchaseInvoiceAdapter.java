package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.PurchaseInvoiceViewHolder;
import com.example.erpnext.callbacks.PurchaseInvoiceCallback;

import java.util.ArrayList;
import java.util.List;

public class PurchaseInvoiceAdapter extends RecyclerView.Adapter<PurchaseInvoiceViewHolder> {

    private final Context context;
    private final PurchaseInvoiceCallback callback;
    private List<List<String>> itemsList;

    public PurchaseInvoiceAdapter(Context context, List<List<String>> itemsList, PurchaseInvoiceCallback callback) {
        this.context = context;
        this.itemsList = itemsList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public PurchaseInvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.purchase_invoice_list_item, parent, false);
        return new PurchaseInvoiceViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseInvoiceViewHolder holder, int position) {
        holder.setData(context, itemsList.get(position), callback, holder, position);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void filteredList(ArrayList<List<String>> filteredList) {
        this.itemsList = filteredList;
        notifyDataSetChanged();
    }

    public void remove(List<String> item) {
        List<List<String>> lists = new ArrayList<>();
        for (List<String> stringList : itemsList) {
            if (!stringList.get(1).equalsIgnoreCase(item.get(1))) {

            }
        }
    }
}