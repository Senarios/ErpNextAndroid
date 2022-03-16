package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.activities.AddNewClosingActivity;
import com.example.erpnext.adapters.viewHolders.POSInvoiceItemsViewHolder;
import com.example.erpnext.callbacks.SelectedInvoiceCallback;
import com.example.erpnext.models.Invoice;

import java.util.ArrayList;
import java.util.List;

public class POSInvoiceItemsAdapter extends RecyclerView.Adapter<POSInvoiceItemsViewHolder> {

    private final Context context;
    private final SelectedInvoiceCallback callback;
    private List<Invoice> itemList;

    public POSInvoiceItemsAdapter(Context context, List<Invoice> itemList, SelectedInvoiceCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public POSInvoiceItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.invoice_list_item, parent, false);
        return new POSInvoiceItemsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull POSInvoiceItemsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, holder);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<Invoice> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }


    public List<Invoice> getAllItems() {
        return this.itemList;
    }

    public void addItem(Invoice selectedInvoice) {
        if (itemList.size() > 0) {
            List<Invoice> list = new ArrayList<>();
            list.addAll(itemList);
            for (Invoice item : list) {
                if (!item.getName().equalsIgnoreCase(selectedInvoice.getName())) {
                    this.itemList.add(selectedInvoice);
                    AddNewClosingActivity.totalQuantity = AddNewClosingActivity.totalQuantity + Float.parseFloat(String.valueOf(selectedInvoice.getTotalQty()));
                    notifyDataSetChanged();
                }
            }
        } else {
            this.itemList.add(selectedInvoice);
            AddNewClosingActivity.totalQuantity = Float.parseFloat(String.valueOf(selectedInvoice.getTotalQty()));
            notifyDataSetChanged();
        }
    }

    public void removeItem(Invoice item, int position) {
        this.itemList.remove(position);
        notifyDataSetChanged();
    }
}