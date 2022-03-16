package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.PurchaseItemsViewHolder;
import com.example.erpnext.callbacks.PurchaseItemCallback;
import com.example.erpnext.models.SearchItemDetail;
import com.example.erpnext.utils.Notify;

import java.util.ArrayList;
import java.util.List;

public class PurchaseItemsAdapter extends RecyclerView.Adapter<PurchaseItemsViewHolder> {

    private final Context context;
    private final PurchaseItemCallback callback;
    private List<SearchItemDetail> itemList;
    private String docType;


    public PurchaseItemsAdapter(Context context, List<SearchItemDetail> itemList, PurchaseItemCallback callback, String docType) {

        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
        this.docType = docType;
    }

    @NonNull
    @Override
    public PurchaseItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.puchase_items_list_item, parent, false);
        return new PurchaseItemsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseItemsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position, this, docType);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<SearchItemDetail> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<SearchItemDetail> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(SearchItemDetail item) {
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

    public List<SearchItemDetail> getAllItems() {
        return this.itemList;
    }


    public void removeItem(SearchItemDetail cartItem) {
        List<SearchItemDetail> cartItems = new ArrayList<>();
        for (SearchItemDetail cartItem1 : itemList) {
            if (!cartItem.getItemCode().equalsIgnoreCase(cartItem1.getItemCode())) {
                cartItems.add(cartItem1);
            }
        }
        this.itemList = cartItems;
        notifyDataSetChanged();
    }
}