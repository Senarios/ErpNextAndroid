package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.InvoiceItemsViewHolder;
import com.example.erpnext.callbacks.CartItemCallback;
import com.example.erpnext.models.InvoiceItem;

import java.util.ArrayList;
import java.util.List;

public class InvoiceItemsAdapter extends RecyclerView.Adapter<InvoiceItemsViewHolder> {

    private final Context context;
    private final CartItemCallback callback;
    private List<InvoiceItem> itemList;

    public InvoiceItemsAdapter(Context context, List<InvoiceItem> itemList, CartItemCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public InvoiceItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pos_ivoice_list_litems, parent, false);
        return new InvoiceItemsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceItemsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<InvoiceItem> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }


    public List<InvoiceItem> getAllItems() {
        return this.itemList;
    }

}