package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.ReferenceViewHolder;
import com.example.erpnext.callbacks.AddLandedCostCallback;
import com.example.erpnext.models.Reference;
import com.example.erpnext.models.Tax;
import com.example.erpnext.utils.Notify;

import java.util.ArrayList;
import java.util.List;

public class ReferenceAdapter extends RecyclerView.Adapter<ReferenceViewHolder> {

    private final Context context;
    private final AddLandedCostCallback callback;
    private List<Reference> itemList;

    public ReferenceAdapter(Context context, List<Reference> itemList, AddLandedCostCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ReferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.reference_list_item, parent, false);
        return new ReferenceViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ReferenceViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, this);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<Reference> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<Reference> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(Reference item) {
        List<Reference> itemDetailList = new ArrayList<>();
        if (item != null) {
//            for (SearchItemDetail itemDetail:itemList){
//                if (item.getItemCode()!=itemDetail.getItemCode()&&item.getQty()!=itemDetail.getQty()&&item.getPriceListRate()!=it)
//            }
            itemList.add(item);
            Notify.Toast("Added");
            notifyItemInserted(itemList.size() - 1);
        }
    }

    public List<Reference> getAllItems() {
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