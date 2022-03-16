package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.ApplicableChargesViewHolder;
import com.example.erpnext.callbacks.AddLandedCostCallback;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.models.Tax;
import com.example.erpnext.utils.Notify;

import java.util.ArrayList;
import java.util.List;

public class ApplicableChargesAdapter extends RecyclerView.Adapter<ApplicableChargesViewHolder> {

    private final Context context;
    private final AddLandedCostCallback callback;
    private List<Tax> itemList;

    public ApplicableChargesAdapter(Context context, List<Tax> itemList, AddLandedCostCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ApplicableChargesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.applicable_charges_list_item, parent, false);
        return new ApplicableChargesViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ApplicableChargesViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, this);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<Tax> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<Tax> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Tax item) {
        List<SearchItemDetail> itemDetailList = new ArrayList<>();
        if (item != null) {
//            for (SearchItemDetail itemDetail:itemList){
//                if (item.getItemCode()!=itemDetail.getItemCode()&&item.getQty()!=itemDetail.getQty()&&item.getPriceListRate()!=it)
//            }
            itemList.add(item);
            Notify.Toast("Added");
            notifyDataSetChanged();
        }
    }

    public List<Tax> getAllItems() {
        return this.itemList;
    }


    public void removeItem(Tax tax, int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }

    public void removeAll() {
        itemList.clear();
        notifyDataSetChanged();
    }
}