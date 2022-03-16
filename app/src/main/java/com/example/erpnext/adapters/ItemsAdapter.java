package com.example.erpnext.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.adapters.viewHolders.ItemsViewHolder;
import com.example.erpnext.callbacks.CartItemCallback;
import com.example.erpnext.models.CartItem;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsViewHolder> {

    private final Context context;
    private final CartItemCallback callback;
    private List<CartItem> itemList = new ArrayList<>();

    public ItemsAdapter(Context context, List<CartItem> itemList, CartItemCallback callback) {
        this.context = context;
        this.itemList = itemList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.product_item_list_item, parent, false);
        return new ItemsViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
        holder.setData(context, itemList.get(position), callback);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void filteredList(ArrayList<CartItem> filteredList) {
        this.itemList = filteredList;
        notifyDataSetChanged();
    }

    public void addItem(List<CartItem> items) {
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(CartItem item) {
        itemList.add(item);
        notifyDataSetChanged();
    }

    public List<CartItem> getAllItems() {
        return this.itemList;
    }

    public void changeQuantity(CartItem cartItem) {
        List<CartItem> cartItemList = new ArrayList<>();
        for (CartItem cartItem1 : itemList) {
            if (cartItem1.getItemCode().equalsIgnoreCase(cartItem.getItemCode())) {
                cartItem1.setActualQty(cartItem1.getActualQty() - 1);
                cartItemList.add(cartItem1);
            } else cartItemList.add(cartItem1);
        }
        itemList = cartItemList;
        notifyDataSetChanged();
    }

    public void changeQuantity(CartItem cartItem, float quantity) {

        List<CartItem> cartItemList = new ArrayList<>();
        for (CartItem cartItem1 : itemList) {
            if (cartItem1.getItemCode().equalsIgnoreCase(cartItem.getItemCode())) {
                cartItem1.setActualQty(cartItem1.getActualQty() + quantity);
                cartItemList.add(cartItem1);
            } else cartItemList.add(cartItem1);
        }
        itemList = cartItemList;
        notifyDataSetChanged();

    }

    public float getItemQuantity(CartItem cartItem) {
        float actualQuantity = 0;
        for (CartItem cartItem1 : itemList) {
            if (cartItem.getItemCode().equalsIgnoreCase(cartItem1.getItemCode())) {
                actualQuantity = cartItem1.getActualQty();
            }
        }
        return actualQuantity;
    }
}