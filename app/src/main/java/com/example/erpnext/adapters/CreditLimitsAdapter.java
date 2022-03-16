package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.CreditLimitsViewHolder;
import com.example.erpnext.callbacks.CreditLimitCallback;
import com.example.erpnext.models.CreditLimit;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.utils.Notify;

import java.util.ArrayList;
import java.util.List;

public class CreditLimitsAdapter extends RecyclerView.Adapter<CreditLimitsViewHolder> {

    private final Context context;
    private final CreditLimitCallback callback;
    private List<CreditLimit> itemList;

    public CreditLimitsAdapter(Context context, List<CreditLimit> itemList, CreditLimitCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CreditLimitsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.credit_limit_list_item, parent, false);
        return new CreditLimitsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CreditLimitsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, this);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<CreditLimit> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<CreditLimit> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(CreditLimit item) {
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

    public List<CreditLimit> getAllItems() {
        return this.itemList;
    }


    public void removeItem(CreditLimit item, int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());
    }

    public void removeAll() {
        itemList.clear();
        notifyDataSetChanged();
    }
}