package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.LandedCostReceiptsViewHolder;
import com.example.erpnext.callbacks.AddLandedCostCallback;
import com.example.erpnext.models.LandedCostReceipt;
import com.example.erpnext.utils.Notify;

import java.util.ArrayList;
import java.util.List;

public class LandedCostReceiptAdapter extends RecyclerView.Adapter<LandedCostReceiptsViewHolder> {

    private final Context context;
    private final AddLandedCostCallback callback;
    private List<LandedCostReceipt> itemList;

    public LandedCostReceiptAdapter(Context context, List<LandedCostReceipt> itemList, AddLandedCostCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }


    @NonNull
    @Override
    public LandedCostReceiptsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.landed_cost_receipt_list_item, parent, false);
        return new LandedCostReceiptsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull LandedCostReceiptsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, this);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<LandedCostReceipt> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<LandedCostReceipt> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(LandedCostReceipt item) {
        List<LandedCostReceipt> itemDetailList = new ArrayList<>();
        if (item != null) {
//            for (SearchItemDetail itemDetail:itemList){
//                if (item.getItemCode()!=itemDetail.getItemCode()&&item.getQty()!=itemDetail.getQty()&&item.getPriceListRate()!=it)
//            }
            itemList.add(item);
            Notify.Toast("Added");
            notifyDataSetChanged();
        }
    }

    public List<LandedCostReceipt> getAllItems() {
        return this.itemList;
    }


    public void removeItem(LandedCostReceipt cartItem, int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }

    public void removeAll() {
        itemList.clear();
        notifyDataSetChanged();
    }
}