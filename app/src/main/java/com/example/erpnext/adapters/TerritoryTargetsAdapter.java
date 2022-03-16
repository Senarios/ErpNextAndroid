package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.TerritoryTargetsViewHolder;
import com.example.erpnext.callbacks.AddNewTerritoryCallBack;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.models.Target;
import com.example.erpnext.utils.Notify;

import java.util.ArrayList;
import java.util.List;

public class TerritoryTargetsAdapter extends RecyclerView.Adapter<TerritoryTargetsViewHolder> {

    private Context context;
    private AddNewTerritoryCallBack callback;
    private List<Target> itemList;

    public TerritoryTargetsAdapter(Context context, List<Target> itemList, AddNewTerritoryCallBack callback) {
        this.context = context;
        this.callback = callback;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public TerritoryTargetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.territory_targets_list_item, parent, false);
        return new TerritoryTargetsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TerritoryTargetsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, this);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<Target> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<Target> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Target item) {
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

    public List<Target> getAllItems() {
        return this.itemList;
    }


    public void removeItem(Target cartItem, int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }

    public void removeAll() {
        itemList.clear();
        notifyDataSetChanged();
    }
}
