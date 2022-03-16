package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.LandedCostItemsViewHolder;
import com.example.erpnext.callbacks.AddLandedCostCallback;
import com.example.erpnext.models.LandedCostItem;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.utils.Notify;

import java.util.ArrayList;
import java.util.List;

public class LandedCostItemsAdapter extends RecyclerView.Adapter<LandedCostItemsViewHolder> {

    private final Context context;
    private final AddLandedCostCallback callback;
    private List<LandedCostItem> itemList;

    public LandedCostItemsAdapter(Context context, List<LandedCostItem> itemList, AddLandedCostCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public LandedCostItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.warehouse_items_list_item, parent, false);
        return new LandedCostItemsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull LandedCostItemsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, this);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<LandedCostItem> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<LandedCostItem> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(LandedCostItem item) {
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

    public List<LandedCostItem> getAllItems() {
        return this.itemList;
    }


    public void removeItem(LandedCostItem cartItem, int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }

    public void removeAll() {
        itemList.clear();
        notifyDataSetChanged();
    }
}