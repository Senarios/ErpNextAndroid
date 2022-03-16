package com.example.erpnext.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erpnext.R;
import com.example.erpnext.callbacks.SearchItesmCallback;

import java.util.List;


public class SearchItemsViewHolder extends RecyclerView.ViewHolder {

    public TextView name, items;
    public RecyclerView linksRV;

    public View parent;

    public SearchItemsViewHolder(@NonNull View itemView) {
        super(itemView);
        parent = itemView;
        name = itemView.findViewById(R.id.name);
        items = itemView.findViewById(R.id.items);


    }

    public void setData(Context context, List<String> item, SearchItesmCallback callback) {
        name.setText(item.get(0));
        items.setText("");
        for (int i = 0; i < item.size(); i++) {
            if (i != 0) {
                if (item.get(i).isEmpty()) {
                    items.append(item.get(i));
                } else items.append(" ," + item.get(i));

            }
        }
        parent.setOnClickListener(v -> {
            callback.onItemClick(item);
        });
    }
}
