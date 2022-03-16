package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.CartItemsViewHolder;
import com.example.erpnext.callbacks.CartItemCallback;
import com.example.erpnext.models.ItemDetail;

import java.util.ArrayList;
import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsViewHolder> {

    private final Context context;
    private final CartItemCallback callback;
    private List<ItemDetail> itemList;

    public CartItemsAdapter(Context context, List<ItemDetail> itemList, CartItemCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CartItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_list_item, parent, false);
        return new CartItemsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback, position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<ItemDetail> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<ItemDetail> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(ItemDetail item) {
        boolean hasItem = false;
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getItemCode().equalsIgnoreCase(item.getItemCode())) {
                hasItem = true;
            }
        }
        if (hasItem) {
            List<ItemDetail> cartItemList = new ArrayList<>();
            for (ItemDetail cartItem : itemList) {
                if (cartItem.getItemCode().equalsIgnoreCase(item.getItemCode())) {
                    ItemDetail cartItem1 = new ItemDetail();
                    cartItem1 = cartItem;
                    cartItem1.setQty(cartItem.getQty() + 1);
                    cartItemList.add(cartItem1);
                } else cartItemList.add(cartItem);
            }
            this.itemList = cartItemList;
        } else itemList.add(item);

        notifyDataSetChanged();
    }

    public List<ItemDetail> getAllItems() {
        return this.itemList;
    }

    public void setItem(ItemDetail cartItem1) {
        List<ItemDetail> cartItems = new ArrayList<>();
        for (ItemDetail cartItem : itemList) {
            if (cartItem.getItemCode().equalsIgnoreCase(cartItem1.getItemCode())) {
                cartItem.setActualQty(cartItem1.getActualQty());
                cartItems.add(cartItem);
            } else cartItems.add(cartItem);
        }
        this.itemList = cartItems;
        notifyDataSetChanged();
    }

    public void removeItem(ItemDetail cartItem) {
        List<ItemDetail> cartItems = new ArrayList<>();
        for (ItemDetail cartItem1 : itemList) {
            if (!cartItem.getItemCode().equalsIgnoreCase(cartItem1.getItemCode())) {
                cartItems.add(cartItem1);
            }
        }
        this.itemList = cartItems;
        notifyDataSetChanged();
    }
}